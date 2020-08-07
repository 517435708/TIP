package pl.pp.tiplab.securevoipclient.cryptographic;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import android.annotation.SuppressLint;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import pl.pp.tiplab.securevoipclient.ApplicationContext;

public class BasicMicRegister implements MicRegister {
    private AppCompatActivity context;
    private final String key = new ApplicationContext(context).getData().getAESKey();
    private static final String LOG_TAG = "AudioCall";
    private static final int SAMPLE_RATE = 8000; // Hertz
    private static final int SAMPLE_INTERVAL = 20; // Milliseconds
    private static final int SAMPLE_SIZE = 2; // Bytes
    private static final int BUF_SIZE = SAMPLE_INTERVAL * SAMPLE_INTERVAL * SAMPLE_SIZE * 2; //Bytes
    byte[] encryptKey = key.getBytes();
    @SuppressLint("GetInstance") Cipher cipher = Cipher.getInstance("AES");
    SecretKeySpec secretKeySpec = new SecretKeySpec(encryptKey, "AES");
    AudioRecord audioRecorder = new AudioRecord(MediaRecorder.AudioSource.VOICE_COMMUNICATION,
            SAMPLE_RATE,
            AudioFormat.CHANNEL_IN_MONO,
            AudioFormat.ENCODING_PCM_16BIT,
            AudioRecord.getMinBufferSize(SAMPLE_RATE,
                    AudioFormat.CHANNEL_IN_MONO,
                    AudioFormat.ENCODING_PCM_16BIT) * 10);
    AudioTrack track = new AudioTrack(AudioManager.STREAM_MUSIC,
            SAMPLE_RATE,
            AudioFormat.CHANNEL_OUT_MONO,
            AudioFormat.ENCODING_PCM_16BIT,
            BUF_SIZE,
            AudioTrack.MODE_STREAM);

    public BasicMicRegister() throws NoSuchPaddingException, NoSuchAlgorithmException {
    }

    @Override
    public byte[] sendVoiceMessage() throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {

        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            int bytes_read;
            byte[] buf = new byte[BUF_SIZE];
                audioRecorder.startRecording();
                    // Capture audio from the mic and transmit it
                    bytes_read = audioRecorder.read(buf, 0, BUF_SIZE);
                    byte[] encryptedData = new byte[bytes_read];
                    encryptedData = cipher.doFinal(buf);
                // Stop recording and release resources
                audioRecorder.stop();
                audioRecorder.release();
            return encryptedData;
            }

    @Override
    public void receiveMessage(byte[] encryptedVoice) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
                    byte[] decryptedVoice;
                    track.play();
                    cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
                    decryptedVoice = cipher.doFinal(encryptedVoice);
                        track.write(decryptedVoice, 0, BUF_SIZE);
                        track.stop();
                        track.flush();
                        track.release();
    }
}

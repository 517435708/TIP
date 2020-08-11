package pl.pp.tiplab.securevoipclient.cryptographic;

import android.annotation.SuppressLint;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;

import java.security.InvalidKeyException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.spec.SecretKeySpec;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import pl.pp.tiplab.securevoipclient.client.network.NetworkException;

public class BasicMicRegister implements MicRegister {

    private static final String LOG_TAG = "AudioCall";
    private static final int SAMPLE_RATE = 8000; // Hertz
    private static final int SAMPLE_INTERVAL = 20; // Milliseconds
    private static final int SAMPLE_SIZE = 2; // Bytes
    private static final int BUF_SIZE = SAMPLE_INTERVAL * SAMPLE_INTERVAL * SAMPLE_SIZE * 2; //Bytes
    @SuppressLint("GetInstance")
    private Cipher cipher;
    private SecretKeySpec secretKeySpec;
    private AudioRecord audioRecorder = new AudioRecord(MediaRecorder.AudioSource.VOICE_COMMUNICATION,
                                                        SAMPLE_RATE,
                                                        AudioFormat.CHANNEL_IN_MONO,
                                                        AudioFormat.ENCODING_PCM_16BIT,
                                                        AudioRecord.getMinBufferSize(SAMPLE_RATE,
                                                                                     AudioFormat.CHANNEL_IN_MONO,
                                                                                     AudioFormat.ENCODING_PCM_16BIT) * 10);
    private AudioTrack track = new AudioTrack(AudioManager.STREAM_MUSIC,
                                              SAMPLE_RATE,
                                              AudioFormat.CHANNEL_OUT_MONO,
                                              AudioFormat.ENCODING_PCM_16BIT,
                                              BUF_SIZE,
                                              AudioTrack.MODE_STREAM);

    @SneakyThrows
    public BasicMicRegister(String key) {
        cipher = Cipher.getInstance("AES");
        byte[] encryptKey = key.getBytes();
        secretKeySpec = new SecretKeySpec(encryptKey, "AES");
    }

    @Override
    public byte[] sendVoiceMessage() {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            byte[] buf = new byte[BUF_SIZE];
            audioRecorder.startRecording();
            audioRecorder.read(buf, 0, BUF_SIZE);
            return cipher.doFinal(buf);
        } catch (BadPaddingException | InvalidKeyException | IllegalBlockSizeException e) {
            e.printStackTrace();
            throw new NetworkException(e);
        } finally {
            audioRecorder.stop();
            audioRecorder.release();
        }
    }

    @Override
    public void receiveMessage(byte[] encryptedVoice) {
        try {
            track.play();
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            byte[] decryptedVoice = cipher.doFinal(encryptedVoice);
            track.write(decryptedVoice, 0, BUF_SIZE);
        } catch (BadPaddingException | InvalidKeyException | IllegalBlockSizeException e) {
            e.printStackTrace();
            throw new NetworkException(e);
        } finally {
            track.stop();
            track.flush();
            track.release();
        }
    }
}

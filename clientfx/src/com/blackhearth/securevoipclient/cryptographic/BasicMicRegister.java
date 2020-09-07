package com.blackhearth.securevoipclient.cryptographic;
import com.blackhearth.securevoipclient.ApplicationConstants;
import javafx.scene.media.AudioTrack;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.sound.sampled.*;
import javax.xml.transform.Source;

public class BasicMicRegister implements MicRegister {
    private static final int SAMPLE_RATE = 8000; // Hertz
    private static final int SAMPLE_INTERVAL = 20; // Milliseconds
    private static final int SAMPLE_SIZE = 2; // Bytes
    private static final int BUF_SIZE = SAMPLE_INTERVAL * SAMPLE_INTERVAL * SAMPLE_SIZE * 2; //Bytes
    private float sampleRate = 16000.0F;
    private int sampleInbits = 16;
    private int channels = 1;
    private boolean signed = true;
    private boolean bigEndian = false;
    private final String key = "something";
    private byte[] encryptKey = key.getBytes();
    private Cipher cipher = Cipher.getInstance("AES");
    private SecretKeySpec secretKeySpec = new SecretKeySpec(encryptKey, "AES");
    ByteArrayOutputStream byteOutputStream;
    private AudioFormat audioFormat;
    private TargetDataLine targetDataLine;
    AudioInputStream inputStream;
    private SourceDataLine sourceDataLine;

    public BasicMicRegister() throws NoSuchPaddingException, NoSuchAlgorithmException {
    }

    @Override
    public byte[] sendVoiceMessage() throws
                                     InvalidKeyException,
                                     BadPaddingException,
                                     IllegalBlockSizeException,
                                     LineUnavailableException {
        cipher.init(Cipher.ENCRYPT_MODE,secretKeySpec);
        int bytes_read;
        byte[] buf = new byte[BUF_SIZE];
        audioFormat = new AudioFormat(sampleRate,sampleInbits,channels,signed,bigEndian);
        DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class,audioFormat);
        targetDataLine.open(audioFormat);
        targetDataLine.start();
        bytes_read = targetDataLine.read(buf,0,BUF_SIZE);
        byte[] encryptedData = new byte[bytes_read];
        encryptedData = cipher.doFinal(buf);
        targetDataLine.stop();
        targetDataLine.flush();
        targetDataLine.close();
        return encryptedData;
    }

    @Override
    public void receiveMessage(byte[] encryptedVoice) throws
                                                      InvalidKeyException,
                                                      BadPaddingException,
                                                      IllegalBlockSizeException,
                                                      LineUnavailableException {
        byte[] decryptedVoice;
        AudioFormat audioFormat =  new AudioFormat(sampleRate,sampleInbits,channels,signed,bigEndian);
        sourceDataLine.start();
        cipher.init(Cipher.DECRYPT_MODE,secretKeySpec);
        decryptedVoice = cipher.doFinal(encryptedVoice);
        sourceDataLine.write(decryptedVoice,0,BUF_SIZE);
        sourceDataLine.stop();
        sourceDataLine.flush();
        sourceDataLine.close();
    }
}

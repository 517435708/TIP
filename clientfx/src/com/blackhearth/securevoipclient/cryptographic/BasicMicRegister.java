package com.blackhearth.securevoipclient.cryptographic;

import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.sound.sampled.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static com.blackhearth.securevoipclient.cryptographic.AudioConstants.*;

public class BasicMicRegister  implements MicRegister {

    private Cipher encrypt;
    private Cipher decrypt;
    private TargetDataLine microphone;
    private SourceDataLine speakers;
    private AudioFormat audioFormat;

    public BasicMicRegister(String key) {

//        this.microphone = microphone;
//        this.speakers = speakers;

        try {
            this.encrypt = Cipher.getInstance("AES");
            this.decrypt = Cipher.getInstance("AES");
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "AES");
            this.audioFormat = new AudioFormat(SAMPLE_RATE, SAMPLE_INBITS, CHANNELS, SIGNED, BIG_ENDIAN);
            encrypt.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            decrypt.init(Cipher.DECRYPT_MODE, secretKeySpec);
            DataLine.Info sendData = new DataLine.Info(TargetDataLine.class, audioFormat);
            DataLine.Info receiveData = new DataLine.Info(SourceDataLine.class, audioFormat);
            this.microphone = (TargetDataLine) AudioSystem.getLine(sendData);
            this.speakers = (SourceDataLine) AudioSystem.getLine(receiveData);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | LineUnavailableException e) {
            System.out.println("Something went wrong");
        }
    }


    @Override
    public byte[] sendVoiceMessage() {
        int bytesRead;
        byte[] buf = new byte[BUF_SIZE];
        byte[] encryptedData;
        try {
            //microphone = (TargetDataLine) AudioSystem.getLine(sendData);
            microphone.open(audioFormat);
            microphone.start();
            bytesRead = microphone.read(buf, 0, BUF_SIZE);
            byte[] data = new byte[bytesRead];
            encryptedData = encrypt.doFinal(data);
            microphone.stop();
            microphone.flush();
            microphone.close();
            return encryptedData;
        } catch (LineUnavailableException | IllegalBlockSizeException | BadPaddingException e) {
            return new byte[0];
        }
    }


    @Override
    public void receiveMessage(byte[] encryptedVoice) {
        try {
            byte[] decryptedVoice = decrypt.doFinal(encryptedVoice);
            //DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, audioFormat);
            //speakers = (SourceDataLine) AudioSystem.getLine(receiveData);
            speakers.open(audioFormat);
            speakers.start();
            speakers.write(decryptedVoice, 0, BUF_SIZE);
            speakers.stop();
            speakers.flush();
            speakers.close();
        } catch (LineUnavailableException | IllegalBlockSizeException | BadPaddingException e) {
            System.out.println("Something went wrong");
        }
    }
}

package com.blackhearth.securevoipclient.cryptographic;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.sound.sampled.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static com.blackhearth.securevoipclient.cryptographic.AudioConstants.*;

public class BasicMicRegister implements MicRegister {

    private Cipher encrypt;
    private Cipher decrypt;
    private TargetDataLine microphone;
    private SourceDataLine speakers;

    public BasicMicRegister(String key) {
        key = generateKey(key);

        try {
            String algorithm = "AES";
            this.encrypt = Cipher.getInstance(algorithm);
            this.decrypt = Cipher.getInstance(algorithm);

            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "AES");
            AudioFormat audioFormat = new AudioFormat(SAMPLE_RATE, SAMPLE_INBITS, CHANNELS, SIGNED, BIG_ENDIAN);
            encrypt.init(Cipher.ENCRYPT_MODE, secretKey);
            decrypt.init(Cipher.DECRYPT_MODE, secretKey);
            DataLine.Info sendData = new DataLine.Info(TargetDataLine.class, audioFormat);
            DataLine.Info receiveData = new DataLine.Info(SourceDataLine.class, audioFormat);
            this.microphone = (TargetDataLine) AudioSystem.getLine(sendData);
            this.speakers = (SourceDataLine) AudioSystem.getLine(receiveData);
            speakers.open(audioFormat);
            speakers.start();
            microphone.open(audioFormat);
            microphone.start();
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | LineUnavailableException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private String generateKey(String key) {

        StringBuilder myKey = new StringBuilder();
        for (int i = 0; i < 32; i++) {
            myKey.append(key.charAt(i*5 % key.length()));
        }
        return myKey.toString();
    }


    @Override
    public byte[] sendVoiceMessage() {
        byte[] buf = new byte[BUF_SIZE];
        try {
            microphone.read(buf, 0, BUF_SIZE);
            return encrypt.doFinal(buf);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            microphone.stop();
            microphone.flush();
            microphone.close();
            e.printStackTrace();
            return new byte[0];
        }
    }


    @Override
    public void receiveMessage(byte[] encryptedVoice) {
        try {
            byte[] decryptedVoice = decrypt.doFinal(encryptedVoice);
            speakers.write(decryptedVoice, 0, BUF_SIZE);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            speakers.stop();
            speakers.flush();
            speakers.close();
            e.printStackTrace();
        }
    }

    @Override
    public void flush() {
        microphone.stop();
        microphone.flush();
        microphone.close();

        speakers.stop();
        speakers.flush();
        speakers.close();
    }


}

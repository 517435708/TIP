package com.blackhearth.securevoipclient.cryptographic;

import org.springframework.stereotype.Component;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.sound.sampled.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import static com.blackhearth.securevoipclient.cryptographic.AudioConstants.*;

public class BasicMicRegister  implements MicRegister {

    private Cipher encrypt;
    private Cipher decrypt;
    private TargetDataLine microphone;
    private SourceDataLine speakers;
    private AudioFormat audioFormat;


    private static String salt = "ssshhhhhhhhhhh!!!!";
    private final String ALGORITHM = "AES/CBC/PKCS5Padding";

    public BasicMicRegister(String key) {

//        this.microphone = microphone;
//        this.speakers = speakers;

        try {
            byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
            IvParameterSpec ivspec = new IvParameterSpec(iv);

            this.encrypt = Cipher.getInstance(ALGORITHM);
            this.decrypt = Cipher.getInstance(ALGORITHM);

            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(key.toCharArray(), salt.getBytes(), 65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");
            this.audioFormat = new AudioFormat(SAMPLE_RATE, SAMPLE_INBITS, CHANNELS, SIGNED, BIG_ENDIAN);
            encrypt.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);
            decrypt.init(Cipher.DECRYPT_MODE, secretKey, ivspec);
            DataLine.Info sendData = new DataLine.Info(TargetDataLine.class, audioFormat);
            DataLine.Info receiveData = new DataLine.Info(SourceDataLine.class, audioFormat);
            this.microphone = (TargetDataLine) AudioSystem.getLine(sendData);
            this.speakers = (SourceDataLine) AudioSystem.getLine(receiveData);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | LineUnavailableException | InvalidKeySpecException | InvalidAlgorithmParameterException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
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

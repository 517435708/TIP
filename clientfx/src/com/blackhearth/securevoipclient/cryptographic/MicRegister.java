package com.blackhearth.securevoipclient.cryptographic;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public interface MicRegister {
    byte[] sendVoiceMessage();
    void receiveMessage(byte[] encryptedVoice);
}

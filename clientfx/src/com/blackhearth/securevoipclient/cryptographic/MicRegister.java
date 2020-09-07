package com.blackhearth.securevoipclient.cryptographic;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.sound.sampled.LineUnavailableException;

public interface MicRegister {
    byte[] sendVoiceMessage() throws
                              InvalidKeyException,
                              BadPaddingException,
                              IllegalBlockSizeException,
                              LineUnavailableException;
    void receiveMessage(byte[] encryptedVoice) throws
                                               InvalidKeyException,
                                               BadPaddingException,
                                               IllegalBlockSizeException,
                                               LineUnavailableException;
}

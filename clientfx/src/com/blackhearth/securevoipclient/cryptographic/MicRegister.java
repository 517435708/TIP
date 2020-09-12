package com.blackhearth.securevoipclient.cryptographic;



public interface MicRegister {
    byte[] sendVoiceMessage();
    void receiveMessage(byte[] encryptedVoice);
}

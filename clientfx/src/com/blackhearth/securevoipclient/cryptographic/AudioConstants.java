package com.blackhearth.securevoipclient.cryptographic;

class AudioConstants {
    private static final int SAMPLE_INTERVAL = 20; // Milliseconds
    private static final int SAMPLE_SIZE = 2; // Bytes
    static final int BUF_SIZE = SAMPLE_INTERVAL * SAMPLE_INTERVAL * SAMPLE_SIZE * 2; //Bytes
    static final float sampleRate = 16000.0F;
    static final int sampleInbits = 16;
    static final int channels = 1;
    static final boolean signed = true;
    static final boolean bigEndian = false;


    AudioConstants() {

    }
}

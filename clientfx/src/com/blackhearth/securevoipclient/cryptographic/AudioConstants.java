package com.blackhearth.securevoipclient.cryptographic;

final class AudioConstants {
    private static final int SAMPLE_INTERVAL = 20; // Milliseconds
    private static final int SAMPLE_SIZE = 2; // Bytes
    static final int BUF_SIZE = SAMPLE_INTERVAL * SAMPLE_INTERVAL * SAMPLE_SIZE * 2; //Bytes
    static final float SAMPLE_RATE = 16000.0F;
    static final int SAMPLE_INBITS = 16;
    static final int CHANNELS = 1;
    static final boolean SIGNED = true;
    static final boolean BIG_ENDIAN = false;


    private AudioConstants() {

    }
}

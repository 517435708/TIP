package com.blackhearth.securevoipclient.rsa;


import java.util.Random;

import lombok.Getter;

@Getter
public class Random128bit {
    private String result = "";
    private Random random = new Random();

    public Random128bit() {
        for (int i = 0; i < 16; i++) {
            int myRandomNumber = random.nextInt(0x10) + 0x10;
            result += Integer.toHexString(myRandomNumber);
        }
    }

}

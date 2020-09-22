package com.blackhearth.securevoipclient.rsa;


import java.util.Random;

import lombok.Getter;

@Getter
public class Random128bit {
    private String result = "";
    private Random random = new Random();

    public Random128bit() {
        random.ints(16, 33, 126).forEach(c -> result += Character.toString(c));
    }

}

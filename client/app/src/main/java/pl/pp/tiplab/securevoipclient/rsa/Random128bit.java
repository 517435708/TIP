package pl.pp.tiplab.securevoipclient.rsa;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Random;

import lombok.Getter;

@RequiresApi(api = Build.VERSION_CODES.N)
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

package pl.pp.tiplab.securevoipclient;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.nio.charset.Charset;
import java.util.Random;

import lombok.Getter;
import lombok.Setter;

@RequiresApi(api = Build.VERSION_CODES.N)
@Getter

public class Random128bit {
    String result = "";
    Random random = new Random();

    public Random128bit() {
        for (int i = 0; i < 16; i++) {
            int myRandomNumber = random.nextInt(0x10) + 0x10;
            this.result += Integer.toHexString(myRandomNumber);

        }
    }

}

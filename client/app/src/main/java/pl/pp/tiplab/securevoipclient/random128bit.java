package pl.pp.tiplab.securevoipclient;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.nio.charset.Charset;
import java.util.Random;

import lombok.Getter;
import lombok.Setter;

@RequiresApi(api = Build.VERSION_CODES.N)
@Getter

public class random128bit {
    String randomText = "";
    String result = "";
    Random random = new Random();

    public random128bit() {
        for (int i = 0; i < 32; i++) {
            int myRandomNumber = random.nextInt(0x10) + 0x10;
            result = result + Integer.toHexString(myRandomNumber);
            i++;
        }
        this.randomText = result;
    }
}

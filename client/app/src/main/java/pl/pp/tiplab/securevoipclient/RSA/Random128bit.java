package pl.pp.tiplab.securevoipclient.RSA;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.nio.charset.Charset;
import java.util.Random;

import lombok.Getter;
import lombok.Setter;

@RequiresApi(api = Build.VERSION_CODES.N)
@Getter
class Random128bit {
    private String result = "";
    private Random random = new Random();

    Random128bit() {
        for (int i = 0; i < 16; i++) {
            int myRandomNumber = random.nextInt(0x10) + 0x10;
            result += Integer.toHexString(myRandomNumber);
        }
    }

}

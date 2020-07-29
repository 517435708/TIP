package pl.pp.tiplab.securevoipclient.client.network;

import android.os.AsyncTask;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import pl.pp.tiplab.securevoipclient.ApplicationContext;

@AllArgsConstructor
public class SocketTaskManager extends AsyncTask<String, Void, Void> {

    private ApplicationContext applicationContext;

    @SneakyThrows
    @Override
    protected Void doInBackground(String... ignored) {
        PacketReceiver packetReceiver = new PacketReceiver();
        packetReceiver.setApplicationContext(applicationContext);

        PacketSender packetSender = new PacketSender();
        packetReceiver.setApplicationContext(applicationContext);

        packetReceiver.start();
        packetSender.start();

        return null;
    }
}

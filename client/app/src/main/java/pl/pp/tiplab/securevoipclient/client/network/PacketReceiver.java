package pl.pp.tiplab.securevoipclient.client.network;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import lombok.Setter;
import pl.pp.tiplab.securevoipclient.ApplicationContext;

import static pl.pp.tiplab.securevoipclient.ApplicationConstants.APPLICATION_HOST;
import static pl.pp.tiplab.securevoipclient.client.network.NetworkConstants.MAX_MESSAGE_RECEIVED;

@Setter
public class PacketReceiver extends Thread {

    private ApplicationContext applicationContext;

    @Override
    public void run() {
        try (DatagramSocket receiveSoc = new DatagramSocket(0,
                                                            InetAddress.getByName(APPLICATION_HOST))) {

            byte[] buf = new byte[MAX_MESSAGE_RECEIVED];
            DatagramPacket pack = new DatagramPacket(buf, buf.length);
            while (applicationContext.isRunning()) {
                receiveSoc.receive(pack);
                applicationContext.put(pack);
            }

        } catch (IOException e) {
            throw new NetworkException(e);
        }

    }
}

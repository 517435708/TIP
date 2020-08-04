package pl.pp.tiplab.securevoipclient.client.network;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.BlockingQueue;

import lombok.AllArgsConstructor;
import lombok.Setter;

import static pl.pp.tiplab.securevoipclient.ApplicationConstants.APPLICATION_HOST;
import static pl.pp.tiplab.securevoipclient.ApplicationConstants.APPLICATION_PORT;

@AllArgsConstructor
public class ClientSend implements Runnable {

    private BlockingQueue<byte[]> data;

    @Override
    public void run() {
        try (DatagramSocket udpSocket = new DatagramSocket(APPLICATION_PORT)) {
            InetAddress serverAddress = InetAddress.getByName(APPLICATION_HOST);
            byte[] buf = ("The String to Send").getBytes();
            DatagramPacket packet = new DatagramPacket(buf, buf.length,serverAddress, APPLICATION_PORT);
            udpSocket.send(packet);
        } catch (IOException e) {
            throw new NetworkException(e);
        }
    }
}

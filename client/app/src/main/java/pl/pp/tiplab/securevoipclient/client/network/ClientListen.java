package pl.pp.tiplab.securevoipclient.client.network;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.concurrent.BlockingQueue;

import lombok.AllArgsConstructor;

import static pl.pp.tiplab.securevoipclient.client.network.NetworkConstants.MAX_MESSAGE_RECEIVED;

@AllArgsConstructor
public class ClientListen implements Runnable {

    static final int LISTEN_CLIENT_PORT = 12345;
    private BlockingQueue<byte[]> data;

    @Override
    public void run() {
        boolean run = true;
        while (run) {
            try (DatagramSocket udpSocket = new DatagramSocket(LISTEN_CLIENT_PORT)) {
                byte[] message = new byte[MAX_MESSAGE_RECEIVED];
                DatagramPacket packet = new DatagramPacket(message, message.length);
                udpSocket.receive(packet);
                System.out.println("RECEIVED: " + new String(packet.getData()).trim() + " SPACJA " + new String(
                        message).trim());
                data.add(message);
            } catch (IOException e) {
                run = false;
            }
        }
    }
}

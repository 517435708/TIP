package pl.pp.tiplab.securevoipclient.client.network;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.concurrent.BlockingQueue;

import lombok.AllArgsConstructor;
import lombok.Setter;

import static pl.pp.tiplab.securevoipclient.ApplicationConstants.APPLICATION_PORT;
import static pl.pp.tiplab.securevoipclient.client.network.NetworkConstants.MAX_MESSAGE_RECEIVED;

@AllArgsConstructor
public class ClientListen implements Runnable {

    private BlockingQueue<byte[]> data;

    @Override
    public void run() {
        boolean run = true;
        while (run) {
            try (DatagramSocket udpSocket = new DatagramSocket(APPLICATION_PORT)){
                byte[] message = new byte[MAX_MESSAGE_RECEIVED];
                DatagramPacket packet = new DatagramPacket(message,message.length);
                udpSocket.receive(packet);
                String text = new String(message, 0, packet.getLength());
                System.out.println(text);
            }catch (IOException e) {
                run = false;
            }
        }
    }
}

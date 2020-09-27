package pl.pp.tiplab.securevoipclient.client.network;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.BlockingQueue;

import lombok.AllArgsConstructor;

import static pl.pp.tiplab.securevoipclient.ApplicationConstants.APPLICATION_HOST;
import static pl.pp.tiplab.securevoipclient.ApplicationConstants.APPLICATION_PORT;

@AllArgsConstructor
public class ClientSender implements Runnable {

    private static final int SENDER_CLIENT_PORT = 12346;
    private BlockingQueue<byte[]> data;

    @Override
    public void run() {
        boolean run = true;
        while (run) {
            while (!data.isEmpty() && run) {
                System.out.println("Trying to send data!");
                System.out.println(new String(data.peek()));
                try (DatagramSocket udpSocket = new DatagramSocket(SENDER_CLIENT_PORT)) {
                    InetAddress serverAddress = InetAddress.getByName(APPLICATION_HOST);
                    byte[] message = data.poll();
                    sendMessageIfNotNull(message, serverAddress, udpSocket);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                    run = false;
                }
            }
        }
    }

    private void sendMessageIfNotNull(byte[] message,
                                      InetAddress serverAddress,
                                      DatagramSocket udpSocket) throws
                                                                IOException {
        if (message != null) {
            DatagramPacket packet = new DatagramPacket(message,
                                                       message.length,
                                                       serverAddress,
                                                       APPLICATION_PORT);
            udpSocket.send(packet);
        }
    }
}

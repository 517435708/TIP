package pl.pp.tiplab.udptest.client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.SocketException;

public class UdpClient implements Runnable {

    private final DatagramSocket socket = new DatagramSocket(1337);


    private final ServerSocket serverSocket = new ServerSocket();

    public UdpClient() throws
                       IOException {
    }

    @Override
    public void run() {
        byte[] message = new byte[1024];
        while (true) {
            try {
                DatagramPacket packet = new DatagramPacket(message, message.length);
                socket.receive(packet);
                System.out.println(new String(message).trim());
            } catch (Exception ex) {
                //ignored;
            }
        }

    }
}

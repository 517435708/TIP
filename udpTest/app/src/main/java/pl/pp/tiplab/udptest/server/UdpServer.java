package pl.pp.tiplab.udptest.server;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UdpServer implements Runnable {


    private final DatagramSocket socket = new DatagramSocket(2137);

    public UdpServer() throws
                       SocketException {
    }

    @Override
    public void run() {

        while (true) {
            try {

                byte[] message = "NOTIFY".getBytes();
                DatagramPacket packet = new DatagramPacket(message,
                                                           message.length,
                                                           InetAddress.getByName("192.168.1.81"),
                                                           6663);
                socket.send(packet);
                Thread.sleep(1000);
            } catch (Exception ex) {
                //ignored
            }


        }

    }
}

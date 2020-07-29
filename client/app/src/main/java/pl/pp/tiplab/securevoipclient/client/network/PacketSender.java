package pl.pp.tiplab.securevoipclient.client.network;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

import lombok.Setter;
import pl.pp.tiplab.securevoipclient.ApplicationContext;

import static pl.pp.tiplab.securevoipclient.ApplicationConstants.APPLICATION_HOST;
import static pl.pp.tiplab.securevoipclient.ApplicationConstants.APPLICATION_PORT;

@Setter
public class PacketSender extends Thread {

    private ApplicationContext context;

    @Override
    public void run() {
        try (DatagramSocket socket = new DatagramSocket()) {

            while (context.isRunning()) {
                while (!context.isEmpty()) {
                    InetSocketAddress address = new InetSocketAddress(APPLICATION_HOST,
                                                                      APPLICATION_PORT);
                    byte[] buffer = context.getMessagesToSend()
                                           .pop()
                                           .getBytes();
                    DatagramPacket packet = new DatagramPacket(buffer,
                                                               buffer.length,
                                                               address.getAddress(),
                                                               address.getPort());
                    socket.send(packet);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

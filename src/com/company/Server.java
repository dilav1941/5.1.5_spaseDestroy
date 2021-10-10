package com.company;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class Server {
    public static void main(String[] args) throws IOException {
        final ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.bind(new InetSocketAddress("localhost", 15236));
        SocketChannel socketChannel = serverChannel.accept();
        final ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        while (socketChannel.isConnected()){
            int bytesCount = socketChannel.read(byteBuffer);
            if (bytesCount == -1) {
                break;
            }
            final String msg = new String(byteBuffer.array(), 0, bytesCount, StandardCharsets.UTF_8);
            byteBuffer.clear();
            System.out.println("Получено сообщение от клиента: " + msg);
            socketChannel.write(ByteBuffer.wrap(("Сообщение без пробелов " + msg.replaceAll(" ", "") + "\n")
                    .getBytes(StandardCharsets.UTF_8)));
        }
    }
}

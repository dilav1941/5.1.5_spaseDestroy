package com.company;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException, InterruptedException {
        InetSocketAddress socketAddress = new InetSocketAddress("127.0.0.1", 15236);
        final SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(socketAddress);
        Scanner scanner = new Scanner(System.in);
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        String msg;

        while (true) {
            System.out.println("Введите сообщение для сервера");
            msg = scanner.nextLine();
            if ("end".equals(msg)) {
                break;
            }
            socketChannel.write(ByteBuffer.wrap(msg.getBytes(StandardCharsets.UTF_8)));
            Thread.sleep(2000);
            int bytesCount = socketChannel.read(byteBuffer);
            System.out.println(new String(byteBuffer.array(), 0, bytesCount, StandardCharsets.UTF_8));
            byteBuffer.clear();
        }
        socketChannel.close();
    }
}

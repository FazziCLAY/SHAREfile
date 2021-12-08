package ru.fazziclay.sharefile;

import com.google.gson.Gson;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import ru.fazziclay.sharefile.packet.Packet;
import ru.fazziclay.sharefile.packet.PacketId;
import ru.fazziclay.sharefile.packet.PacketProcessingErrorPacket;
import ru.fazziclay.sharefile.packet.SendMessagePacket;
import ru.fazziclay.sharefile.packet.StartFileSendingPacket;

public class Server extends Thread {
    ServerHandler serverHandler;
    List<ClientHandler> clients = new ArrayList<>();
    ServerSocket serverSocket = null;


    public Server(ServerHandler serverHandler) {
        this.serverHandler = serverHandler;
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(SharedConstrains.APP_PORT);
            ClientHandler clientHandler = new ClientHandler(serverSocket.accept());
            clients.add(clientHandler);
            clientHandler.start();

        } catch (IOException e) {
            serverHandler.onError(e);
        }
    }

    static class ClientHandler extends Thread {
        Socket socket;
        Scanner is;
        OutputStream os;
        Gson gson = new Gson();

        boolean isHanshaked = false;
        boolean isDataFile = false;

        void sendPacket(Packet packet, Class<? extends Packet> cls) throws IOException {
            String packetStr = gson.toJson(packet, cls);
            os.write(packetStr.getBytes(StandardCharsets.UTF_8));
            os.flush();
        }

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                is = new Scanner(socket.getInputStream());
                os = socket.getOutputStream();

                while (!socket.isInputShutdown()) {
                    if (!is.hasNextLine()) continue;
                    String packetStr = is.nextLine();
                    try {
                        Packet packetBase = gson.fromJson(packetStr, Packet.class);
                        if (packetBase.getId() == PacketId.HANDSHAKE.i) {
                            isHanshaked = true;
                            continue;
                        }
                        if (!isHanshaked) {
                            sendPacket(new PacketProcessingErrorPacket(new IllegalAccessException()), PacketProcessingErrorPacket.class);
                            socket.close();
                            continue;
                        }

                        if (packetBase.getId() == PacketId.SEND_MESSAGE.i) {
                            SendMessagePacket s = gson.fromJson(packetStr, SendMessagePacket.class);
                            continue;
                        }


                    } catch (Exception e) {
                        sendPacket(new PacketProcessingErrorPacket(e), PacketProcessingErrorPacket.class);
                        socket.close();
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public interface ServerHandler {
        void onError(Exception e);
    }
}

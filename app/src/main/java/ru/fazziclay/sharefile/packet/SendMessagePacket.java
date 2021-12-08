package ru.fazziclay.sharefile.packet;

public class SendMessagePacket extends Packet {
    String message;

    public SendMessagePacket(String message) {
        super(PacketId.SEND_MESSAGE);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

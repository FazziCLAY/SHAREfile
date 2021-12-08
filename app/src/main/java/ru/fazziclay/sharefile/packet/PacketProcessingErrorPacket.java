package ru.fazziclay.sharefile.packet;

public class PacketProcessingErrorPacket extends Packet {
    String error;

    public PacketProcessingErrorPacket(Exception e) {
        super(PacketId.PACKET_PROCESSING_ERROR);
        error = e.toString();
    }

    public String getError() {
        return error;
    }
}

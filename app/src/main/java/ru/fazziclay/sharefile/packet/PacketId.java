package ru.fazziclay.sharefile.packet;

public enum PacketId {
    SEND_MESSAGE(3),
    PACKET_PROCESSING_ERROR(2),
    STARTING_FILE_SENDING(1),
    HANDSHAKE(1);

    public int i;
    PacketId(int i) {
        this.i = i;
    }
}

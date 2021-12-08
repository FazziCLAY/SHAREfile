package ru.fazziclay.sharefile.packet;

public class StartFileSendingPacket extends Packet {
    long size;
    String name;

    public StartFileSendingPacket() {
        super(PacketId.STARTING_FILE_SENDING);
    }

    public long getSize() {
        return size;
    }

    public String getName() {
        return name;
    }
}

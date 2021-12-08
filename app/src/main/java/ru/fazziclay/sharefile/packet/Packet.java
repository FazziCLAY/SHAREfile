package ru.fazziclay.sharefile.packet;

public class Packet {
    int id;

    public Packet(PacketId id) {
        this.id = id.i;
    }

    public int getId() {
        return id;
    }
}

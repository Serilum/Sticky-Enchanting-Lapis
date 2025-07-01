package com.natamus.stickyenchantinglapis.networking;

import com.natamus.collective.implementations.networking.api.Network;
import com.natamus.stickyenchantinglapis.networking.packets.ToClientReceiveLapisCountPacket;

public class PacketRegistration {

    public void init() {
        initClientPackets();
        initServerPackets();
    }

    private void initClientPackets() {
        Network.registerPacket(ToClientReceiveLapisCountPacket.CHANNEL, ToClientReceiveLapisCountPacket.class, ToClientReceiveLapisCountPacket::encode, ToClientReceiveLapisCountPacket::decode, ToClientReceiveLapisCountPacket::handle);
    }

    private void initServerPackets() {

    }
}

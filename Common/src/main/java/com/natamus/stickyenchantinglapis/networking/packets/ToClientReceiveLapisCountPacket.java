package com.natamus.stickyenchantinglapis.networking.packets;

import com.natamus.collective.implementations.networking.data.PacketContext;
import com.natamus.collective.implementations.networking.data.Side;
import com.natamus.stickyenchantinglapis.util.ClientUtil;
import com.natamus.stickyenchantinglapis.util.Reference;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class ToClientReceiveLapisCountPacket {
    public static final ResourceLocation CHANNEL = new ResourceLocation(Reference.MOD_ID, "to_client_receive_lapis_count_packet");

    private static BlockPos enchantingTableBlockPos;
    private static int lapisCount;

    public ToClientReceiveLapisCountPacket(BlockPos enchantingTableBlockPosIn, int lapisCountIn) {
        enchantingTableBlockPos = enchantingTableBlockPosIn;
        lapisCount = lapisCountIn;
    }

    public static ToClientReceiveLapisCountPacket decode(FriendlyByteBuf buf) {
        BlockPos enchantingTableBlockPosIn = buf.readBlockPos();
        int lapisCountIn = buf.readInt();

        return new ToClientReceiveLapisCountPacket(enchantingTableBlockPosIn, lapisCountIn);
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeBlockPos(enchantingTableBlockPos);
        buf.writeInt(lapisCount);
    }

    public static void handle(PacketContext<ToClientReceiveLapisCountPacket> ctx) {
        if (ctx.side().equals(Side.CLIENT)) {
            ClientUtil.syncLapisToClients(enchantingTableBlockPos, lapisCount);
        }
    }
}

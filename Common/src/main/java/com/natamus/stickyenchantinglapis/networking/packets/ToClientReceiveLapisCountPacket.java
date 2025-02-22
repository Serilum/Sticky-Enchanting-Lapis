package com.natamus.stickyenchantinglapis.networking.packets;

import com.natamus.collective.implementations.networking.data.PacketContext;
import com.natamus.collective.implementations.networking.data.Side;
import com.natamus.stickyenchantinglapis.util.ClientUtil;
import com.natamus.stickyenchantinglapis.util.Reference;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class ToClientReceiveLapisCountPacket {
	public static final ResourceLocation CHANNEL = ResourceLocation.fromNamespaceAndPath(Reference.MOD_ID, "to_client_receive_lapis_count_packet");

	private final int lapisCount;
	private final int enchantingTableXPosition;
	private final int enchantingTableYPosition;
	private final int enchantingTableZPosition;

	public ToClientReceiveLapisCountPacket(int lapisCountIn, int enchantingTableXPositionIn, int enchantingTableYPositionIn, int enchantingTableZPositionIn) {
		this.lapisCount = lapisCountIn;
		this.enchantingTableXPosition = enchantingTableXPositionIn;
		this.enchantingTableYPosition = enchantingTableYPositionIn;
		this.enchantingTableZPosition = enchantingTableZPositionIn;
	}

	public static ToClientReceiveLapisCountPacket decode(FriendlyByteBuf buf) {
		int lapisCountIn = buf.readInt();
		int enchantingTableXPosition = buf.readInt();
		int enchantingTableYPosition = buf.readInt();
		int enchantingTableZPosition = buf.readInt();

		return new ToClientReceiveLapisCountPacket(lapisCountIn, enchantingTableXPosition, enchantingTableYPosition, enchantingTableZPosition);
	}

	public void encode(FriendlyByteBuf buf) {
		buf.writeInt(lapisCount);
		buf.writeInt(enchantingTableXPosition);
		buf.writeInt(enchantingTableYPosition);
		buf.writeInt(enchantingTableZPosition);
	}

	public static void handle(PacketContext<ToClientReceiveLapisCountPacket> ctx) {
		if (ctx.side().equals(Side.CLIENT)) {
			ToClientReceiveLapisCountPacket packet = ctx.message();
			ClientUtil.syncLapisToClients(packet.lapisCount, new BlockPos(packet.enchantingTableXPosition, packet.enchantingTableYPosition, packet.enchantingTableZPosition));
		}
	}
}
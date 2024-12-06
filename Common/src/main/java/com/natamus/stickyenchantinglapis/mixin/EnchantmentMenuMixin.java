package com.natamus.stickyenchantinglapis.mixin;

import com.natamus.collective.implementations.networking.api.Dispatcher;
import com.natamus.stickyenchantinglapis.data.Variables;
import com.natamus.stickyenchantinglapis.networking.packets.ToClientReceiveLapisCountPacket;
import com.natamus.stickyenchantinglapis.util.Util;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.EnchantmentMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.EnchantingTableBlockEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

@Mixin(value = EnchantmentMenu.class, priority = 1001)
public class EnchantmentMenuMixin {
	@Shadow private @Final Container enchantSlots;
	@Unique private Player player;

	@Inject(method = "<init>(ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/inventory/ContainerLevelAccess;)V", at = @At(value = "TAIL"))
	public void EnchantmentMenu(int i, Inventory inventory, ContainerLevelAccess containerLevelAccess, CallbackInfo ci) {
		this.player = inventory.player;

		EnchantingTableBlockEntity enchantingTableBlockEntity = Util.getEnchantingTableBlockEntity(player);
		if (enchantingTableBlockEntity != null) {
			int lapisCount = Util.getLapisCount(player.level(), enchantingTableBlockEntity);
			if (lapisCount > 0) {
				enchantSlots.setItem(1, new ItemStack(Items.LAPIS_LAZULI, lapisCount));
			}
		}
	}

	@Inject(method = "slotsChanged(Lnet/minecraft/world/Container;)V", at = @At(value = "TAIL"))
	public void slotsChanged(Container container, CallbackInfo ci) {
		if (this.player == null) {
			return;
		}

		int lapisCount = 0;

		ItemStack itemStack = enchantSlots.getItem(1);
		if (itemStack.getItem().equals(Items.LAPIS_LAZULI)) {
			lapisCount = itemStack.getCount();
		}

		EnchantingTableBlockEntity enchantingTableBlockEntity = Util.getEnchantingTableBlockEntity(this.player);
		if (enchantingTableBlockEntity != null) {
			Level level = this.player.level();

			if (lapisCount == Util.getLapisCount(level, enchantingTableBlockEntity)) {
				return;
			}

			Util.saveLapisCount(level, enchantingTableBlockEntity, lapisCount);

			if (!level.isClientSide) {
				Dispatcher.sendToClientsInLevel(new ToClientReceiveLapisCountPacket(enchantingTableBlockEntity.getBlockPos(), lapisCount), (ServerLevel)level);

				for (Player otherPlayer : level.getServer().getPlayerList().getPlayers()) {
					UUID otherPlayerUUID = otherPlayer.getUUID();
					if (otherPlayerUUID.equals(this.player.getUUID())) {
						continue;
					}

					if (!Variables.lastEnchantingTableInteraction.containsKey(otherPlayerUUID)) {
						return;
					}

					if (!enchantingTableBlockEntity.getBlockPos().equals(Variables.lastEnchantingTableInteraction.get(otherPlayerUUID))) {
						return;
					}

					if (otherPlayer.containerMenu instanceof EnchantmentMenu) {
						otherPlayer.containerMenu.getSlot(1).set(new ItemStack(Items.LAPIS_LAZULI, lapisCount));
					}
				}
			}
		}
	}

	@Inject(method = "removed(Lnet/minecraft/world/entity/player/Player;)V", at = @At(value = "HEAD"))
	public void removed(Player player, CallbackInfo ci) {
		this.player = null;
	}
}

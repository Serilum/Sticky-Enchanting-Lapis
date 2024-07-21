package com.natamus.stickyenchantinglapis.util;

import com.natamus.stickyenchantinglapis.data.Variables;
import com.natamus.stickyenchantinglapis.mixin.EnchantmentMenuAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.EnchantmentMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.EnchantingTableBlockEntity;

import java.util.UUID;

public class ClientUtil {
	public static void syncLapisToClients(BlockPos enchantingTableBlockPos, int lapisCount) {
		Player player = Minecraft.getInstance().player;
		if (player != null) {
			UUID playerUUID = player.getUUID();

			if (!Variables.lastEnchantingTableInteraction.containsKey(playerUUID)) {
				return;
			}

			if (!enchantingTableBlockPos.equals(Variables.lastEnchantingTableInteraction.get(playerUUID))) {
				return;
			}

			Level level = player.level();

			BlockEntity blockEntity = level.getBlockEntity(enchantingTableBlockPos);
			if (blockEntity instanceof EnchantingTableBlockEntity enchantingTableBlockEntity) {
				Util.saveLapisCount(level, enchantingTableBlockEntity, lapisCount);

				if (player.containerMenu instanceof EnchantmentMenu enchantmentMenu) {
					ItemStack lapisStack = ItemStack.EMPTY;
					if (lapisCount > 0) {
						lapisStack = new ItemStack(Items.LAPIS_LAZULI, lapisCount);
					}

					((EnchantmentMenuAccessor) enchantmentMenu).getEnchantSlots().setItem(1, lapisStack);
				}
			}
		}
	}
}

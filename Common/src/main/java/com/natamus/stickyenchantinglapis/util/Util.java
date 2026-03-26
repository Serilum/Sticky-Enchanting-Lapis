package com.natamus.stickyenchantinglapis.util;

import com.natamus.stickyenchantinglapis.data.Variables;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EnchantingTableBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.EnchantingTableBlockEntity;

import java.util.UUID;

public class Util {
	public static EnchantingTableBlockEntity getEnchantingTableBlockEntity(Player player) {
		if (player != null) {
			Level level = player.level();
			UUID playerUUID = player.getUUID();

			BlockPos enchantingTableBlockPos = null;

			if (Variables.lastEnchantingTableInteraction.containsKey(playerUUID)) {
				enchantingTableBlockPos = Variables.lastEnchantingTableInteraction.get(playerUUID).immutable();
			}
			else {
				BlockPos playerPos = player.blockPosition();

				for (BlockPos blockPos : BlockPos.betweenClosed(playerPos.getX() - 8, playerPos.getY() - 8, playerPos.getZ() - 8, playerPos.getX() + 8, playerPos.getY() + 8, playerPos.getZ() + 8)) {
					if (level.getBlockState(blockPos).getBlock() instanceof EnchantingTableBlock) {
						enchantingTableBlockPos = blockPos.immutable();
						break;
					}
				}
			}

			if (enchantingTableBlockPos != null) {
				BlockEntity blockEntity = level.getBlockEntity(enchantingTableBlockPos);
				if (blockEntity instanceof EnchantingTableBlockEntity enchantingTableBlockEntity) {
					return enchantingTableBlockEntity;
				}
			}
		}

		return null;
	}

	public static void saveLapisCount(Level level, EnchantingTableBlockEntity enchantingTableBlockEntity, int lapisCount) {
		DataComponentMap dataComponentMap = enchantingTableBlockEntity.components();
		DataComponentMap.Builder dataComponentMapBuilder = DataComponentMap.builder().addAll(dataComponentMap);

		if (lapisCount <= 0) {
			dataComponentMapBuilder.set(DataComponents.MAX_STACK_SIZE, null);
		}
		else {
			dataComponentMapBuilder.set(DataComponents.MAX_STACK_SIZE, lapisCount);
		}

		enchantingTableBlockEntity.setComponents(dataComponentMapBuilder.build());
		enchantingTableBlockEntity.setChanged();
	}

	public static int getLapisCount(Level level, EnchantingTableBlockEntity enchantingTableBlockEntity) {
		DataComponentMap dataComponentMap = enchantingTableBlockEntity.components();
		if (dataComponentMap.has(DataComponents.MAX_STACK_SIZE)) {
			return dataComponentMap.get(DataComponents.MAX_STACK_SIZE);
		}

		return 0;
	}
}

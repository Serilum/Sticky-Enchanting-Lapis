package com.natamus.stickyenchantinglapis.mixin;

import com.natamus.stickyenchantinglapis.util.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.EnchantingTableBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Block.class, priority = 1001)
public class BlockMixin {
	@Inject(method = "dropResources(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/entity/BlockEntity;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/item/ItemStack;)V", at = @At(value = "HEAD"))
	private static void dropResources(BlockState $$0, Level $$1, BlockPos $$2, BlockEntity blockEntity, Entity $$4, ItemStack $$5, CallbackInfo ci) {
		if (blockEntity instanceof EnchantingTableBlockEntity enchantingTableBlockEntity) {
			Level level = enchantingTableBlockEntity.getLevel();

			int lapisCount = Util.getLapisCount(level, enchantingTableBlockEntity);
			if (lapisCount > 0) {
				BlockPos blockPos = enchantingTableBlockEntity.getBlockPos();
				ItemStack lapisStack = new ItemStack(Items.LAPIS_LAZULI, lapisCount);

				level.addFreshEntity(new ItemEntity(level, blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5, lapisStack));
			}
		}
	}
}

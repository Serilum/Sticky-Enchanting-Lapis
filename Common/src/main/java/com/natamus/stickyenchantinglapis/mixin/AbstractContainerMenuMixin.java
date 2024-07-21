package com.natamus.stickyenchantinglapis.mixin;

import com.natamus.stickyenchantinglapis.util.Util;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.EnchantmentMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.EnchantingTableBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = AbstractContainerMenu.class, priority = 1001)
public class AbstractContainerMenuMixin {
	@Inject(method = "clearContainer(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/Container;)V", at = @At(value = "HEAD"))
	protected void clearContainer(Player player, Container container, CallbackInfo ci) {
		AbstractContainerMenu abstractContainerMenu = (AbstractContainerMenu)(Object)this;
		if (abstractContainerMenu instanceof EnchantmentMenu) {
			EnchantingTableBlockEntity enchantingTableBlockEntity = Util.getEnchantingTableBlockEntity(player);
			if (enchantingTableBlockEntity != null) {
				int lapisCount = 0;

				ItemStack itemStack = container.getItem(1);
				if (itemStack.getItem().equals(Items.LAPIS_LAZULI)) {
					lapisCount = itemStack.getCount();
				}

				Util.saveLapisCount(player.level(), enchantingTableBlockEntity, lapisCount);

				container.setItem(1, ItemStack.EMPTY);
			}
		}
	}
}

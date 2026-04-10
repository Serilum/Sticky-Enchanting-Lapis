package com.natamus.stickyenchantinglapis.mixin;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.EnchantmentMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = EnchantmentMenu.class, priority = 1001)
public interface EnchantmentMenuAccessor {
	@Accessor Container getEnchantSlots();
}

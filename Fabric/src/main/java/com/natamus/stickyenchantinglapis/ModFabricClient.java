package com.natamus.stickyenchantinglapis;

import net.fabricmc.api.ClientModInitializer;
import com.natamus.stickyenchantinglapis.util.Reference;
import com.natamus.collective.check.ShouldLoadCheck;

public class ModFabricClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		if (!ShouldLoadCheck.shouldLoad(Reference.MOD_ID)) {
			return;
		}

		ModCommon.registerPackets();

		registerEvents();
	}
	
	private void registerEvents() {

	}
}

package com.natamus.stickyenchantinglapis;

import net.fabricmc.api.ClientModInitializer;

public class ModFabricClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ModCommon.registerPackets();

		registerEvents();
	}
	
	private void registerEvents() {

	}
}

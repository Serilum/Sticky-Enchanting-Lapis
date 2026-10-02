package com.serilum.stickyenchantinglapis;

import com.serilum.stickyenchantinglapis.networking.PacketRegistration;

public class ModCommon {

	public static void init() {
		registerPackets();

		load();
	}

	private static void load() {
		
	}

	public static void registerPackets() {
		new PacketRegistration().init();
	}
}
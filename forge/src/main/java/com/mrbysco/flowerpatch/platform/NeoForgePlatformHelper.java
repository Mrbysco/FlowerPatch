package com.mrbysco.flowerpatch.platform;

import com.mrbysco.flowerpatch.platform.services.IPlatformHelper;
import net.neoforged.fml.ModList;

public class NeoForgePlatformHelper implements IPlatformHelper {

	@Override
	public boolean isModLoaded(String modID) {
		return ModList.get().isLoaded(modID);
	}
}

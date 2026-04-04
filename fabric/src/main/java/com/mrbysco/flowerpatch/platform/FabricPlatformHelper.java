package com.mrbysco.flowerpatch.platform;

import com.mrbysco.flowerpatch.platform.services.IPlatformHelper;
import net.fabricmc.loader.api.FabricLoader;

public class FabricPlatformHelper implements IPlatformHelper {

	@Override
	public boolean isModLoaded(String modID) {
		return FabricLoader.getInstance().isModLoaded(modID);
	}
}

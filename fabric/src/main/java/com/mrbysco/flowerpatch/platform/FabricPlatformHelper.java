package com.mrbysco.flowerpatch.platform;

import com.mrbysco.flowerpatch.FlowerPatchFabric;
import com.mrbysco.flowerpatch.platform.services.IPlatformHelper;
import net.fabricmc.loader.api.FabricLoader;

public class FabricPlatformHelper implements IPlatformHelper {

	@Override
	public boolean patchBonemealing() {
		return FlowerPatchFabric.config.get().general.patchBonemealing;
	}

	@Override
	public boolean flowerToPatchBonemealing() {
		return FlowerPatchFabric.config.get().general.flowerToPatchBonemealing;
	}

	@Override
	public boolean placeOnLeaves() {
		return FlowerPatchFabric.config.get().general.placeOnLeaves;
	}

	@Override
	public boolean isModLoaded(String modID) {
		return FabricLoader.getInstance().isModLoaded(modID);
	}
}

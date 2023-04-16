package com.mrbysco.flowerpatch.platform;

import com.mrbysco.flowerpatch.FlowerPatchFabric;
import com.mrbysco.flowerpatch.platform.services.IPlatformHelper;

public class FabricPlatformHelper implements IPlatformHelper {

	@Override
	public boolean patchBonemealing() {
		return FlowerPatchFabric.config.get().general.patchBonemealing;
	}

	@Override
	public boolean flowerToPatchBonemealing() {
		return FlowerPatchFabric.config.get().general.flowerToPatchBonemealing;
	}
}

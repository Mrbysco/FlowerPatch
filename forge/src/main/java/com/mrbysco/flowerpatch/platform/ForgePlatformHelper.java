package com.mrbysco.flowerpatch.platform;

import com.mrbysco.flowerpatch.config.PatchConfigForge;
import com.mrbysco.flowerpatch.platform.services.IPlatformHelper;

public class ForgePlatformHelper implements IPlatformHelper {

	@Override
	public boolean patchBonemealing() {
		return PatchConfigForge.COMMON.patchBonemealing.get();
	}

	@Override
	public boolean flowerToPatchBonemealing() {
		return PatchConfigForge.COMMON.flowerToPatchBonemealing.get();
	}

	@Override
	public boolean placeOnLeaves() {
		return PatchConfigForge.COMMON.placeOnLeaves.get();
	}
}

package com.mrbysco.flowerpatch.platform;

import com.mrbysco.flowerpatch.config.PatchConfigNeoForge;
import com.mrbysco.flowerpatch.platform.services.IPlatformHelper;

public class NeoForgePlatformHelper implements IPlatformHelper {

	@Override
	public boolean patchBonemealing() {
		return PatchConfigNeoForge.COMMON.patchBonemealing.get();
	}

	@Override
	public boolean flowerToPatchBonemealing() {
		return PatchConfigNeoForge.COMMON.flowerToPatchBonemealing.get();
	}

	@Override
	public boolean placeOnLeaves() {
		return PatchConfigNeoForge.COMMON.placeOnLeaves.get();
	}
}

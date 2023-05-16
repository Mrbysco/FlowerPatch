package com.mrbysco.flowerpatch.platform.services;

public interface IPlatformHelper {

	/**
	 * Returns if patch bonemealing has been enabled in the config
	 */
	boolean patchBonemealing();

	/**
	 * Returns if flower to patch bonemealing has been enabled in the config
	 */
	boolean flowerToPatchBonemealing();

	/**
	 * Returns if bush-like blocks should be place-able on leaf blocks
	 */
	boolean placeOnLeaves();
}

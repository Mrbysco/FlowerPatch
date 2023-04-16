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
}

package com.mrbysco.flowerpatch.config;

import com.mrbysco.flowerpatch.FlowerPatch;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.CollapsibleObject;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = FlowerPatch.MOD_ID)
public class PatchConfig implements ConfigData {

	@CollapsibleObject
	public General general = new General();

	public static class General {

		@Comment("Allows flowers to be bonemealed into flower patches [Default: true]")
		public boolean flowerToPatchBonemealing = true;

		@Comment("Allows flowers patches to be bonemealed to add more flowers [Default: true]")
		public boolean patchBonemealing = true;
	}
}
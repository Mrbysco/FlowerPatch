package com.mrbysco.flowerpatch.config;

import com.mrbysco.flowerpatch.Constants;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.CollapsibleObject;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = Constants.MOD_ID)
public class PatchConfigFabric implements ConfigData {

	@CollapsibleObject
	public General general = new General();

	public static class General {
		@Comment("Allows flowers to be bonemealed into flower patches [Default: true]")
		public boolean flowerToPatchBonemealing = true;

		@Comment("Allows flowers patches to be bonemealed to add more flowers [Default: true]")
		public boolean patchBonemealing = true;

		@Comment("Allows flowers and other bush-like blocks to be place-able on leaves [Default: true]")
		public boolean placeOnLeaves = true;
	}
}
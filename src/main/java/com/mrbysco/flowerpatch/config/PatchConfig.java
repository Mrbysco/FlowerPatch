package com.mrbysco.flowerpatch.config;


import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class PatchConfig {
	public static class Common {
		public final ForgeConfigSpec.BooleanValue flowerToPatchBonemealing;
		public final ForgeConfigSpec.BooleanValue patchBonemealing;
		public final ForgeConfigSpec.BooleanValue placeOnLeaves;

		Common(ForgeConfigSpec.Builder builder) {
			builder.comment("General settings")
					.push("General");

			flowerToPatchBonemealing = builder
					.comment("Allows flowers to be bonemealed into flower patches [Default: true]")
					.define("flowerToPatchBonemealing", true);

			patchBonemealing = builder
					.comment("Allows flowers patches to be bonemealed to add more flowers [Default: true]")
					.define("patchBonemealing", true);

			placeOnLeaves = builder
					.comment("Allows flowers and other bush-like blocks to be place-able on leaves [Default: true]")
					.define("placeOnLeaves", true);

			builder.pop();
		}
	}

	public static final ForgeConfigSpec commonSpec;
	public static final Common COMMON;

	static {
		final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
		commonSpec = specPair.getRight();
		COMMON = specPair.getLeft();
	}
}

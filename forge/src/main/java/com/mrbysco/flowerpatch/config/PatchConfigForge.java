package com.mrbysco.flowerpatch.config;

import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.BooleanValue;
import org.apache.commons.lang3.tuple.Pair;

public class PatchConfigForge {
	public static class Common {
		public final BooleanValue flowerToPatchBonemealing;
		public final BooleanValue patchBonemealing;
		public final BooleanValue placeOnLeaves;

		Common(ModConfigSpec.Builder builder) {
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

	public static final ModConfigSpec commonSpec;
	public static final Common COMMON;

	static {
		final Pair<Common, ModConfigSpec> specPair = new ModConfigSpec.Builder().configure(Common::new);
		commonSpec = specPair.getRight();
		COMMON = specPair.getLeft();
	}
}

package com.mrbysco.flowerpatch;

import com.mrbysco.flowerpatch.config.PatchConfigFabric;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;

public class FlowerPatchFabric implements ModInitializer {

	public static ConfigHolder<PatchConfigFabric> config;

	@Override
	public void onInitialize() {
		config = AutoConfig.register(PatchConfigFabric.class, Toml4jConfigSerializer::new);
		config.get();

		CommonClass.init();

		UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> CommonClass.onBlockInteraction(world, hitResult.getBlockPos(), player, hand));
	}
}

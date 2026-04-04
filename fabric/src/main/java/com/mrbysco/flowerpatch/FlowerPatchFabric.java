package com.mrbysco.flowerpatch;

import com.mrbysco.flowerpatch.config.PatchConfig;
import fuzs.forgeconfigapiport.fabric.api.v5.ConfigRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.neoforged.fml.config.ModConfig;

public class FlowerPatchFabric implements ModInitializer {
	
	@Override
	public void onInitialize() {
		ConfigRegistry.INSTANCE.register(Constants.MOD_ID, ModConfig.Type.COMMON, PatchConfig.commonSpec);

		CommonClass.init();

		UseBlockCallback.EVENT.register((player, level, hand, hitResult) ->
				CommonClass.onBlockInteraction(level, hitResult.getBlockPos(), player, hand));
	}
}

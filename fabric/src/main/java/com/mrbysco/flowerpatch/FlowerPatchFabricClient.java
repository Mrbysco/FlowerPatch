package com.mrbysco.flowerpatch;

import com.mrbysco.flowerpatch.block.FlowerPatchBlock;
import com.mrbysco.flowerpatch.registration.PatchRegistry;
import com.mrbysco.flowerpatch.registration.RegistryObject;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Block;

public class FlowerPatchFabricClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		for (RegistryObject<Block> registryObject : PatchRegistry.BLOCKS.getEntries()) {
			if (registryObject.get() instanceof FlowerPatchBlock flowerPatchBlock) {
				BlockRenderLayerMap.INSTANCE.putBlock(flowerPatchBlock, RenderType.cutout());
			}
		}
	}
}

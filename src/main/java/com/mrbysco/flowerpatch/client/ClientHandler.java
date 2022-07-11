package com.mrbysco.flowerpatch.client;

import com.mrbysco.flowerpatch.block.FlowerPatchBlock;
import com.mrbysco.flowerpatch.registry.PatchRegistry;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.RegistryObject;

public class ClientHandler {
	public static void onClientSetup(final FMLClientSetupEvent event) {
		for (RegistryObject<Block> registryObject : PatchRegistry.BLOCKS.getEntries()) {
			if (registryObject.get() instanceof FlowerPatchBlock flowerPatchBlock) {
				ItemBlockRenderTypes.setRenderLayer(flowerPatchBlock, RenderType.cutout());
			}
		}
	}
}

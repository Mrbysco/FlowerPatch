package com.mrbysco.flowerpatch.client;

import com.mrbysco.flowerpatch.block.PatchBlock;
import com.mrbysco.flowerpatch.registration.PatchRegistry;
import com.mrbysco.flowerpatch.registration.RegistryObject;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.world.level.block.Block;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientHandler {
	@SuppressWarnings("deprecation")
	public static void onClientSetup(final FMLClientSetupEvent event) {
		for (RegistryObject<Block> registryObject : PatchRegistry.BLOCKS.getEntries()) {
			if (registryObject.get() instanceof PatchBlock) {
				ItemBlockRenderTypes.setRenderLayer(registryObject.get(), ChunkSectionLayer.CUTOUT);
			}
		}
	}
}
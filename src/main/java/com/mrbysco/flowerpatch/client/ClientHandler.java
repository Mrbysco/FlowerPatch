package com.mrbysco.flowerpatch.client;

import com.mrbysco.flowerpatch.block.FlowerPatchBlock;
import com.mrbysco.flowerpatch.registry.PatchRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientHandler {
	public static void onClientSetup(final FMLClientSetupEvent event) {
		for (RegistryObject<Block> registryObject : PatchRegistry.BLOCKS.getEntries()) {
			if (registryObject.get() instanceof FlowerPatchBlock) {
				RenderTypeLookup.setRenderLayer(registryObject.get(), RenderType.cutout());
			}
		}
	}
}

package com.mrbysco.flowerpatch;

import com.mrbysco.flowerpatch.registry.PatchRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.renderer.RenderType;

public class FlowerPatchClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		BlockRenderLayerMap.INSTANCE.putBlock(PatchRegistry.DANDELION_PATCH, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(PatchRegistry.POPPY_PATCH, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(PatchRegistry.BLUE_ORCHID_PATCH, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(PatchRegistry.ALLIUM_PATCH, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(PatchRegistry.AZURE_BLUET_PATCH, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(PatchRegistry.RED_TULIP_PATCH, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(PatchRegistry.ORANGE_TULIP_PATCH, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(PatchRegistry.WHITE_TULIP_PATCH, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(PatchRegistry.PINK_TULIP_PATCH, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(PatchRegistry.OXEYE_DAISY_PATCH, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(PatchRegistry.CORNFLOWER_PATCH, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(PatchRegistry.WITHER_ROSE_PATCH, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(PatchRegistry.LILY_OF_THE_VALLEY_PATCH, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(PatchRegistry.BROWN_MUSHROOM_PATCH, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(PatchRegistry.RED_MUSHROOM_PATCH, RenderType.cutout());
	}
}

package com.mrbysco.flowerpatch;

import com.mojang.logging.LogUtils;
import com.mrbysco.flowerpatch.block.FlowerPatchBlock;
import com.mrbysco.flowerpatch.handler.PlaceHandler;
import com.mrbysco.flowerpatch.registry.PatchRegistry;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

@Mod(FlowerPatch.MOD_ID)
public class FlowerPatch {
	public static final String MOD_ID = "flowerpatch";
	private static final Logger LOGGER = LogUtils.getLogger();

	public FlowerPatch() {
		IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
		PatchRegistry.BLOCKS.register(eventBus);

		MinecraftForge.EVENT_BUS.addListener(PlaceHandler::onBlockInteraction);

		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
			eventBus.addListener(this::onClientSetup);
		});
	}


	public void onClientSetup(final FMLClientSetupEvent event) {
		for (RegistryObject<Block> registryObject : PatchRegistry.BLOCKS.getEntries()) {
			if (registryObject.get() instanceof FlowerPatchBlock flowerPatchBlock) {
				ItemBlockRenderTypes.setRenderLayer(flowerPatchBlock, RenderType.cutout());
			}
		}
	}
}

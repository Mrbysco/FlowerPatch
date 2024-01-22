package com.mrbysco.flowerpatch;

import com.mrbysco.flowerpatch.client.ClientHandler;
import com.mrbysco.flowerpatch.config.PatchConfig;
import com.mrbysco.flowerpatch.handler.PlaceHandler;
import com.mrbysco.flowerpatch.registry.PatchRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(FlowerPatch.MOD_ID)
public class FlowerPatch {
	public static final String MOD_ID = "flowerpatch";
	public static final Logger LOGGER = LogManager.getLogger();

	public static final ITag.INamedTag<Block> BONEMEAL_ABLE_FLOWERS = BlockTags.bind(new ResourceLocation(FlowerPatch.MOD_ID, "bonemeal_able_flowers").toString());
	public static final ITag.INamedTag<Item> BONEMEAL = ItemTags.bind(new ResourceLocation(FlowerPatch.MOD_ID, "bonemeal").toString());

	public FlowerPatch() {
		IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, PatchConfig.commonSpec);
		PatchRegistry.BLOCKS.register(eventBus);

		MinecraftForge.EVENT_BUS.addListener(PlaceHandler::onBlockInteraction);
		MinecraftForge.EVENT_BUS.addListener(PlaceHandler::onBonemeal);

		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
			eventBus.addListener(ClientHandler::onClientSetup);
		});
	}
}

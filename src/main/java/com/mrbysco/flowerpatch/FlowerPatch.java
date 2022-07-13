package com.mrbysco.flowerpatch;

import com.mojang.logging.LogUtils;
import com.mrbysco.flowerpatch.client.ClientHandler;
import com.mrbysco.flowerpatch.config.PatchConfig;
import com.mrbysco.flowerpatch.handler.PlaceHandler;
import com.mrbysco.flowerpatch.registry.PatchRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(FlowerPatch.MOD_ID)
public class FlowerPatch {
	public static final String MOD_ID = "flowerpatch";
	private static final Logger LOGGER = LogUtils.getLogger();

	public static final TagKey<Block> BONEMEAL_ABLE_FLOWERS = BlockTags.create(new ResourceLocation(FlowerPatch.MOD_ID, "bonemeal_able_flowers"));
	public static final TagKey<Item> BONEMEAL = ItemTags.create(new ResourceLocation(FlowerPatch.MOD_ID, "bonemeal"));

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

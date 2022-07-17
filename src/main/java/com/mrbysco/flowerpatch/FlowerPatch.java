package com.mrbysco.flowerpatch;

import com.mojang.logging.LogUtils;
import com.mrbysco.flowerpatch.config.PatchConfig;
import com.mrbysco.flowerpatch.handler.PlaceHandler;
import com.mrbysco.flowerpatch.registry.PatchRegistry;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.slf4j.Logger;

public class FlowerPatch implements ModInitializer {
	public static final String MOD_ID = "flowerpatch";
	public static final Logger LOGGER = LogUtils.getLogger();
	public static PatchConfig config;

	public static final TagKey<Block> BONEMEAL_ABLE_FLOWERS = TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(FlowerPatch.MOD_ID, "bonemeal_able_flowers"));
	public static final TagKey<Item> BONEMEAL = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(FlowerPatch.MOD_ID, "bonemeal"));

	@Override
	public void onInitialize() {
		ConfigHolder<PatchConfig> holder = AutoConfig.register(PatchConfig.class, Toml4jConfigSerializer::new);
		config = holder.getConfig();

		PatchRegistry.loadClass();

		UseBlockCallback.EVENT.register(PlaceHandler::onUseBlock);
	}
}

package com.mrbysco.flowerpatch;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Constants {

	public static final String MOD_ID = "flowerpatch";
	public static final String MOD_NAME = "Flower Patch";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

	public static final TagKey<Block> BONEMEAL_ABLE_FLOWERS = TagKey.create(Registries.BLOCK, modLoc("bonemeal_able_flowers"));

	public static final TagKey<Item> BONEMEAL = TagKey.create(Registries.ITEM, modLoc("bonemeal"));

	public static ResourceLocation modLoc(String path) {
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
	}
}
package com.mrbysco.flowerpatch.registry;

import com.mrbysco.flowerpatch.FlowerPatch;
import com.mrbysco.flowerpatch.block.FlowerPatchBlock;
import com.mrbysco.flowerpatch.block.MushroomPatchBlock;
import com.mrbysco.flowerpatch.block.WitherRosePatchBlock;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

import java.util.ArrayList;
import java.util.List;

public class PatchRegistry {
	public static void loadClass() {

	}

	public static List<Block> BLOCKS = new ArrayList<>();

	public static final Block DANDELION_PATCH = registerBlock("dandelion_patch", new FlowerPatchBlock(MobEffects.SATURATION, 7, () -> Blocks.DANDELION, BlockBehaviour.Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ)));
	public static final Block POPPY_PATCH = registerBlock("poppy_patch", new FlowerPatchBlock(MobEffects.NIGHT_VISION, 5, () -> Blocks.POPPY, BlockBehaviour.Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ)));
	public static final Block BLUE_ORCHID_PATCH = registerBlock("blue_orchid_patch", new FlowerPatchBlock(MobEffects.SATURATION, 7, () -> Blocks.BLUE_ORCHID, BlockBehaviour.Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ)));
	public static final Block ALLIUM_PATCH = registerBlock("allium_patch", new FlowerPatchBlock(MobEffects.FIRE_RESISTANCE, 4, () -> Blocks.ALLIUM, BlockBehaviour.Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ)));
	public static final Block AZURE_BLUET_PATCH = registerBlock("azure_bluet_patch", new FlowerPatchBlock(MobEffects.BLINDNESS, 8, () -> Blocks.AZURE_BLUET, BlockBehaviour.Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ)));
	public static final Block RED_TULIP_PATCH = registerBlock("red_tulip_patch", new FlowerPatchBlock(MobEffects.WEAKNESS, 9, () -> Blocks.RED_TULIP, BlockBehaviour.Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ)));
	public static final Block ORANGE_TULIP_PATCH = registerBlock("orange_tulip_patch", new FlowerPatchBlock(MobEffects.WEAKNESS, 9, () -> Blocks.ORANGE_TULIP, BlockBehaviour.Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ)));
	public static final Block WHITE_TULIP_PATCH = registerBlock("white_tulip_patch", new FlowerPatchBlock(MobEffects.WEAKNESS, 9, () -> Blocks.WHITE_TULIP, BlockBehaviour.Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ)));
	public static final Block PINK_TULIP_PATCH = registerBlock("pink_tulip_patch", new FlowerPatchBlock(MobEffects.WEAKNESS, 9, () -> Blocks.PINK_TULIP, BlockBehaviour.Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ)));
	public static final Block OXEYE_DAISY_PATCH = registerBlock("oxeye_daisy_patch", new FlowerPatchBlock(MobEffects.REGENERATION, 8, () -> Blocks.OXEYE_DAISY, BlockBehaviour.Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ)));
	public static final Block CORNFLOWER_PATCH = registerBlock("cornflower_patch", new FlowerPatchBlock(MobEffects.JUMP, 6, () -> Blocks.CORNFLOWER, BlockBehaviour.Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ)));
	public static final Block WITHER_ROSE_PATCH = registerBlock("wither_rose_patch", new WitherRosePatchBlock(MobEffects.WITHER, () -> Blocks.WITHER_ROSE, BlockBehaviour.Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ)));
	public static final Block LILY_OF_THE_VALLEY_PATCH = registerBlock("lily_of_the_valley_patch", new FlowerPatchBlock(MobEffects.POISON, 12, () -> Blocks.LILY_OF_THE_VALLEY, BlockBehaviour.Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ)));

	public static final Block BROWN_MUSHROOM_PATCH = registerBlock("brown_mushroom_patch", new MushroomPatchBlock(() -> Blocks.BROWN_MUSHROOM, BlockBehaviour.Properties.of(Material.PLANT, MaterialColor.COLOR_BROWN).noCollission().randomTicks().instabreak().sound(SoundType.GRASS).lightLevel((state) -> 1).hasPostProcess((state, getter, pos) -> true)));
	public static final Block RED_MUSHROOM_PATCH = registerBlock("red_mushroom_patch", new MushroomPatchBlock(() -> Blocks.RED_MUSHROOM, BlockBehaviour.Properties.of(Material.PLANT, MaterialColor.COLOR_RED).noCollission().randomTicks().instabreak().sound(SoundType.GRASS).hasPostProcess((state, getter, pos) -> true)));

	public static Block registerBlock(String name, Block block) {
		Block registeredBlock = Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(FlowerPatch.MOD_ID, name), block);
		BLOCKS.add(registeredBlock);
		return registeredBlock;
	}
}

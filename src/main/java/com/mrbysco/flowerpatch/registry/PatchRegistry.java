package com.mrbysco.flowerpatch.registry;

import com.mrbysco.flowerpatch.FlowerPatch;
import com.mrbysco.flowerpatch.block.FlowerPatchBlock;
import com.mrbysco.flowerpatch.block.MushroomPatchBlock;
import com.mrbysco.flowerpatch.block.WitherRosePatchBlock;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class PatchRegistry {
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, FlowerPatch.MOD_ID);

	public static final RegistryObject<Block> DANDELION_PATCH = BLOCKS.register("dandelion_patch", () ->
			new FlowerPatchBlock(MobEffects.SATURATION, 7, () -> Blocks.DANDELION, BlockBehaviour.Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ)));
	public static final RegistryObject<Block> POPPY_PATCH = BLOCKS.register("poppy_patch", () ->
			new FlowerPatchBlock(MobEffects.NIGHT_VISION, 5, () -> Blocks.POPPY, BlockBehaviour.Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ)));
	public static final RegistryObject<Block> BLUE_ORCHID_PATCH = BLOCKS.register("blue_orchid_patch", () ->
			new FlowerPatchBlock(MobEffects.SATURATION, 7, () -> Blocks.BLUE_ORCHID, BlockBehaviour.Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ)));
	public static final RegistryObject<Block> ALLIUM_PATCH = BLOCKS.register("allium_patch", () ->
			new FlowerPatchBlock(MobEffects.FIRE_RESISTANCE, 4, () -> Blocks.ALLIUM, BlockBehaviour.Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ)));
	public static final RegistryObject<Block> AZURE_BLUET_PATCH = BLOCKS.register("azure_bluet_patch", () ->
			new FlowerPatchBlock(MobEffects.BLINDNESS, 8, () -> Blocks.AZURE_BLUET, BlockBehaviour.Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ)));
	public static final RegistryObject<Block> RED_TULIP_PATCH = BLOCKS.register("red_tulip_patch", () ->
			new FlowerPatchBlock(MobEffects.WEAKNESS, 9, () -> Blocks.RED_TULIP, BlockBehaviour.Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ)));
	public static final RegistryObject<Block> ORANGE_TULIP_PATCH = BLOCKS.register("orange_tulip_patch", () ->
			new FlowerPatchBlock(MobEffects.WEAKNESS, 9, () -> Blocks.ORANGE_TULIP, BlockBehaviour.Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ)));
	public static final RegistryObject<Block> WHITE_TULIP_PATCH = BLOCKS.register("white_tulip_patch", () ->
			new FlowerPatchBlock(MobEffects.WEAKNESS, 9, () -> Blocks.WHITE_TULIP, BlockBehaviour.Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ)));
	public static final RegistryObject<Block> PINK_TULIP_PATCH = BLOCKS.register("pink_tulip_patch", () ->
			new FlowerPatchBlock(MobEffects.WEAKNESS, 9, () -> Blocks.PINK_TULIP, BlockBehaviour.Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ)));
	public static final RegistryObject<Block> OXEYE_DAISY_PATCH = BLOCKS.register("oxeye_daisy_patch", () ->
			new FlowerPatchBlock(MobEffects.REGENERATION, 8, () -> Blocks.OXEYE_DAISY, BlockBehaviour.Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ)));
	public static final RegistryObject<Block> CORNFLOWER_PATCH = BLOCKS.register("cornflower_patch", () ->
			new FlowerPatchBlock(MobEffects.JUMP, 6, () -> Blocks.CORNFLOWER, BlockBehaviour.Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ)));
	public static final RegistryObject<Block> WITHER_ROSE_PATCH = BLOCKS.register("wither_rose_patch", () ->
			new WitherRosePatchBlock(MobEffects.WITHER, () -> Blocks.WITHER_ROSE, BlockBehaviour.Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ)));
	public static final RegistryObject<Block> LILY_OF_THE_VALLEY_PATCH = BLOCKS.register("lily_of_the_valley_patch", () ->
			new FlowerPatchBlock(MobEffects.POISON, 12, () -> Blocks.LILY_OF_THE_VALLEY, BlockBehaviour.Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ)));

	public static final RegistryObject<Block> BROWN_MUSHROOM_PATCH = BLOCKS.register("brown_mushroom_patch",
			() -> new MushroomPatchBlock(() -> Blocks.BROWN_MUSHROOM, BlockBehaviour.Properties.of(Material.PLANT, MaterialColor.COLOR_BROWN).noCollission().randomTicks().instabreak().sound(SoundType.GRASS).lightLevel((state) -> 1).hasPostProcess((state, getter, pos) -> true)));
	public static final RegistryObject<Block> RED_MUSHROOM_PATCH = BLOCKS.register("red_mushroom_patch",
			() -> new MushroomPatchBlock(() -> Blocks.RED_MUSHROOM, BlockBehaviour.Properties.of(Material.PLANT, MaterialColor.COLOR_RED).noCollission().randomTicks().instabreak().sound(SoundType.GRASS).hasPostProcess((state, getter, pos) -> true)));
}

package com.mrbysco.flowerpatch.registration;

import com.mrbysco.flowerpatch.Constants;
import com.mrbysco.flowerpatch.block.FlowerPatchBlock;
import com.mrbysco.flowerpatch.block.MushroomPatchBlock;
import com.mrbysco.flowerpatch.block.WitherRosePatchBlock;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class PatchRegistry {
	public static final RegistrationProvider<Block> BLOCKS = RegistrationProvider.get(Registries.BLOCK, Constants.MOD_ID);

	public static final RegistryObject<Block> DANDELION_PATCH = BLOCKS.register("dandelion_patch", () ->
			new FlowerPatchBlock(MobEffects.SATURATION, 7, () -> Blocks.DANDELION, BlockBehaviour.Properties.copy(Blocks.DANDELION).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ)));
	public static final RegistryObject<Block> POPPY_PATCH = BLOCKS.register("poppy_patch", () ->
			new FlowerPatchBlock(MobEffects.NIGHT_VISION, 5, () -> Blocks.POPPY, BlockBehaviour.Properties.copy(Blocks.POPPY).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ)));
	public static final RegistryObject<Block> BLUE_ORCHID_PATCH = BLOCKS.register("blue_orchid_patch", () ->
			new FlowerPatchBlock(MobEffects.SATURATION, 7, () -> Blocks.BLUE_ORCHID, BlockBehaviour.Properties.copy(Blocks.BLUE_ORCHID).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ)));
	public static final RegistryObject<Block> ALLIUM_PATCH = BLOCKS.register("allium_patch", () ->
			new FlowerPatchBlock(MobEffects.FIRE_RESISTANCE, 4, () -> Blocks.ALLIUM, BlockBehaviour.Properties.copy(Blocks.ALLIUM).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ)));
	public static final RegistryObject<Block> AZURE_BLUET_PATCH = BLOCKS.register("azure_bluet_patch", () ->
			new FlowerPatchBlock(MobEffects.BLINDNESS, 8, () -> Blocks.AZURE_BLUET, BlockBehaviour.Properties.copy(Blocks.AZURE_BLUET).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ)));
	public static final RegistryObject<Block> RED_TULIP_PATCH = BLOCKS.register("red_tulip_patch", () ->
			new FlowerPatchBlock(MobEffects.WEAKNESS, 9, () -> Blocks.RED_TULIP, BlockBehaviour.Properties.copy(Blocks.RED_TULIP).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ)));
	public static final RegistryObject<Block> ORANGE_TULIP_PATCH = BLOCKS.register("orange_tulip_patch", () ->
			new FlowerPatchBlock(MobEffects.WEAKNESS, 9, () -> Blocks.ORANGE_TULIP, BlockBehaviour.Properties.copy(Blocks.ORANGE_TULIP).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ)));
	public static final RegistryObject<Block> WHITE_TULIP_PATCH = BLOCKS.register("white_tulip_patch", () ->
			new FlowerPatchBlock(MobEffects.WEAKNESS, 9, () -> Blocks.WHITE_TULIP, BlockBehaviour.Properties.copy(Blocks.WHITE_TULIP).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ)));
	public static final RegistryObject<Block> PINK_TULIP_PATCH = BLOCKS.register("pink_tulip_patch", () ->
			new FlowerPatchBlock(MobEffects.WEAKNESS, 9, () -> Blocks.PINK_TULIP, BlockBehaviour.Properties.copy(Blocks.PINK_TULIP).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ)));
	public static final RegistryObject<Block> OXEYE_DAISY_PATCH = BLOCKS.register("oxeye_daisy_patch", () ->
			new FlowerPatchBlock(MobEffects.REGENERATION, 8, () -> Blocks.OXEYE_DAISY, BlockBehaviour.Properties.copy(Blocks.OXEYE_DAISY).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ)));
	public static final RegistryObject<Block> CORNFLOWER_PATCH = BLOCKS.register("cornflower_patch", () ->
			new FlowerPatchBlock(MobEffects.JUMP, 6, () -> Blocks.CORNFLOWER, BlockBehaviour.Properties.copy(Blocks.CORNFLOWER).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ)));
	public static final RegistryObject<Block> WITHER_ROSE_PATCH = BLOCKS.register("wither_rose_patch", () ->
			new WitherRosePatchBlock(MobEffects.WITHER, () -> Blocks.WITHER_ROSE, BlockBehaviour.Properties.copy(Blocks.WITHER_ROSE).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ)));
	public static final RegistryObject<Block> LILY_OF_THE_VALLEY_PATCH = BLOCKS.register("lily_of_the_valley_patch", () ->
			new FlowerPatchBlock(MobEffects.POISON, 12, () -> Blocks.LILY_OF_THE_VALLEY, BlockBehaviour.Properties.copy(Blocks.LILY_OF_THE_VALLEY).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ)));

	public static final RegistryObject<Block> BROWN_MUSHROOM_PATCH = BLOCKS.register("brown_mushroom_patch",
			() -> new MushroomPatchBlock(() -> Blocks.BROWN_MUSHROOM, BlockBehaviour.Properties.copy(Blocks.BROWN_MUSHROOM).noCollission().randomTicks().instabreak().sound(SoundType.GRASS).lightLevel((state) -> 1).hasPostProcess((state, getter, pos) -> true)));
	public static final RegistryObject<Block> RED_MUSHROOM_PATCH = BLOCKS.register("red_mushroom_patch",
			() -> new MushroomPatchBlock(() -> Blocks.RED_MUSHROOM, BlockBehaviour.Properties.copy(Blocks.RED_MUSHROOM).noCollission().randomTicks().instabreak().sound(SoundType.GRASS).hasPostProcess((state, getter, pos) -> true)));


	// Called in the mod initializer / constructor in order to make sure that items are registered
	public static void loadClass() {
	}
}

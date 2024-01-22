package com.mrbysco.flowerpatch.registry;

import com.mrbysco.flowerpatch.FlowerPatch;
import com.mrbysco.flowerpatch.block.FlowerPatchBlock;
import com.mrbysco.flowerpatch.block.MushroomPatchBlock;
import com.mrbysco.flowerpatch.block.WitherRosePatchBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.potion.Effects;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class PatchRegistry {
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, FlowerPatch.MOD_ID);

	public static final RegistryObject<Block> DANDELION_PATCH = BLOCKS.register("dandelion_patch", () ->
			new FlowerPatchBlock(Effects.SATURATION, 7, () -> Blocks.DANDELION, AbstractBlock.Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.GRASS)));
	public static final RegistryObject<Block> POPPY_PATCH = BLOCKS.register("poppy_patch", () ->
			new FlowerPatchBlock(Effects.NIGHT_VISION, 5, () -> Blocks.POPPY, AbstractBlock.Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.GRASS)));
	public static final RegistryObject<Block> BLUE_ORCHID_PATCH = BLOCKS.register("blue_orchid_patch", () ->
			new FlowerPatchBlock(Effects.SATURATION, 7, () -> Blocks.BLUE_ORCHID, AbstractBlock.Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.GRASS)));
	public static final RegistryObject<Block> ALLIUM_PATCH = BLOCKS.register("allium_patch", () ->
			new FlowerPatchBlock(Effects.FIRE_RESISTANCE, 4, () -> Blocks.ALLIUM, AbstractBlock.Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.GRASS)));
	public static final RegistryObject<Block> AZURE_BLUET_PATCH = BLOCKS.register("azure_bluet_patch", () ->
			new FlowerPatchBlock(Effects.BLINDNESS, 8, () -> Blocks.AZURE_BLUET, AbstractBlock.Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.GRASS)));
	public static final RegistryObject<Block> RED_TULIP_PATCH = BLOCKS.register("red_tulip_patch", () ->
			new FlowerPatchBlock(Effects.WEAKNESS, 9, () -> Blocks.RED_TULIP, AbstractBlock.Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.GRASS)));
	public static final RegistryObject<Block> ORANGE_TULIP_PATCH = BLOCKS.register("orange_tulip_patch", () ->
			new FlowerPatchBlock(Effects.WEAKNESS, 9, () -> Blocks.ORANGE_TULIP, AbstractBlock.Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.GRASS)));
	public static final RegistryObject<Block> WHITE_TULIP_PATCH = BLOCKS.register("white_tulip_patch", () ->
			new FlowerPatchBlock(Effects.WEAKNESS, 9, () -> Blocks.WHITE_TULIP, AbstractBlock.Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.GRASS)));
	public static final RegistryObject<Block> PINK_TULIP_PATCH = BLOCKS.register("pink_tulip_patch", () ->
			new FlowerPatchBlock(Effects.WEAKNESS, 9, () -> Blocks.PINK_TULIP, AbstractBlock.Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.GRASS)));
	public static final RegistryObject<Block> OXEYE_DAISY_PATCH = BLOCKS.register("oxeye_daisy_patch", () ->
			new FlowerPatchBlock(Effects.REGENERATION, 8, () -> Blocks.OXEYE_DAISY, AbstractBlock.Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.GRASS)));
	public static final RegistryObject<Block> CORNFLOWER_PATCH = BLOCKS.register("cornflower_patch", () ->
			new FlowerPatchBlock(Effects.JUMP, 6, () -> Blocks.CORNFLOWER, AbstractBlock.Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.GRASS)));
	public static final RegistryObject<Block> WITHER_ROSE_PATCH = BLOCKS.register("wither_rose_patch", () ->
			new WitherRosePatchBlock(Effects.WITHER, () -> Blocks.WITHER_ROSE, AbstractBlock.Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.GRASS)));
	public static final RegistryObject<Block> LILY_OF_THE_VALLEY_PATCH = BLOCKS.register("lily_of_the_valley_patch", () ->
			new FlowerPatchBlock(Effects.POISON, 12, () -> Blocks.LILY_OF_THE_VALLEY, AbstractBlock.Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.GRASS)));

	public static final RegistryObject<Block> BROWN_MUSHROOM_PATCH = BLOCKS.register("brown_mushroom_patch",
			() -> new MushroomPatchBlock(() -> Blocks.BROWN_MUSHROOM, AbstractBlock.Properties.copy(Blocks.BROWN_MUSHROOM).noCollission().randomTicks().instabreak().sound(SoundType.GRASS).lightLevel((state) -> 1).hasPostProcess((state, getter, pos) -> true)));
	public static final RegistryObject<Block> RED_MUSHROOM_PATCH = BLOCKS.register("red_mushroom_patch",
			() -> new MushroomPatchBlock(() -> Blocks.RED_MUSHROOM, AbstractBlock.Properties.copy(Blocks.RED_MUSHROOM).noCollission().randomTicks().instabreak().sound(SoundType.GRASS).hasPostProcess((state, getter, pos) -> true)));
}

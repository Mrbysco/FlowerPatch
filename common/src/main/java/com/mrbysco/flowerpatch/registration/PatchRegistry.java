package com.mrbysco.flowerpatch.registration;

import com.mrbysco.flowerpatch.Constants;
import com.mrbysco.flowerpatch.block.FlowerPatchBlock;
import com.mrbysco.flowerpatch.block.MushroomPatchBlock;
import com.mrbysco.flowerpatch.block.WitherRosePatchBlock;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Function;

public class PatchRegistry {
	public static final RegistrationProvider<Block> BLOCKS = RegistrationProvider.get(Registries.BLOCK, Constants.MOD_ID);

	public static final RegistryObject<Block> DANDELION_PATCH = register("dandelion_patch", (properties) ->
			new FlowerPatchBlock(MobEffects.SATURATION, 7, () -> Blocks.DANDELION, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.DANDELION).noCollision().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ));
	public static final RegistryObject<Block> POPPY_PATCH = register("poppy_patch", (properties) ->
			new FlowerPatchBlock(MobEffects.NIGHT_VISION, 5, () -> Blocks.POPPY, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.POPPY).noCollision().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ));
	public static final RegistryObject<Block> BLUE_ORCHID_PATCH = register("blue_orchid_patch", (properties) ->
			new FlowerPatchBlock(MobEffects.SATURATION, 7, () -> Blocks.BLUE_ORCHID, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.BLUE_ORCHID).noCollision().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ));
	public static final RegistryObject<Block> ALLIUM_PATCH = register("allium_patch", (properties) ->
			new FlowerPatchBlock(MobEffects.FIRE_RESISTANCE, 4, () -> Blocks.ALLIUM, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.ALLIUM).noCollision().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ));
	public static final RegistryObject<Block> AZURE_BLUET_PATCH = register("azure_bluet_patch", (properties) ->
			new FlowerPatchBlock(MobEffects.BLINDNESS, 8, () -> Blocks.AZURE_BLUET, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.AZURE_BLUET).noCollision().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ));
	public static final RegistryObject<Block> RED_TULIP_PATCH = register("red_tulip_patch", (properties) ->
			new FlowerPatchBlock(MobEffects.WEAKNESS, 9, () -> Blocks.RED_TULIP, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.RED_TULIP).noCollision().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ));
	public static final RegistryObject<Block> ORANGE_TULIP_PATCH = register("orange_tulip_patch", (properties) ->
			new FlowerPatchBlock(MobEffects.WEAKNESS, 9, () -> Blocks.ORANGE_TULIP, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.ORANGE_TULIP).noCollision().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ));
	public static final RegistryObject<Block> WHITE_TULIP_PATCH = register("white_tulip_patch", (properties) ->
			new FlowerPatchBlock(MobEffects.WEAKNESS, 9, () -> Blocks.WHITE_TULIP, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.WHITE_TULIP).noCollision().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ));
	public static final RegistryObject<Block> PINK_TULIP_PATCH = register("pink_tulip_patch", (properties) ->
			new FlowerPatchBlock(MobEffects.WEAKNESS, 9, () -> Blocks.PINK_TULIP, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.PINK_TULIP).noCollision().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ));
	public static final RegistryObject<Block> OXEYE_DAISY_PATCH = register("oxeye_daisy_patch", (properties) ->
			new FlowerPatchBlock(MobEffects.REGENERATION, 8, () -> Blocks.OXEYE_DAISY, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.OXEYE_DAISY).noCollision().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ));
	public static final RegistryObject<Block> CORNFLOWER_PATCH = register("cornflower_patch", (properties) ->
			new FlowerPatchBlock(MobEffects.JUMP_BOOST, 6, () -> Blocks.CORNFLOWER, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.CORNFLOWER).noCollision().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ));
	public static final RegistryObject<Block> WITHER_ROSE_PATCH = register("wither_rose_patch", (properties) ->
			new WitherRosePatchBlock(MobEffects.WITHER, () -> Blocks.WITHER_ROSE, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.WITHER_ROSE).noCollision().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ));
	public static final RegistryObject<Block> LILY_OF_THE_VALLEY_PATCH = register("lily_of_the_valley_patch", (properties) ->
			new FlowerPatchBlock(MobEffects.POISON, 12, () -> Blocks.LILY_OF_THE_VALLEY, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.LILY_OF_THE_VALLEY).noCollision().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ));
	public static final RegistryObject<Block> TORCHFLOWER = register("torchflower", (properties) ->
			new FlowerPatchBlock(MobEffects.NIGHT_VISION, 5, () -> Blocks.TORCHFLOWER, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.TORCHFLOWER).noCollision().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ));

	public static final RegistryObject<Block> BROWN_MUSHROOM_PATCH = register("brown_mushroom_patch", (properties) ->
			new MushroomPatchBlock(() -> Blocks.BROWN_MUSHROOM, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.BROWN_MUSHROOM).noCollision().randomTicks().instabreak().sound(SoundType.GRASS).lightLevel((state) -> 1));
	public static final RegistryObject<Block> RED_MUSHROOM_PATCH = register("red_mushroom_patch", (properties) ->
			new MushroomPatchBlock(() -> Blocks.RED_MUSHROOM, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.RED_MUSHROOM).noCollision().randomTicks().instabreak().sound(SoundType.GRASS));

	/**
	 * Helper method to register a block with and automatically set the ID
	 * @param name the name of the block
	 * @param func a function that takes properties and returns a block
	 * @param props the properties to apply to the block
	 * @return a RegistryObject<Block> that represents the registered block
	 */
	public static RegistryObject<Block> register(String name, Function<BlockBehaviour.Properties, ? extends Block> func, BlockBehaviour.Properties props) {
		return BLOCKS.register(name, () -> func.apply(props.setId(ResourceKey.create(Registries.BLOCK, Constants.modLoc(name)))));
	}

	// Called in the mod initializer / constructor in order to make sure that items are registered
	public static void loadClass() {
	}
}

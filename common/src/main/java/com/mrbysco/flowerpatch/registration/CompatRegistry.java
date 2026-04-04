package com.mrbysco.flowerpatch.registration;

import com.mrbysco.flowerpatch.Constants;
import com.mrbysco.flowerpatch.block.CompatPatchBlock;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

import java.util.function.Function;

public class CompatRegistry {
	public static final RegistrationProvider<Block> BLOCKS = RegistrationProvider.get(Registries.BLOCK, Constants.MOD_ID);

	public static class BiomesWeveGoneCompat {
		public static final RegistryObject<Block> ALPINE_BELLFLOWER_PATCH = registerPatch("biomeswevegone:alpine_bellflower");
		public static final RegistryObject<Block> AMARANTH_PATCH = registerPatch("biomeswevegone:amaranth");
		public static final RegistryObject<Block> ANGELICA_PATCH = registerPatch("biomeswevegone:angelica");
		public static final RegistryObject<Block> BEGONIA_PATCH = registerPatch("biomeswevegone:begonia");
		public static final RegistryObject<Block> BISTORT_PATCH = registerPatch("biomeswevegone:bistort");
		public static final RegistryObject<Block> BLACK_ROSE_PATCH = registerPatch("biomeswevegone:black_rose");
		public static final RegistryObject<Block> BLUE_SAGE_PATCH = registerPatch("biomeswevegone:blue_sage");
		public static final RegistryObject<Block> CALIFORNIA_POPPY_PATCH = registerPatch("biomeswevegone:california_poppy");
		public static final RegistryObject<Block> CROCUS_PATCH = registerPatch("biomeswevegone:crocus");
		public static final RegistryObject<Block> CYAN_AMARANTH_PATCH = registerPatch("biomeswevegone:cyan_amaranth");
		public static final RegistryObject<Block> CYAN_ROSE_PATCH = registerPatch("biomeswevegone:cyan_rose");
		public static final RegistryObject<Block> CYAN_TULIP_PATCH = registerPatch("biomeswevegone:cyan_tulip");
		public static final RegistryObject<Block> DAFFODIL_PATCH = registerPatch("biomeswevegone:daffodil");
		public static final RegistryObject<Block> FAIRY_SLIPPER_PATCH = registerPatch("biomeswevegone:fairy_slipper");
		public static final RegistryObject<Block> GREEN_TULIP_PATCH = registerPatch("biomeswevegone:green_tulip");
		public static final RegistryObject<Block> GUZMANIA_PATCH = registerPatch("biomeswevegone:guzmania");
		public static final RegistryObject<Block> INCAN_LILY_PATCH = registerPatch("biomeswevegone:incan_lily");
		public static final RegistryObject<Block> IRIS_PATCH = registerPatch("biomeswevegone:iris");
		public static final RegistryObject<Block> KOVAN_FLOWER_PATCH = registerPatch("biomeswevegone:kovan_flower");
		public static final RegistryObject<Block> LAZARUS_BELLFLOWER_PATCH = registerPatch("biomeswevegone:lazarus_bellflower");
		public static final RegistryObject<Block> LOLLIPOP_FLOWER_PATCH = registerPatch("biomeswevegone:lollipop_flower");
		public static final RegistryObject<Block> MAGENTA_AMARANTH_PATCH = registerPatch("biomeswevegone:magenta_amaranth");
		public static final RegistryObject<Block> MAGENTA_TULIP_PATCH = registerPatch("biomeswevegone:magenta_tulip");
		public static final RegistryObject<Block> ORANGE_AMARANTH_PATCH = registerPatch("biomeswevegone:orange_amaranth");
		public static final RegistryObject<Block> ORANGE_DAISY_PATCH = registerPatch("biomeswevegone:orange_daisy");
		public static final RegistryObject<Block> OSIRIA_ROSE_PATCH = registerPatch("biomeswevegone:osiria_rose");
		public static final RegistryObject<Block> PEACH_LEATHER_FLOWER_PATCH = registerPatch("biomeswevegone:peach_leather_flower");
		public static final RegistryObject<Block> PINK_ALLIUM_PATCH = registerPatch("biomeswevegone:pink_allium");
		public static final RegistryObject<Block> PINK_ANEMONE_PATCH = registerPatch("biomeswevegone:pink_anemone");
		public static final RegistryObject<Block> PINK_DAFFODIL_PATCH = registerPatch("biomeswevegone:pink_daffodil");
		public static final RegistryObject<Block> PROTEA_FLOWER_PATCH = registerPatch("biomeswevegone:protea_flower");
		public static final RegistryObject<Block> PURPLE_AMARANTH_PATCH = registerPatch("biomeswevegone:purple_amaranth");
		public static final RegistryObject<Block> PURPLE_SAGE_PATCH = registerPatch("biomeswevegone:purple_sage");
		public static final RegistryObject<Block> PURPLE_TULIP_PATCH = registerPatch("biomeswevegone:purple_tulip");
		public static final RegistryObject<Block> RICHEA_PATCH = registerPatch("biomeswevegone:richea");
		public static final RegistryObject<Block> ROSE_PATCH = registerPatch("biomeswevegone:rose");
		public static final RegistryObject<Block> SILVER_VASE_FLOWER_PATCH = registerPatch("biomeswevegone:silver_vase_flower");
		public static final RegistryObject<Block> SNOWDROPS_PATCH = registerPatch("biomeswevegone:snowdrops");
		public static final RegistryObject<Block> VIOLET_LEATHER_FLOWER_PATCH = registerPatch("biomeswevegone:violet_leather_flower");
		public static final RegistryObject<Block> WHITE_ALLIUM_PATCH = registerPatch("biomeswevegone:white_allium");
		public static final RegistryObject<Block> WHITE_ANEMONE_PATCH = registerPatch("biomeswevegone:white_anemone");
		public static final RegistryObject<Block> WHITE_SAGE_PATCH = registerPatch("biomeswevegone:white_sage");
		public static final RegistryObject<Block> WINTER_CYCLAMEN_PATCH = registerPatch("biomeswevegone:winter_cyclamen");
		public static final RegistryObject<Block> WINTER_ROSE_PATCH = registerPatch("biomeswevegone:winter_rose");
		public static final RegistryObject<Block> WINTER_SCILLA_PATCH = registerPatch("biomeswevegone:winter_scilla");
		public static final RegistryObject<Block> YELLOW_DAFFODIL_PATCH = registerPatch("biomeswevegone:yellow_daffodil");
		public static final RegistryObject<Block> YELLOW_TULIP_PATCH = registerPatch("biomeswevegone:yellow_tulip");

		// Called in the mod initializer / constructor in order to make sure that items are registered
		public static void loadClass() {
		}
	}

	public static class EternalStarlightCompat {
		public static final RegistryObject<Block> AUREATE_FLOWER_PATCH = registerPatch("eternal_starlight:aureate_flower");
		public static final RegistryObject<Block> BLUE_CRYSTALFLEUR_PATCH = registerPatch("eternal_starlight:blue_crystalfleur");
		public static final RegistryObject<Block> CONEBLOOM_PATCH = registerPatch("eternal_starlight:conebloom");
		public static final RegistryObject<Block> DESERT_AMETHYSIA_PATCH = registerPatch("eternal_starlight:desert_amethysia");
		public static final RegistryObject<Block> NIGHTFAN_PATCH = registerPatch("eternal_starlight:nightfan");
		public static final RegistryObject<Block> PINK_ROSE_PATCH = registerPatch("eternal_starlight:pink_rose");
		public static final RegistryObject<Block> RED_CRYSTALFLEUR_PATCH = registerPatch("eternal_starlight:red_crystalfleur");
		public static final RegistryObject<Block> RED_VELVETUMOSS_FLOWER_PATCH = registerPatch("eternal_starlight:red_velvetumoss_flower");
		public static final RegistryObject<Block> STARLIGHT_FLOWER_PATCH = registerPatch("eternal_starlight:starlight_flower");
		public static final RegistryObject<Block> STARLIGHT_TORCHFLOWER_PATCH = registerPatch("eternal_starlight:starlight_torchflower");
		public static final RegistryObject<Block> SWAMP_ROSE_PATCH = registerPatch("eternal_starlight:swamp_rose");
		public static final RegistryObject<Block> WHISPERBLOOM_PATCH = registerPatch("eternal_starlight:whisperbloom");
		public static final RegistryObject<Block> WITHERED_DESERT_AMETHYSIA_PATCH = registerPatch("eternal_starlight:withered_desert_amethysia");
		public static final RegistryObject<Block> WITHERED_STARLIGHT_FLOWER_PATCH = registerPatch("eternal_starlight:withered_starlight_flower");

		// Called in the mod initializer / constructor in order to make sure that items are registered
		public static void loadClass() {
		}
	}

	public static class RegionsUnexploredCompat {
		public static final RegistryObject<Block> ALPHA_DANDELION_PATCH = registerPatch("regions_unexplored:alpha_dandelion");
		public static final RegistryObject<Block> ALPHA_ROSE_PATCH = registerPatch("regions_unexplored:alpha_rose");
		public static final RegistryObject<Block> ASTER_PATCH = registerPatch("regions_unexplored:aster");
		public static final RegistryObject<Block> BLACK_SNOWBELLE_PATCH = registerPatch("regions_unexplored:black_snowbelle");
		public static final RegistryObject<Block> BLEEDING_HEART_PATCH = registerPatch("regions_unexplored:bleeding_heart");
		public static final RegistryObject<Block> BLUE_LUPINE_PATCH = registerPatch("regions_unexplored:blue_lupine", "lupine_blue");
		public static final RegistryObject<Block> BLUE_SNOWBELLE_PATCH = registerPatch("regions_unexplored:blue_snowbelle");
		public static final RegistryObject<Block> BROWN_SNOWBELLE_PATCH = registerPatch("regions_unexplored:brown_snowbelle");
		public static final RegistryObject<Block> CYAN_SNOWBELLE_PATCH = registerPatch("regions_unexplored:cyan_snowbelle");
		public static final RegistryObject<Block> DAISY_PATCH = registerPatch("regions_unexplored:daisy");
		public static final RegistryObject<Block> DORCEL_PATCH = registerPatch("regions_unexplored:dorcel");
		public static final RegistryObject<Block> FELICIA_DAISY_PATCH = registerPatch("regions_unexplored:felicia_daisy");
		public static final RegistryObject<Block> FIREWEED_PATCH = registerPatch("regions_unexplored:fireweed");
		public static final RegistryObject<Block> GLISTERING_BLOOM_PATCH = registerPatch("regions_unexplored:glistering_bloom");
		public static final RegistryObject<Block> GRAY_SNOWBELLE_PATCH = registerPatch("regions_unexplored:gray_snowbelle");
		public static final RegistryObject<Block> GREEN_SNOWBELLE_PATCH = registerPatch("regions_unexplored:green_snowbelle");
		public static final RegistryObject<Block> HIBISCUS_PATCH = registerPatch("regions_unexplored:hibiscus");
		public static final RegistryObject<Block> HYSSOP_PATCH = registerPatch("regions_unexplored:hyssop");
		public static final RegistryObject<Block> LIGHT_BLUE_SNOWBELLE_PATCH = registerPatch("regions_unexplored:light_blue_snowbelle");
		public static final RegistryObject<Block> LIGHT_GRAY_SNOWBELLE_PATCH = registerPatch("regions_unexplored:light_gray_snowbelle");
		public static final RegistryObject<Block> LIME_SNOWBELLE_PATCH = registerPatch("regions_unexplored:lime_snowbelle");
		public static final RegistryObject<Block> MAGENTA_SNOWBELLE_PATCH = registerPatch("regions_unexplored:magenta_snowbelle");
		public static final RegistryObject<Block> MALLOW_PATCH = registerPatch("regions_unexplored:mallow");
		public static final RegistryObject<Block> ORANGE_SNOWBELLE_PATCH = registerPatch("regions_unexplored:orange_snowbelle");
		public static final RegistryObject<Block> PINK_LUPINE_PATCH = registerPatch("regions_unexplored:pink_lupine", "lupine_pink");
		public static final RegistryObject<Block> PINK_SNOWBELLE_PATCH = registerPatch("regions_unexplored:pink_snowbelle");
		public static final RegistryObject<Block> POPPY_BUSH_PATCH = registerPatch("regions_unexplored:poppy_bush");
		public static final RegistryObject<Block> PURPLE_LUPINE_PATCH = registerPatch("regions_unexplored:purple_lupine", "lupine_purple");
		public static final RegistryObject<Block> PURPLE_SNOWBELLE_PATCH = registerPatch("regions_unexplored:purple_snowbelle");
		public static final RegistryObject<Block> RED_LUPINE_PATCH = registerPatch("regions_unexplored:red_lupine", "lupine_red");
		public static final RegistryObject<Block> RED_SNOWBELLE_PATCH = registerPatch("regions_unexplored:red_snowbelle");
		public static final RegistryObject<Block> SALMON_POPPY_BUSH_PATCH = registerPatch("regions_unexplored:salmon_poppy_bush", "poppy_bush_salmon");
		public static final RegistryObject<Block> TSUBAKI_PATCH = registerPatch("regions_unexplored:tsubaki");
		public static final RegistryObject<Block> WARATAH_PATCH = registerPatch("regions_unexplored:waratah");
		public static final RegistryObject<Block> WHITE_SNOWBELLE_PATCH = registerPatch("regions_unexplored:white_snowbelle");
		public static final RegistryObject<Block> WHITE_TRILLIUM_PATCH = registerPatch("regions_unexplored:white_trillium", "trillium_white");
		public static final RegistryObject<Block> WILTING_TRILLIUM_PATCH = registerPatch("regions_unexplored:wilting_trillium");
		public static final RegistryObject<Block> YELLOW_LUPINE_PATCH = registerPatch("regions_unexplored:yellow_lupine", "lupine_yellow");
		public static final RegistryObject<Block> YELLOW_SNOWBELLE_PATCH = registerPatch("regions_unexplored:yellow_snowbelle");

		// Called in the mod initializer / constructor in order to make sure that items are registered
		public static void loadClass() {
		}
	}

	/**
	 * Helper method to register a block with and automatically set the ID
	 * @param name the name of the block
	 * @param func a function that takes properties and returns a block
	 * @param props the properties to apply to the block
	 * @return a RegistryObject<Block> that represents the registered block
	 */
	public static RegistryObject<Block> register(String name, Function<Properties, ? extends Block> func, BlockBehaviour.Properties props) {
		return BLOCKS.register(name, () -> func.apply(props.setId(ResourceKey.create(Registries.BLOCK, Constants.modLoc(name)))));
	}

	public static RegistryObject<Block> registerPatch(String block) {
		Identifier blockLoc = Identifier.parse(block);
		ResourceKey<Block> blockKey = ResourceKey.create(Registries.BLOCK, blockLoc.withSuffix("_patch"));
		return register(blockLoc.getPath() + "_patch", (properties) ->
						new CompatPatchBlock(MobEffects.UNLUCK, 7, blockLoc, blockLoc.getPath(), properties),
				BlockBehaviour.Properties.ofFullCopy(Blocks.WHITE_TULIP).setId(blockKey).noCollision().instabreak()
						.sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ)
		);
	}

	public static RegistryObject<Block> registerPatch(String block, String textureName) {
		Identifier blockLoc = Identifier.parse(block);
		ResourceKey<Block> blockKey = ResourceKey.create(Registries.BLOCK, blockLoc.withSuffix("_patch"));
		return register(blockLoc.getPath() + "_patch", (properties) ->
						new CompatPatchBlock(MobEffects.UNLUCK, 7, blockLoc, textureName, properties),
				BlockBehaviour.Properties.ofFullCopy(Blocks.WHITE_TULIP).setId(blockKey).noCollision().instabreak()
						.sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ)
		);
	}
}

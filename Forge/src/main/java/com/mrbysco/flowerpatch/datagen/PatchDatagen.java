package com.mrbysco.flowerpatch.datagen;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import com.mrbysco.flowerpatch.Constants;
import com.mrbysco.flowerpatch.block.FlowerPatchBlock;
import com.mrbysco.flowerpatch.block.PatchBlock;
import com.mrbysco.flowerpatch.registration.PatchRegistry;
import com.mrbysco.flowerpatch.registration.RegistryObject;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTable.Builder;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class PatchDatagen {
	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();
		ExistingFileHelper helper = event.getExistingFileHelper();

		if (event.includeServer()) {
			generator.addProvider(event.includeServer(), new Loots(generator));
			BlockTagsProvider provider;
			generator.addProvider(event.includeServer(), provider = new PatchBlockTags(generator, helper));
			generator.addProvider(event.includeServer(), new PatchItemTags(generator, provider, helper));
		}
		if (event.includeClient()) {
			generator.addProvider(event.includeClient(), new Language(generator));
			generator.addProvider(event.includeClient(), new BlockModels(generator, helper));
			generator.addProvider(event.includeClient(), new BlockStates(generator, helper));
		}
	}

	private static class Loots extends LootTableProvider {
		public Loots(DataGenerator gen) {
			super(gen);
		}

		@Override
		protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, Builder>>>, LootContextParamSet>> getTables() {
			return ImmutableList.of(
					Pair.of(PatchBlockLoot::new, LootContextParamSets.BLOCK)
			);
		}

		public static class PatchBlockLoot extends BlockLoot {

			@Override
			protected void addTables() {
				for (RegistryObject<Block> registryObject : PatchRegistry.BLOCKS.getEntries()) {
					if (registryObject.get() instanceof PatchBlock patch) {
						Block patchBlock = registryObject.get();
						this.add(patchBlock, (block) -> LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
								.add(applyExplosionDecay(block, LootItem.lootTableItem(patch.getPatchDelegate().get()).apply(List.of(2, 3, 4), (value) ->
										SetItemCountFunction.setCount(ConstantValue.exactly((float) value.intValue())).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(patchBlock)
												.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(patch.getProperty(), value))))))));
					}
				}
			}

			@Override
			protected Iterable<Block> getKnownBlocks() {
				return (Iterable<Block>) PatchRegistry.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
			}
		}

		@Override
		protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext validationContext) {
			map.forEach((name, table) -> LootTables.validate(validationContext, name, table));
		}
	}

	private static class Language extends LanguageProvider {
		public Language(DataGenerator gen) {
			super(gen, Constants.MOD_ID, "en_us");
		}

		@Override
		protected void addTranslations() {
			for (RegistryObject<Block> registryObject : PatchRegistry.BLOCKS.getEntries()) {
				if (registryObject.get() instanceof PatchBlock patchBlock) {
					this.addBlock(registryObject, I18n.get(patchBlock.getPatchDelegate().get().getDescriptionId()) + " Patch");
				}
			}

			this.add("text.autoconfig.flowerpatch.option.general", "General");
			this.add("text.autoconfig.flowerpatch.option.general.flowerToPatchBonemealing", "flowerToPatchBonemealing");
			this.add("text.autoconfig.flowerpatch.option.general.patchBonemealing", "patchBonemealing");
		}
	}

	private static class BlockStates extends BlockStateProvider {
		public BlockStates(DataGenerator gen, ExistingFileHelper helper) {
			super(gen, Constants.MOD_ID, helper);
		}

		@Override
		protected void registerStatesAndModels() {
			for (RegistryObject<Block> registryObject : PatchRegistry.BLOCKS.getEntries()) {
				if (registryObject.get() instanceof PatchBlock) {
					this.generatePatchState(registryObject.get());
				}
			}
		}

		protected void generatePatchState(Block block) {
			ModelFile patchModel2 = models().getExistingFile(modLoc("block/" + ForgeRegistries.BLOCKS.getKey(block).getPath() + "_2"));
			ModelFile patchModel3 = models().getExistingFile(modLoc("block/" + ForgeRegistries.BLOCKS.getKey(block).getPath() + "_3"));
			ModelFile patchModel4 = models().getExistingFile(modLoc("block/" + ForgeRegistries.BLOCKS.getKey(block).getPath() + "_4"));
			PatchBlock patchBlock = (PatchBlock) block;
			getVariantBuilder(block)
					.partialState().with(patchBlock.getProperty(), 2)
					.addModels(
							new ConfiguredModel(patchModel2),
							new ConfiguredModel(patchModel2, 0, 90, false),
							new ConfiguredModel(patchModel2, 0, 180, false),
							new ConfiguredModel(patchModel2, 0, 270, false))
					.partialState().with(patchBlock.getProperty(), 3)
					.addModels(
							new ConfiguredModel(patchModel3),
							new ConfiguredModel(patchModel3, 0, 90, false),
							new ConfiguredModel(patchModel3, 0, 180, false),
							new ConfiguredModel(patchModel3, 0, 270, false))
					.partialState().with(patchBlock.getProperty(), 4)
					.addModels(
							new ConfiguredModel(patchModel4),
							new ConfiguredModel(patchModel4, 0, 90, false),
							new ConfiguredModel(patchModel4, 0, 180, false),
							new ConfiguredModel(patchModel4, 0, 270, false));
		}
	}

	private static class BlockModels extends BlockModelProvider {
		public BlockModels(DataGenerator gen, ExistingFileHelper helper) {
			super(gen, Constants.MOD_ID, helper);
		}

		@Override
		protected void registerModels() {
			for (RegistryObject<Block> registryObject : PatchRegistry.BLOCKS.getEntries()) {
				if (registryObject.get() instanceof PatchBlock) {
					this.generatePatchModels(registryObject.get());
				}
			}
		}

		protected void generatePatchModels(Block block) {
			crossBlock(block);
		}

		private void crossBlock(Block block) {
			patchBlock(block, 2);
			patchBlock(block, 3);
			patchBlock(block, 4);
		}

		private BlockModelBuilder patchBlock(Block block, int flowers) {
			String path = ForgeRegistries.BLOCKS.getKey(block).getPath() + "_" + flowers;
			return singleTexture(path, modLoc(BLOCK_FOLDER + "/patch" + flowers),
					"cross", mcLoc(BLOCK_FOLDER + "/" + ForgeRegistries.BLOCKS.getKey(((PatchBlock) block).getPatchDelegate().get()).getPath())).renderType("cutout");
		}
	}

	public static class PatchBlockTags extends BlockTagsProvider {

		public PatchBlockTags(DataGenerator generator, @Nullable ExistingFileHelper existingFileHelper) {
			super(generator, Constants.MOD_ID, existingFileHelper);
		}

		@Override
		protected void addTags() {
			for (RegistryObject<Block> registryObject : PatchRegistry.BLOCKS.getEntries()) {
				if (registryObject.get() instanceof FlowerPatchBlock flowerPatchBlock) {
					this.tag(BlockTags.FLOWERS).add(flowerPatchBlock);
				}
			}

			this.tag(Constants.BONEMEAL_ABLE_FLOWERS).add(Blocks.DANDELION, Blocks.POPPY, Blocks.BLUE_ORCHID, Blocks.ALLIUM,
					Blocks.AZURE_BLUET, Blocks.RED_TULIP, Blocks.ORANGE_TULIP, Blocks.WHITE_TULIP, Blocks.PINK_TULIP,
					Blocks.OXEYE_DAISY, Blocks.CORNFLOWER, Blocks.LILY_OF_THE_VALLEY);
		}
	}

	public static class PatchItemTags extends ItemTagsProvider {
		public PatchItemTags(DataGenerator dataGenerator, BlockTagsProvider blockTagsProvider, ExistingFileHelper existingFileHelper) {
			super(dataGenerator, blockTagsProvider, Constants.MOD_ID, existingFileHelper);
		}

		@Override
		protected void addTags() {
			this.tag(Constants.BONEMEAL).add(Items.BONE_MEAL);
		}
	}
}
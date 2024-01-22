package com.mrbysco.flowerpatch.datagen;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import com.mrbysco.flowerpatch.FlowerPatch;
import com.mrbysco.flowerpatch.block.FlowerPatchBlock;
import com.mrbysco.flowerpatch.block.PatchBlock;
import com.mrbysco.flowerpatch.registry.PatchRegistry;
import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.data.LootTableProvider;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraft.item.Items;
import net.minecraft.loot.ConstantRange;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.LootParameterSet;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.ValidationTracker;
import net.minecraft.loot.conditions.BlockStateProperty;
import net.minecraft.loot.functions.SetCount;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ForgeI18n;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
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
			generator.addProvider(new Loots(generator));
			BlockTagsProvider provider;
			generator.addProvider(provider = new PatchBlockTags(generator, helper));
			generator.addProvider(new PatchItemTags(generator, provider, helper));
		}
		if (event.includeClient()) {
			generator.addProvider(new Language(generator));
			generator.addProvider(new BlockModels(generator, helper));
			generator.addProvider(new BlockStates(generator, helper));
		}
	}

	private static class Loots extends LootTableProvider {
		public Loots(DataGenerator gen) {
			super(gen);
		}

		@Override
		protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>> getTables() {
			return ImmutableList.of(
					Pair.of(PatchBlockLoot::new, LootParameterSets.BLOCK)
			);
		}

		public static class PatchBlockLoot extends BlockLootTables {

			@Override
			protected void addTables() {
				for (RegistryObject<Block> registryObject : PatchRegistry.BLOCKS.getEntries()) {
					if (registryObject.get() instanceof PatchBlock) {
						PatchBlock patchBlock = (PatchBlock) registryObject.get();
						this.add(registryObject.get(), (block) -> LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1))
								.add(applyExplosionDecay(block, ItemLootEntry.lootTableItem(patchBlock.getPatchDelegate().get())
										.apply(SetCount.setCount(ConstantRange.exactly(2))
												.when(BlockStateProperty.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(FlowerPatchBlock.FLOWERS, 2))))
										.apply(SetCount.setCount(ConstantRange.exactly(3))
												.when(BlockStateProperty.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(FlowerPatchBlock.FLOWERS, 3))))
										.apply(SetCount.setCount(ConstantRange.exactly(4))
												.when(BlockStateProperty.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(FlowerPatchBlock.FLOWERS, 4))))))));
					}
				}
			}

			@Override
			protected Iterable<Block> getKnownBlocks() {
				return (Iterable<Block>) PatchRegistry.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
			}
		}

		@Override
		protected void validate(Map<ResourceLocation, LootTable> map, ValidationTracker validationtracker) {
			map.forEach((name, table) -> table.validate(validationtracker));
		}
	}

	private static class Language extends LanguageProvider {
		public Language(DataGenerator gen) {
			super(gen, FlowerPatch.MOD_ID, "en_us");
		}

		@Override
		protected void addTranslations() {
			for (RegistryObject<Block> registryObject : PatchRegistry.BLOCKS.getEntries()) {
				if (registryObject.get() instanceof PatchBlock) {
					PatchBlock patchBlock = (PatchBlock) registryObject.get();
					this.addBlock(registryObject, ForgeI18n.parseFormat(patchBlock.getPatchDelegate().get().getDescriptionId()) + " Patch");
				}
			}
		}
	}

	private static class BlockStates extends BlockStateProvider {
		public BlockStates(DataGenerator gen, ExistingFileHelper helper) {
			super(gen, FlowerPatch.MOD_ID, helper);
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
			super(gen, FlowerPatch.MOD_ID, helper);
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
					"cross", mcLoc(BLOCK_FOLDER + "/" + ForgeRegistries.BLOCKS.getKey(((PatchBlock)block).getPatchDelegate().get()).getPath()));
		}
	}

	public static class PatchBlockTags extends BlockTagsProvider {

		public PatchBlockTags(DataGenerator generator, @Nullable ExistingFileHelper existingFileHelper) {
			super(generator, FlowerPatch.MOD_ID, existingFileHelper);
		}

		@Override
		protected void addTags() {
			for (RegistryObject<Block> registryObject : PatchRegistry.BLOCKS.getEntries()) {
				if (registryObject.get() instanceof FlowerPatchBlock) {
					this.tag(BlockTags.FLOWERS).add(registryObject.get());
				}
			}

			this.tag(FlowerPatch.BONEMEAL_ABLE_FLOWERS).add(Blocks.DANDELION, Blocks.POPPY, Blocks.BLUE_ORCHID, Blocks.ALLIUM,
					Blocks.AZURE_BLUET, Blocks.RED_TULIP, Blocks.ORANGE_TULIP, Blocks.WHITE_TULIP, Blocks.PINK_TULIP,
					Blocks.OXEYE_DAISY, Blocks.CORNFLOWER, Blocks.LILY_OF_THE_VALLEY);
		}
	}

	public static class PatchItemTags extends ItemTagsProvider {
		public PatchItemTags(DataGenerator dataGenerator, BlockTagsProvider blockTagsProvider, ExistingFileHelper existingFileHelper) {
			super(dataGenerator, blockTagsProvider, FlowerPatch.MOD_ID, existingFileHelper);
		}

		@Override
		protected void addTags() {
			this.tag(FlowerPatch.BONEMEAL).add(Items.BONE_MEAL);
		}
	}
}

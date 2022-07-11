package com.mrbysco.flowerpatch.datagen;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import com.mrbysco.flowerpatch.FlowerPatch;
import com.mrbysco.flowerpatch.block.FlowerPatchBlock;
import com.mrbysco.flowerpatch.registry.PatchRegistry;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
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
import net.minecraftforge.common.ForgeI18n;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

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
		protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, Builder>>>, LootContextParamSet>> getTables() {
			return ImmutableList.of(
					Pair.of(PatchBlockLoot::new, LootContextParamSets.BLOCK)
			);
		}

		public static class PatchBlockLoot extends BlockLoot {

			@Override
			protected void addTables() {
				for (RegistryObject<Block> registryObject : PatchRegistry.BLOCKS.getEntries()) {
					if (registryObject.get() instanceof FlowerPatchBlock flowerPatchBlock) {
						this.add(flowerPatchBlock, (block) -> LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
								.add(applyExplosionDecay(block, LootItem.lootTableItem(flowerPatchBlock.getFlowerDelegate().get())
										.apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F))
												.when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(FlowerPatchBlock.FLOWERS, 2))))
										.apply(SetItemCountFunction.setCount(ConstantValue.exactly(3.0F))
												.when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(FlowerPatchBlock.FLOWERS, 3))))
										.apply(SetItemCountFunction.setCount(ConstantValue.exactly(4.0F))
												.when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(FlowerPatchBlock.FLOWERS, 4))))))));
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
			super(gen, FlowerPatch.MOD_ID, "en_us");
		}

		@Override
		protected void addTranslations() {
			for (RegistryObject<Block> registryObject : PatchRegistry.BLOCKS.getEntries()) {
				if (registryObject.get() instanceof FlowerPatchBlock flowerPatchBlock) {
					this.addBlock(registryObject, ForgeI18n.parseFormat(flowerPatchBlock.getFlowerDelegate().get().getDescriptionId()) + " Patch");
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
				if (registryObject.get() instanceof FlowerPatchBlock flowerPatchBlock) {
					this.generatePatchState(flowerPatchBlock);
				}
			}
		}

		protected void generatePatchState(FlowerPatchBlock flowerPatchBlock) {
			ModelFile patchModel2 = models().getExistingFile(modLoc("block/" + ForgeRegistries.BLOCKS.getKey(flowerPatchBlock).getPath() + "_2"));
			ModelFile patchModel3 = models().getExistingFile(modLoc("block/" + ForgeRegistries.BLOCKS.getKey(flowerPatchBlock).getPath() + "_3"));
			ModelFile patchModel4 = models().getExistingFile(modLoc("block/" + ForgeRegistries.BLOCKS.getKey(flowerPatchBlock).getPath() + "_4"));
			getVariantBuilder(flowerPatchBlock)
					.partialState().with(FlowerPatchBlock.FLOWERS, 2)
					.addModels(
							new ConfiguredModel(patchModel2),
							new ConfiguredModel(patchModel2, 0, 90, false),
							new ConfiguredModel(patchModel2, 0, 180, false),
							new ConfiguredModel(patchModel2, 0, 270, false))
					.partialState().with(FlowerPatchBlock.FLOWERS, 3)
					.addModels(
							new ConfiguredModel(patchModel3),
							new ConfiguredModel(patchModel3, 0, 90, false),
							new ConfiguredModel(patchModel3, 0, 180, false),
							new ConfiguredModel(patchModel3, 0, 270, false))
					.partialState().with(FlowerPatchBlock.FLOWERS, 4)
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
				if (registryObject.get() instanceof FlowerPatchBlock flowerPatchBlock) {
					this.generatePatchModels(flowerPatchBlock);
				}
			}
		}

		protected void generatePatchModels(FlowerPatchBlock flowerPatchBlock) {
			crossBlock(flowerPatchBlock);
		}

		private void crossBlock(FlowerPatchBlock flowerPatchBlock) {
			patchBlock(flowerPatchBlock, 2);
			patchBlock(flowerPatchBlock, 3);
			patchBlock(flowerPatchBlock, 4);
		}

		private BlockModelBuilder patchBlock(FlowerPatchBlock block, int flowers) {
			String path = ForgeRegistries.BLOCKS.getKey(block).getPath() + "_" + flowers;
			return singleTexture(path, modLoc(BLOCK_FOLDER + "/patch" + flowers),
					"cross", mcLoc(BLOCK_FOLDER + "/" + ForgeRegistries.BLOCKS.getKey(block.getFlowerDelegate().get()).getPath()));
		}
	}

	public static class PatchBlockTags extends BlockTagsProvider {

		public PatchBlockTags(DataGenerator generator, @Nullable ExistingFileHelper existingFileHelper) {
			super(generator, FlowerPatch.MOD_ID, existingFileHelper);
		}

		@Override
		protected void addTags() {
			for (RegistryObject<Block> registryObject : PatchRegistry.BLOCKS.getEntries()) {
				if (registryObject.get() instanceof FlowerPatchBlock flowerPatchBlock) {
					this.tag(BlockTags.FLOWERS).add(flowerPatchBlock);
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

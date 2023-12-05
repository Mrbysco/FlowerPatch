package com.mrbysco.flowerpatch.datagen;

import com.mrbysco.flowerpatch.Constants;
import com.mrbysco.flowerpatch.block.FlowerPatchBlock;
import com.mrbysco.flowerpatch.block.PatchBlock;
import com.mrbysco.flowerpatch.registration.PatchRegistry;
import com.mrbysco.flowerpatch.registration.RegistryObject;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder;
import net.neoforged.neoforge.client.model.generators.BlockModelProvider;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class PatchDatagen {
	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();
		PackOutput packOutput = generator.getPackOutput();
		CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
		ExistingFileHelper helper = event.getExistingFileHelper();

		if (event.includeServer()) {
			generator.addProvider(event.includeServer(), new Loots(packOutput));
			BlockTagsProvider provider;
			generator.addProvider(event.includeServer(), provider = new PatchBlockTags(packOutput, lookupProvider, helper));
			generator.addProvider(event.includeServer(), new PatchItemTags(packOutput, lookupProvider, provider, helper));
		}
		if (event.includeClient()) {
			generator.addProvider(event.includeClient(), new Language(packOutput));
			generator.addProvider(event.includeClient(), new BlockModels(packOutput, helper));
			generator.addProvider(event.includeClient(), new BlockStates(packOutput, helper));
		}
	}

	private static class Loots extends LootTableProvider {
		public Loots(PackOutput packOutput) {
			super(packOutput, Set.of(), List.of(
					new SubProviderEntry(PatchBlockLoot::new, LootContextParamSets.BLOCK)
			));
		}

		public static class PatchBlockLoot extends BlockLootSubProvider {
			protected PatchBlockLoot() {
				super(Set.of(), FeatureFlags.REGISTRY.allFlags());
			}

			@Override
			protected void generate() {
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
			map.forEach((name, table) -> table.validate(validationContext));
		}
	}

	private static class Language extends LanguageProvider {
		public Language(PackOutput packOutput) {
			super(packOutput, Constants.MOD_ID, "en_us");
		}

		@Override
		protected void addTranslations() {
			for (RegistryObject<Block> registryObject : PatchRegistry.BLOCKS.getEntries()) {
				if (registryObject.get() instanceof PatchBlock patchBlock) {
					this.addBlock(registryObject, I18n.get(patchBlock.getPatchDelegate().get().getDescriptionId()) + " Patch");
				}
			}

			this.add("text.autoconfig.flowerpatch.title", "Flower Patch");
			this.add("text.autoconfig.flowerpatch.option.general", "General");
			this.add("text.autoconfig.flowerpatch.option.general.flowerToPatchBonemealing", "flowerToPatchBonemealing");
			this.add("text.autoconfig.flowerpatch.option.general.patchBonemealing", "patchBonemealing");
			this.add("text.autoconfig.flowerpatch.option.general.placeOnLeaves", "placeOnLeaves");
		}
	}

	private static class BlockStates extends BlockStateProvider {
		public BlockStates(PackOutput packOutput, ExistingFileHelper helper) {
			super(packOutput, Constants.MOD_ID, helper);
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
			ModelFile patchModel2 = models().getExistingFile(modLoc("block/" + BuiltInRegistries.BLOCK.getKey(block).getPath() + "_2"));
			ModelFile patchModel3 = models().getExistingFile(modLoc("block/" + BuiltInRegistries.BLOCK.getKey(block).getPath() + "_3"));
			ModelFile patchModel4 = models().getExistingFile(modLoc("block/" + BuiltInRegistries.BLOCK.getKey(block).getPath() + "_4"));
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
		public BlockModels(PackOutput packOutput, ExistingFileHelper helper) {
			super(packOutput, Constants.MOD_ID, helper);
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
			String path = BuiltInRegistries.BLOCK.getKey(block).getPath() + "_" + flowers;
			return singleTexture(path, modLoc(BLOCK_FOLDER + "/patch" + flowers),
					"cross", mcLoc(BLOCK_FOLDER + "/" + BuiltInRegistries.BLOCK.getKey(((PatchBlock) block).getPatchDelegate().get()).getPath())).renderType("cutout");
		}
	}

	public static class PatchBlockTags extends BlockTagsProvider {

		public PatchBlockTags(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
			super(packOutput, lookupProvider, Constants.MOD_ID, existingFileHelper);
		}

		@Override
		protected void addTags(HolderLookup.Provider provider) {
			for (RegistryObject<Block> registryObject : PatchRegistry.BLOCKS.getEntries()) {
				if (registryObject.get() instanceof FlowerPatchBlock flowerPatchBlock) {
					this.tag(BlockTags.FLOWERS).add(flowerPatchBlock);
				}
			}

			this.tag(Constants.BONEMEAL_ABLE_FLOWERS).add(Blocks.DANDELION, Blocks.POPPY, Blocks.BLUE_ORCHID, Blocks.ALLIUM,
					Blocks.AZURE_BLUET, Blocks.RED_TULIP, Blocks.ORANGE_TULIP, Blocks.WHITE_TULIP, Blocks.PINK_TULIP,
					Blocks.OXEYE_DAISY, Blocks.CORNFLOWER, Blocks.LILY_OF_THE_VALLEY, Blocks.TORCHFLOWER);
		}
	}

	public static class PatchItemTags extends ItemTagsProvider {
		public PatchItemTags(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider, BlockTagsProvider blockTagsProvider, ExistingFileHelper existingFileHelper) {
			super(packOutput, lookupProvider, blockTagsProvider.contentsGetter(), Constants.MOD_ID, existingFileHelper);
		}

		@Override
		protected void addTags(HolderLookup.Provider provider) {
			this.tag(Constants.BONEMEAL).add(Items.BONE_MEAL);
		}
	}
}

package com.mrbysco.flowerpatch.datagen;

import com.mrbysco.flowerpatch.Constants;
import com.mrbysco.flowerpatch.block.CompatPatchBlock;
import com.mrbysco.flowerpatch.block.FlowerPatchBlock;
import com.mrbysco.flowerpatch.block.PatchBlock;
import com.mrbysco.flowerpatch.registration.CompatRegistry;
import com.mrbysco.flowerpatch.registration.PatchRegistry;
import com.mrbysco.flowerpatch.registration.RegistryObject;
import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ItemTagsProvider;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber
public class PatchDatagen {
	@SubscribeEvent
	public static void gatherData(GatherDataEvent.Client event) {
		DataGenerator generator = event.getGenerator();
		PackOutput packOutput = generator.getPackOutput();
		CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

		generator.addProvider(true, new Loots(packOutput, lookupProvider));
		generator.addProvider(true, new PatchBlockTags(packOutput, lookupProvider));
		generator.addProvider(true, new PatchItemTags(packOutput, lookupProvider));

		generator.addProvider(true, new Language(packOutput));
		generator.addProvider(true, new Models(packOutput));
	}

	private static class Loots extends LootTableProvider {
		public Loots(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
			super(packOutput, Set.of(), List.of(
					new SubProviderEntry(PatchBlockLoot::new, LootContextParamSets.BLOCK)
			), lookupProvider);
		}

		public static class PatchBlockLoot extends BlockLootSubProvider {
			protected PatchBlockLoot(HolderLookup.Provider lookupProvider) {
				super(Set.of(), FeatureFlags.REGISTRY.allFlags(), lookupProvider);
			}

			@Override
			protected void generate() {
				for (RegistryObject<Block> registryObject : PatchRegistry.BLOCKS.getEntries()) {
					if (registryObject.get() instanceof PatchBlock patch) {
						addPatch(patch);
					}
				}
				for (RegistryObject<Block> registryObject : CompatRegistry.BLOCKS.getEntries()) {
					if (registryObject.get() instanceof PatchBlock patch) {
						add(registryObject.get(), LootTable.lootTable());
					}
				}
			}

			private void addPatch(PatchBlock patch) {
				this.add((Block) patch, (block) -> LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
						.add(applyExplosionDecay(block, LootItem.lootTableItem(patch.getPatchDelegate().get()).apply(List.of(2, 3, 4), (value) ->
								SetItemCountFunction.setCount(ConstantValue.exactly(value.floatValue())).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
										.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(patch.getProperty(), value))))))));
			}

			@Override
			protected Iterable<Block> getKnownBlocks() {
				List<Block> blocks = new ArrayList<>();
				PatchRegistry.BLOCKS.getEntries().forEach(object -> blocks.add(object.get()));
				// Compat blocks
				CompatRegistry.BLOCKS.getEntries().forEach(object -> blocks.add(object.get()));
				return (Iterable<Block>) blocks::iterator;
			}
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
			for (RegistryObject<Block> registryObject : CompatRegistry.BLOCKS.getEntries()) {
				if (registryObject.get() instanceof PatchBlock patchBlock) {
					this.addBlock(registryObject, "%s Patch");
				}
			}

			this.add("text.autoconfig.flowerpatch.title", "Flower Patch");
			this.addConfig("general", null, "General", "General Settings");
			this.addConfig("flowerToPatchBonemealing", "general", "Flower to Patch Bonemealing",
					"Allows flowers to be bonemealed into flower patches");
			this.addConfig("patchBonemealing", "general", "Patch Bonemealing",
					"Allows flower patches to be bonemealed to add more flowers");
			this.addConfig("placeOnLeaves", "general", "Place on Leaves",
					"Allows flowers and other bush-like blocks to be place-able on leaves");
		}

		/**
		 * Add the translation for a config entry
		 *
		 * @param path        The path of the config entry
		 * @param name        The name of the config entry
		 * @param description The description of the config entry (optional in case of targeting "title" or similar entries that have no tooltip)
		 */
		private void addConfig(String path, @Nullable String category, String name, @Nullable String description) {
			String categoryString = category == null ? "" : category + ".";
			this.add(Constants.MOD_ID + ".configuration." + path, name);
			this.add("text.autoconfig." + Constants.MOD_ID + ".option." + categoryString + path, name);
			if (description != null && !description.isEmpty()) {
				this.add(Constants.MOD_ID + ".configuration." + path + ".tooltip", description);
				this.add("text.autoconfig." + Constants.MOD_ID + ".option." + categoryString + path + ".@Tooltip", description);
			}
		}
	}

	private static class Models extends ModelProvider {
		public static final ModelTemplate PATCH_2 = ModelTemplates.create("flowerpatch:patch2", TextureSlot.CROSS);
		public static final ModelTemplate PATCH_3 = ModelTemplates.create("flowerpatch:patch3", TextureSlot.CROSS);
		public static final ModelTemplate PATCH_4 = ModelTemplates.create("flowerpatch:patch4", TextureSlot.CROSS);

		public Models(PackOutput output) {
			super(output, Constants.MOD_ID);
		}

		@Override
		protected void registerModels(@NotNull BlockModelGenerators blockModels, @NotNull ItemModelGenerators itemModels) {
			for (RegistryObject<Block> registryObject : PatchRegistry.BLOCKS.getEntries()) {
				if (registryObject.get() instanceof PatchBlock) {
					this.generatePatchState(blockModels, registryObject.get());
				}
			}
			for (RegistryObject<Block> registryObject : CompatRegistry.BLOCKS.getEntries()) {
				if (registryObject.get() instanceof PatchBlock) {
					this.generatePatchState(blockModels, registryObject.get());
				}
			}
		}

		protected void generatePatchState(@NotNull BlockModelGenerators blockModels, Block block) {
			PatchBlock patchBlock = (PatchBlock) block;

			TextureMapping crossMapping;
			if (block instanceof CompatPatchBlock compatPatchBlock) {
				crossMapping = TextureMapping.singleSlot(TextureSlot.CROSS,
						new Material(Identifier.fromNamespaceAndPath(
								compatPatchBlock.getNameSpace(), "block/" + compatPatchBlock.getTexturePath())));
			} else {
				crossMapping = TextureMapping.singleSlot(TextureSlot.CROSS,
						new Material(Identifier.parse("block/" + BuiltInRegistries.BLOCK.getKey(patchBlock.getPatchDelegate().get()).getPath())));
			}
			Identifier patchModel2 = PATCH_2.createWithSuffix(block, "_2", crossMapping, blockModels.modelOutput);
			Identifier patchModel3 = PATCH_3.createWithSuffix(block, "_3", crossMapping, blockModels.modelOutput);
			Identifier patchModel4 = PATCH_4.createWithSuffix(block, "_4", crossMapping, blockModels.modelOutput);

			blockModels.blockStateOutput
					.accept(
							MultiVariantGenerator.dispatch(block)
									.with(
											PropertyDispatch.initial(patchBlock.getProperty())
													.select(2,
															BlockModelGenerators.createRotatedVariants(
																	BlockModelGenerators.plainModel(patchModel2)))
													.select(3,
															BlockModelGenerators.createRotatedVariants(
																	BlockModelGenerators.plainModel(patchModel3)))
													.select(4,
															BlockModelGenerators.createRotatedVariants(
																	BlockModelGenerators.plainModel(patchModel4)))
									)
					);
		}
	}

	public static class PatchBlockTags extends BlockTagsProvider {

		public PatchBlockTags(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
			super(packOutput, lookupProvider, Constants.MOD_ID);
		}

		@Override
		protected void addTags(HolderLookup.Provider provider) {
			for (RegistryObject<Block> registryObject : PatchRegistry.BLOCKS.getEntries()) {
				if (registryObject.get() instanceof FlowerPatchBlock flowerPatchBlock) {
					this.tag(BlockTags.FLOWERS).add(flowerPatchBlock);
				}
			}

			for (RegistryObject<Block> registryObject : CompatRegistry.BLOCKS.getEntries()) {
				if (registryObject.get() instanceof CompatPatchBlock) {
					this.tag(BlockTags.FLOWERS).addOptional(registryObject.get());
				}
			}

			this.tag(Constants.BONEMEAL_ABLE_FLOWERS).add(Blocks.DANDELION, Blocks.POPPY, Blocks.BLUE_ORCHID, Blocks.ALLIUM,
					Blocks.AZURE_BLUET, Blocks.RED_TULIP, Blocks.ORANGE_TULIP, Blocks.WHITE_TULIP, Blocks.PINK_TULIP,
					Blocks.OXEYE_DAISY, Blocks.CORNFLOWER, Blocks.LILY_OF_THE_VALLEY);
		}
	}

	public static class PatchItemTags extends ItemTagsProvider {
		public PatchItemTags(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
			super(packOutput, lookupProvider, Constants.MOD_ID);
		}

		@Override
		protected void addTags(HolderLookup.Provider provider) {
			this.tag(Constants.BONEMEAL).add(Items.BONE_MEAL);
		}
	}
}

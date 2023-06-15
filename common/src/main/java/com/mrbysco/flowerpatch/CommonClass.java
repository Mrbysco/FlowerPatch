package com.mrbysco.flowerpatch;

import com.mrbysco.flowerpatch.block.FlowerPatchBlock;
import com.mrbysco.flowerpatch.block.PatchBlock;
import com.mrbysco.flowerpatch.platform.Services;
import com.mrbysco.flowerpatch.registration.PatchRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

import java.util.Optional;

public class CommonClass {
	public static void init() {
		PatchRegistry.loadClass();
	}

	public static InteractionResult onBlockInteraction(Level level, BlockPos pos, Player player, InteractionHand hand) {
		final BlockState state = level.getBlockState(pos);
		final ItemStack stack = player.getItemInHand(hand);
		if (state.getBlock().asItem().equals(stack.getItem()) ||
				(state.getBlock() instanceof PatchBlock patchBlock && patchBlock.getPatchDelegate().get().asItem().equals(stack.getItem()))) {
			Optional<Block> optionalPatch = PatchRegistry.BLOCKS.getEntries().stream().filter(object -> object.get() instanceof PatchBlock patchBlock &&
					patchBlock.getPatchDelegate().get().asItem().equals(stack.getItem())).map(object -> object.get()).findFirst();

			if (optionalPatch.isPresent()) {
				Block block = optionalPatch.get();
				PatchBlock patchBlock = (PatchBlock) block;
				BlockState newState = block.defaultBlockState();
				IntegerProperty property = patchBlock.getProperty();
				if (state.hasProperty(property)) {
					if (state.getValue(property) == patchBlock.getMaxAmount()) {
						return InteractionResult.FAIL;
					}
					newState = state.setValue(property, Math.min(patchBlock.getMaxAmount(), state.getValue(property) + 1));
				}
				level.setBlockAndUpdate(pos, newState);
				level.playSound((Player) null, pos, newState.getSoundType().getPlaceSound(), SoundSource.BLOCKS, 1.0F, 1.0F);
				if (!player.getAbilities().instabuild) {
					stack.shrink(1);
				}
				return InteractionResult.SUCCESS;
			}
		}
		return InteractionResult.PASS;
	}


	public static InteractionResult onBonemeal(Level level, BlockPos pos, BlockState state, ItemStack stack, Player player) {
		if (Services.PLATFORM.flowerToPatchBonemealing() &&
				state.is(Constants.BONEMEAL_ABLE_FLOWERS) && stack.is(Constants.BONEMEAL)) {
			Optional<FlowerPatchBlock> flowerPatchOptional = PatchRegistry.BLOCKS.getEntries().stream()
					.filter(object -> object.get() instanceof FlowerPatchBlock flowerPatchBlock &&
							state.is(flowerPatchBlock.getPatchDelegate().get())).map(object -> (FlowerPatchBlock) object.get()).findFirst();

			if (flowerPatchOptional.isPresent()) {
				FlowerPatchBlock patchBlock = flowerPatchOptional.get();
				BlockState newState = patchBlock.defaultBlockState();
				level.setBlockAndUpdate(pos, newState);
				level.playSound((Player) null, pos, newState.getSoundType().getPlaceSound(), SoundSource.BLOCKS, 1.0F, 1.0F);
				if (!player.getAbilities().instabuild) {
					stack.shrink(1);
				}
				return InteractionResult.SUCCESS;
			}
		}
		return InteractionResult.PASS;
	}
}
package com.mrbysco.flowerpatch.handler;

import com.mrbysco.flowerpatch.FlowerPatch;
import com.mrbysco.flowerpatch.block.FlowerPatchBlock;
import com.mrbysco.flowerpatch.config.PatchConfig;
import com.mrbysco.flowerpatch.registry.PatchRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event.Result;

import java.util.Optional;


public class PlaceHandler {
	public static void onBlockInteraction(PlayerInteractEvent.RightClickBlock event) {
		final World level = event.getWorld();
		final BlockPos pos = event.getPos();
		final BlockState state = level.getBlockState(pos);
		final PlayerEntity player = event.getPlayer();
		final ItemStack stack = player.getItemInHand(event.getHand());
		if ((state.is(BlockTags.FLOWERS) && state.getBlock().asItem().equals(stack.getItem())) ||
				(state.getBlock() instanceof FlowerPatchBlock && ((FlowerPatchBlock) state.getBlock()).getPatchDelegate().get().asItem().equals(stack.getItem()))) {
			Optional<FlowerPatchBlock> flowerPatchOptional = PatchRegistry.BLOCKS.getEntries().stream().filter(object -> object.get() instanceof FlowerPatchBlock &&
					((FlowerPatchBlock) object.get()).getPatchDelegate().get().asItem().equals(stack.getItem())).map(object -> (FlowerPatchBlock) object.get()).findFirst();

			if (flowerPatchOptional.isPresent()) {
				FlowerPatchBlock patchBlock = flowerPatchOptional.get();
				BlockState newState = patchBlock.defaultBlockState();
				if (state.hasProperty(FlowerPatchBlock.FLOWERS)) {
					if (state.getValue(FlowerPatchBlock.FLOWERS) == FlowerPatchBlock.MAX_FLOWERS) {
						event.setUseBlock(Result.DENY);
						return;
					}
					newState = state.setValue(FlowerPatchBlock.FLOWERS, Math.min(4, state.getValue(FlowerPatchBlock.FLOWERS) + 1));
				}
				level.setBlockAndUpdate(pos, newState);
				level.playSound((PlayerEntity) null, pos, newState.getSoundType().getPlaceSound(), SoundCategory.BLOCKS, 1.0F, 1.0F);
				if (!player.abilities.instabuild) {
					stack.shrink(1);
				}
				event.setCanceled(true);
			}
		}
	}


	public static void onBonemeal(BonemealEvent event) {
		final World level = event.getWorld();
		final BlockPos pos = event.getPos();
		final BlockState state = event.getBlock();
		final ItemStack stack = event.getStack();
		final PlayerEntity player = event.getPlayer();

		if (PatchConfig.COMMON.flowerToPatchBonemealing.get() &&
				state.is(FlowerPatch.BONEMEAL_ABLE_FLOWERS) && stack.getItem().is(FlowerPatch.BONEMEAL)) {
			Optional<FlowerPatchBlock> flowerPatchOptional = PatchRegistry.BLOCKS.getEntries().stream().filter(object -> object.get() instanceof FlowerPatchBlock &&
					state.is(((FlowerPatchBlock) object.get()).getPatchDelegate().get())).map(object -> (FlowerPatchBlock) object.get()).findFirst();

			if (flowerPatchOptional.isPresent()) {
				FlowerPatchBlock patchBlock = flowerPatchOptional.get();
				BlockState newState = patchBlock.defaultBlockState();
				level.setBlockAndUpdate(pos, newState);
				level.playSound((PlayerEntity) null, pos, newState.getSoundType().getPlaceSound(), SoundCategory.BLOCKS, 1.0F, 1.0F);
				if (!player.abilities.instabuild) {
					stack.shrink(1);
				}
				event.setCanceled(true);
			}
		}
	}
}

package com.mrbysco.flowerpatch.handler;

import com.mrbysco.flowerpatch.FlowerPatch;
import com.mrbysco.flowerpatch.block.FlowerPatchBlock;
import com.mrbysco.flowerpatch.block.PatchBlock;
import com.mrbysco.flowerpatch.config.PatchConfig;
import com.mrbysco.flowerpatch.registry.PatchRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event.Result;

import java.util.Optional;


public class PlaceHandler {
	public static void onBlockInteraction(PlayerInteractEvent.RightClickBlock event) {
		final Level level = event.getLevel();
		final BlockPos pos = event.getPos();
		final BlockState state = level.getBlockState(pos);
		final Player player = event.getEntity();
		final ItemStack stack = player.getItemInHand(event.getHand());
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
						event.setUseBlock(Result.DENY);
						return;
					}
					newState = state.setValue(property, Math.min(patchBlock.getMaxAmount(), state.getValue(property) + 1));
				}
				level.setBlockAndUpdate(pos, newState);
				level.playSound((Player) null, pos, newState.getSoundType().getPlaceSound(), SoundSource.BLOCKS, 1.0F, 1.0F);
				if (!player.getAbilities().instabuild) {
					stack.shrink(1);
				}
				event.setCanceled(true);
			}
		}
	}


	public static void onBonemeal(BonemealEvent event) {
		final Level level = event.getLevel();
		final BlockPos pos = event.getPos();
		final BlockState state = event.getBlock();
		final ItemStack stack = event.getStack();
		final Player player = event.getEntity();

		if (PatchConfig.COMMON.flowerToPatchBonemealing.get() &&
				state.is(FlowerPatch.BONEMEAL_ABLE_FLOWERS) && stack.is(FlowerPatch.BONEMEAL)) {
			Optional<FlowerPatchBlock> flowerPatchOptional = PatchRegistry.BLOCKS.getEntries().stream().filter(object -> object.get() instanceof FlowerPatchBlock flowerPatchBlock &&
					state.is(flowerPatchBlock.getPatchDelegate().get())).map(object -> (FlowerPatchBlock) object.get()).findFirst();

			if (flowerPatchOptional.isPresent()) {
				FlowerPatchBlock patchBlock = flowerPatchOptional.get();
				BlockState newState = patchBlock.defaultBlockState();
				level.setBlockAndUpdate(pos, newState);
				level.playSound((Player) null, pos, newState.getSoundType().getPlaceSound(), SoundSource.BLOCKS, 1.0F, 1.0F);
				if (!player.getAbilities().instabuild) {
					stack.shrink(1);
				}
				event.setCanceled(true);
			}
		}
	}
}

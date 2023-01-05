package com.mrbysco.flowerpatch.handler;

import com.mrbysco.flowerpatch.block.FlowerPatchBlock;
import com.mrbysco.flowerpatch.block.PatchBlock;
import com.mrbysco.flowerpatch.registry.PatchRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;

import java.util.Optional;


public class PlaceHandler {

	public static InteractionResult onUseBlock(Player player, Level level, InteractionHand interactionHand, BlockHitResult blockHitResult) {
		final BlockPos pos = blockHitResult.getBlockPos();
		final BlockState state = level.getBlockState(pos);
		final ItemStack stack = player.getItemInHand(interactionHand);
		if ((state.is(BlockTags.FLOWERS) && state.getBlock().asItem().equals(stack.getItem())) ||
				(state.getBlock() instanceof FlowerPatchBlock flowerPatchBlock && flowerPatchBlock.getFlowerDelegate().get().asItem().equals(stack.getItem()))) {
			Optional<Block> optionalPatch = PatchRegistry.BLOCKS.stream().filter(object -> object instanceof PatchBlock patchBlock &&
					patchBlock.getPatchDelegate().get().asItem().equals(stack.getItem())).findFirst();

			if (optionalPatch.isPresent()) {
				Block block = optionalPatch.get();
				PatchBlock patchBlock = (PatchBlock) block;
				BlockState newState = block.defaultBlockState();
				IntegerProperty property = patchBlock.getProperty();
				if (state.hasProperty(property)) {
					if (state.getValue(property) == patchBlock.getMaxAmount()) {
						return InteractionResult.PASS;
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
}

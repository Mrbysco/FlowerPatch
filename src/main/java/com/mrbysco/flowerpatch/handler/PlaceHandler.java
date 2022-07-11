package com.mrbysco.flowerpatch.handler;

import com.mrbysco.flowerpatch.block.FlowerPatchBlock;
import com.mrbysco.flowerpatch.registry.PatchRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event.Result;

import java.util.Optional;


public class PlaceHandler {
	public static void onBlockInteraction(PlayerInteractEvent.RightClickBlock event) {
		final Level level = event.getWorld();
		final BlockPos pos = event.getPos();
		final BlockState state = level.getBlockState(pos);
		final Player player = event.getPlayer();
		final ItemStack stack = player.getItemInHand(event.getHand());
		if ((state.is(BlockTags.FLOWERS) && state.getBlock().asItem().equals(stack.getItem())) ||
				(state.getBlock() instanceof FlowerPatchBlock flowerPatchBlock && flowerPatchBlock.getFlowerDelegate().get().asItem().equals(stack.getItem()))) {
			Optional<FlowerPatchBlock> flowerPatchOptional = PatchRegistry.BLOCKS.getEntries().stream().filter(object -> object.get() instanceof FlowerPatchBlock flowerPatchBlock &&
					flowerPatchBlock.getFlowerDelegate().get().asItem().equals(stack.getItem())).map(object -> (FlowerPatchBlock) object.get()).findFirst();

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
				level.playSound((Player) null, pos, newState.getSoundType().getPlaceSound(), SoundSource.BLOCKS, 1.0F, 1.0F);
				if (!player.getAbilities().instabuild) {
					stack.shrink(1);
				}
				event.setCanceled(true);
			}
		}
	}
}

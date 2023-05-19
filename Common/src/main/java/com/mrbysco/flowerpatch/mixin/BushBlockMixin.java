package com.mrbysco.flowerpatch.mixin;

import com.mrbysco.flowerpatch.platform.Services;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BushBlock.class)
public class BushBlockMixin {

	@Inject(method = "mayPlaceOn(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;)Z",
			at = @At(value = "HEAD"), cancellable = true)
	private void flowerpatch_mayPlaceOn(BlockState state, BlockGetter blockGetter, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
		if (Services.PLATFORM.placeOnLeaves() && !(blockGetter instanceof WorldGenRegion) && state.is(BlockTags.LEAVES)) {
			cir.setReturnValue(true);
		}
	}
}
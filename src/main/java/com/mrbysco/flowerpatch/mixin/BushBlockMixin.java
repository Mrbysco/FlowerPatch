package com.mrbysco.flowerpatch.mixin;


import com.mrbysco.flowerpatch.config.PatchConfig;
import net.minecraft.block.BlockState;
import net.minecraft.block.BushBlock;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.gen.WorldGenRegion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BushBlock.class)
public class BushBlockMixin {
	@Inject(method = "mayPlaceOn(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/IBlockReader;Lnet/minecraft/util/math/BlockPos;)Z",
			at = @At(value = "HEAD"), cancellable = true)
	private void flowerpatch_mayPlaceOn(BlockState state, IBlockReader blockReader, BlockPos pPos, CallbackInfoReturnable<Boolean> cir) {
		if (PatchConfig.COMMON.placeOnLeaves.get() && !(blockReader instanceof WorldGenRegion) && state.is(BlockTags.LEAVES)) {
			cir.setReturnValue(true);
		}
	}
}
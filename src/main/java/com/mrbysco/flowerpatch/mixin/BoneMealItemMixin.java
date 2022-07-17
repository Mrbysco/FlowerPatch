package com.mrbysco.flowerpatch.mixin;

import com.mrbysco.flowerpatch.FlowerPatch;
import com.mrbysco.flowerpatch.block.FlowerPatchBlock;
import com.mrbysco.flowerpatch.config.PatchConfig;
import com.mrbysco.flowerpatch.registry.PatchRegistry;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(BoneMealItem.class)
public class BoneMealItemMixin {
	@Inject(at = @At("HEAD"), method = "growCrop(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;)Z", cancellable = true)
	private static void init(ItemStack stack, Level level, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
		BlockState state = level.getBlockState(pos);
		if (FlowerPatch.config == null) FlowerPatch.config = AutoConfig.getConfigHolder(PatchConfig.class).getConfig();

		if (FlowerPatch.config.general.flowerToPatchBonemealing && state.is(FlowerPatch.BONEMEAL_ABLE_FLOWERS) && stack.is(FlowerPatch.BONEMEAL)) {
			Optional<FlowerPatchBlock> flowerPatchOptional = PatchRegistry.BLOCKS.stream().filter(block -> block instanceof FlowerPatchBlock flowerPatchBlock && state.is(flowerPatchBlock.getFlowerDelegate().get())).map(block -> (FlowerPatchBlock) block).findFirst();

			if (flowerPatchOptional.isPresent()) {
				FlowerPatchBlock patchBlock = flowerPatchOptional.get();
				BlockState newState = patchBlock.defaultBlockState();
				level.setBlockAndUpdate(pos, newState);
				level.playSound((Player) null, pos, newState.getSoundType().getPlaceSound(), SoundSource.BLOCKS, 1.0F, 1.0F);
				stack.shrink(1);
				cir.setReturnValue(true);
			}
		}
	}
}

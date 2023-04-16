package com.mrbysco.flowerpatch.mixin;

import com.mrbysco.flowerpatch.CommonClass;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BoneMealItem.class)
public class BoneMealItemMixin {

	@Inject(method = "useOn(Lnet/minecraft/world/item/context/UseOnContext;)Lnet/minecraft/world/InteractionResult;",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/world/item/BoneMealItem;growCrop(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;)Z",
					shift = At.Shift.BEFORE,
					ordinal = 0
			),
			cancellable = true
	)
	private void flowerpatch_useOn(UseOnContext context, CallbackInfoReturnable<InteractionResult> cir) {
		Level level = context.getLevel();
		BlockPos pos = context.getClickedPos();
		InteractionResult result = CommonClass.onBonemeal(level, pos, level.getBlockState(pos), context.getItemInHand(), context.getPlayer());
		if (result == InteractionResult.SUCCESS) {
			cir.setReturnValue(result);
		}
	}
}
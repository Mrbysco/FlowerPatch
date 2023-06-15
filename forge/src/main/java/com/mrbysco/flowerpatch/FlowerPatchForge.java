package com.mrbysco.flowerpatch;

import com.mrbysco.flowerpatch.client.ClientHandler;
import com.mrbysco.flowerpatch.config.PatchConfigForge;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Constants.MOD_ID)
public class FlowerPatchForge {

	public FlowerPatchForge() {
		IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, PatchConfigForge.commonSpec);

		CommonClass.init();

		MinecraftForge.EVENT_BUS.addListener(this::onBlockInteraction);
		MinecraftForge.EVENT_BUS.addListener(this::onBonemeal);

		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
			eventBus.addListener(ClientHandler::onClientSetup);
		});
	}


	private void onBlockInteraction(PlayerInteractEvent.RightClickBlock event) {
		final Level level = event.getLevel();
		final BlockPos pos = event.getPos();
		final Player player = event.getEntity();
		InteractionResult result = CommonClass.onBlockInteraction(level, pos, player, event.getHand());
		if (result == InteractionResult.FAIL) {
			event.setUseBlock(Event.Result.DENY);
		}
		if (result == InteractionResult.SUCCESS) {
			event.setCanceled(true);
		}
	}

	private void onBonemeal(BonemealEvent event) {
		final Level level = event.getLevel();
		final BlockPos pos = event.getPos();
		final BlockState state = event.getBlock();
		final ItemStack stack = event.getStack();
		final Player player = event.getEntity();
		InteractionResult result = CommonClass.onBonemeal(level, pos, state, stack, player);
		if (result == InteractionResult.SUCCESS) {
			event.setCanceled(true);
		}
	}
}
package com.mrbysco.flowerpatch;

import com.mrbysco.flowerpatch.client.ClientHandler;
import com.mrbysco.flowerpatch.config.PatchConfigForge;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.BonemealEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@Mod(Constants.MOD_ID)
public class FlowerPatchForge {

	public FlowerPatchForge(IEventBus eventBus) {
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, PatchConfigForge.commonSpec);

		CommonClass.init();

		NeoForge.EVENT_BUS.addListener(this::onBlockInteraction);
		NeoForge.EVENT_BUS.addListener(this::onBonemeal);

		if (FMLEnvironment.dist.isClient()) {
			eventBus.addListener(ClientHandler::onClientSetup);
		}
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
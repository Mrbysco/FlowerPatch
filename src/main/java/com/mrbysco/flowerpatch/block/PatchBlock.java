package com.mrbysco.flowerpatch.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

import java.util.function.Supplier;

public interface PatchBlock {
	Supplier<Block> getPatchDelegate();

	IntegerProperty getProperty();

	int getMaxAmount();
}

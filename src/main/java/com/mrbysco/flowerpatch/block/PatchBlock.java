package com.mrbysco.flowerpatch.block;

import net.minecraft.block.Block;
import net.minecraft.state.IntegerProperty;

import java.util.function.Supplier;

public interface PatchBlock {
	Supplier<Block> getPatchDelegate();

	IntegerProperty getProperty();

	int getMaxAmount();
}

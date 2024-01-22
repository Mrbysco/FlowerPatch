package com.mrbysco.flowerpatch.block;

import com.mrbysco.flowerpatch.config.PatchConfig;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowerBlock;
import net.minecraft.block.IGrowable;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;
import java.util.function.Supplier;

public class FlowerPatchBlock extends FlowerBlock implements IGrowable, PatchBlock {
	public static final int MAX_FLOWERS = 4;
	public static final IntegerProperty FLOWERS = IntegerProperty.create("flowers", 2, 4);
	protected static final VoxelShape ONE_AABB = Block.box(6.0D, 0.0D, 6.0D, 10.0D, 6.0D, 10.0D);
	protected static final VoxelShape TWO_AABB = Block.box(3.0D, 0.0D, 3.0D, 13.0D, 6.0D, 13.0D);
	protected static final VoxelShape THREE_AABB = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 6.0D, 14.0D);
	protected static final VoxelShape FOUR_AABB = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 7.0D, 14.0D);
	private final Supplier<Block> flowerDelegate;

	public FlowerPatchBlock(Effect mobEffect, int effectsDuration, Supplier<Block> flowerSupplier, Properties properties) {
		super(mobEffect, effectsDuration, properties);
		this.flowerDelegate = flowerSupplier;
		this.registerDefaultState(this.stateDefinition.any().setValue(FLOWERS, Integer.valueOf(2)));
	}

	@Override
	public boolean canBeReplaced(BlockState state, BlockItemUseContext placeContext) {
		return super.canBeReplaced(state, placeContext) && placeContext.getItemInHand().getItem() == this.flowerDelegate.get().asItem();
	}

	@Override
	public Supplier<Block> getPatchDelegate() {
		return flowerDelegate;
	}

	@Override
	public IntegerProperty getProperty() {
		return FLOWERS;
	}

	@Override
	public int getMaxAmount() {
		return MAX_FLOWERS;
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader blockGetter, BlockPos pos, ISelectionContext pContext) {
		Vector3d vec3 = state.getOffset(blockGetter, pos);
		switch (state.getValue(FLOWERS)) {
			case 1:
			default:
				return ONE_AABB.move(vec3.x, vec3.y, vec3.z);
			case 2:
				return TWO_AABB.move(vec3.x, vec3.y, vec3.z);
			case 3:
				return THREE_AABB.move(vec3.x, vec3.y, vec3.z);
			case 4:
				return FOUR_AABB.move(vec3.x, vec3.y, vec3.z);
		}
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> blockStateBuilder) {
		blockStateBuilder.add(FLOWERS);
	}

	@Override
	public ItemStack getCloneItemStack(IBlockReader reader, BlockPos pos, BlockState state) {
		return new ItemStack(flowerDelegate.get());
	}

	@Override
	public OffsetType getOffsetType() {
		return OffsetType.XZ;
	}

	@Override
	public boolean isValidBonemealTarget(IBlockReader level, BlockPos pos, BlockState state, boolean isClient) {
		return PatchConfig.COMMON.patchBonemealing.get() && state.getValue(FLOWERS) < MAX_FLOWERS;
	}

	@Override
	public boolean isBonemealSuccess(World level, Random randomSource, BlockPos pos, BlockState state) {
		return true;
	}

	@Override
	public void performBonemeal(ServerWorld level, Random randomSource, BlockPos pos, BlockState state) {
		level.setBlock(pos, state.setValue(FLOWERS, MathHelper.clamp(Integer.valueOf(state.getValue(FLOWERS) + 1), 2, 4)), 3);
	}
}

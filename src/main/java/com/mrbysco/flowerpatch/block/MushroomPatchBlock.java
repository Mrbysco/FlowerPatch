package com.mrbysco.flowerpatch.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.MushroomBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;
import java.util.function.Supplier;

public class MushroomPatchBlock extends MushroomBlock implements PatchBlock {
	public static final int MAX_MUSHROOMS = 4;
	public static final IntegerProperty MUSHROOMS = IntegerProperty.create("mushrooms", 2, 4);
	protected static final VoxelShape ONE_AABB = Block.box(6.0D, 0.0D, 6.0D, 10.0D, 6.0D, 10.0D);
	protected static final VoxelShape TWO_AABB = Block.box(3.0D, 0.0D, 3.0D, 13.0D, 6.0D, 13.0D);
	protected static final VoxelShape THREE_AABB = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 6.0D, 14.0D);
	protected static final VoxelShape FOUR_AABB = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 7.0D, 14.0D);
	private final Supplier<Block> mushroomDelegate;

	public MushroomPatchBlock(Supplier<Block> mushroomSupplier, Properties properties) {
		super(properties);
		this.mushroomDelegate = mushroomSupplier;
		this.registerDefaultState(this.stateDefinition.any().setValue(MUSHROOMS, Integer.valueOf(2)));
	}

	public boolean canBeReplaced(BlockState state, BlockItemUseContext placeContext) {
		return super.canBeReplaced(state, placeContext) && placeContext.getItemInHand().getItem() == this.mushroomDelegate.get().asItem();
	}

	public Supplier<Block> getPatchDelegate() {
		return mushroomDelegate;
	}

	@Override
	public IntegerProperty getProperty() {
		return MUSHROOMS;
	}

	@Override
	public int getMaxAmount() {
		return MAX_MUSHROOMS;
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader blockGetter, BlockPos pos, ISelectionContext pContext) {
		Vector3d vec3 = state.getOffset(blockGetter, pos);
		switch (state.getValue(MUSHROOMS)) {
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
		blockStateBuilder.add(MUSHROOMS);
	}

	@Override
	public ItemStack getCloneItemStack(IBlockReader reader, BlockPos pos, BlockState state) {
		return new ItemStack(mushroomDelegate.get());
	}

	@Override
	public boolean isValidBonemealTarget(IBlockReader level, BlockPos pos, BlockState state, boolean isClient) {
		return false;
	}

	@Override
	public boolean isBonemealSuccess(World level, Random randomSource, BlockPos pos, BlockState state) {
		return false;
	}

	@Override
	public void performBonemeal(ServerWorld level, Random randomSource, BlockPos pos, BlockState state) {
		//NOP
	}
}

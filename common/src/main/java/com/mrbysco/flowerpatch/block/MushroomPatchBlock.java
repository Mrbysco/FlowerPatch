package com.mrbysco.flowerpatch.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.MushroomBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

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
		super(null, properties);
		this.mushroomDelegate = mushroomSupplier;
		this.registerDefaultState(this.stateDefinition.any().setValue(MUSHROOMS, Integer.valueOf(2)));
	}

	public boolean canBeReplaced(BlockState state, BlockPlaceContext placeContext) {
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

	public VoxelShape getShape(BlockState state, BlockGetter blockGetter, BlockPos pos, CollisionContext collisionContext) {
		Vec3 vec3 = state.getOffset(blockGetter, pos);
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
	protected void createBlockStateDefinition(Builder<Block, BlockState> blockStateBuilder) {
		blockStateBuilder.add(MUSHROOMS);
	}

	@Override
	public ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state) {
		return new ItemStack(mushroomDelegate.get());
	}

	@Override
	public boolean growMushroom(ServerLevel serverLevel, BlockPos pos, BlockState state, RandomSource randomSource) {
		return false;
	}

	@Override
	public boolean isValidBonemealTarget(LevelReader levelReader, BlockPos pos, BlockState state) {
		return false;
	}

	@Override
	public boolean isBonemealSuccess(Level level, RandomSource randomSource, BlockPos pos, BlockState state) {
		return false;
	}

	@Override
	public void performBonemeal(ServerLevel level, RandomSource randomSource, BlockPos pos, BlockState state) {
		//NOP
	}
}

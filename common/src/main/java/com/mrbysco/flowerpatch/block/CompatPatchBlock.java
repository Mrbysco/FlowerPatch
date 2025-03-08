package com.mrbysco.flowerpatch.block;

import com.mrbysco.flowerpatch.platform.Services;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class CompatPatchBlock extends FlowerBlock implements BonemealableBlock, PatchBlock {
	public static final int MAX_FLOWERS = 4;
	public static final IntegerProperty FLOWERS = IntegerProperty.create("flowers", 2, 4);
	protected static final VoxelShape ONE_AABB = Block.box(6.0D, 0.0D, 6.0D, 10.0D, 6.0D, 10.0D);
	protected static final VoxelShape TWO_AABB = Block.box(3.0D, 0.0D, 3.0D, 13.0D, 6.0D, 13.0D);
	protected static final VoxelShape THREE_AABB = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 6.0D, 14.0D);
	protected static final VoxelShape FOUR_AABB = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 7.0D, 14.0D);
	private final ResourceLocation flowerLocation;
	private final Supplier<Block> flowerDelegate;
	private final String texturePath;

	public CompatPatchBlock(Holder<MobEffect> mobEffect, int effectsDuration, ResourceLocation flowerLocation, String texturePath, Properties properties) {
		super(mobEffect, effectsDuration, properties);
		this.flowerLocation = flowerLocation;
		this.texturePath = texturePath;
		this.flowerDelegate = () -> BuiltInRegistries.BLOCK.get(this.flowerLocation);
		this.registerDefaultState(this.stateDefinition.any().setValue(FLOWERS, Integer.valueOf(2)));
	}

	public boolean canBeReplaced(BlockState state, BlockPlaceContext placeContext) {
		return super.canBeReplaced(state, placeContext) && placeContext.getItemInHand().getItem() == this.flowerDelegate.get().asItem();
	}

	public Supplier<Block> getPatchDelegate() {
		return flowerDelegate;
	}

	public String getNameSpace() {
		return flowerLocation.getNamespace();
	}

	public String getTexturePath() {
		return texturePath;
	}

	@Override
	public IntegerProperty getProperty() {
		return FLOWERS;
	}

	@Override
	public int getMaxAmount() {
		return MAX_FLOWERS;
	}

	public VoxelShape getShape(BlockState state, BlockGetter blockGetter, BlockPos pos, CollisionContext collisionContext) {
		Vec3 vec3 = state.getOffset(blockGetter, pos);
		return switch (state.getValue(FLOWERS)) {
			default -> ONE_AABB.move(vec3.x, vec3.y, vec3.z);
			case 2 -> TWO_AABB.move(vec3.x, vec3.y, vec3.z);
			case 3 -> THREE_AABB.move(vec3.x, vec3.y, vec3.z);
			case 4 -> FOUR_AABB.move(vec3.x, vec3.y, vec3.z);
		};
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> blockStateBuilder) {
		blockStateBuilder.add(FLOWERS);
	}

	@Override
	public ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state) {
		return new ItemStack(flowerDelegate.get());
	}

	@Override
	public boolean isValidBonemealTarget(LevelReader levelReader, BlockPos pos, BlockState state) {
		return Services.PLATFORM.patchBonemealing() && state.getValue(FLOWERS) < MAX_FLOWERS;
	}

	@Override
	public boolean isBonemealSuccess(Level level, RandomSource randomSource, BlockPos pos, BlockState state) {
		return true;
	}

	@Override
	public void performBonemeal(ServerLevel level, RandomSource randomSource, BlockPos pos, BlockState state) {
		level.setBlock(pos, state.setValue(FLOWERS, Mth.clamp(Integer.valueOf(state.getValue(FLOWERS) + 1), 2, 4)), 3);
	}

	@Override
	public MutableComponent getName() {
		return Component.translatable(this.getDescriptionId(), Component.translatable(getPatchDelegate().get().getDescriptionId()));
	}

	@Override
	protected List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
		int flowers = state.getValue(FLOWERS);
		List<ItemStack> stacks = new ArrayList<>();
		for(int i = 0; i < flowers; i++) {
			stacks.add(new ItemStack(flowerDelegate.get()));
		}
		return List.copyOf(stacks);
	}
}

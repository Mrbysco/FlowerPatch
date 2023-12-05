package com.mrbysco.flowerpatch.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.function.Supplier;

public class WitherRosePatchBlock extends FlowerPatchBlock {
	public WitherRosePatchBlock(MobEffect mobEffect, Supplier<Block> flowerSupplier, Properties properties) {
		super(mobEffect, 8, flowerSupplier, properties);
	}

	protected boolean mayPlaceOn(BlockState state, BlockGetter blockGetter, BlockPos pos) {
		return super.mayPlaceOn(state, blockGetter, pos) || state.is(Blocks.NETHERRACK) || state.is(Blocks.SOUL_SAND) || state.is(Blocks.SOUL_SOIL);
	}

	public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource randomSource) {
		VoxelShape voxelshape = this.getShape(state, level, pos, CollisionContext.empty());
		Vec3 vec3 = voxelshape.bounds().getCenter();
		double d0 = (double) pos.getX() + vec3.x;
		double d1 = (double) pos.getZ() + vec3.z;

		for (int i = 0; i < 3; ++i) {
			if (randomSource.nextBoolean()) {
				level.addParticle(ParticleTypes.SMOKE, d0 + randomSource.nextDouble() / 5.0D, (double) pos.getY() + (0.5D - randomSource.nextDouble()), d1 + randomSource.nextDouble() / 5.0D, 0.0D, 0.0D, 0.0D);
			}
		}
	}

	public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
		if (!level.isClientSide && level.getDifficulty() != Difficulty.PEACEFUL) {
			if (entity instanceof LivingEntity) {
				LivingEntity livingentity = (LivingEntity) entity;
				if (!livingentity.isInvulnerableTo(entity.damageSources().wither())) {
					livingentity.addEffect(new MobEffectInstance(MobEffects.WITHER, 40));
				}
			}
		}
	}

	@Override
	public boolean isValidBonemealTarget(LevelReader levelReader, BlockPos pos, BlockState state) {
		return false;
	}
}

package com.mrbysco.flowerpatch.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import java.util.Random;
import java.util.function.Supplier;

public class WitherRosePatchBlock extends FlowerPatchBlock {
	public WitherRosePatchBlock(Effect mobEffect, Supplier<Block> flowerSupplier, Properties properties) {
		super(mobEffect, 8, flowerSupplier, properties);
	}

	@Override
	protected boolean mayPlaceOn(BlockState state, IBlockReader blockGetter, BlockPos pos) {
		return super.mayPlaceOn(state, blockGetter, pos) || state.is(Blocks.NETHERRACK) || state.is(Blocks.SOUL_SAND) || state.is(Blocks.SOUL_SOIL);
	}

	@Override
	public void animateTick(BlockState state, World level, BlockPos pos, Random randomSource) {
		VoxelShape voxelshape = this.getShape(state, level, pos, ISelectionContext.empty());
		Vector3d vec3 = voxelshape.bounds().getCenter();
		double d0 = (double) pos.getX() + vec3.x;
		double d1 = (double) pos.getZ() + vec3.z;

		for (int i = 0; i < 3; ++i) {
			if (randomSource.nextBoolean()) {
				level.addParticle(ParticleTypes.SMOKE, d0 + randomSource.nextDouble() / 5.0D, (double) pos.getY() + (0.5D - randomSource.nextDouble()), d1 + randomSource.nextDouble() / 5.0D, 0.0D, 0.0D, 0.0D);
			}
		}
	}

	@Override
	public void entityInside(BlockState state, World level, BlockPos pos, Entity entity) {
		if (!level.isClientSide && level.getDifficulty() != Difficulty.PEACEFUL) {
			if (entity instanceof LivingEntity) {
				LivingEntity livingentity = (LivingEntity) entity;
				if (!livingentity.isInvulnerableTo(DamageSource.WITHER)) {
					livingentity.addEffect(new EffectInstance(Effects.WITHER, 40));
				}
			}
		}
	}

	@Override
	public boolean isValidBonemealTarget(IBlockReader level, BlockPos pos, BlockState state, boolean b) {
		return false;
	}
}

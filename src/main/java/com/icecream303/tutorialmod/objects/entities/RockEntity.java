package com.icecream303.tutorialmod.objects.entities;

import com.icecream303.tutorialmod.core.init.EntityInit;
import com.icecream303.tutorialmod.core.init.ItemInit;

import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.network.NetworkHooks;

public class RockEntity extends ThrowableItemProjectile {

	public RockEntity(EntityType<? extends RockEntity> type, Level level) {
		super(type, level);
	}

	public RockEntity(Level level, LivingEntity player) {
		super(EntityInit.ROCK_ENTITY.get(), player, level);
	}

	public RockEntity(Level level, double x, double y, double z) {
		super(EntityInit.ROCK_ENTITY.get(), x, y, z, level);
	}

	@Override
	protected Item getDefaultItem() {
		// TODO Auto-generated method stub
		return ItemInit.ROCK_ITEM.get();
	}

	@Override
	public Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	private ParticleOptions getParticle() {
		ItemStack itemstack = this.getItemRaw();
		return (ParticleOptions) (itemstack.isEmpty() ? ParticleTypes.ITEM_SNOWBALL
				: new ItemParticleOption(ParticleTypes.ITEM, itemstack));
	}

	public void handleEntityEvent(byte p_37402_) {
		if (p_37402_ == 3) {
			ParticleOptions particleoptions = this.getParticle();

			for (int i = 0; i < 8; ++i) {
				this.level.addParticle(particleoptions, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
			}
		}

	}

	protected void onHitEntity(EntityHitResult p_37404_) {
		super.onHitEntity(p_37404_);
		Entity entity = p_37404_.getEntity();
		int i = entity instanceof Blaze ? 3 : 0;
		entity.hurt(DamageSource.thrown(this, this.getOwner()), (float) i);
	}

	protected void onHit(HitResult p_37406_) {
		super.onHit(p_37406_);
		if (!this.level.isClientSide) {
			this.level.broadcastEntityEvent(this, (byte) 3);
			this.discard();
		}

	}
}

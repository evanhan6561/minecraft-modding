package com.icecream303.tutorialmod.objects.entities;

import com.icecream303.tutorialmod.core.init.EntityInit;
import com.icecream303.tutorialmod.core.init.ItemInit;

import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

public class PokeballEntity extends ThrowableItemProjectile {

	public PokeballEntity(EntityType<? extends PokeballEntity> type, Level level) {
		super(type, level);
	}

	public PokeballEntity(Level level, LivingEntity player) {
		super(EntityInit.POKEBALL_ENTITY.get(), player, level);
	}

	public PokeballEntity(Level level, double x, double y, double z) {
		super(EntityInit.POKEBALL_ENTITY.get(), x, y, z, level);
	}

	@Override
	protected Item getDefaultItem() {
		return ItemInit.POKEBALL_ITEM.get();
	}

	@Override
	public Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	private ParticleOptions getParticle() {
		ItemStack itemstack = this.getItemRaw();
		return (ParticleOptions) (itemstack.isEmpty() ? ParticleTypes.CRIT
				: new ItemParticleOption(ParticleTypes.ITEM, itemstack));
	}

	public void handleEntityEvent(byte p_37402_) {
		if (p_37402_ == 3) {
			ParticleOptions particleoptions = this.getParticle();

			for (int i = 0; i < 8; ++i) {
				this.level.addParticle(particleoptions, this.getX(), this.getY(), this.getZ(), 1.0D, 1.0D, 1.0D);
			}
		}

	}

	@Override
	protected void onHit(HitResult result) {
		super.onHit(result);
		if (!this.level.isClientSide) {
			boolean didHitEntity = result.getType() == HitResult.Type.ENTITY;

			ItemStack pokeballToDrop = null;
			Vec3 pokeballSpawnLocation = result.getLocation();
			
			if (didHitEntity && ((EntityHitResult) result).getEntity() instanceof LivingEntity) {
				// We captured an entity. A filled pokeball will be dropped.
				Entity entity = ((EntityHitResult) result).getEntity();
				CompoundTag c = entity.serializeNBT();
				pokeballToDrop = new ItemStack(ItemInit.POKEBALL_FILLED_ITEM.get());
				pokeballToDrop.addTagElement("CapturedMob", c);
				
				entity.setRemoved(Entity.RemovalReason.DISCARDED);
				this.level.broadcastEntityEvent(this, (byte) 3);
			} else {
				// No entity was captured. An empty pokeball will be dropped.
				pokeballToDrop = new ItemStack(ItemInit.POKEBALL_ITEM.get());
			}
			
			ItemEntity pokeballItemEntity = new ItemEntity(this.getLevel(), pokeballSpawnLocation.x,
					pokeballSpawnLocation.y, pokeballSpawnLocation.z, pokeballToDrop);
			level.addFreshEntity(pokeballItemEntity);
			
			
			this.discard();
		}

	}
}

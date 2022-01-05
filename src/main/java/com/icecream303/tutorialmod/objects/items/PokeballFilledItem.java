package com.icecream303.tutorialmod.objects.items;

import java.util.Optional;
import java.util.Set;

import com.icecream303.tutorialmod.core.init.EntityInit;
import com.icecream303.tutorialmod.objects.entities.PokeballEntity;
import com.icecream303.tutorialmod.objects.entities.PokeballFilledEntity;
import com.icecream303.tutorialmod.objects.entities.RockEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class PokeballFilledItem extends Item {

	public PokeballFilledItem(Item.Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack itemstack = player.getItemInHand(hand);
		level.playSound((Player) null, player.getX(), player.getY(), player.getZ(), SoundEvents.EGG_THROW,
				SoundSource.NEUTRAL, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
		if (!level.isClientSide) {
			// Pass on the entity data to the ball
			PokeballFilledEntity ball = new PokeballFilledEntity(level, player);
			CompoundTag c = itemstack.getTag();
			
			ball.addAdditionalSaveData(c);
			ball.setItem(itemstack);
			ball.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
			level.addFreshEntity(ball);
		}

		if (!player.getAbilities().instabuild) {
			itemstack.shrink(1);
		}

		return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
	}
	
	
}

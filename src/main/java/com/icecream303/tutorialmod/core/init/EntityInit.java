package com.icecream303.tutorialmod.core.init;

import com.icecream303.tutorialmod.TutorialMod;
import com.icecream303.tutorialmod.objects.entities.PokeballEntity;
import com.icecream303.tutorialmod.objects.entities.RockEntity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EntityInit {

	public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES,
			TutorialMod.MOD_ID);

	public static final RegistryObject<EntityType<RockEntity>> ROCK_ENTITY = ENTITIES.register("rock",
			() -> EntityType.Builder.<RockEntity>of(RockEntity::new, MobCategory.MISC).sized(0.25f, 0.25f)
					.clientTrackingRange(4).updateInterval(10).build("rock"));

	public static final RegistryObject<EntityType<PokeballEntity>> POKEBALL_ENTITY = ENTITIES.register("pokeball",
			() -> EntityType.Builder.<PokeballEntity>of(PokeballEntity::new, MobCategory.MISC).sized(0.25f, 0.25f)
					.clientTrackingRange(4).updateInterval(10).build("pokeball"));
	
	public static final RegistryObject<EntityType<RockEntity>> POKEBALL_FILLED_ENTITY = ENTITIES.register("pokeball_filled",
			() -> EntityType.Builder.<RockEntity>of(RockEntity::new, MobCategory.MISC).sized(0.25f, 0.25f)
					.clientTrackingRange(4).updateInterval(10).build("pokeball_filled"));
}

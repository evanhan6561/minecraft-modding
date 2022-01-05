package com.icecream303.tutorialmod.core.init;

import com.icecream303.tutorialmod.TutorialMod;
import com.icecream303.tutorialmod.objects.items.PokeballFilledItem;
import com.icecream303.tutorialmod.objects.items.PokeballItem;
import com.icecream303.tutorialmod.objects.items.RockItem;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemInit {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS,
			TutorialMod.MOD_ID);
	public static final RegistryObject<Item> EXAMPLE_ITEM = ITEMS.register("example_item",
			() -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

	public static final RegistryObject<Item> ROCK_ITEM = ITEMS.register("rock_item",
			() -> new RockItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
	
	public static final RegistryObject<Item> POKEBALL_ITEM = ITEMS.register("pokeball",
			() -> new PokeballItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
	
	public static final RegistryObject<Item> POKEBALL_FILLED_ITEM = ITEMS.register("pokeball_filled",
			() -> new PokeballFilledItem(new Item.Properties().stacksTo(1)));
}

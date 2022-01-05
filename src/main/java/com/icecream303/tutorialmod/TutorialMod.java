	package com.icecream303.tutorialmod;

import java.util.function.Supplier;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.icecream303.tutorialmod.core.init.BlockInit;
import com.icecream303.tutorialmod.core.init.EntityInit;
import com.icecream303.tutorialmod.core.init.ItemInit;
import com.icecream303.tutorialmod.objects.entities.PokeballEntity;
import com.icecream303.tutorialmod.objects.entities.RockEntity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

@Mod("tutorialmod")
@Mod.EventBusSubscriber(modid = TutorialMod.MOD_ID, bus = Bus.MOD)
public class TutorialMod {
	public static final Logger LOGGER = LogManager.getLogger();
	public static final String MOD_ID = "tutorialmod";

	public TutorialMod() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

		ItemInit.ITEMS.register(bus);
		BlockInit.BLOCKS.register(bus);
		EntityInit.ENTITIES.register(bus);
		
		// Register ourselves for server and other game events we are interested in
		MinecraftForge.EVENT_BUS.register(this);
	}
	

	@SubscribeEvent
	public static void onRegisterItems(final RegistryEvent.Register<Item> event) {
		// Every block of our mod will automatically have an associated BlockItem instance
		// registered.
		
		BlockInit.BLOCKS.getEntries().stream().map(RegistryObject::get).forEach(block -> {
			BlockItem matchingBlockItem = new BlockItem(block, new Item.Properties().tab(CreativeModeTab.TAB_MISC));
			matchingBlockItem.setRegistryName(block.getRegistryName());
			
			event.getRegistry().register(matchingBlockItem);
		});
	}
	
	/**
	 * 	Shout out to:
	 *	https://github.com/CrackedScreen/Mod_1.18/blob/main/src/main/java/com/example/examplemod/ExampleMod.java
	 */
    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {

        @SubscribeEvent
        public static void registerRenders(EntityRenderersEvent.RegisterRenderers event) {
            event.registerEntityRenderer(EntityInit.ROCK_ENTITY.get(), ThrownItemRenderer<RockEntity>::new);
            event.registerEntityRenderer(EntityInit.POKEBALL_ENTITY.get(), ThrownItemRenderer<PokeballEntity>::new);
        }

    }
}

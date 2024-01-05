package com.hexagram2021.oceanworld;

import com.hexagram2021.oceanworld.common.OWContent;
import com.hexagram2021.oceanworld.common.OWFoods;
import com.hexagram2021.oceanworld.common.config.OWCommonConfig;
import com.hexagram2021.oceanworld.common.register.OWBlocks;
import com.hexagram2021.oceanworld.common.register.OWItems;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.*;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Triple;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.SlotTypePreset;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;

import static com.hexagram2021.oceanworld.common.OWLogger.LOGGER;

@SuppressWarnings("unused")
@Mod(OceanWorld.MODID)
public class OceanWorld {
	public static final String MODID = "oceanworld";
	public static final String MODNAME = "Ocean World";
	public static final String VERSION = "${version}";

	public OceanWorld() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		DeferredWorkQueue queue = DeferredWorkQueue.lookup(Optional.of(ModLoadingStage.CONSTRUCT)).orElseThrow();
		Consumer<Runnable> runLater = job -> queue.enqueueWork(
				ModLoadingContext.get().getActiveContainer(), job
		);
		OWContent.modConstruction(bus, runLater);

		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, OWCommonConfig.getConfig());

		bus.addListener(this::setup);
		bus.addListener(this::enqueueIMC);
		bus.addListener(this::creativeTabEvent);
		MinecraftForge.EVENT_BUS.register(this);
	}

	private void setup(final FMLCommonSetupEvent event) {
		// preinit
		LOGGER.info("Welcome to the ocean world!");
		OWContent.init();
	}

	private void enqueueIMC(final InterModEnqueueEvent event) {
		if(ModList.get().isLoaded("diet")) {
			InterModComms.sendTo("diet", "item",
					() -> new Tuple<Item, BiFunction<Player, ItemStack, Triple<List<ItemStack>, Integer, Float>>>(
							OWItems.SEA_CUCUMBER.get(),
							(player, stack) -> new ImmutableTriple<>(Collections.singletonList(stack), OWFoods.SEA_CUCUMBER.getNutrition(), OWFoods.SEA_CUCUMBER.getSaturationModifier())
					)
			);
		}
		if(ModList.get().isLoaded("curios")) {
			InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.BRACELET.getMessageBuilder().build());
			InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.NECKLACE.getMessageBuilder().build());
		}
	}

	@SuppressWarnings("NotNullFieldNotInitialized")
	public static CreativeModeTab ITEM_GROUP;

	public void creativeTabEvent(CreativeModeTabEvent.Register event) {
		ITEM_GROUP =  event.registerCreativeModeTab(new ResourceLocation(MODID, "building_blocks"), builder -> builder
				.icon(() -> new ItemStack(OWBlocks.TO_STAIRS.get(new ResourceLocation(MODID, "polished_serpentine"))))
				.title(Component.translatable("itemGroup.oceanworld")).displayItems(
						(flags, output) -> OWItems.ItemEntry.ALL_ITEMS.forEach(output::accept)
				));
	}
}

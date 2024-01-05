package com.hexagram2021.oceanworld.common.world.village;

import com.hexagram2021.oceanworld.common.register.OWItems;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

import static com.hexagram2021.oceanworld.OceanWorld.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class Villages {
	@SubscribeEvent
	public static void registerTrades(VillagerTradesEvent event) {
		Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
		ResourceLocation currentVillagerProfession = ForgeRegistries.VILLAGER_PROFESSIONS.getKey(event.getType());

		if(new ResourceLocation(VillagerProfession.FISHERMAN.name()).equals(currentVillagerProfession)) {
			trades.get(4).add((entity, random) -> switch (random.nextInt(8)) {
				case 0, 1, 2 -> new MerchantOffer(new ItemStack(OWItems.BLACK_PEARL.get()), new ItemStack(Items.EMERALD, 3), 3, 30, 0.05F);
				case 3 -> new MerchantOffer(new ItemStack(OWItems.GOLDEN_PEARL.get()), new ItemStack(Items.EMERALD, 5), 3, 30, 0.05F);
				default -> new MerchantOffer(new ItemStack(OWItems.WHITE_PEARL.get()), new ItemStack(Items.EMERALD, 2), 3, 30, 0.05F);
			});
		}
	}
}

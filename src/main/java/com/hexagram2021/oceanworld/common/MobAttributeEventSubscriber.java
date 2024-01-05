package com.hexagram2021.oceanworld.common;

import com.hexagram2021.oceanworld.common.entities.OceanologerEntity;
import com.hexagram2021.oceanworld.common.entities.OysterEntity;
import com.hexagram2021.oceanworld.common.entities.SeaCucumberEntity;
import com.hexagram2021.oceanworld.common.register.OWEntities;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.hexagram2021.oceanworld.OceanWorld.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MobAttributeEventSubscriber {

	@SubscribeEvent
	public static void onAttributeCreate(EntityAttributeCreationEvent event) {
		event.put(OWEntities.SEA_CUCUMBER, SeaCucumberEntity.createAttributes().build());
		event.put(OWEntities.OCEANOLOGER, OceanologerEntity.createAttributes().build());
		event.put(OWEntities.BLACKLIP_OYSTER, OysterEntity.createAttributes().build());
		event.put(OWEntities.WHITELIP_OYSTER, OysterEntity.createAttributes().build());
	}
}

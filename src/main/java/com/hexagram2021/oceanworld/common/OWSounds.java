package com.hexagram2021.oceanworld.common;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.RegisterEvent;

import java.util.HashMap;
import java.util.Map;

import static com.hexagram2021.oceanworld.OceanWorld.MODID;

public class OWSounds {
	static final Map<ResourceLocation, SoundEvent> registeredEvents = new HashMap<>();

	public static final SoundEvent SEA_CUCUMBER_AMBIENT = registerSound("sea_cucumber.ambient");
	public static final SoundEvent SEA_CUCUMBER_DEATH = registerSound("sea_cucumber.death");
	public static final SoundEvent SEA_CUCUMBER_HURT = registerSound("sea_cucumber.hurt");

	public static final SoundEvent OYSTER_AMBIENT = registerSound("oyster.ambient");
	public static final SoundEvent OYSTER_ATTACK = registerSound("oyster.attack");
	public static final SoundEvent OYSTER_DEATH = registerSound("oyster.death");
	public static final SoundEvent OYSTER_HURT = registerSound("oyster.hurt");
	public static final SoundEvent BUCKET_FILL_OYSTER = registerSound("item.bucket.fill_oyster");

	public static final SoundEvent OCEANOLOGER_AMBIENT = registerSound("oceanologer.ambient");
	public static final SoundEvent OCEANOLOGER_CAST_SPELL = registerSound("oceanologer.cast_spell");
	public static final SoundEvent OCEANOLOGER_CELEBRATE = registerSound("oceanologer.celebrate");
	public static final SoundEvent OCEANOLOGER_DEATH = registerSound("oceanologer.death");
	public static final SoundEvent OCEANOLOGER_HURT = registerSound("oceanologer.hurt");
	public static final SoundEvent OCEANOLOGER_PREPARE_ATTACK = registerSound("oceanologer.prepare_drip_ice");
	public static final SoundEvent OCEANOLOGER_PREPARE_SUMMON = registerSound("oceanologer.prepare_trap");
	public static final SoundEvent OCEANOLOGER_PREPARE_WOLOLO = registerSound("oceanologer.prepare_wololo");


	private static SoundEvent registerSound(String name) {
		ResourceLocation location = new ResourceLocation(MODID, name);
		SoundEvent event = SoundEvent.createVariableRangeEvent(location);
		registeredEvents.put(location, event);
		return event;
	}

	public static void init(RegisterEvent event) {
		event.register(Registries.SOUND_EVENT, helper -> registeredEvents.forEach(helper::register));
	}
}

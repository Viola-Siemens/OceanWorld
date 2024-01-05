package com.hexagram2021.oceanworld.common.register;

import com.hexagram2021.oceanworld.common.entities.*;
import com.hexagram2021.oceanworld.common.spawns.OWMobCategory;
import com.hexagram2021.oceanworld.common.spawns.OWRaiderTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;

import static com.hexagram2021.oceanworld.OceanWorld.MODID;

public class OWEntities {
	public static final EntityType<OceanologerEntity> OCEANOLOGER = EntityType.Builder.of(OceanologerEntity::new, MobCategory.MONSTER)
			.sized(0.6F, 1.95F)
			.clientTrackingRange(8)
			.build(new ResourceLocation(MODID, "oceanologer").toString());

	public static final EntityType<DripIceEntity> DRIP_ICE = EntityType.Builder.of(DripIceEntity::new, MobCategory.MISC)
			.sized(0.25F, 0.25F)
			.clientTrackingRange(4)
			.updateInterval(10)
			.build(new ResourceLocation(MODID, "drip_ice").toString());

	public static final EntityType<SeaCucumberEntity> SEA_CUCUMBER = EntityType.Builder.of(SeaCucumberEntity::new, OWMobCategory.MOLLUSCAN)
			.sized(0.25F, 0.25F)
			.clientTrackingRange(4)
			.build(new ResourceLocation(MODID, "sea_cucumber").toString());

	public static final EntityType<BlacklipOysterEntity> BLACKLIP_OYSTER = EntityType.Builder.of(BlacklipOysterEntity::new, OWMobCategory.MOLLUSCAN)
			.sized(0.9F, 0.75F)
			.clientTrackingRange(8)
			.build(new ResourceLocation(MODID, "blacklip_oyster").toString());

	public static final EntityType<WhitelipOysterEntity> WHITELIP_OYSTER = EntityType.Builder.of(WhitelipOysterEntity::new, OWMobCategory.MOLLUSCAN)
			.sized(0.9F, 0.75F)
			.clientTrackingRange(8)
			.build(new ResourceLocation(MODID, "whitelip_oyster").toString());

	private OWEntities() { }


	public static void init(RegisterEvent event) {
		event.register(ForgeRegistries.Keys.ENTITY_TYPES, helper -> {
			OWRaiderTypes.init();
			helper.register(new ResourceLocation(MODID, "oceanologer"), OCEANOLOGER);
			helper.register(new ResourceLocation(MODID, "drip_ice"), DRIP_ICE);
			helper.register(new ResourceLocation(MODID, "sea_cucumber"), SEA_CUCUMBER);
			helper.register(new ResourceLocation(MODID, "blacklip_oyster"), BLACKLIP_OYSTER);
			helper.register(new ResourceLocation(MODID, "whitelip_oyster"), WHITELIP_OYSTER);
		});
	}
}

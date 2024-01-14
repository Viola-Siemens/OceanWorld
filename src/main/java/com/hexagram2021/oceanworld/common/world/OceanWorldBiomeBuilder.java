package com.hexagram2021.oceanworld.common.world;

import com.hexagram2021.oceanworld.common.register.OWBiomeKeys;
import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;

import java.util.function.Consumer;

public class OceanWorldBiomeBuilder {
	private static final Climate.Parameter[] TEMPERATURES = new Climate.Parameter[] {
			Climate.Parameter.span(-1.0F, -0.5F),
			Climate.Parameter.span(-0.5F, 0.25F),
			Climate.Parameter.span(0.25F, 1.0F)
	};
	private static final Climate.Parameter[] HUMIDITIES = new Climate.Parameter[] {
			Climate.Parameter.span(-1.0F, -0.4F),
			Climate.Parameter.span(-0.4F, 0.3F),
			Climate.Parameter.span(0.3F, 1.0F)
	};

	private static final Climate.Parameter ALL = Climate.Parameter.span(-1.0F, 1.0F);

	private static final Climate.Parameter SURFACE = Climate.Parameter.span(0.0F, 0.35F);
	private static final Climate.Parameter UNDERGROUND = Climate.Parameter.span(0.35F, 1.0F);

	private static final Climate.Parameter OFF_COAST = Climate.Parameter.span(-1.2F, 0.875F);
	private static final Climate.Parameter ISLAND = Climate.Parameter.span(0.875F, 1.2F);

	private static final Climate.Parameter NORMAL = Climate.Parameter.span(-1.0F, 0.625F);
	private static final Climate.Parameter WEIRD = Climate.Parameter.span(0.625F, 1.0F);

	private static final Climate.Parameter NOT_POLLUTED = Climate.Parameter.span(-1.0F, 0.8125F);
	private static final Climate.Parameter POLLUTED = Climate.Parameter.span(0.8125F, 1.0F);

	/*
				 |   Cold  | Neutral |   Hot
		---------+---------+---------+---------
		   Dry   |   icy   | deposit | volcano
		 Neutral |   icy   |  ocean  |  coral
		  Humid  | glacier | meadow  |  coral
	 */

	public void addBiomes(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper) {
		//Ocean
		this.addBiome(mapper, OWBiomeKeys.ICY_OCEAN, TEMPERATURES[0], Climate.Parameter.span(HUMIDITIES[0], HUMIDITIES[1]), OFF_COAST, NOT_POLLUTED, ALL, SURFACE);
		this.addBiome(mapper, OWBiomeKeys.GLACIER, TEMPERATURES[0], HUMIDITIES[2], OFF_COAST, NOT_POLLUTED, ALL, SURFACE);
		this.addBiome(mapper, OWBiomeKeys.DEPOSIT_OCEAN, TEMPERATURES[1], HUMIDITIES[0], OFF_COAST, NOT_POLLUTED, NORMAL, SURFACE);
		this.addBiome(mapper, OWBiomeKeys.OCEAN, TEMPERATURES[1], HUMIDITIES[1], OFF_COAST, NOT_POLLUTED, NORMAL, SURFACE);
		this.addBiome(mapper, OWBiomeKeys.SEAGRASS_MEADOW, TEMPERATURES[1], HUMIDITIES[2], OFF_COAST, NOT_POLLUTED, NORMAL, SURFACE);
		this.addBiome(mapper, OWBiomeKeys.VOLCANIC_OCEAN, TEMPERATURES[2], HUMIDITIES[0], OFF_COAST, NOT_POLLUTED, NORMAL, SURFACE);
		this.addBiome(mapper, OWBiomeKeys.CORAL_OCEAN, TEMPERATURES[2], Climate.Parameter.span(HUMIDITIES[1], HUMIDITIES[2]), OFF_COAST, NOT_POLLUTED, NORMAL, SURFACE);

		this.addBiome(mapper, OWBiomeKeys.REEF_OCEAN, Climate.Parameter.span(TEMPERATURES[0], TEMPERATURES[1]), ALL, OFF_COAST, NOT_POLLUTED, WEIRD, SURFACE);

		this.addBiome(mapper, OWBiomeKeys.POLLUTED_OCEAN, ALL, ALL, OFF_COAST, POLLUTED, ALL, SURFACE);

		//Island
		this.addBiome(mapper, OWBiomeKeys.FROZEN_ISLAND, TEMPERATURES[0], ALL, ISLAND, ALL, NORMAL, SURFACE);
		this.addBiome(mapper, OWBiomeKeys.ISLAND, TEMPERATURES[1], ALL, ISLAND, ALL, NORMAL, SURFACE);
		this.addBiome(mapper, OWBiomeKeys.TROPICAL_ISLAND, TEMPERATURES[2], ALL, ISLAND, ALL, NORMAL, SURFACE);
		this.addBiome(mapper, OWBiomeKeys.MAGICAL_ISLAND, ALL, ALL, ISLAND, ALL, WEIRD, SURFACE);

		//Underground
		this.addBiome(mapper, OWBiomeKeys.UNDERGROUND, ALL, ALL, Climate.Parameter.span(OFF_COAST, ISLAND), ALL, ALL, UNDERGROUND);
	}

	private void addBiome(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper, OWBiomeKeys.BiomeKey biomeKey,
						  Climate.Parameter temperature, Climate.Parameter humidity,
						  Climate.Parameter continentalness, Climate.Parameter pollution,
						  Climate.Parameter weirdness, Climate.Parameter depth) {
		mapper.accept(new Pair<>(Climate.parameters(
				temperature, humidity, continentalness, pollution, depth, weirdness, biomeKey.suppress()
		), biomeKey.key()));
	}
}

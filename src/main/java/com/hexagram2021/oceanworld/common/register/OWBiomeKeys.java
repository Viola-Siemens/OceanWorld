package com.hexagram2021.oceanworld.common.register;

import com.google.common.collect.Maps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.function.Supplier;

import static com.hexagram2021.oceanworld.OceanWorld.MODID;
import static com.hexagram2021.oceanworld.common.config.OWCommonConfig.*;

public class OWBiomeKeys {
	public static final BiomeKey CORAL_OCEAN = new BiomeKey("coral_ocean", GENERATE_CORAL_OCEAN, SUPPRESS_CORAL_OCEAN.get()::floatValue);
	public static final BiomeKey DEPOSIT_OCEAN = new BiomeKey("deposit_ocean", GENERATE_DEPOSIT_OCEAN, SUPPRESS_DEPOSIT_OCEAN.get()::floatValue);
	public static final BiomeKey GLACIER = new BiomeKey("glacier", GENERATE_GLACIER, SUPPRESS_GLACIER.get()::floatValue);
	public static final BiomeKey ICY_OCEAN = new BiomeKey("icy_ocean", GENERATE_ICY_OCEAN, SUPPRESS_ICY_OCEAN.get()::floatValue);
	public static final BiomeKey OCEAN = new BiomeKey("ocean", true, -0.15F);
	public static final BiomeKey POLLUTED_OCEAN = new BiomeKey("polluted_ocean", GENERATE_POLLUTED_OCEAN, SUPPRESS_POLLUTED_OCEAN.get()::floatValue);
	public static final BiomeKey REEF_OCEAN = new BiomeKey("reef_ocean", GENERATE_REEF_OCEAN, SUPPRESS_REEF_OCEAN.get()::floatValue);
	public static final BiomeKey SEAGRASS_MEADOW = new BiomeKey("seagrass_meadow", GENERATE_SEAGRASS_MEADOW, SUPPRESS_SEAGRASS_MEADOW.get()::floatValue);
	public static final BiomeKey UNDERGROUND = new BiomeKey("underground", true, 0F);
	public static final BiomeKey VOLCANIC_OCEAN = new BiomeKey("volcanic_ocean", GENERATE_VOLCANIC_OCEAN, SUPPRESS_VOLCANIC_OCEAN.get()::floatValue);

	public static final BiomeKey FROZEN_ISLAND = new BiomeKey("frozen_island", GENERATE_FROZEN_ISLAND, SUPPRESS_FROZEN_ISLAND.get()::floatValue);
	public static final BiomeKey ISLAND = new BiomeKey("island", GENERATE_ISLAND, SUPPRESS_ISLAND.get()::floatValue);
	public static final BiomeKey TROPICAL_ISLAND = new BiomeKey("tropical_island", GENERATE_TROPICAL_ISLAND, SUPPRESS_TROPICAL_ISLAND.get()::floatValue);
	public static final BiomeKey MAGICAL_ISLAND = new BiomeKey("magical_island", GENERATE_MAGICAL_ISLAND, SUPPRESS_MAGICAL_ISLAND.get()::floatValue);

	public record BiomeKey(ResourceKey<Biome> key, boolean generate, float suppress) {
		static final Map<ResourceKey<Biome>, BiomeKey> ALL_BIOME_KEYS = Maps.newHashMap();

		public BiomeKey(ResourceKey<Biome> key, boolean generate, float suppress) {
			this.key = key;
			this.generate = generate;
			this.suppress = suppress;

			ALL_BIOME_KEYS.put(key, this);
		}

		public BiomeKey(String name, Supplier<Boolean> generate, Supplier<Float> suppress) {
			this(name, generate.get(), suppress.get());
		}

		public BiomeKey(String name, boolean generate, float suppress) {
			this(ResourceKey.create(ForgeRegistries.Keys.BIOMES, new ResourceLocation(MODID, name)), generate, suppress);
		}
	}

	@Nullable
	public static BiomeKey getBiomeKey(ResourceKey<Biome> key) {
		return BiomeKey.ALL_BIOME_KEYS.get(key);
	}

	@SuppressWarnings("unused")
	public static float getSuppress(ResourceKey<Biome> key) {
		return getSuppress(key, 0.0F);
	}

	public static float getSuppress(ResourceKey<Biome> key, float defaultValue) {
		BiomeKey biomeKey = getBiomeKey(key);
		return biomeKey == null ? defaultValue : biomeKey.suppress();
	}
}

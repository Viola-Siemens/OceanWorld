package com.hexagram2021.oceanworld.common.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class OWCommonConfig {
	private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
	private static final ForgeConfigSpec SPEC;

	public static final ForgeConfigSpec.BooleanValue GENERATE_CORAL_OCEAN;
	public static final ForgeConfigSpec.BooleanValue GENERATE_DEPOSIT_OCEAN;
	public static final ForgeConfigSpec.BooleanValue GENERATE_GLACIER;
	public static final ForgeConfigSpec.BooleanValue GENERATE_ICY_OCEAN;
	public static final ForgeConfigSpec.BooleanValue GENERATE_POLLUTED_OCEAN;
	public static final ForgeConfigSpec.BooleanValue GENERATE_REEF_OCEAN;
	public static final ForgeConfigSpec.BooleanValue GENERATE_SEAGRASS_MEADOW;
	public static final ForgeConfigSpec.BooleanValue GENERATE_VOLCANIC_OCEAN;
	public static final ForgeConfigSpec.BooleanValue GENERATE_FROZEN_ISLAND;
	public static final ForgeConfigSpec.BooleanValue GENERATE_ISLAND;
	public static final ForgeConfigSpec.BooleanValue GENERATE_TROPICAL_ISLAND;
	public static final ForgeConfigSpec.BooleanValue GENERATE_MAGICAL_ISLAND;

	public static final ForgeConfigSpec.DoubleValue SUPPRESS_CORAL_OCEAN;
	public static final ForgeConfigSpec.DoubleValue SUPPRESS_DEPOSIT_OCEAN;
	public static final ForgeConfigSpec.DoubleValue SUPPRESS_GLACIER;
	public static final ForgeConfigSpec.DoubleValue SUPPRESS_ICY_OCEAN;
	public static final ForgeConfigSpec.DoubleValue SUPPRESS_POLLUTED_OCEAN;
	public static final ForgeConfigSpec.DoubleValue SUPPRESS_REEF_OCEAN;
	public static final ForgeConfigSpec.DoubleValue SUPPRESS_SEAGRASS_MEADOW;
	public static final ForgeConfigSpec.DoubleValue SUPPRESS_VOLCANIC_OCEAN;
	public static final ForgeConfigSpec.DoubleValue SUPPRESS_FROZEN_ISLAND;
	public static final ForgeConfigSpec.DoubleValue SUPPRESS_ISLAND;
	public static final ForgeConfigSpec.DoubleValue SUPPRESS_TROPICAL_ISLAND;
	public static final ForgeConfigSpec.DoubleValue SUPPRESS_MAGICAL_ISLAND;

	public static final ForgeConfigSpec.BooleanValue SPAWN_OCEAN_PATROL_OVERWORLD;
	public static final ForgeConfigSpec.BooleanValue SPAWN_OCEAN_PATROL_OCEANWORLD;
	public static final ForgeConfigSpec.IntValue OCEAN_PATROL_SPAWN_INTERVAL;
	public static final ForgeConfigSpec.IntValue OCEAN_PATROL_SPAWN_CHANCE;

	static {
		BUILDER.push("oceanworld-common-config");
			BUILDER.push("biomes-generation");
				BUILDER.comment("The following values will determine whether the corresponding biomes can be generated ( = true) or not ( = false).");
				GENERATE_CORAL_OCEAN = BUILDER.define("GENERATE_CORAL_OCEAN", true);
				GENERATE_DEPOSIT_OCEAN = BUILDER.define("GENERATE_DEPOSIT_OCEAN", true);
				GENERATE_GLACIER = BUILDER.define("GENERATE_GLACIER", true);
				GENERATE_ICY_OCEAN = BUILDER.define("GENERATE_ICY_OCEAN", true);
				GENERATE_POLLUTED_OCEAN = BUILDER.define("GENERATE_POLLUTED_OCEAN", true);
				GENERATE_REEF_OCEAN = BUILDER.define("GENERATE_REEF_OCEAN", true);
				GENERATE_SEAGRASS_MEADOW = BUILDER.define("GENERATE_SEAGRASS_MEADOW", true);
				GENERATE_VOLCANIC_OCEAN = BUILDER.define("GENERATE_VOLCANIC_OCEAN", true);
				GENERATE_FROZEN_ISLAND = BUILDER.define("GENERATE_FROZEN_ISLAND", true);
				GENERATE_ISLAND = BUILDER.define("GENERATE_ISLAND", true);
				GENERATE_TROPICAL_ISLAND = BUILDER.define("GENERATE_TROPICAL_ISLAND", true);
				GENERATE_MAGICAL_ISLAND = BUILDER.define("GENERATE_MAGICAL_ISLAND", true);
				BUILDER.comment("The following values will affect the probability of corresponding biomes. The bigger it is, the less possible the biome will appears. Range (-0.25, 1.0) is recommended.");
				SUPPRESS_CORAL_OCEAN = BUILDER.defineInRange("SUPPRESS_CORAL_OCEAN", 0.05D, -1.0D, 4.0D);
				SUPPRESS_DEPOSIT_OCEAN = BUILDER.defineInRange("SUPPRESS_DEPOSIT_OCEAN", 0.0D, -1.0D, 4.0D);
				SUPPRESS_GLACIER = BUILDER.defineInRange("SUPPRESS_GLACIER", 0.0D, -1.0D, 4.0D);
				SUPPRESS_ICY_OCEAN = BUILDER.defineInRange("SUPPRESS_ICY_OCEAN", 0.05D, -1.0D, 4.0D);
				SUPPRESS_POLLUTED_OCEAN = BUILDER.defineInRange("SUPPRESS_POLLUTED_OCEAN", 0.0D, -1.0D, 4.0D);
				SUPPRESS_REEF_OCEAN = BUILDER.defineInRange("SUPPRESS_REEF_OCEAN", 0.125D, -1.0D, 4.0D);
				SUPPRESS_SEAGRASS_MEADOW = BUILDER.defineInRange("SUPPRESS_SEAGRASS_MEADOW", -0.05D, -1.0D, 4.0D);
				SUPPRESS_VOLCANIC_OCEAN = BUILDER.defineInRange("SUPPRESS_VOLCANIC_OCEAN", 0.0625D, -1.0D, 4.0D);
				SUPPRESS_FROZEN_ISLAND = BUILDER.defineInRange("SUPPRESS_FROZEN_ISLAND", 0.0D, -1.0D, 4.0D);
				SUPPRESS_ISLAND = BUILDER.defineInRange("SUPPRESS_ISLAND", 0.0D, -1.0D, 4.0D);
				SUPPRESS_TROPICAL_ISLAND = BUILDER.defineInRange("SUPPRESS_TROPICAL_ISLAND", 0.0D, -1.0D, 4.0D);
				SUPPRESS_MAGICAL_ISLAND = BUILDER.defineInRange("SUPPRESS_MAGICAL_ISLAND", 0.225D, -1.0D, 4.0D);
			BUILDER.pop();
			BUILDER.push("custom-spawners");
				SPAWN_OCEAN_PATROL_OVERWORLD = BUILDER.comment("If true, oceanologers will spawn as patrol on the oceans of overworld.").define("SPAWN_OCEAN_PATROL_OVERWORLD", true);
				SPAWN_OCEAN_PATROL_OCEANWORLD = BUILDER.comment("If true, oceanologers will spawn as patrol on the oceans of oceanworld.").define("SPAWN_OCEAN_PATROL_OCEANWORLD", true);
				OCEAN_PATROL_SPAWN_INTERVAL = BUILDER.comment("How many seconds (1 sec = 20 ticks) will ocean patrols spawn again after one try.").defineInRange("OCEAN_PATROL_SPAWN_INTERVAL", 600, 60, 7200);
				OCEAN_PATROL_SPAWN_CHANCE = BUILDER.comment("How likely (0 = never, 100 = always) will a try of spawning ocean patrols success.").defineInRange("OCEAN_PATROL_SPAWN_CHANCE", 20, 1, 100);
			BUILDER.pop();
		BUILDER.pop();
		SPEC = BUILDER.build();
	}

	public static ForgeConfigSpec getConfig() {
		return SPEC;
	}
}

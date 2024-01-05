package com.hexagram2021.oceanworld.common.spawns;

import com.hexagram2021.oceanworld.common.register.OWEntities;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.raid.Raid;

@SuppressWarnings("unused")
public final class OWRaiderTypes {
	/*
		VINDICATOR	{0, 0, 2, 0, 1, 4, 2, 5}
		EVOKER		{0, 0, 0, 0, 0, 1, 1, 2}
		PILLAGER	{0, 4, 3, 3, 4, 4, 4, 2}
		WITCH		{0, 0, 0, 0, 3, 0, 0, 1}
		RAVAGER		{0, 0, 0, 1, 0, 1, 0, 2}
		ILLUSIONER	{0, 0, 0, 0, 0, 1, 2, 0}
		OCEANOLOGER	{0, 0, 0, 1, 0, 0, 0, 1}
	 */
	public static final Raid.RaiderType ILLUSIONER = Raid.RaiderType.create("ILLUSIONER", EntityType.ILLUSIONER, new int[]{0, 0, 0, 0, 0, 1, 2, 0});
	public static final Raid.RaiderType OCEANOLOGER = Raid.RaiderType.create("OCEANOLOGER", OWEntities.OCEANOLOGER, new int[]{0, 0, 0, 1, 0, 0, 0, 1});

	public static void init() {
	}
}

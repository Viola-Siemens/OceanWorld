package com.hexagram2021.oceanworld.common.register;

import com.google.common.collect.Lists;
import com.hexagram2021.oceanworld.common.ModsLoadedHelper;
import com.hexagram2021.oceanworld.common.OWFoods;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static com.hexagram2021.oceanworld.OceanWorld.MODID;

@SuppressWarnings("unused")
public class OWItems {
	public static final DeferredRegister<Item> REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);

	public static final ItemEntry<Item> RED_CLAY_BALL = ItemEntry.register(
			"red_clay_ball", () -> new Item(new Item.Properties())
	);

	public static final ItemEntry<Item> BLACK_BRICK = ItemEntry.register(
			"black_brick", () -> new Item(new Item.Properties())
	);
	public static final ItemEntry<Item> GOLDEN_BRICK = ItemEntry.register(
			"golden_brick", () -> new Item(new Item.Properties())
	);

	public static final ItemEntry<Item> SEA_CUCUMBER = ItemEntry.register(
			"sea_cucumber", () -> new Item(new Item.Properties().rarity(Rarity.RARE).food(OWFoods.SEA_CUCUMBER))
	);
	public static final ItemEntry<Item> OYSTER_MEAT = ItemEntry.register(
			"oyster_meat", () -> new Item(new Item.Properties().rarity(Rarity.RARE).food(OWFoods.OYSTER_MEAT))
	);
	public static final ItemEntry<Item> COOKED_OYSTER_MEAT = ItemEntry.register(
			"cooked_oyster_meat", () -> new Item(new Item.Properties().rarity(Rarity.RARE).food(OWFoods.COOKED_OYSTER_MEAT))
	);

	public static final ItemEntry<Item> WHITE_PEARL = ItemEntry.register(
			"white_pearl", () -> new Item(new Item.Properties().stacksTo(16))
	);
	public static final ItemEntry<Item> BLACK_PEARL = ItemEntry.register(
			"black_pearl", () -> new Item(new Item.Properties().stacksTo(16))
	);
	public static final ItemEntry<Item> GOLDEN_PEARL = ItemEntry.register(
			"golden_pearl", () -> new Item(new Item.Properties().stacksTo(16).rarity(Rarity.RARE))
	);

	public static final ItemEntry<MobBucketItem> BLACKLIP_OYSTER_BUCKET = ItemEntry.register(
			"blacklip_oyster_bucket", () -> new MobBucketItem(
					() -> OWEntities.BLACKLIP_OYSTER,
					() -> Fluids.WATER,
					() -> SoundEvents.BUCKET_EMPTY_FISH,
					new Item.Properties().stacksTo(1)
			)
	);
	public static final ItemEntry<MobBucketItem> WHITELIP_OYSTER_BUCKET = ItemEntry.register(
			"whitelip_oyster_bucket", () -> new MobBucketItem(
					() -> OWEntities.WHITELIP_OYSTER,
					() -> Fluids.WATER,
					() -> SoundEvents.BUCKET_EMPTY_FISH,
					new Item.Properties().stacksTo(1)
			)
	);

	public static final ItemEntry<SpawnEggItem> SEA_CUCUMBER_SPAWN_EGG = ItemEntry.register(
			"sea_cucumber_spawn_egg", () -> new ForgeSpawnEggItem(() -> OWEntities.SEA_CUCUMBER, 0x585227, 0x1b1416, new Item.Properties())
	);
	public static final ItemEntry<SpawnEggItem> OCEANOLOGER_SPAWN_EGG = ItemEntry.register(
			"oceanologer_spawn_egg", () -> new ForgeSpawnEggItem(() -> OWEntities.OCEANOLOGER, 0x3c3cd0, 0xe02844, new Item.Properties())
	);
	public static final ItemEntry<SpawnEggItem> BLACKLIP_OYSTER_SPAWN_EGG = ItemEntry.register(
			"blacklip_oyster_spawn_egg", () -> new ForgeSpawnEggItem(() -> OWEntities.BLACKLIP_OYSTER, 0x381d4c, 0x8c6e98, new Item.Properties())
	);
	public static final ItemEntry<SpawnEggItem> WHITELIP_OYSTER_SPAWN_EGG = ItemEntry.register(
			"whitelip_oyster_spawn_egg", () -> new ForgeSpawnEggItem(() -> OWEntities.WHITELIP_OYSTER, 0xf3f1ca, 0xf2f2f2, new Item.Properties())
	);

	public static final ItemEntry<Item> WHITE_PEARL_NECKLACE = ItemEntry.register(
			"white_pearl_necklace", () -> ModsLoadedHelper.makeCuriosItem(
					livingEntity -> {
						if(livingEntity.getHealth() < livingEntity.getMaxHealth() * 0.5F) {
							livingEntity.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 20, 0, false, false, true));
						}
					}
			)
	);
	public static final ItemEntry<Item> BLACK_PEARL_NECKLACE = ItemEntry.register(
			"black_pearl_necklace", () -> ModsLoadedHelper.makeCuriosItem(
					livingEntity -> {
						if(livingEntity.getHealth() < livingEntity.getMaxHealth() * 0.5F) {
							livingEntity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 20, 0, false, false, true));
						}
					}
			)
	);
	public static final ItemEntry<Item> GOLDEN_PEARL_NECKLACE = ItemEntry.register(
			"golden_pearl_necklace", () -> ModsLoadedHelper.makeCuriosItem(
					livingEntity -> {
						if(livingEntity.getHealth() < livingEntity.getMaxHealth() * 0.5F) {
							livingEntity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 100, 0, false, false, true));
						}
					}
			)
	);

	public static final ItemEntry<Item> WHITE_PEARL_BRACELET = ItemEntry.register(
			"white_pearl_bracelet", () -> ModsLoadedHelper.makeCuriosItem(
					livingEntity -> livingEntity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 10, 0, false, false, true))
			)
	);
	public static final ItemEntry<Item> BLACK_PEARL_BRACELET = ItemEntry.register(
			"black_pearl_bracelet", () -> ModsLoadedHelper.makeCuriosItem(
					livingEntity -> livingEntity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 20, 1, false, false, true))
			)
	);
	public static final ItemEntry<Item> GOLDEN_PEARL_BRACELET = ItemEntry.register(
			"golden_pearl_bracelet", () -> ModsLoadedHelper.makeCuriosItem(
					livingEntity -> livingEntity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 40, 2, false, false, true))
			)
	);

	private OWItems() { }

	public static void init(IEventBus bus) {
		REGISTER.register(bus);
	}

	public static class ItemEntry<T extends Item> implements Supplier<T>, ItemLike {
		public static final List<ItemEntry<? extends Item>> ALL_ITEMS = Lists.newArrayList();

		private final RegistryObject<T> regObject;

		private static ItemEntry<Item> simple(String name) {
			return simple(name, $ -> { }, $ -> { });
		}

		private static ItemEntry<Item> simple(String name, Consumer<Item.Properties> makeProps, Consumer<Item> processItem) {
			return register(name, () -> Util.make(new Item(Util.make(new Item.Properties(), makeProps)), processItem));
		}

		static <T extends Item> ItemEntry<T> register(String name, Supplier<? extends T> make) {
			return new ItemEntry<>(REGISTER.register(name, make));
		}

		private static <T extends Item> ItemEntry<T> of(T existing) {
			return new ItemEntry<>(RegistryObject.create(ForgeRegistries.ITEMS.getKey(existing), ForgeRegistries.ITEMS));
		}

		private ItemEntry(RegistryObject<T> regObject) {
			this.regObject = regObject;
			ALL_ITEMS.add(this);
		}

		@Override
		public T get() {
			return this.regObject.get();
		}

		@Override
		public Item asItem() {
			return this.regObject.get();
		}

		public ResourceLocation getId() {
			return this.regObject.getId();
		}
	}
}

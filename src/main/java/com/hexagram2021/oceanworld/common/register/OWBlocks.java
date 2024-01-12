package com.hexagram2021.oceanworld.common.register;

import com.hexagram2021.oceanworld.common.blocks.FakeFrostedIceBlock;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.hexagram2021.oceanworld.OceanWorld.MODID;

public class OWBlocks {
	public static final DeferredRegister<Block> REGISTER = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);

	private OWBlocks() {}

	private static <T extends Block> void registerStairs(BlockEntry<T> fullBlock) {
		String name = fullBlock.getId().getPath();
		if(name.endsWith("_block")) {
			name = name.replaceAll("_block", "_stairs");
		} else if(name.endsWith("_bricks")) {
			name = name.replaceAll("_bricks", "_brick_stairs");
		} else {
			name = name + "_stairs";
		}
		TO_STAIRS.put(fullBlock.getId(), new BlockEntry<>(
				name,
				fullBlock::getProperties,
				p -> new StairBlock(fullBlock::defaultBlockState, p)
		));
	}

	private static <T extends Block> void registerSlab(BlockEntry<T> fullBlock) {
		String name = fullBlock.getId().getPath();
		if(name.endsWith("_block")) {
			name = name.replaceAll("_block", "_slab");
		} else if(name.endsWith("_bricks")) {
			name = name.replaceAll("_bricks", "_brick_slab");
		} else {
			name = name + "_slab";
		}
		TO_SLAB.put(fullBlock.getId(), new BlockEntry<>(
				name,
				fullBlock::getProperties,
				p -> new SlabBlock(p.isSuffocating((state, world, pos) ->
						fullBlock.defaultBlockState().isSuffocating(world, pos) && state.getValue(SlabBlock.TYPE) == SlabType.DOUBLE
				).isRedstoneConductor((state, world, pos) ->
						fullBlock.defaultBlockState().isRedstoneConductor(world, pos) && state.getValue(SlabBlock.TYPE) == SlabType.DOUBLE
				)
				)
		));
	}

	private static <T extends Block> void registerWall(BlockEntry<T> fullBlock) {
		String name = fullBlock.getId().getPath();
		if(name.endsWith("_block")) {
			name = name.replaceAll("_block", "_wall");
		} else if(name.endsWith("_bricks")) {
			name = name.replaceAll("_bricks", "_brick_wall");
		} else {
			name = name + "_wall";
		}
		TO_WALL.put(fullBlock.getId(), new BlockEntry<>(
				name,
				fullBlock::getProperties,
				WallBlock::new
		));
	}

	public static final class StoneDecoration {
		public static final Supplier<BlockBehaviour.Properties> STONE_PROPERTIES = () -> BlockBehaviour.Properties.of()
				.mapColor(MapColor.STONE)
				.instrument(NoteBlockInstrument.BASEDRUM)
				.requiresCorrectToolForDrops()
				.strength(1.5F, 6.0F);
		public static final Supplier<BlockBehaviour.Properties> CONGLOMERATE_PROPERTIES = () -> BlockBehaviour.Properties.of()
				.mapColor(MapColor.STONE)
				.instrument(NoteBlockInstrument.BASEDRUM)
				.requiresCorrectToolForDrops()
				.strength(0.8F);
		public static final Supplier<BlockBehaviour.Properties> SMOOTH_CONGLOMERATE_PROPERTIES = () -> BlockBehaviour.Properties.of()
				.mapColor(MapColor.STONE)
				.instrument(NoteBlockInstrument.BASEDRUM)
				.requiresCorrectToolForDrops()
				.strength(2.0F, 6.0F);
		public static final Supplier<BlockBehaviour.Properties> BASALT_SAND_PROPERTIES = () ->
				BlockBehaviour.Properties.of()
						.mapColor(MapColor.TERRACOTTA_GRAY)
						.instrument(NoteBlockInstrument.SNARE)
						.strength(0.5F).sound(SoundType.SAND);
		public static final Supplier<BlockBehaviour.Properties> BASALT_SANDSTONE_PROPERTIES = () ->
				BlockBehaviour.Properties.of()
						.mapColor(MapColor.TERRACOTTA_GRAY)
						.instrument(NoteBlockInstrument.BASEDRUM)
						.requiresCorrectToolForDrops().strength(0.8F);


		public static final BlockEntry<Block> PERIDOTITE = new BlockEntry<>(
				"peridotite", STONE_PROPERTIES, Block::new
		);
		public static final BlockEntry<Block> SERPENTINE = new BlockEntry<>(
				"serpentine", STONE_PROPERTIES, Block::new
		);

		public static final BlockEntry<Block> POLISHED_PERIDOTITE = new BlockEntry<>(
				"polished_peridotite", STONE_PROPERTIES, Block::new
		);
		public static final BlockEntry<Block> POLISHED_SERPENTINE = new BlockEntry<>(
				"polished_serpentine", STONE_PROPERTIES, Block::new
		);

		public static final BlockEntry<Block> CONGLOMERATE = new BlockEntry<>(
				"conglomerate", CONGLOMERATE_PROPERTIES, Block::new
		);
		public static final BlockEntry<Block> SMOOTH_CONGLOMERATE = new BlockEntry<>(
				"smooth_conglomerate", SMOOTH_CONGLOMERATE_PROPERTIES, Block::new
		);
		public static final BlockEntry<Block> CUT_CONGLOMERATE = new BlockEntry<>(
				"cut_conglomerate", CONGLOMERATE_PROPERTIES, Block::new
		);
		public static final BlockEntry<Block> CHISELED_CONGLOMERATE = new BlockEntry<>(
				"chiseled_conglomerate", CONGLOMERATE_PROPERTIES, Block::new
		);

		public static final BlockEntry<SandBlock> BASALT_SAND = new BlockEntry<>(
				"basalt_sand", BASALT_SAND_PROPERTIES, (p) -> new SandBlock(0x585656, p)
		);
		public static final BlockEntry<Block> BASALT_SANDSTONE = new BlockEntry<>(
				"basalt_sandstone", BASALT_SANDSTONE_PROPERTIES, Block::new
		);
		public static final BlockEntry<Block> SMOOTH_BASALT_SANDSTONE = new BlockEntry<>(
				"smooth_basalt_sandstone", BASALT_SANDSTONE_PROPERTIES, Block::new
		);
		public static final BlockEntry<Block> CUT_BASALT_SANDSTONE = new BlockEntry<>(
				"cut_basalt_sandstone", BASALT_SANDSTONE_PROPERTIES, Block::new
		);
		public static final BlockEntry<Block> CHISELED_BASALT_SANDSTONE = new BlockEntry<>(
				"chiseled_basalt_sandstone", BASALT_SANDSTONE_PROPERTIES, Block::new
		);

		private static void init() {
			OWItems.ItemEntry.register(PERIDOTITE.getId().getPath(), () -> new BlockItem(PERIDOTITE.get(), new Item.Properties()));
			OWItems.ItemEntry.register(SERPENTINE.getId().getPath(), () -> new BlockItem(SERPENTINE.get(), new Item.Properties()));
			OWItems.ItemEntry.register(POLISHED_PERIDOTITE.getId().getPath(), () -> new BlockItem(POLISHED_PERIDOTITE.get(), new Item.Properties()));
			OWItems.ItemEntry.register(POLISHED_SERPENTINE.getId().getPath(), () -> new BlockItem(POLISHED_SERPENTINE.get(), new Item.Properties()));

			OWItems.ItemEntry.register(CONGLOMERATE.getId().getPath(), () -> new BlockItem(CONGLOMERATE.get(), new Item.Properties()));
			OWItems.ItemEntry.register(SMOOTH_CONGLOMERATE.getId().getPath(), () -> new BlockItem(SMOOTH_CONGLOMERATE.get(), new Item.Properties()));
			OWItems.ItemEntry.register(CUT_CONGLOMERATE.getId().getPath(), () -> new BlockItem(CUT_CONGLOMERATE.get(), new Item.Properties()));
			OWItems.ItemEntry.register(CHISELED_CONGLOMERATE.getId().getPath(), () -> new BlockItem(CHISELED_CONGLOMERATE.get(), new Item.Properties()));

			OWItems.ItemEntry.register(BASALT_SAND.getId().getPath(), () -> new BlockItem(BASALT_SAND.get(), new Item.Properties()));
			OWItems.ItemEntry.register(BASALT_SANDSTONE.getId().getPath(), () -> new BlockItem(BASALT_SANDSTONE.get(), new Item.Properties()));
			OWItems.ItemEntry.register(SMOOTH_BASALT_SANDSTONE.getId().getPath(), () -> new BlockItem(SMOOTH_BASALT_SANDSTONE.get(), new Item.Properties()));
			OWItems.ItemEntry.register(CUT_BASALT_SANDSTONE.getId().getPath(), () -> new BlockItem(CUT_BASALT_SANDSTONE.get(), new Item.Properties()));
			OWItems.ItemEntry.register(CHISELED_BASALT_SANDSTONE.getId().getPath(), () -> new BlockItem(CHISELED_BASALT_SANDSTONE.get(), new Item.Properties()));

			registerStairs(PERIDOTITE);
			registerStairs(SERPENTINE);
			registerStairs(POLISHED_PERIDOTITE);
			registerStairs(POLISHED_SERPENTINE);
			registerSlab(PERIDOTITE);
			registerSlab(SERPENTINE);
			registerSlab(POLISHED_PERIDOTITE);
			registerSlab(POLISHED_SERPENTINE);
			registerWall(PERIDOTITE);
			registerWall(SERPENTINE);

			registerStairs(CONGLOMERATE);
			registerStairs(SMOOTH_CONGLOMERATE);
			registerSlab(CONGLOMERATE);
			registerSlab(SMOOTH_CONGLOMERATE);
			registerSlab(CUT_CONGLOMERATE);
			registerWall(CONGLOMERATE);

			registerStairs(BASALT_SANDSTONE);
			registerSlab(BASALT_SANDSTONE);
			registerWall(BASALT_SANDSTONE);
			registerSlab(CUT_BASALT_SANDSTONE);
			registerStairs(SMOOTH_BASALT_SANDSTONE);
			registerSlab(SMOOTH_BASALT_SANDSTONE);
		}
	}

	public static final class BrickDecoration {
		public static final Supplier<BlockBehaviour.Properties> CLAY_PROPERTIES = () -> BlockBehaviour.Properties.of()
				.mapColor(MapColor.COLOR_RED)
				.instrument(NoteBlockInstrument.FLUTE)
				.strength(0.6F).sound(SoundType.GRAVEL);
		public static final Supplier<BlockBehaviour.Properties> BLACK_BRICK_PROPERTIES = () -> BlockBehaviour.Properties.of()
				.mapColor(MapColor.TERRACOTTA_CYAN)
				.instrument(NoteBlockInstrument.BASEDRUM)
				.requiresCorrectToolForDrops()
				.strength(2.0F, 6.0F);
		public static final Supplier<BlockBehaviour.Properties> GOLDEN_BRICK_PROPERTIES = () -> BlockBehaviour.Properties.of()
				.mapColor(MapColor.GOLD)
				.instrument(NoteBlockInstrument.BASEDRUM)
				.requiresCorrectToolForDrops()
				.strength(2.0F, 6.0F);

		public static final BlockEntry<Block> RED_CLAY = new BlockEntry<>(
				"red_clay", CLAY_PROPERTIES, Block::new
		);
		public static final BlockEntry<Block> BLACK_BRICKS = new BlockEntry<>(
				"black_bricks", BLACK_BRICK_PROPERTIES, Block::new
		);
		public static final BlockEntry<Block> GOLDEN_BRICKS = new BlockEntry<>(
				"golden_bricks", GOLDEN_BRICK_PROPERTIES, Block::new
		);


		private static void init() {
			OWItems.ItemEntry.register(RED_CLAY.getId().getPath(), () -> new BlockItem(RED_CLAY.get(), new Item.Properties()));
			OWItems.ItemEntry.register(BLACK_BRICKS.getId().getPath(), () -> new BlockItem(BLACK_BRICKS.get(), new Item.Properties()));
			OWItems.ItemEntry.register(GOLDEN_BRICKS.getId().getPath(), () -> new BlockItem(GOLDEN_BRICKS.get(), new Item.Properties()));

			registerStairs(BLACK_BRICKS);
			registerSlab(BLACK_BRICKS);
			registerWall(BLACK_BRICKS);
			registerStairs(GOLDEN_BRICKS);
			registerSlab(GOLDEN_BRICKS);
			registerWall(GOLDEN_BRICKS);
		}
	}

	public static final class IceDecoration {
		public static final Supplier<BlockBehaviour.Properties> FAKE_FROSTED_ICE_PROPERTIES = () ->
				BlockBehaviour.Properties.of().mapColor(MapColor.ICE).friction(0.98F).randomTicks().strength(0.5F).sound(SoundType.GLASS).noOcclusion();

		public static final BlockEntry<FakeFrostedIceBlock> FAKE_FROSTED_ICE = new BlockEntry<>(
				"fake_frosted_ice", FAKE_FROSTED_ICE_PROPERTIES, FakeFrostedIceBlock::new
		);

		private static void init() {}
	}

	public static void init(IEventBus bus) {
		REGISTER.register(bus);

		StoneDecoration.init();
		BrickDecoration.init();
		IceDecoration.init();

		for(Map.Entry<ResourceLocation, OWBlocks.BlockEntry<SlabBlock>> blockSlab : OWBlocks.TO_SLAB.entrySet()) {
			OWItems.ItemEntry.register(blockSlab.getValue().getId().getPath(), () -> new BlockItem(blockSlab.getValue().get(), new Item.Properties()));
		}
		for(Map.Entry<ResourceLocation, OWBlocks.BlockEntry<StairBlock>> blockStairs : OWBlocks.TO_STAIRS.entrySet()) {
			OWItems.ItemEntry.register(blockStairs.getValue().getId().getPath(), () -> new BlockItem(blockStairs.getValue().get(), new Item.Properties()));
		}
		for(Map.Entry<ResourceLocation, OWBlocks.BlockEntry<WallBlock>> blockWall : OWBlocks.TO_WALL.entrySet()) {
			OWItems.ItemEntry.register(blockWall.getValue().getId().getPath(), () -> new BlockItem(blockWall.getValue().get(), new Item.Properties()));
		}
	}

	public static final Map<ResourceLocation, BlockEntry<SlabBlock>> TO_SLAB = new HashMap<>();
	public static final Map<ResourceLocation, BlockEntry<StairBlock>> TO_STAIRS = new HashMap<>();
	public static final Map<ResourceLocation, BlockEntry<WallBlock>> TO_WALL = new HashMap<>();

	public static final class BlockEntry<T extends Block> implements Supplier<T>, ItemLike {
		private final RegistryObject<T> regObject;
		private final Supplier<BlockBehaviour.Properties> properties;

		public BlockEntry(String name, Supplier<BlockBehaviour.Properties> properties, Function<BlockBehaviour.Properties, T> make) {
			this.properties = properties;
			this.regObject = REGISTER.register(name, () -> make.apply(properties.get()));
		}

		@Override
		public T get()
		{
			return regObject.get();
		}

		public BlockState defaultBlockState() {
			return get().defaultBlockState();
		}

		public ResourceLocation getId() {
			return regObject.getId();
		}

		public BlockBehaviour.Properties getProperties()
		{
			return properties.get();
		}

		@Override
		public Item asItem()
		{
			return get().asItem();
		}
	}
}

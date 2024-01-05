package com.hexagram2021.oceanworld.common.world.structures;

import com.hexagram2021.oceanworld.common.register.OWStructureTypes;
import com.hexagram2021.oceanworld.common.world.OceanJigsawPlacement;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;

import java.util.Optional;
import java.util.function.Function;

public class UnderwaterJigsawStructureFeature extends Structure {
	public static final int MAX_TOTAL_STRUCTURE_RANGE = 128;

	public static final Codec<UnderwaterJigsawStructureFeature> CODEC = RecordCodecBuilder.<UnderwaterJigsawStructureFeature>mapCodec((instance) ->
			instance.group(
					settingsCodec(instance),
					StructureTemplatePool.CODEC.fieldOf("start_pool").forGetter(feature -> feature.startPool),
					Codec.intRange(0, 7).fieldOf("size").forGetter(feature -> feature.maxDepth),
					HeightProvider.CODEC.fieldOf("start_height").forGetter(feature -> feature.startHeight),
					Codec.intRange(-60, 60).fieldOf("max_generate_height").forGetter(feature -> feature.maxGenerateHeight),
					Codec.BOOL.fieldOf("use_expansion_hack").forGetter(feature -> feature.useExpansionHack),
					Codec.intRange(1, 128).fieldOf("max_distance_from_center").forGetter(feature -> feature.maxDistanceFromCenter)
			).apply(instance, UnderwaterJigsawStructureFeature::new)).flatXmap(verifyRange(), verifyRange()).codec();

	private static Function<UnderwaterJigsawStructureFeature, DataResult<UnderwaterJigsawStructureFeature>> verifyRange() {
		return feature -> {
			int adaptation = switch (feature.terrainAdaptation()) {
				case NONE -> 0;
				case BURY, BEARD_THIN, BEARD_BOX -> 12;
			};
			return feature.maxDistanceFromCenter + adaptation > MAX_TOTAL_STRUCTURE_RANGE ?
					DataResult.error(() -> "Structure size including terrain adaptation must not exceed %d".formatted(MAX_TOTAL_STRUCTURE_RANGE)) :
					DataResult.success(feature);
		};
	}

	private final Holder<StructureTemplatePool> startPool;
	private final int maxDepth;
	private final HeightProvider startHeight;
	private final int maxGenerateHeight;
	private final boolean useExpansionHack;
	private final int maxDistanceFromCenter;

	public UnderwaterJigsawStructureFeature(StructureSettings settings, Holder<StructureTemplatePool> startPool, int maxDepth,
											HeightProvider startHeight, int maxGenerateHeight, boolean useExpansionHack, int maxDistanceFromCenter) {
		super(settings);
		this.startPool = startPool;
		this.maxDepth = maxDepth;
		this.startHeight = startHeight;
		this.maxGenerateHeight = maxGenerateHeight;
		this.useExpansionHack = useExpansionHack;
		this.maxDistanceFromCenter = maxDistanceFromCenter;
	}

	@Override
	public Optional<Structure.GenerationStub> findGenerationPoint(Structure.GenerationContext context) {
		ChunkPos chunkpos = context.chunkPos();
		int x = chunkpos.getMinBlockX();
		int z = chunkpos.getMinBlockZ();
		if(context.chunkGenerator().getFirstOccupiedHeight(x, z, Heightmap.Types.OCEAN_FLOOR_WG, context.heightAccessor(), context.randomState()) > this.maxGenerateHeight) {
			return Optional.empty();
		}
		BlockPos blockpos = new BlockPos(x, 1, z);
		return OceanJigsawPlacement.addPieces(context, this.startPool, null, this.maxDepth, blockpos, this.useExpansionHack, this.maxDistanceFromCenter);
	}

	@Override
	public StructureType<?> type() {
		return OWStructureTypes.UNDERWATER_JIGSAW.get();
	}
}

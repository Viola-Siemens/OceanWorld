package com.hexagram2021.oceanworld.common.world;

import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import com.hexagram2021.oceanworld.common.OWLogger;
import net.minecraft.core.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.block.JigsawBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.pools.EmptyPoolElement;
import net.minecraft.world.level.levelgen.structure.pools.JigsawJunction;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.apache.commons.lang3.mutable.MutableObject;

import javax.annotation.Nullable;
import java.util.Deque;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class OceanJigsawPlacement {
	public static Optional<Structure.GenerationStub> addPieces(Structure.GenerationContext context, Holder<StructureTemplatePool> pool,
															   @Nullable ResourceLocation piece, int maxDepth, BlockPos blockPos,
															   boolean expansion, int maxD) {
		RegistryAccess registryaccess = context.registryAccess();
		ChunkGenerator chunkgenerator = context.chunkGenerator();
		StructureTemplateManager structuretemplatemanager = context.structureTemplateManager();
		LevelHeightAccessor level = context.heightAccessor();
		WorldgenRandom random = context.random();
		Registry<StructureTemplatePool> registry = registryaccess.registryOrThrow(Registries.TEMPLATE_POOL);
		Rotation rotation = Rotation.getRandom(random);
		StructureTemplatePool structuretemplatepool = pool.value();
		StructurePoolElement structurepoolelement = structuretemplatepool.getRandomTemplate(random);
		if (structurepoolelement == EmptyPoolElement.INSTANCE) {
			return Optional.empty();
		} else {
			BlockPos blockpos;
			if (piece != null) {
				Optional<BlockPos> optional = getRandomNamedJigsaw(structurepoolelement, piece, blockPos, rotation, structuretemplatemanager, random);
				if (optional.isEmpty()) {
					OWLogger.LOGGER.error("No starting jigsaw {} found in start pool {}", piece, Objects.requireNonNull(
							pool.unwrapKey().map(resourceKey -> resourceKey.location().toString()).orElse("<unregistered>")
					));
					return Optional.empty();
				}

				blockpos = optional.get();
			} else {
				blockpos = blockPos;
			}

			Vec3i vec3i = blockpos.subtract(blockPos);
			BlockPos blockpos1 = blockPos.subtract(vec3i);
			PoolElementStructurePiece element = new PoolElementStructurePiece(structuretemplatemanager, structurepoolelement, blockpos1, structurepoolelement.getGroundLevelDelta(), rotation, structurepoolelement.getBoundingBox(structuretemplatemanager, blockpos1, rotation));
			BoundingBox boundingbox = element.getBoundingBox();
			int i = (boundingbox.maxX() + boundingbox.minX()) / 2;
			int j = (boundingbox.maxZ() + boundingbox.minZ()) / 2;
			int k = blockPos.getY() + chunkgenerator.getFirstFreeHeight(i, j, Heightmap.Types.OCEAN_FLOOR_WG, level, context.randomState());

			int l = boundingbox.minY() + element.getGroundLevelDelta();
			element.move(0, k - l, 0);
			int i1 = k + vec3i.getY();
			return Optional.of(new Structure.GenerationStub(new BlockPos(i, i1, j), builder -> {
				List<PoolElementStructurePiece> list = Lists.newArrayList();
				list.add(element);
				if (maxDepth > 0) {
					AABB aabb = new AABB(i - maxD, i1 - maxD, j - maxD, i + maxD + 1, i1 + maxD + 1, j + maxD + 1);
					VoxelShape voxelshape = Shapes.join(Shapes.create(aabb), Shapes.create(AABB.of(boundingbox)), BooleanOp.ONLY_FIRST);
					addPieces(context.randomState(), maxDepth, expansion, chunkgenerator, structuretemplatemanager, level, random, registry, element, list, voxelshape);
					list.forEach(builder::addPiece);
				}
			}));
		}
	}

	private static Optional<BlockPos> getRandomNamedJigsaw(StructurePoolElement poolElement, ResourceLocation piece, BlockPos blockPos, Rotation rotation,
														   StructureTemplateManager manager, WorldgenRandom random) {
		List<StructureTemplate.StructureBlockInfo> list = poolElement.getShuffledJigsawBlocks(manager, blockPos, rotation, random);
		Optional<BlockPos> optional = Optional.empty();

		for(StructureTemplate.StructureBlockInfo blockInfo : list) {
			ResourceLocation resourcelocation = ResourceLocation.tryParse(blockInfo.nbt.getString("name"));
			if (piece.equals(resourcelocation)) {
				optional = Optional.of(blockInfo.pos);
				break;
			}
		}

		return optional;
	}

	private static void addPieces(RandomState randomState, int depth, boolean expansion, ChunkGenerator chunkGenerator, StructureTemplateManager manager,
								  LevelHeightAccessor level, RandomSource random, Registry<StructureTemplatePool> pools,
								  PoolElementStructurePiece piece, List<PoolElementStructurePiece> pieces, VoxelShape shape) {
		OceanJigsawPlacement.Placer placer = new OceanJigsawPlacement.Placer(pools, depth, chunkGenerator, manager, pieces, random);
		placer.placing.addLast(new OceanJigsawPlacement.PieceState(piece, new MutableObject<>(shape), 0));

		while(!placer.placing.isEmpty()) {
			OceanJigsawPlacement.PieceState pieceState = placer.placing.removeFirst();
			placer.tryPlacingChildren(pieceState.piece, pieceState.free, pieceState.depth, expansion, level, randomState);
		}
	}

	static final class PieceState {
		final PoolElementStructurePiece piece;
		final MutableObject<VoxelShape> free;
		final int depth;

		PieceState(PoolElementStructurePiece piece, MutableObject<VoxelShape> shape, int depth) {
			this.piece = piece;
			this.free = shape;
			this.depth = depth;
		}
	}

	static final class Placer {
		private final Registry<StructureTemplatePool> pools;
		private final int maxDepth;
		private final ChunkGenerator chunkGenerator;
		private final StructureTemplateManager structureTemplateManager;
		private final List<? super PoolElementStructurePiece> pieces;
		private final RandomSource random;
		final Deque<OceanJigsawPlacement.PieceState> placing = Queues.newArrayDeque();

		Placer(Registry<StructureTemplatePool> pools, int depth, ChunkGenerator chunkGenerator, StructureTemplateManager manager,
			   List<? super PoolElementStructurePiece> pieces, RandomSource random) {
			this.pools = pools;
			this.maxDepth = depth;
			this.chunkGenerator = chunkGenerator;
			this.structureTemplateManager = manager;
			this.pieces = pieces;
			this.random = random;
		}

		@SuppressWarnings("deprecation")
		void tryPlacingChildren(PoolElementStructurePiece piece, MutableObject<VoxelShape> shape, int depth, boolean expansion, LevelHeightAccessor level, RandomState random) {
			StructurePoolElement structurepoolelement = piece.getElement();
			BlockPos blockpos = piece.getPosition();
			Rotation rotation = piece.getRotation();
			StructureTemplatePool.Projection projection = structurepoolelement.getProjection();
			boolean flag = projection == StructureTemplatePool.Projection.RIGID;
			MutableObject<VoxelShape> mutableobject = new MutableObject<>();
			BoundingBox boundingbox = piece.getBoundingBox();
			int i = boundingbox.minY();

			jigsawBlockSearch:
			for(StructureTemplate.StructureBlockInfo jigsawInfo : structurepoolelement.getShuffledJigsawBlocks(this.structureTemplateManager, blockpos, rotation, this.random)) {
				Direction direction = JigsawBlock.getFrontFacing(jigsawInfo.state);
				BlockPos blockpos1 = jigsawInfo.pos;
				BlockPos blockpos2 = blockpos1.relative(direction);
				int j = blockpos1.getY() - i;
				int k = -1;
				ResourceKey<StructureTemplatePool> resourcekey = readPoolName(jigsawInfo);
				Optional<? extends Holder<StructureTemplatePool>> optional = this.pools.getHolder(resourcekey);
				if (optional.isEmpty()) {
					OWLogger.LOGGER.warn("Empty or non-existent pool: {}", resourcekey.location());
				} else {
					Holder<StructureTemplatePool> holder = optional.get();
					if (holder.value().size() == 0 && !holder.is(Pools.EMPTY)) {
						OWLogger.LOGGER.warn("Empty or non-existent pool: {}", resourcekey.location());
					} else {
						Holder<StructureTemplatePool> holder1 = holder.value().getFallback();
						if (holder1.value().size() == 0 && !holder1.is(Pools.EMPTY)) {
							OWLogger.LOGGER.warn("Empty or non-existent fallback pool: {}", holder1.unwrapKey().map(resourceKey -> resourceKey.location().toString()).orElse("<unregistered>"));
						} else {
							boolean flag1 = boundingbox.isInside(blockpos2);
							MutableObject<VoxelShape> mutableobject1;
							if (flag1) {
								mutableobject1 = mutableobject;
								if (mutableobject.getValue() == null) {
									mutableobject.setValue(Shapes.create(AABB.of(boundingbox)));
								}
							} else {
								mutableobject1 = shape;
							}

							List<StructurePoolElement> list = Lists.newArrayList();
							if (depth != this.maxDepth) {
								list.addAll(holder.value().getShuffledTemplates(this.random));
							}

							list.addAll(holder1.value().getShuffledTemplates(this.random));

							for(StructurePoolElement nextElement : list) {
								if (nextElement == EmptyPoolElement.INSTANCE) {
									break;
								}

								for(Rotation rotation1 : Rotation.getShuffled(this.random)) {
									List<StructureTemplate.StructureBlockInfo> nextJigsawBlocks = nextElement.getShuffledJigsawBlocks(this.structureTemplateManager, BlockPos.ZERO, rotation1, this.random);
									BoundingBox nextbbox = nextElement.getBoundingBox(this.structureTemplateManager, BlockPos.ZERO, rotation1);
									int l;
									if (expansion && nextbbox.getYSpan() <= 16) {
										l = nextJigsawBlocks.stream().mapToInt(blockInfo -> {
											if (!nextbbox.isInside(blockInfo.pos.relative(JigsawBlock.getFrontFacing(blockInfo.state)))) {
												return 0;
											} else {
												ResourceKey<StructureTemplatePool> pool2 = readPoolName(blockInfo);
												Optional<? extends Holder<StructureTemplatePool>> optional1 = this.pools.getHolder(pool2);
												Optional<Holder<StructureTemplatePool>> optional2 = optional1.map(pool -> pool.value().getFallback());
												int j3 = optional1.map(pool -> pool.value().getMaxSize(this.structureTemplateManager)).orElse(0);
												int k3 = optional2.map(pool -> pool.value().getMaxSize(this.structureTemplateManager)).orElse(0);
												return Math.max(j3, k3);
											}
										}).max().orElse(0);
									} else {
										l = 0;
									}

									for(StructureTemplate.StructureBlockInfo nextJigsawInfo : nextJigsawBlocks) {
										if (JigsawBlock.canAttach(jigsawInfo, nextJigsawInfo)) {
											BlockPos blockpos3 = nextJigsawInfo.pos;
											BlockPos blockpos4 = blockpos2.subtract(blockpos3);
											BoundingBox boundingbox2 = nextElement.getBoundingBox(this.structureTemplateManager, blockpos4, rotation1);
											int i1 = boundingbox2.minY();
											StructureTemplatePool.Projection nextProjection = nextElement.getProjection();
											boolean flag2 = nextProjection == StructureTemplatePool.Projection.RIGID;
											int j1 = blockpos3.getY();
											int k1 = j - j1 + JigsawBlock.getFrontFacing(jigsawInfo.state).getStepY();
											int l1;
											if (flag && flag2) {
												l1 = i + k1;
											} else {
												if (k == -1) {
													k = this.chunkGenerator.getFirstFreeHeight(blockpos1.getX(), blockpos1.getZ(), Heightmap.Types.OCEAN_FLOOR_WG, level, random);
												}

												l1 = k - j1;
											}

											int i2 = l1 - i1;
											BoundingBox boundingbox3 = boundingbox2.moved(0, i2, 0);
											BlockPos blockpos5 = blockpos4.offset(0, i2, 0);
											if (l > 0) {
												int j2 = Math.max(l + 1, boundingbox3.maxY() - boundingbox3.minY());
												boundingbox3.encapsulate(new BlockPos(boundingbox3.minX(), boundingbox3.minY() + j2, boundingbox3.minZ()));
											}

											if (!Shapes.joinIsNotEmpty(mutableobject1.getValue(), Shapes.create(AABB.of(boundingbox3).deflate(0.25D)), BooleanOp.ONLY_SECOND)) {
												mutableobject1.setValue(Shapes.joinUnoptimized(mutableobject1.getValue(), Shapes.create(AABB.of(boundingbox3)), BooleanOp.ONLY_FIRST));
												int i3 = piece.getGroundLevelDelta();
												int k2;
												if (flag2) {
													k2 = i3 - k1;
												} else {
													k2 = nextElement.getGroundLevelDelta();
												}

												PoolElementStructurePiece poolelementstructurepiece = new PoolElementStructurePiece(this.structureTemplateManager, nextElement, blockpos5, k2, rotation1, boundingbox3);
												int l2;
												if (flag) {
													l2 = i + j;
												} else if (flag2) {
													l2 = l1 + j1;
												} else {
													if (k == -1) {
														k = this.chunkGenerator.getFirstFreeHeight(blockpos1.getX(), blockpos1.getZ(), Heightmap.Types.OCEAN_FLOOR_WG, level, random);
													}

													l2 = k + k1 / 2;
												}

												piece.addJunction(new JigsawJunction(blockpos2.getX(), l2 - j + i3, blockpos2.getZ(), k1, nextProjection));
												poolelementstructurepiece.addJunction(new JigsawJunction(blockpos1.getX(), l2 - j1 + k2, blockpos1.getZ(), -k1, projection));
												this.pieces.add(poolelementstructurepiece);
												if (depth + 1 <= this.maxDepth) {
													this.placing.addLast(new OceanJigsawPlacement.PieceState(poolelementstructurepiece, mutableobject1, depth + 1));
												}
												continue jigsawBlockSearch;
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}

		private static ResourceKey<StructureTemplatePool> readPoolName(StructureTemplate.StructureBlockInfo info) {
			return ResourceKey.create(Registries.TEMPLATE_POOL, new ResourceLocation(info.nbt.getString("pool")));
		}
	}
}

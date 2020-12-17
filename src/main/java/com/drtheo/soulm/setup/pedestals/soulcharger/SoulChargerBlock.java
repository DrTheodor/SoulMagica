package com.drtheo.soulm.setup.pedestals.soulcharger;

import com.drtheo.soulm.setup.pedestals.ModTileEntityTypes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.*;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.Random;

public class SoulChargerBlock extends Block {
    private static final VoxelShape SHAPE = VoxelShapes.or(
            Block.makeCuboidShape(2.5, 3, 2.5, 13.5, 4, 13.5),
            Block.makeCuboidShape(1, 0, 1, 15, 1, 15),
            Block.makeCuboidShape(1.5, 1, 1.5, 14.5, 2, 14.5),
            Block.makeCuboidShape(2.5, 2, 2.5, 13.5, 3, 13.5),
            Block.makeCuboidShape(7, 4.5, 7, 9, 5, 9),
            Block.makeCuboidShape(6, 5, 7, 7, 6, 9),
            Block.makeCuboidShape(9, 5, 7, 10, 6, 9),
            Block.makeCuboidShape(7, 5, 9, 9, 6, 10),
            Block.makeCuboidShape(7, 5, 6, 9, 6, 7),
            Block.makeCuboidShape(1, 6, 2, 2, 7, 3),
            Block.makeCuboidShape(6, 9, 16, 7, 10, 17),
            Block.makeCuboidShape(2, 9, 14, 3, 10, 15),
            Block.makeCuboidShape(15, 9, 12, 16, 10, 13),
            Block.makeCuboidShape(15, 7, 5, 16, 8, 6),
            Block.makeCuboidShape(8, 11, 0, 9, 12, 1),
            Block.makeCuboidShape(2.5, 2, 2, 13.5, 3, 2.5),
            Block.makeCuboidShape(2.5, 2, 13.5, 13.5, 3, 14),
            Block.makeCuboidShape(13.5, 2, 2, 14, 3, 14),
            Block.makeCuboidShape(2, 2, 2, 2.5, 3, 14),
            Block.makeCuboidShape(0.5, 0, 13, 1, 0.5, 15.5),
            Block.makeCuboidShape(1, 0, 13, 3, 0.5, 15.5),
            Block.makeCuboidShape(13, 0, 13, 15.5, 0.5, 15.5),
            Block.makeCuboidShape(13, 0, 0.5, 15.5, 0.5, 3),
            Block.makeCuboidShape(0.5, 0, 0.5, 3, 0.5, 3),
            Block.makeCuboidShape(1.5, 1, 1, 2, 2, 1.5),
            Block.makeCuboidShape(1, 1, 1, 1.5, 2, 2),
            Block.makeCuboidShape(14.5, 1, 14, 15, 2, 14.5),
            Block.makeCuboidShape(14, 1, 1, 15, 2, 1.5),
            Block.makeCuboidShape(14, 1, 14.5, 15, 2, 15),
            Block.makeCuboidShape(14.5, 1, 1.5, 15, 2, 2),
            Block.makeCuboidShape(1, 1, 14, 1.5, 2, 15),
            Block.makeCuboidShape(1.5, 1, 14.5, 2, 2, 15)).simplify();

    public SoulChargerBlock() {
        super(Block.Properties.create(Material.IRON)
                .hardnessAndResistance(3.5F, 4.0F)
                .sound(SoundType.BASALT)
                .harvestLevel(0)
                .harvestTool(ToolType.PICKAXE)
                .setRequiresTool());
    }

    public SoulChargerBlock(Properties from) {
        super(from);
    }


    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (!worldIn.isRemote) {
            final TileEntity tile = worldIn.getTileEntity(pos);
            if (tile instanceof SoulChargerTileEntity) {
                NetworkHooks.openGui((ServerPlayerEntity) player, (SoulChargerTileEntity) tile, pos);
                return ActionResultType.SUCCESS;
            }
        }
        return ActionResultType.FAIL;
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return ModTileEntityTypes.SOUL_CHARGER.get().create();
    }



    @OnlyIn(Dist.CLIENT)
    @Override
    public void animateTick(BlockState state, World world, BlockPos pos, Random random) {
        super.animateTick(state, world, pos, random);
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        int i = x;
        int j = y;
        int k = z;
        if (true)
            for (int l = 0; l < 4; ++l) {
                double d0 = (i + random.nextFloat() * 2);
                double d1 = (j + random.nextFloat() * 2);
                double d2 = (k + random.nextFloat() * 2);
                int i1 = random.nextInt(2) * 2 - 5;
                double d3 = (random.nextFloat() - 0.5D);
                double d4 = (random.nextFloat() - 0.5D);
                double d5 = (random.nextFloat() - 0.5D);
                world.addParticle(ParticleTypes.SOUL, d0, d1, d2, d3, d4, d5);
            }
        }
    @Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            TileEntity tile = worldIn.getTileEntity(pos);
            if (tile instanceof SoulChargerTileEntity) {
                InventoryHelper.dropItems(worldIn, pos, ((SoulChargerTileEntity) tile).getItems());
            }
        }
    }

    }
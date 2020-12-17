package com.drtheo.soulm.setup.pedestals.pentagrams;


import com.drtheo.soulm.setup.pedestals.ModTileEntityTypes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class PentagramAltarBlock extends Block {

    private static final VoxelShape SHAPE = VoxelShapes.or(
            Block.makeCuboidShape(5, 1, 5, 7, 2, 7),
            Block.makeCuboidShape(5, 1, 9, 7, 2, 11),
            Block.makeCuboidShape(9, 1, 9, 11, 2, 11),
            Block.makeCuboidShape(9, 1, 5, 11, 2, 7),
            Block.makeCuboidShape(6, 2, 6, 10, 3, 10),
            Block.makeCuboidShape(7, 3, 7, 9, 4, 9),
            Block.makeCuboidShape(7, 4, 9, 9, 5, 10),
            Block.makeCuboidShape(6, 0, 10, 8, 1, 12),
            Block.makeCuboidShape(7, 4, 6, 9, 5, 7),
            Block.makeCuboidShape(9, 4, 7, 10, 5, 9),
            Block.makeCuboidShape(10, 0, 8, 12, 1, 10),
            Block.makeCuboidShape(6, 4, 7, 7, 5, 9),
            Block.makeCuboidShape(4, 0, 6, 6, 1, 8),
            Block.makeCuboidShape(7, 0, 7, 9, 2, 9),
            Block.makeCuboidShape(7.5, 4, 5, 8.5, 7.5, 6),
            Block.makeCuboidShape(7.5, 4, 10, 8.5, 6, 11),
            Block.makeCuboidShape(10, 4, 7.5, 11, 7, 8.5),
            Block.makeCuboidShape(5, 4, 7.5, 6, 6.5, 8.5),
            Block.makeCuboidShape(8, 0, 4, 10, 1, 6),
            Block.makeCuboidShape(6, 0, 4, 8, 1, 6),
            Block.makeCuboidShape(10, 0, 6, 12, 1, 8),
            Block.makeCuboidShape(8, 0, 10, 10, 1, 12),
            Block.makeCuboidShape(4, 0, 8, 6, 1, 10)
    ).simplify();

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    public PentagramAltarBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return ModTileEntityTypes.PENTAGRAM_ALTAR.get().create();
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player,
                                             Hand handIn, BlockRayTraceResult result) {
        if (!worldIn.isRemote) {
            TileEntity tile = worldIn.getTileEntity(pos);
            if (tile instanceof PentagramAltarTileEntity) {
                NetworkHooks.openGui((ServerPlayerEntity) player, (PentagramAltarTileEntity) tile, pos);
                return ActionResultType.SUCCESS;
            }
        }
        return ActionResultType.FAIL;
    }

    @Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            TileEntity te = worldIn.getTileEntity(pos);
            if (te instanceof PentagramAltarTileEntity) {
                InventoryHelper.dropItems(worldIn, pos, ((PentagramAltarTileEntity) te).getItems());
            }
        }
    }
}
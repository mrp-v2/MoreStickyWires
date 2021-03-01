package io.github.mrp_v2.morestickywires.block;

import mrp_v2.morewires.block.InfiniwireBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class StickyInfiniwireBlock extends InfiniwireBlock implements IWaterLoggable
{
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public StickyInfiniwireBlock(float hueChange)
    {
        super(hueChange);
        this.setDefaultState(this.getDefaultState().with(WATERLOGGED, false));
        sideBaseState = sideBaseState.with(WATERLOGGED, false);
    }

    @Override public BlockState getStateForPlacement(BlockItemUseContext context)
    {
        return super.getStateForPlacement(context)
                .with(WATERLOGGED, context.getWorld().getFluidState(context.getPos()).getFluid() == Fluids.WATER);
    }

    @Override protected BlockState getUpdatedState(IBlockReader reader, BlockState state, BlockPos pos)
    {
        return super.getUpdatedState(reader, state, pos).with(WATERLOGGED, state.get(WATERLOGGED));
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn,
            BlockPos currentPos, BlockPos facingPos)
    {
        if (stateIn.get(WATERLOGGED))
        {
            worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
        }
        return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos)
                .with(WATERLOGGED, stateIn.get(WATERLOGGED));
    }

    @Override protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
    {
        super.fillStateContainer(builder);
        builder.add(WATERLOGGED);
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player,
            Hand handIn, BlockRayTraceResult hit)
    {
        Item item = player.getHeldItem(handIn).getItem();
        if (item == Items.BUCKET || item == Items.WATER_BUCKET)
        {
            return ActionResultType.PASS;
        }
        if (player.abilities.allowEdit)
        {
            if (areAllSidesValid(state) || areAllSidesInvalid(state))
            {
                BlockState blockstate = areAllSidesValid(state) ? this.getDefaultState() : this.sideBaseState;
                blockstate = blockstate.with(POWER, state.get(POWER)).with(WATERLOGGED, state.get(WATERLOGGED));
                blockstate = this.getUpdatedState(worldIn, blockstate, pos);
                if (blockstate != state)
                {
                    worldIn.setBlockState(pos, blockstate, 3);
                    this.updateChangedConnections(worldIn, pos, state, blockstate);
                    return ActionResultType.SUCCESS;
                }
            }
        }
        return ActionResultType.PASS;
    }

    @Override public FluidState getFluidState(BlockState state)
    {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
    }

    @Override public boolean isReplaceable(BlockState state, Fluid fluid)
    {
        if (fluid == Fluids.WATER || fluid == Fluids.FLOWING_WATER)
        {
            return false;
        }
        return super.isReplaceable(state, fluid);
    }
} // If placed underwater next to a power source, doesn't always connect visually

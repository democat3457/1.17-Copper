package democat.copper.blocks;

import java.util.Random;

import democat.copper.sound.SoundTypes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockChangeOverTimeStairs extends BlockStairs implements BlockChangeOverTime {
    private final Block changeTo;

    public BlockChangeOverTimeStairs(IBlockState state, Block block) {
        super(state);
        this.setTickRandomly(false);
        this.setHardness(3.0F);
        this.setResistance(6.0F);
        this.changeTo = block;

        this.setSoundType(SoundTypes.COPPER);

        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(HALF, BlockStairs.EnumHalf.BOTTOM).withProperty(SHAPE, BlockStairs.EnumShape.STRAIGHT));
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase entity, ItemStack item) {
        super.onBlockPlacedBy(world, pos, state, entity, item);
        scheduleChange(world, this, pos);
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random random) {
        super.updateTick(world, pos, state, random);
        change(world, world.getBlockState(pos), pos);
    }

    @Override
    public IBlockState getChangeTo(IBlockState state) {
        return this.changeTo.getDefaultState()
            .withProperty(FACING, state.getValue(FACING))
            .withProperty(HALF, state.getValue(HALF))
            .withProperty(SHAPE, state.getValue(SHAPE));
    }
}
package democat.copper.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface BlockChangeOverTime {
    default int getChangeInterval(Random rand) {
        return 1200000 + rand.nextInt(768000);
    }

    IBlockState getChangeTo(IBlockState paramBlockState);
    
    default void scheduleChange(World world, Block block, BlockPos blockPos) {
        world.scheduleUpdate(blockPos, block, getChangeInterval(world.rand));
    }
    
    default void change(World world, IBlockState blockState, BlockPos blockPos) {
        world.setBlockState(blockPos, getChangeTo(blockState));
    }
}
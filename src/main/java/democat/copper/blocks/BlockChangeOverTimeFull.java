package democat.copper.blocks;

import java.util.Random;

import democat.copper.sound.SoundTypes;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockChangeOverTimeFull extends Block implements BlockChangeOverTime {
    private final Block changeTo;
  
    public BlockChangeOverTimeFull(Material material, MapColor color, Block block) {
        super(material, color);
        this.setTickRandomly(false);
        this.setHardness(3.0F);
        this.setResistance(6.0F);
        this.changeTo = block;

        this.setSoundType(SoundTypes.COPPER);
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
        return this.changeTo.getDefaultState();
    }
}
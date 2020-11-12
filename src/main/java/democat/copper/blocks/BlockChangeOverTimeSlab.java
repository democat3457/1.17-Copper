package democat.copper.blocks;

import java.util.Random;

import democat.copper.sound.SoundTypes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockChangeOverTimeSlab extends BlockSlab implements BlockChangeOverTime {
    public static final PropertyEnum<BlockChangeOverTimeSlab.Variant> VARIANT = PropertyEnum.<BlockChangeOverTimeSlab.Variant>create("variant", BlockChangeOverTimeSlab.Variant.class);
    
    private final Block changeTo;

    public BlockChangeOverTimeSlab(Material material, MapColor color, Block block) {
        super(material, color);
        this.setTickRandomly(false);
        this.setHardness(3.0F);
        this.setResistance(6.0F);
        this.changeTo = block;

        this.setSoundType(SoundTypes.COPPER);

        IBlockState iblockstate = this.blockState.getBaseState();
        if (!this.isDouble())
            iblockstate = iblockstate.withProperty(HALF, BlockSlab.EnumBlockHalf.BOTTOM);
        this.setDefaultState(iblockstate.withProperty(VARIANT, Variant.DEFAULT));
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
            .withProperty(VARIANT, state.getValue(VARIANT))
            .withProperty(HALF, state.getValue(HALF));
    }

    @Override
    public Comparable<?> getTypeForItem(ItemStack stack) {
        return Variant.DEFAULT;
    }

    @Override
    public String getUnlocalizedName(int meta) {
        return super.getUnlocalizedName();
    }

    @Override
    public IProperty<?> getVariantProperty() {
        return VARIANT;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        IBlockState iblockstate = this.getDefaultState().withProperty(VARIANT, Variant.DEFAULT);

        if (!this.isDouble())
        {
            iblockstate = iblockstate.withProperty(HALF, (meta & 8) == 0 ? BlockSlab.EnumBlockHalf.BOTTOM : BlockSlab.EnumBlockHalf.TOP);
        }

        return iblockstate;
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int i = 0;
        if (!this.isDouble() && state.getValue(HALF) == BlockSlab.EnumBlockHalf.TOP)
            i |= 8;
        return i;
    }

    @Override
    public boolean isDouble() {
        return false;
    }

    public static class Double extends BlockChangeOverTimeSlab {
        public Double(Material material, MapColor color, Block changeTo) {
            super(material, color, changeTo);
        }
        
        public boolean isDouble()
        {
            return true;
        }
    }

    public static class Half extends BlockChangeOverTimeSlab {
        public Half(Material material, MapColor color, Block changeTo) {
            super(material, color, changeTo);
        }

        public boolean isDouble()
        {
            return false;
        }
    }

    public static enum Variant implements IStringSerializable {
        DEFAULT;

        public String getName()
        {
            return "default";
        }
    }
}
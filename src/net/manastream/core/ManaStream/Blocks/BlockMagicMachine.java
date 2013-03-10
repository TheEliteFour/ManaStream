/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.manastream.core.ManaStream.Blocks;

import java.util.Random;
import net.manastream.core.ManaStream.Interfaces.IMagicItem;
import net.manastream.core.ManaStream.Interfaces.ISneakUseWand;
import net.manastream.core.ManaStream.Interfaces.IToolWand;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 *
 * @ModificationAuthor Xeology
 * @OriginalAuthor Calclavia
 *
 * This class is a modified version of ElectricityNetwork inside of
 * UniversalElectricity. The code has been modified and adapted to a new purpose
 * and theme while trying to slim and correct any short comings perceived by
 * Xeology. All credit for the original code and process goes to it's rightful
 * owner, I make no further claims other then the modifications.
 *
 */
public abstract class BlockMagicMachine extends BlockContainer implements ISneakUseWand {

    public BlockMagicMachine(int id, Material material) {
	super(id, material);
	this.setHardness(0.6f);
    }

    public BlockMagicMachine(int id, int textureIndex, Material material) {
	super(id, textureIndex, material);
	this.setHardness(0.6f);
    }

    /**
     * DO NOT OVERRIDE THIS FUNCTION! Called when the block is right clicked by
     * the player. This modified version detects magic items and wand actions on
     * your magic machine block. Do not override this function. Use
     * onMagicMachineActivated instead! (It does the same thing)
     *
     * @param world The World Object.
     * @param x , y, z The coordinate of the block.
     * @param side The side the player clicked on.
     * @param hitX , hitY, hitZ The position the player clicked on relative to
     * the block.
     */
    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer par5EntityPlayer, int side, float hitX, float hitY, float hitZ) {
	int metadata = world.getBlockMetadata(x, y, z);

	/**
	 * Check if the player is holding a wand or a magic item. If so, do not
	 * open the GUI.
	 */
	if (par5EntityPlayer.inventory.getCurrentItem() != null) {
	    if (par5EntityPlayer.inventory.getCurrentItem().getItem() instanceof IToolWand) {
		world.notifyBlocksOfNeighborChange(x, y, z, this.blockID);
		((IToolWand) par5EntityPlayer.inventory.getCurrentItem().getItem()).wandUsed(par5EntityPlayer, x, y, z);

		return this.onUseWand(world, x, y, z, par5EntityPlayer, side, hitX, hitY, hitZ);
	    } else if (par5EntityPlayer.inventory.getCurrentItem().getItem() instanceof IMagicItem) {
		if (this.onUseMagicItem(world, x, y, z, par5EntityPlayer, side, hitX, hitY, hitZ)) {
		    return true;
		}
	    }
	}

	if (par5EntityPlayer.isSneaking()) {
	    if (this.onSneakMagicMachineActivated(world, x, y, z, par5EntityPlayer, side, hitX, hitY, hitZ)) {
		return true;
	    }
	}

	return this.onMagicMachineActivated(world, x, y, z, par5EntityPlayer, side, hitX, hitY, hitZ);
    }

    /**
     * Called when the magic machine is right clicked by the player
     *
     * @return True if something happens
     */
    public boolean onMagicMachineActivated(World par1World, int x, int y, int z, EntityPlayer par5EntityPlayer, int side, float hitX, float hitY, float hitZ) {
	return false;
    }

    /**
     * Called when the machine is being wanded by a player while sneaking.
     *
     * @return True if something happens
     */
    public boolean onSneakMagicMachineActivated(World par1World, int x, int y, int z, EntityPlayer par5EntityPlayer, int side, float hitX, float hitY, float hitZ) {
	return false;
    }

    /**
     * Called when a player uses a magic item on the magic machine
     *
     * @return True if some happens
     */
    public boolean onUseMagicItem(World par1World, int x, int y, int z, EntityPlayer par5EntityPlayer, int side, float hitX, float hitY, float hitZ) {
	return false;
    }

    /**
     * Called when a player uses a wrench on the magic machine
     *
     * @return True if some happens
     */
    public boolean onUseWand(World par1World, int x, int y, int z, EntityPlayer par5EntityPlayer, int side, float hitX, float hitY, float hitZ) {
	return false;
    }

    /**
     * Called when a player uses a wand on the magic machine while sneaking.
     * Only works with the MS wand.
     *
     * @return True if some happens
     */
    @Override
    public boolean onSneakUseWand(World par1World, int x, int y, int z, EntityPlayer par5EntityPlayer, int side, float hitX, float hitY, float hitZ) {
	return this.onUseWand(par1World, x, y, z, par5EntityPlayer, side, hitX, hitY, hitZ);
    }

    /**
     * Returns the TileEntity used by this block. You should use the metadata
     * sensitive version of this to get the maximum optimization!
     */
    @Override
    public TileEntity createNewTileEntity(World var1) {
	return null;
    }

    @Override
    public void breakBlock(World par1World, int x, int y, int z, int par5, int par6) {
	this.dropEntireInventory(par1World, x, y, z, par5, par6);
	super.breakBlock(par1World, x, y, z, par5, par6);
    }

    /**
     * Override this if you don't need it. This will eject all items out of this
     * machine if it has an inventory.
     */
    public void dropEntireInventory(World par1World, int x, int y, int z, int par5, int par6) {
	TileEntity tileEntity = par1World.getBlockTileEntity(x, y, z);

	if (tileEntity != null) {
	    if (tileEntity instanceof IInventory) {
		IInventory inventory = (IInventory) tileEntity;

		for (int var6 = 0; var6 < inventory.getSizeInventory(); ++var6) {
		    ItemStack var7 = inventory.getStackInSlot(var6);

		    if (var7 != null) {
			Random random = new Random();
			float var8 = random.nextFloat() * 0.8F + 0.1F;
			float var9 = random.nextFloat() * 0.8F + 0.1F;
			float var10 = random.nextFloat() * 0.8F + 0.1F;

			while (var7.stackSize > 0) {
			    int var11 = random.nextInt(21) + 10;

			    if (var11 > var7.stackSize) {
				var11 = var7.stackSize;
			    }

			    var7.stackSize -= var11;
			    EntityItem var12 = new EntityItem(par1World, (x + var8), (y + var9), (z + var10), new ItemStack(var7.itemID, var11, var7.getItemDamage()));

			    if (var7.hasTagCompound()) {
				var12.getEntityData().setCompoundTag(var7.getTagCompound().getName(), (NBTTagCompound) var7.getTagCompound().copy());
			    }

			    float var13 = 0.05F;
			    var12.motionX = ((float) random.nextGaussian() * var13);
			    var12.motionY = ((float) random.nextGaussian() * var13 + 0.2F);
			    var12.motionZ = ((float) random.nextGaussian() * var13);
			    par1World.spawnEntityInWorld(var12);
			}
		    }
		}
	    }
	}
    }
}

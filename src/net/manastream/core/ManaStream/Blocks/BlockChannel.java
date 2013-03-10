/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.manastream.core.ManaStream.Blocks;

import net.manastream.core.ManaStream.Interfaces.IManaChannel;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 *
 * @author Xeology
 */
public class BlockChannel extends BlockContainer {

    public BlockChannel(int id, Material material) {
	super(id, material);
    }

    @Override
    public void onBlockAdded(World world, int x, int y, int z) {
	super.onBlockAdded(world, x, y, z);

	TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

	if (tileEntity != null) {
	    if (tileEntity instanceof IManaChannel) {
		((IManaChannel) tileEntity).refreshConnectedBlocks();
		world.markBlockForUpdate(x, y, z);
	    }
	}
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, int id) {
	TileEntity tile = world.getBlockTileEntity(x, y, z);

	if (tile != null) {
	    if (tile instanceof IManaChannel) {
		((IManaChannel) tile).refreshConnectedBlocks();
		world.markBlockForUpdate(x, y, z);
	    }
	}
    }

    @Override
    public TileEntity createNewTileEntity(World world) {
	return null;
    }
}
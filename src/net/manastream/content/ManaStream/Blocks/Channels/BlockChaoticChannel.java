/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.manastream.content.ManaStream.Blocks.Channels;

import cpw.mods.fml.common.registry.GameRegistry;
import net.manastream.content.ManaStream.Items.Channels.ItemChaoticChannel;
import net.manastream.content.ManaStream.Tiles.Channels.TileChaoticChannel;
import net.manastream.core.ManaStream.Blocks.BlockChannel;
import net.manastream.core.ManaStream.ManaStream;
import net.manastream.core.ManaStream.Util.MSTab;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 *
 * @author Xeology
 */
public class BlockChaoticChannel extends BlockChannel
{


public BlockChaoticChannel(int id)
	{
		super(id, Material.glass);
		setBlockName("ChatoicChannel");
		setStepSound(soundGlassFootstep);
		setResistance(0.2F);
		setHardness(0.1f);
		setBlockBounds(0.30F, 0.30F, 0.30F, 0.70F, 0.70F, 0.70F);
		blockIndexInTexture = 19;
		setCreativeTab(MSTab.INSTANCE);
		GameRegistry.registerBlock(this, ItemChaoticChannel.class, "Chaotic Channel");
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	public int getRenderType()
	{
		return -1;
	}

	@Override
	public TileEntity createNewTileEntity(World world)
	{
		return new TileChaoticChannel();
	}

	@Override
	public String getTextureFile()
	{
		return ManaStream.getStatic().getModRegistry().getTextureRegistry().CHAOTIC_CHANNEL;
	}
}

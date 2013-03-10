/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.manastream.core.ManaStream.Util;

/**
 *
 * @author Xeology
 */
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class CommonProxy implements IGuiHandler {

    public void preInit() {
    }

    public void init() {
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
	return null;
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
	TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

	if (tileEntity != null) {
	    switch (ID) {
		case 0:
		//return new ContainerBatteryBox(player.inventory, ((TileEntityBatteryBox) tileEntity));
		case 1:
		//return new ContainerCoalGenerator(player.inventory, ((TileEntityCoalGenerator) tileEntity));
		case 2:
		//return new ContainerElectricFurnace(player.inventory, ((TileEntityElectricFurnace) tileEntity));
	    }
	}

	return null;
    }
}

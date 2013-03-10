/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.manastream.core.ManaStream.Tiles;

import java.util.EnumSet;
import net.manastream.core.ManaStream.EnergyStream.Energy;
import net.manastream.core.ManaStream.EnergyStream.MagicConnections;
import net.manastream.core.ManaStream.Interfaces.IMagic;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;

/**
 *
 * @author Xeology
 */
public abstract class TileEnergyMaker extends TileEnergyDisableable implements IMagic {

    public TileEnergyMaker() {
	super();
	MagicConnections.registerConnector(this, EnumSet.range(ForgeDirection.DOWN, ForgeDirection.EAST));
    }

    @Override
    public void updateEntity() {
	super.updateEntity();
    }

    @Override
    public int getPotency() {
	return 1;
    }

    @Override
    public void invalidate() {
	Energy.instance.unregister(this);
	super.invalidate();

    }
}

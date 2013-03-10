/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.manastream.core.ManaStream.Tiles;

import java.util.EnumSet;
import net.manastream.core.ManaStream.EnergyStream.EnergyStream;
import net.manastream.core.ManaStream.EnergyStream.MagicConnections;
import net.manastream.core.ManaStream.EnergyStream.MagicLoad;
import net.manastream.core.ManaStream.Interfaces.IMagicStorage;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ForgeDirection;

/**
 *
 * @author Xeology
 */
public class TileEnergyStorage extends TileEnergyUser implements IMagicStorage {

    private double magic = 0;
    public boolean isPotencySensitive = true;
    public double prevMagic = 0;

    @Override
    public void updateEntity() {
	super.updateEntity();

	prevMagic = magic;

	if (!worldObj.isRemote) {
	    if (!this.isDisabled()) {
		MagicLoad load = EnergyStream.consumeFromMultipleSides(this, this.getConsumingSides(), this.getRequest());
		onReceive(load);
	    } else {
		EnergyStream.consumeFromMultipleSides(this, new MagicLoad());
	    }
	}

    }

    protected EnumSet<ForgeDirection> getConsumingSides() {
	return MagicConnections.getDirections(this);
    }

    public MagicLoad getRequest() {
	return new MagicLoad((getMaxMagic() - getMagic()), getPotency());
    }

    public void onReceive(MagicLoad load) {
	/**
	 * Creates an explosion if the voltage is too high.
	 */
	if (isPotencySensitive) {
	    if (load.getPotencyObject().getPotency() > getPotency()) {
		worldObj.createExplosion(null, xCoord, yCoord, zCoord, 1.5f, true);
		return;
	    }
	}

	setMagic(getMagic() + load.getMagic());
    }

    @Override
    public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
	super.readFromNBT(par1NBTTagCompound);
	magic = par1NBTTagCompound.getDouble("magic");
    }

    /**
     * Writes a tile entity to NBT.
     */
    @Override
    public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
	super.writeToNBT(par1NBTTagCompound);

	par1NBTTagCompound.setDouble("magic", magic);
    }

    @Override
    public double getMagic(Object... data) {
	return magic;
    }

    @Override
    public void setMagic(double magic, Object... data) {
	this.magic = Math.max(Math.min(magic, getMaxMagic()), 0);
    }

    @Override
    public double getMaxMagic() {
	return 10000;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.manastream.core.ManaStream.Tiles;

import java.util.EnumSet;
import net.manastream.core.ManaStream.EnergyStream.EnergyStream;
import net.manastream.core.ManaStream.EnergyStream.MagicConnections;
import net.manastream.core.ManaStream.EnergyStream.MagicLoad;
import net.minecraftforge.common.ForgeDirection;

/**
 *
 * @author Xeology
 */
public class TileEnergyRunnable extends TileEnergyUser {

    /**
     * The amount of watts received this tick. This variable should be deducted
     * when used.
     */
    public double prevMagic = 0;
    public double magicReceived = 0;
    public boolean isPotencySensitive = true;

    @Override
    public void updateEntity() {
	super.updateEntity();

	prevMagic = magicReceived;

	//Only run server side
	if (!worldObj.isRemote) {
	    //If it is disabled stop asking for magic
	    if (!this.isDisabled()) {
		MagicLoad load = EnergyStream.consumeFromMultipleSides(this, this.getConsumingSides(), this.getRequest());
		this.onReceive(load);
	    } else {
		EnergyStream.consumeFromMultipleSides(this, new MagicLoad());
	    }
	}
    }

    public MagicLoad getRequest() {
	return new MagicLoad();
    }

    protected EnumSet<ForgeDirection> getConsumingSides() {
	return MagicConnections.getDirections(this);
    }

    public void onReceive(MagicLoad load) {
	if (isPotencySensitive) {
	    if (load.getPotencyObject().getPotency() > this.getPotency()) {
		this.worldObj.createExplosion(null, this.xCoord, this.yCoord, this.zCoord, 1.5f, true);
		return;
	    }
	}

	magicReceived = Math.min(magicReceived + load.getMagic(), this.getMagicBuffer());
    }

    public double getMagicBuffer() {
	return this.getRequest().getMagic() * 2;
    }

    @Override
    public double getMagic(Object... data) {
	return 1;
    }

    @Override
    public int getPotency() {
	return 1;
    }
}

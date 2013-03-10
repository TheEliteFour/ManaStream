/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.manastream.content.ManaStream.Tiles.Channels;

import net.manastream.core.ManaStream.ManaStream;
import net.manastream.core.ManaStream.Tiles.TileManaChannel;

/**
 *
 * @author Xeology
 */
public class TileBurntChannel extends TileManaChannel {

    public double distrubance = 1000;
    public double maxPotency = 100;

    public TileBurntChannel() {
    }

    @Override
    public double getDisturbance() {
	return distrubance;
    }

    @Override
    public double getMaxPotency() {
	return maxPotency;
    }

    @Override
    public void onOverCharge() {
	if (!worldObj.isRemote) {
	    worldObj.setBlockWithNotify(xCoord, yCoord, zCoord, 0);
	}
    }
}

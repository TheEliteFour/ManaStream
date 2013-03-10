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
public class TileChaoticChannel extends TileManaChannel {

    public double distrubance = 0.00;
    public double maxPotency = 4;

    public TileChaoticChannel() {
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
	    worldObj.setBlockWithNotify(xCoord, yCoord, zCoord, ManaStream.getStatic().getModRegistry().getBlockRegistry().BURNT_CHANNEL.blockID);
	}
    }
}

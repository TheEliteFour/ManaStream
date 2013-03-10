/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.manastream.core.ManaStream.Util;

import java.util.ArrayList;
import java.util.List;
import net.manastream.core.ManaStream.Tiles.TileManaChannel;
import net.manastream.core.ManaStream.Tiles.TileEnergyUser;

/**
 *
 * @author Xeology
 */
public class ManaPath {

    private TileEnergyUser energyUser;
    private TileManaChannel manaChannel;
    private List<TileManaChannel> registeredManaChannels = new ArrayList<TileManaChannel>();
    ;
    private int distance;

    public ManaPath(TileEnergyUser energyUser, TileManaChannel manaChannel, int distance) {
	this.energyUser = energyUser;
	this.manaChannel = manaChannel;
	this.distance = distance;
    }

    public TileEnergyUser getTileEnergyUser() {
	return energyUser;
    }

    public TileManaChannel getTileManaChannel() {
	return manaChannel;
    }

    public boolean isTileManaChannelInPath(TileManaChannel channel) {
	return registeredManaChannels.contains(channel);
    }

    public void addTileManaChannelInPath(TileManaChannel channel) {
	registeredManaChannels.add(channel);
    }

    public void removeTileManaChannelInPath(TileManaChannel channel) {
	registeredManaChannels.remove(channel);
    }

    public int distance() {
	return distance;
    }
}

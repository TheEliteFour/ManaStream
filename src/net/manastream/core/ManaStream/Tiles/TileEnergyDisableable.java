/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.manastream.core.ManaStream.Tiles;

import net.manastream.core.ManaStream.Interfaces.IDisableable;

/**
 *
 * @author Xeology
 */
public class TileEnergyDisableable extends TileEnergyAdvanced implements IDisableable {

    protected int tics = 0;

    @Override
    public void updateEntity() {
	super.updateEntity();

	if (tics > 0) {
	    tics--;
	    whileDisable();
	    return;
	}
    }

    protected void whileDisable() {
    }

    @Override
    public void onDisable(int duration) {
	tics = duration;
    }

    @Override
    public boolean isDisabled() {
	return tics > 0;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.manastream.core.ManaStream.Util;

/**
 *
 * @author Xeology
 */
public enum BlockSide {

    NORTH,
    SOUTH,
    WEST,
    EAST,
    TOP,
    BOTTOM;

    public BlockSide getOpposite() {
	switch (this) {
	    case NORTH:
		return SOUTH;
	    case SOUTH:
		return NORTH;
	    case WEST:
		return EAST;
	    case EAST:
		return WEST;
	    case TOP:
		return BOTTOM;
	    case BOTTOM:
		return TOP;
	}
	return null;
    }
}

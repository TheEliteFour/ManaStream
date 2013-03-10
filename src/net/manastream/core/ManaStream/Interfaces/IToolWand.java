/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.manastream.core.ManaStream.Interfaces;

import net.minecraft.entity.player.EntityPlayer;

/**
 *
 * @author Xeology
 */
public interface IToolWand {

    public boolean canWand(EntityPlayer player, int x, int y, int z);

    public void wandUsed(EntityPlayer player, int x, int y, int z);
}

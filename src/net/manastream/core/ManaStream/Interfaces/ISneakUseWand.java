/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.manastream.core.ManaStream.Interfaces;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

/**
 *
 * @author Xeology
 */
public interface ISneakUseWand {

    public boolean onSneakUseWand(World world, int x, int y, int z, EntityPlayer player, int face, float hX, float hY, float hZ);
}

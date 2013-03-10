/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.manastream.core.ManaStream.Interfaces;

import net.minecraft.item.ItemStack;

/**
 *
 * @author Xeology
 */
public interface IMagicItem extends IMagicStorage, IMagic {

    public double onReceive(double magic, ItemStack itemStack);

    //When magic is requested
    public double onUse(double magic, ItemStack itemStack);

    public boolean canReceiveMagic();

    public boolean canProduceMagic();
}

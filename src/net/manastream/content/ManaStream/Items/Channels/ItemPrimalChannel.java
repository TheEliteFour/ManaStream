/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.manastream.content.ManaStream.Items.Channels;

import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

/**
 *
 * @author Xeology
 */
public class ItemPrimalChannel extends ItemBlock {

    public ItemPrimalChannel(int id) {
	super(id);
	this.setMaxDamage(0);
	this.setHasSubtypes(true);
    }
    
    @Override
    public String getItemDisplayName(ItemStack it){
	return "Primal Channel";
    }

    @Override
    public int getMetadata(int damage) {
	return damage;
    }

    @Override
    public void addInformation(ItemStack item, EntityPlayer player, List info, boolean par4) {
	info.add("Disturbance: §b0.05");
	info.add("Max Potency: §bPrimal");
    }
}
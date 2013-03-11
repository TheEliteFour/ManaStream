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
public class ItemBurntChannel extends ItemBlock {

    public ItemBurntChannel(int id) {
	super(id);
	this.setMaxDamage(0);
	this.setHasSubtypes(true);
    }
    
    @Override
    public String getItemDisplayName(ItemStack it){
	return "Burnt Channel";
    }

    @Override
    public int getMetadata(int damage) {
	return damage;
    }

    @Override
    public void addInformation(ItemStack item, EntityPlayer player, List info, boolean par4) {
	info.add("Disturbance: §b1000.0");
	info.add("Max Potency: §bVoid");
    }
}
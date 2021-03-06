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
public class ItemChaoticChannel extends ItemBlock {

    public ItemChaoticChannel(int id) {
	super(id);
	this.setMaxDamage(0);
	this.setHasSubtypes(true);
    }

    @Override
    public int getMetadata(int damage) {
	return damage;
    }
    
    @Override
    public String getItemDisplayName(ItemStack it){
	return "Chaotic Channel";
    }

    @Override
    public void addInformation(ItemStack item, EntityPlayer player, List info, boolean par4) {
	info.add("Disturbance: §b0.00");
	info.add("Max Potency: §bChaotic");
    }
}
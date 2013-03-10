/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.manastream.core.ManaStream.Util;

import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

/**
 *
 * @author Xeology
 */
public class MSTab extends CreativeTabs
{
	public static final MSTab INSTANCE = new MSTab("ManaStream");
	private static ItemStack itemStack;

	public MSTab(String par2Str)
	{
		super(CreativeTabs.getNextID(), par2Str);
		LanguageRegistry.instance().addStringLocalization("itemGroup.ManaStream", "en_US", "ManaStream");
	}

	public static void setItemStack(ItemStack newItemStack)
	{
		if (itemStack == null)
		{
			itemStack = newItemStack;
		}
	}

	@Override
	public ItemStack getIconItemStack()
	{
		if (itemStack == null) { return new ItemStack(Block.blocksList[this.getTabIconItemIndex()]); }

		return itemStack;
	}
}

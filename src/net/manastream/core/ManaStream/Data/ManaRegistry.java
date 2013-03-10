/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.manastream.core.ManaStream.Data;

/**
 *
 * @author Xeology
 */
public class ManaRegistry {

    private ManaBlocks manaBlocks;
    private ManaItems manaItems;
    private ManaTextures manaTextures;

    public ManaBlocks getBlockRegistry() {
	return manaBlocks;
    }

    public ManaItems getItemRegistry() {
	return manaItems;
    }

    public ManaTextures getTextureRegistry() {
	return manaTextures;
    }

    public ManaRegistry() {
	manaTextures = new ManaTextures();
	manaBlocks = new ManaBlocks();
	manaItems = new ManaItems();
    }
}

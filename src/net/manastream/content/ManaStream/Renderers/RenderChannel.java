/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.manastream.content.ManaStream.Renderers;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.File;
import net.manastream.content.ManaStream.Models.ModelChannel;
import net.manastream.content.ManaStream.Tiles.Channels.*;
import net.manastream.core.ManaStream.ManaStream;
import net.manastream.core.ManaStream.Tiles.TileManaChannel;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

/**
 *
 * @author Calclavia
 * @modified-author Xeology
 */
@SideOnly(Side.CLIENT)
public class RenderChannel extends TileEntitySpecialRenderer {

    private ModelChannel model;

    public RenderChannel() {
	model = new ModelChannel();
    }

    public void renderAModelAt(TileManaChannel tileEntity, double d, double d1, double d2, float f) {
	// Texture file
	if (tileEntity instanceof TilePrimalChannel) {
	    bindTextureByName(ManaStream.getStatic().getModRegistry().getTextureRegistry().PRIMAL_CHANNEL);
	}
	if (tileEntity instanceof TilePotentChannel) {
	    bindTextureByName(ManaStream.getStatic().getModRegistry().getTextureRegistry().POTENT_CHANNEL);
	}
	if (tileEntity instanceof TilePureChannel) {
	    bindTextureByName(ManaStream.getStatic().getModRegistry().getTextureRegistry().PURE_CHANNEL);
	}
	if (tileEntity instanceof TileChaoticChannel) {
	    bindTextureByName(ManaStream.getStatic().getModRegistry().getTextureRegistry().CHAOTIC_CHANNEL);
	}
	if (tileEntity instanceof TileBurntChannel) {
	    bindTextureByName(ManaStream.getStatic().getModRegistry().getTextureRegistry().BURNT_CHANNEL);
	    tileEntity.worldObj.spawnParticle("smoke", tileEntity.xCoord, tileEntity.yCoord - .5, tileEntity.zCoord, 0, -1, 0);
	}
	GL11.glPushMatrix();
	GL11.glTranslatef((float) d + 0.5F, (float) d1 + 1.5F, (float) d2 + 0.5F);
	GL11.glScalef(1.0F, -1F, -1F);

	if (tileEntity.visuallyConnected[0]) {
	    model.renderBottom();
	}

	if (tileEntity.visuallyConnected[1]) {
	    model.renderTop();
	}

	if (tileEntity.visuallyConnected[2]) {
	    model.renderBack();
	}

	if (tileEntity.visuallyConnected[3]) {
	    model.renderFront();
	}

	if (tileEntity.visuallyConnected[4]) {
	    model.renderLeft();
	}

	if (tileEntity.visuallyConnected[5]) {
	    model.renderRight();
	}

	model.renderMiddle();
	GL11.glPopMatrix();
    }

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double var2, double var4, double var6, float var8) {
	this.renderAModelAt((TileManaChannel) tileEntity, var2, var4, var6, var8);
    }
}

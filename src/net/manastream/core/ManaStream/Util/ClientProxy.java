/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.manastream.core.ManaStream.Util;

/**
 *
 * @author Xeology
 */
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.manastream.content.ManaStream.Renderers.RenderChannel;
import net.manastream.core.ManaStream.Data.ManaTextures;
import net.manastream.core.ManaStream.ManaStream;
import net.manastream.core.ManaStream.Tiles.TileManaChannel;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {

    @Override
    public void preInit() {
	super.preInit();
	ManaTextures text = ManaStream.getStatic().getModRegistry().getTextureRegistry();
	System.out.println(text.PRIMAL_CHANNEL);
	MinecraftForgeClient.preloadTexture(text.PRIMAL_CHANNEL);
	MinecraftForgeClient.preloadTexture(text.POTENT_CHANNEL);
	MinecraftForgeClient.preloadTexture(text.PURE_CHANNEL);
	MinecraftForgeClient.preloadTexture(text.CHAOTIC_CHANNEL);
	MinecraftForgeClient.preloadTexture(text.BURNT_CHANNEL);
	MinecraftForgeClient.preloadTexture(text.PRIMAL_CHANNEL_ITEM);
	MinecraftForgeClient.preloadTexture(text.POTENT_CHANNEL_ITEM);
	MinecraftForgeClient.preloadTexture(text.PURE_CHANNEL_ITEM);
	MinecraftForgeClient.preloadTexture(text.CHAOTIC_CHANNEL_ITEM);
	MinecraftForgeClient.preloadTexture(text.BURNT_CHANNEL_ITEM);
    }

    @Override
    public void init() {
	super.init();
	ClientRegistry.bindTileEntitySpecialRenderer(TileManaChannel.class, new RenderChannel());	
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
	TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

	if (tileEntity != null) {
	    switch (ID) {
		case 0:

		case 1:

		case 2:
		//return new GuiElectricFurnace(player.inventory, ((TileEntityElectricFurnace) tileEntity));
	    }
	}

	return null;
    }
}

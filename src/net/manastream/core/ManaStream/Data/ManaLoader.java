/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.manastream.core.ManaStream.Data;

import cpw.mods.fml.common.registry.GameRegistry;
import net.manastream.content.ManaStream.Blocks.Channels.*;
import net.manastream.core.ManaStream.ManaStream;
import net.manastream.core.ManaStream.Tiles.TileEnergyAdvanced;
import net.manastream.core.ManaStream.Tiles.TileManaChannel;
import net.minecraft.creativetab.CreativeTabs;

/**
 *
 * @author Xeology
 */
public class ManaLoader {   
   
    public void registerTileEntities(){	
	GameRegistry.registerTileEntity(TileManaChannel.class, "MSManaChannel");
    }
    
    public void registerTextures(){
	ManaTextures texts = ManaStream.getStatic().getModRegistry().getTextureRegistry();
	ManaConfig config=new ManaConfig();
	String texPath=config.getTexturePath()+"/";
	texts.PRIMAL_CHANNEL=texPath+"PrimalChannel.png";
	texts.PURE_CHANNEL=texPath+"PureChannel.png";
	texts.POTENT_CHANNEL=texPath+"PotentChannel.png";
	texts.CHAOTIC_CHANNEL=texPath+"ChaoticChannel.png";
	texts.BURNT_CHANNEL=texPath+"BurntChannel.png";
    }
    
    public void registerItems(){
	
    }
    
    public void registerBlocks(){
	ManaBlocks blocks = ManaStream.getStatic().getModRegistry().getBlockRegistry();
	ManaConfig config=new ManaConfig();
	blocks.PRIMAL_CHANNEL=new BlockPrimalChannel(config.getBlockID("Primal-Channel", 2300));
	blocks.POTENT_CHANNEL=new BlockPotentChannel(config.getBlockID("Potent-Channel", 2301));
	blocks.PURE_CHANNEL=new BlockPureChannel(config.getBlockID("Pure-Channel", 2302));
	blocks.CHAOTIC_CHANNEL=new BlockChaoticChannel(config.getBlockID("Chaotic-Channel", 2303));
	blocks.BURNT_CHANNEL=new BlockBurntChannel(config.getBlockID("Burnt-Channel", 2304));
    }
    
    public void registerSounds(){
	
    }
    
    public void registerEntities(){
	
    }
    
    public void registerDimensions(){
	
    }
    
    public void registerListeners(){
	
    }
    
    public void registerCommands(){
	
    }
    
}
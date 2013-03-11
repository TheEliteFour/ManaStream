package net.manastream.core.ManaStream;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.Mod.ServerStarting;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.relauncher.Side;
import java.util.logging.Logger;
import net.manastream.core.ManaStream.Data.ManaLoader;
import net.manastream.core.ManaStream.Data.ManaRegistry;
import net.manastream.core.ManaStream.Util.CommonProxy;
import net.manastream.core.ManaStream.Util.MSTab;
import net.manastream.core.ManaStream.Util.PacketManager;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;

@Mod(modid = "ManaStream", name = "ManaStream", version = "1.0")
@NetworkMod(clientSideRequired = true, serverSideRequired = false, packetHandler = PacketManager.class)
public class ManaStream {
    
    private static ManaStream instance = null;
    private ManaRegistry manaRegistry;
    public static final Logger logger = Logger.getLogger("Minecraft");
    @SidedProxy(clientSide = "net.manastream.core.ManaStream.Util.ClientProxy", serverSide = "net.manastream.core.ManaStream.Util.CommonProxy")
    public static CommonProxy proxy;
    
    public static ManaStream getStatic() {
	return instance;
    }
    
    public ManaRegistry getModRegistry() {
	return manaRegistry;
    }
    
    private void setStatic() {
	instance = this;
    }    
    
    private void setModItemRegistry(ManaRegistry reg) {
	manaRegistry = reg;
    }    
    
    @PreInit
    public void init(FMLPreInitializationEvent e) {	
	setStatic();	
	setModItemRegistry(new ManaRegistry());
	ManaLoader loader = new ManaLoader();
	logger.info("[ManaStream] loading content and hooks!");	
	loader.registerTextures();
	loader.registerSounds();
	loader.registerItems();
	loader.registerBlocks();
	loader.registerTileEntities();		
	loader.registerDimensions();
	loader.registerEntities();
	loader.registerCommands();
	loader.registerListeners();
	MSTab.setItemStack(new ItemStack(322, 1, 1));
	proxy.preInit();
	logger.info("[ManaStream] done content and hooks!");	
	
    }
    
    @ServerStarting
    public void onEnable(FMLServerStartingEvent e) {
	proxy.init();
	logger.info("[ManaStream] is ACTIVE!");	
	
	
    }
}

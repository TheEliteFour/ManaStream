/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.manastream.core.ManaStream.Data;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import java.io.File;
import java.io.IOException;
import net.manastream.content.ManaStream.Textures.TexturePather;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.Configuration;

/**
 *
 * @author Xeology
 */
public class ManaConfig {

    public File getDataFolder() {

	File dir = new File(getBaseDir(), "config");
	if (!dir.exists()) {
	    dir.mkdir();
	}
	return new File(new File(getBaseDir(), "config"), "ManaStream");
    }

    public static File getBaseDir() {
	if (FMLCommonHandler.instance().getSide().isClient()) {
	    FMLClientHandler.instance().getClient();
	    return Minecraft.getMinecraftDir();
	} else {
	    return new File(".");
	}
    }

    private File getFile() {
	File config = new File(getDataFolder(), "ManaStream.properties");
	if (!config.exists()) {
	    try {
		config.createNewFile();
	    } catch (IOException ex) {
	    }
	}
	return config;
    }

    private Configuration getConfig() {
	Configuration config = new Configuration(getFile());
	config.load();
	return config;
    }

    private void save(Configuration config) {
	config.save();
    }

    public String getTexturePath() {
	return TexturePather.getTexturePath().replace("TexturePather.class", "");
    }

    public int getBlockID(String block, int def) {
	Configuration config = getConfig();
	int id = config.get("Blocks", block, def).getInt();
	config.save();
	return id;
    }

    public int getItemID(String item, int def) {
	Configuration config = getConfig();
	int id = config.get("Items", item, def).getInt();
	config.save();
	return id;
    }

    public int getEntityID(String entity, int def) {
	Configuration config = getConfig();
	int id = config.get("Entity", entity, def).getInt();
	config.save();
	return id;
    }
}

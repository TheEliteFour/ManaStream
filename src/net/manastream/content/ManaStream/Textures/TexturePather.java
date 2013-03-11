/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.manastream.content.ManaStream.Textures;

/**
 *
 * @author Xeology
 */
public class TexturePather {

    public static String getTexturePath() {
	ClassLoader loader = TexturePather.class.getClassLoader();
	return "/net/manastream/content/ManaStream/Textures/";
    }
}

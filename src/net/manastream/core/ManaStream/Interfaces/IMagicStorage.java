/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.manastream.core.ManaStream.Interfaces;

/**
 *
 * @author Xeology
 */
public interface IMagicStorage {

    public double getMagic(Object... data);

    public void setMagic(double joules, Object... data);

    public double getMaxMagic();
}

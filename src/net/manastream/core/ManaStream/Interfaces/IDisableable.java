/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.manastream.core.ManaStream.Interfaces;

/**
 *
 * @author Xeology
 */
public interface IDisableable {

    //Tics to disable for
    public void onDisable(int time);

    public boolean isDisabled();
}

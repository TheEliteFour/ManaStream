package net.manastream.core.ManaStream.Interfaces;

import net.manastream.core.ManaStream.EnergyStream.EnergyStream;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;

/**
 * Must be applied to all tile entities that are channels.
 * 
 * @author Calclavia
 * 
 */

public interface IManaChannel {
    
    public EnergyStream getEnergyStream();
    
    public void setEnergyStream(EnergyStream energyStream);
    
    public TileEntity[] getConnectedBlocks();
    
    public double getDisturbance();
    
    public double getMaxPotency();
    
    public void onOverCharge();
    
    public void reset();
    
    public void refreshConnectedBlocks();
    
    public void updateConnection(TileEntity tileEntity, ForgeDirection side);

    public void updateConnectionWithoutSplit(TileEntity connectorFromSide, ForgeDirection orientation);
    
}

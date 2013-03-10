/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.manastream.core.ManaStream.Tiles;

import com.google.common.io.ByteArrayDataInput;
import java.util.ArrayList;
import java.util.List;
import net.manastream.core.ManaStream.Interfaces.IManaChannel;
import net.manastream.core.ManaStream.Util.BlockSide;
import net.manastream.core.ManaStream.Tiles.TileEnergyAdvanced;
import net.manastream.core.ManaStream.EnergyStream.Energy;
import net.manastream.core.ManaStream.EnergyStream.EnergyStream;
import net.manastream.core.ManaStream.EnergyStream.MagicConnections;
import net.manastream.core.ManaStream.Interfaces.IPacketReceiver;
import net.manastream.core.ManaStream.Utis.Vectors.Vector3;
import net.manastream.core.ManaStream.ManaStream;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import org.bouncycastle.util.Arrays;

/**
 *
 * @author Xeology
 * 
 * Some code contained in this class is from UniversalElectricity and
 * all credit goes to it's respective creator.
 * 
 */
public class TileManaChannel extends TileEnergyAdvanced implements IManaChannel, IPacketReceiver {

    private EnergyStream energyStream = null;
    /**
     * Used client side to render.
     */
    public boolean[] visuallyConnected = {false, false, false, false, false, false};
    /**
     * Stores information on the blocks that this conductor is connected to.
     */
    public TileEntity[] connectedBlocks = {null, null, null, null, null, null};
    protected String channel = "";

    @Override
    public void setEnergyStream(EnergyStream energyStream) {
	this.energyStream = energyStream;
    }

    @Override
    public EnergyStream getEnergyStream() {
	return energyStream;
    }

    public TileEntity getTileEntityFromSide(BlockSide side) {
	switch (side) {
	    case TOP:
		return this.worldObj.getBlockTileEntity(xCoord, yCoord + 1, zCoord);
	    case BOTTOM:
		return this.worldObj.getBlockTileEntity(xCoord, yCoord - 1, zCoord);
	    case WEST:
		return this.worldObj.getBlockTileEntity(xCoord - 1, yCoord, zCoord);
	    case EAST:
		return this.worldObj.getBlockTileEntity(xCoord + 1, yCoord, zCoord);
	    case NORTH:
		return this.worldObj.getBlockTileEntity(xCoord, yCoord, zCoord + 1);
	    case SOUTH:
		return this.worldObj.getBlockTileEntity(xCoord, yCoord, zCoord - 1);
	}
	return null;
    }

    //Get List of sides, NORTH, SOUTH, WEST, EAST, TOP, BOTTOM
    public List<TileEntity> getListofTileEntities() {
	List<TileEntity> entities = new ArrayList<TileEntity>();
	for (BlockSide side : BlockSide.values()) {
	    entities.add(getTileEntityFromSide(side));
	}
	return entities;
    }

    @Override
    public TileEntity[] getConnectedBlocks() {
	return connectedBlocks;

    }

    @Override
    public double getDisturbance() {
	return .05;
    }

    @Override
    public double getMaxPotency() {
	return 1;
    }

    @Override
    public void onOverCharge() {
	this.worldObj.setBlockWithNotify(xCoord, yCoord, zCoord, ManaStream.getStatic().getModRegistry().getBlockRegistry().BURNT_CHANNEL.blockID);
	this.refreshConnectedBlocks();
    }

    @Override
    public void reset() {
	this.energyStream = null;
	if (Energy.instance != null) {
	    Energy.instance.registerChannel(this);
	}
    }

    @Override
    public void refreshConnectedBlocks() {
	if (this.worldObj != null) {
	    if (!this.worldObj.isRemote) {
		boolean[] previousConnections = this.visuallyConnected.clone();

		for (byte i = 0; i < 6; i++) {
		    this.updateConnection(Vector3.getConnectorFromSide(this.worldObj, new Vector3(this), ForgeDirection.getOrientation(i)), ForgeDirection.getOrientation(i));
		}

		/**
		 * Only send packet updates if visuallyConnected changed.
		 */
		if (!Arrays.areEqual(previousConnections, this.visuallyConnected)) {
		    this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
		}
	    }

	}
    }

    @Override
    public void updateConnection(TileEntity tileEntity, ForgeDirection side) {
	if (!this.worldObj.isRemote) {
	    if (tileEntity != null) {
		if (MagicConnections.canConnect(tileEntity, side.getOpposite())) {
		    this.connectedBlocks[side.ordinal()] = tileEntity;
		    this.visuallyConnected[side.ordinal()] = true;

		    if (tileEntity.getClass() == this.getClass()) {
			Energy.instance.mergeConnection(this.getEnergyStream(), ((IManaChannel) tileEntity).getEnergyStream());
		    }

		    return;
		}
	    }

	    if (this.connectedBlocks[side.ordinal()] != null) {
		if (this.connectedBlocks[side.ordinal()] instanceof IManaChannel) {
		    Energy.instance.splitConnection(this, (IManaChannel) this.getConnectedBlocks()[side.ordinal()]);
		}

		this.getEnergyStream().stopProducingMagic(this.connectedBlocks[side.ordinal()]);
		this.getEnergyStream().stopRequestingMagic(this.connectedBlocks[side.ordinal()]);
	    }

	    this.connectedBlocks[side.ordinal()] = null;
	    this.visuallyConnected[side.ordinal()] = false;
	}
    }

    @Override
    public void updateConnectionWithoutSplit(TileEntity connectorFromSide, ForgeDirection orientation) {
	if (!this.worldObj.isRemote) {
	    if (connectorFromSide != null) {
		if (MagicConnections.canConnect(connectorFromSide, orientation.getOpposite())) {
		    this.connectedBlocks[orientation.ordinal()] = connectorFromSide;
		    this.visuallyConnected[orientation.ordinal()] = true;

		    if (connectorFromSide.getClass() == this.getClass()) {
			Energy.instance.mergeConnection(this.getEnergyStream(), ((IManaChannel) connectorFromSide).getEnergyStream());
		    }

		    return;
		}
	    }

	    this.connectedBlocks[orientation.ordinal()] = null;
	    this.visuallyConnected[orientation.ordinal()] = false;
	}
    }

    @Override
    public void handlePacketData(INetworkManager network, int packetType, Packet250CustomPayload packet, EntityPlayer player, ByteArrayDataInput dataStream) {
	if (this.worldObj.isRemote) {
	    this.visuallyConnected[0] = dataStream.readBoolean();
	    this.visuallyConnected[1] = dataStream.readBoolean();
	    this.visuallyConnected[2] = dataStream.readBoolean();
	    this.visuallyConnected[3] = dataStream.readBoolean();
	    this.visuallyConnected[4] = dataStream.readBoolean();
	    this.visuallyConnected[5] = dataStream.readBoolean();
	}
    }
}

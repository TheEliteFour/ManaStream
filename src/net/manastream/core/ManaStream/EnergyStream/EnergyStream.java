package net.manastream.core.ManaStream.EnergyStream;

import net.manastream.core.ManaStream.Utis.Vectors.Vector3;
import cpw.mods.fml.common.FMLLog;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.manastream.core.ManaStream.Interfaces.IManaChannel;
import net.manastream.core.ManaStream.Util.BlockSide;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;

/**
 *
 * @ModificationAuthor Xeology
 * @OriginalAuthor Calclavia
 *
 * This class is a modified version of ElectricityNetwork inside of
 * UniversalElectricity. The code has been modified and adapted to a new purpose
 * and theme while trying to slim and correct any short comings perceived by
 * Xeology. All credit for the original code and process goes to it's rightful
 * owner, I make no further claims other then the modifications.
 *
 */
public class EnergyStream {
    
    private HashMap<TileEntity, MagicLoad> producers = new HashMap<TileEntity, MagicLoad>();
    private HashMap<TileEntity, MagicLoad> users = new HashMap<TileEntity, MagicLoad>();
    public List<IManaChannel> channels = new ArrayList<IManaChannel>();
    
    public EnergyStream(IManaChannel channel) {
	addChannel(channel);
    }

    //Input magic into the network
    public void produceMagic(TileEntity tile, MagicLoad load) {
	if (tile != null && load.getMagic() > 0) {
	    producers.put(tile, load);
	}
    }
    
    public void produceMagic(TileEntity tile, double magic, double potency) {
	produceMagic(tile, new MagicLoad(magic));
    }

    //Is inputting magic into the network
    public boolean isProducingMagic(TileEntity tile) {
	return producers.containsKey(tile);
    }

    //Stop inputting magic into the network
    public void stopProducingMagic(TileEntity tile) {
	producers.remove(tile);
    }

    //Request magic from the network
    public void startRequestingMagic(TileEntity tile, MagicLoad load) {
	if (tile != null && load.getMagic() > 0) {
	    users.put(tile, load);
	}
    }
    
    public void startRequestingMagic(TileEntity tile, double magic, double potency) {
	startRequestingMagic(tile, new MagicLoad(magic));
    }

    //Is requesting magic from the network
    public boolean isRequestingMagic(TileEntity tile) {
	return users.containsKey(tile);
    }

    //Stop requesting magic from the network
    public void stopRequestingMagic(TileEntity tile) {
	users.remove(tile);
    }
    
    public MagicLoad getProducedMagic(TileEntity... ignore) {
	MagicLoad totalMagic = new MagicLoad();
	
	Iterator itr = producers.entrySet().iterator();
	
	loop:
	while (itr.hasNext()) {
	    Map.Entry entry = (Map.Entry) itr.next();
	    
	    if (entry != null) {
		TileEntity tile = (TileEntity) entry.getKey();
		
		if (tile == null) {
		    itr.remove();
		    continue;
		}
		
		if (tile.isInvalid()) {
		    itr.remove();
		    continue;
		}
		
		if (tile.worldObj.getBlockTileEntity(tile.xCoord, tile.yCoord, tile.zCoord) != tile) {
		    itr.remove();
		    continue;
		}
		
		if (ignore != null) {
		    for (TileEntity ignoreTile : ignore) {
			if (tile == ignoreTile) {
			    continue loop;
			}
		    }
		}
		
		MagicLoad load = (MagicLoad) entry.getValue();
		
		if (entry.getKey() != null && entry.getValue() != null && load != null) {
		    double newMagic = totalMagic.getMagic() + load.getMagic();
		    
		    if (load.getPotencyObject().getPotency() > totalMagic.getPotencyObject().getPotency()) {
			totalMagic.setPotency(load.getPotencyObject().getPotency());
		    }
		    totalMagic.setMagic(newMagic);
		}
	    }
	}
	
	return totalMagic;
    }

    //How much Magic this network needs.    
    public MagicLoad getRequest(TileEntity... ignoreTiles) {
	MagicLoad totalMagic = this.getRequestWithoutReduction();
	return totalMagic;
    }
    
    private MagicLoad getRequestWithoutReduction() {
	MagicLoad totalMagic = new MagicLoad();
	
	Iterator it = users.entrySet().iterator();
	
	while (it.hasNext()) {
	    Map.Entry pairs = (Map.Entry) it.next();
	    
	    if (pairs != null) {
		TileEntity tileEntity = (TileEntity) pairs.getKey();
		
		if (tileEntity == null) {
		    it.remove();
		    continue;
		}
		
		if (tileEntity.isInvalid()) {
		    it.remove();
		    continue;
		}
		
		if (tileEntity.worldObj.getBlockTileEntity(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord) != tileEntity) {
		    it.remove();
		    continue;
		}
		
		MagicLoad load = (MagicLoad) pairs.getValue();
		
		if (load != null) {
		    totalMagic.setMagic(totalMagic.getMagic() + load.getMagic());
		}
	    }
	}
	
	return totalMagic;
    }

    //@param tileEntity
    //@return The magic being input into this tile entity.	
    public MagicLoad consumeMagic(TileEntity tileEntity) {
	MagicLoad totalMagic = new MagicLoad();
	
	try {
	    MagicLoad tileRequest = users.get(tileEntity);
	    
	    if (users.containsKey(tileEntity) && tileRequest != null) {
		// Calculate the magic this tile entity is receiving in
		// percentage.
		totalMagic = getProducedMagic();
		
		if (totalMagic.getMagic() > 0) {
		    MagicLoad totalRequest = getRequest();
		    
		    int distance = channels.size();
		    double magicReceived = totalRequest.getMagic() - (getTotalDisturbance());
		    if (magicReceived < 0) {
			magicReceived = 0;
		    }
		    
		    totalMagic.setMagic(magicReceived);
		    
		    return totalMagic;
		}
	    }
	} catch (Exception e) {
	    FMLLog.severe("Failed to consume magic!");
	    e.printStackTrace();
	}
	
	return totalMagic;
    }

    //@return Returns all producers in this magic network.
    public HashMap<TileEntity, MagicLoad> getProducers() {
	return producers;
    }

    //@return Returns all consumers in this magic network.
    public HashMap<TileEntity, MagicLoad> getConsumers() {
	return users;
    }
    
    public void addChannel(IManaChannel newChannel) {
	this.cleanChannels();
	
	if (!channels.contains(newChannel)) {
	    channels.add(newChannel);
	    newChannel.setEnergyStream(this);
	}
    }

    //Get only the magic units that can receive magic from the given side.
    public List<TileEntity> getReceivers() {
	List<TileEntity> receivers = new ArrayList<TileEntity>();
	
	Iterator it = users.entrySet().iterator();
	
	while (it.hasNext()) {
	    Map.Entry pairs = (Map.Entry) it.next();
	    
	    if (pairs != null) {
		receivers.add((TileEntity) pairs.getKey());
	    }
	}
	
	return receivers;
    }
    
    public void cleanChannels() {
	for (int i = 0; i < channels.size(); i++) {
	    if (channels.get(i) == null) {
		channels.remove(i);
	    } else if (((TileEntity) channels.get(i)).isInvalid()) {
		channels.remove(i);
	    }
	}
    }
    
    public void resetChannels() {
	for (int i = 0; i < channels.size(); i++) {
	    channels.get(i).reset();
	}
    }
    
    public void setEnergyStream() {
	this.cleanChannels();
	
	for (IManaChannel channel : channels) {
	    channel.setEnergyStream(this);
	}
    }
    
    public void onOverCharge() {
	cleanChannels();
	
	for (int i = 0; i < channels.size(); i++) {
	    channels.get(i).onOverCharge();
	}
    }

    //Gets the total amount of disturbance of this magical network. In offset.
    public double getTotalDisturbance() {
	
	double disturbance = 0;
	for (IManaChannel channel : channels) {
	    disturbance += channel.getDisturbance();
	}
	return disturbance;
    }
    
    public double getLowestPotencyTolerance() {
	double lowestPotency = 0;
	
	for (IManaChannel channel : channels) {
	    if (lowestPotency == 0 || channel.getMaxPotency() < lowestPotency) {
		lowestPotency = channel.getMaxPotency();
	    }
	}
	
	return lowestPotency;
    }

    //This function is called to refresh all channels in this network
    public void refreshChannels() {
	for (int j = 0; j < channels.size(); j++) {
	    IManaChannel channel = channels.get(j);
	    channel.refreshConnectedBlocks();
	}
    }

    /**
     * Tries to find the magic network based in a tile entity and checks to see
     * if it is a channel. All devices should use this function to search for a
     * connecting channel around it.
     *
     * @param channel - The TileEntity channel
     * @param approachDirection - The direction you are approaching this channel
     * from.
     * @return The EnergyStream or null if not found.
     */
    public static EnergyStream getEnergyStreamFromTileEntity(TileEntity tileEntity, ForgeDirection approachDirection) {
	if (tileEntity != null) {
	    if (tileEntity instanceof IManaChannel) {
		if (MagicConnections.isConnector(tileEntity)) {
		    if (MagicConnections.canConnect(tileEntity, approachDirection.getOpposite())) {
			return ((IManaChannel) tileEntity).getEnergyStream();
		    }
		}
	    }
	}
	
	return null;
    }

    /**
     * @param tileEntity - The TileEntity's sides.
     * @param approachingDirection - The directions that can be connected.
     * @return A list of networks from all specified sides. There will be no
     * repeated MagicNetworks and it will never return null.
     */
    public static List<EnergyStream> getEnergyStreamsFromMultipleSides(TileEntity tileEntity, EnumSet<ForgeDirection> approachingDirection) {
	final List<EnergyStream> connectedNetworks = new ArrayList<EnergyStream>();
	
	for (int i = 0; i < 6; i++) {
	    ForgeDirection direction = ForgeDirection.getOrientation(i);
	    
	    if (approachingDirection.contains(direction)) {
		Vector3 position = new Vector3(tileEntity);
		position.modifyPositionFromSide(direction);
		TileEntity outputChannel = position.getTileEntity(tileEntity.worldObj);
		EnergyStream energyStream = getEnergyStreamFromTileEntity(outputChannel, direction);
		
		if (energyStream != null && !connectedNetworks.contains(energyStream)) {
		    connectedNetworks.add(energyStream);
		}
	    }
	}
	
	return connectedNetworks;
    }

    /**
     * Requests and attempts to consume magic from all specified sides. Use this
     * as a simple helper function.
     *
     * @param tileEntity- The TileEntity consuming the magic.
     * @param approachDirection - The sides in which you can connect.
     * @param requestPack - The amount of magic to be requested.
     * @return The consumed MagicLoad.
     */
    public static MagicLoad consumeFromMultipleSides(TileEntity tileEntity, EnumSet<ForgeDirection> approachingDirection, MagicLoad requestPack) {
	MagicLoad consumedPack = new MagicLoad();
	
	if (tileEntity != null && approachingDirection != null) {
	    final List<EnergyStream> connectedNetworks = getEnergyStreamsFromMultipleSides(tileEntity, approachingDirection);
	    
	    if (connectedNetworks.size() > 0) {
		//Requests an even amount of magic from all sides.
		double magicPerSide = (requestPack.getMagic() / connectedNetworks.size());
		double potency = requestPack.getPotencyObject().getPotency();
		
		for (EnergyStream network : connectedNetworks) {
		    if (magicPerSide > 0 && requestPack.getMagic() > 0) {
			network.startRequestingMagic(tileEntity, magicPerSide, potency);
			MagicLoad receivedPack = network.consumeMagic(tileEntity);
			consumedPack.setMagic(consumedPack.getMagic() + receivedPack.getMagic());
		    } else {
			network.stopRequestingMagic(tileEntity);
		    }
		}
	    }
	}
	
	return consumedPack;
    }
    
    public static MagicLoad consumeFromMultipleSides(TileEntity tileEntity, MagicLoad load) {
	return consumeFromMultipleSides(tileEntity, MagicConnections.getDirections(tileEntity), load);
    }

    /**
     * Produces magic from all specified sides. Use this as a simple helper
     * function.
     *
     * @param tileEntity- The TileEntity consuming the magic.
     * @param approachDirection - The sides in which you can connect to.
     * @param producePack - The amount of magic to be produced.
     * @return What remained in the magic pack.
     */
    public static MagicLoad produceFromMultipleSides(TileEntity tileEntity, EnumSet<ForgeDirection> approachingDirection, MagicLoad producingPack) {
	MagicLoad remainingMagic = producingPack.clone();
	
	if (tileEntity != null && approachingDirection != null) {
	    final List<EnergyStream> connectedNetworks = getEnergyStreamsFromMultipleSides(tileEntity, approachingDirection);
	    
	    if (connectedNetworks.size() > 0) {
		/**
		 * Requests an even amount of electricity from all sides.
		 */
		double magicPerSide = (producingPack.getMagic() / connectedNetworks.size());
		double potency = producingPack.getPotencyObject().getPotency();
		
		for (EnergyStream network : connectedNetworks) {
		    if (magicPerSide > 0 && producingPack.getMagic() > 0) {
			network.produceMagic(tileEntity, magicPerSide, potency);
			remainingMagic.setMagic(remainingMagic.getMagic() - magicPerSide);
		    } else {
			network.stopProducingMagic(tileEntity);
		    }
		}
	    }
	}
	
	return remainingMagic;
    }
    
    public static MagicLoad produceFromMultipleSides(TileEntity tileEntity, MagicLoad load) {
	return produceFromMultipleSides(tileEntity, MagicConnections.getDirections(tileEntity), load);
    }
}

package net.manastream.core.ManaStream.EnergyStream;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.common.FMLLog;
import net.manastream.core.ManaStream.Interfaces.IManaChannel;
import net.manastream.core.ManaStream.Utis.Vectors.Vector3;

/**
 * The Electricity Network Manager.
 * 
 * @author Calclavia
 * 
 */
public class Energy
{
	public static Energy instance = new Energy();

	private List<EnergyStream> energyStreams = new ArrayList<EnergyStream>();

	/**
	 * Registers a conductor into the UE electricity net.
	 */
	public void registerChannel(IManaChannel newChannel)
	{
		this.cleanUpEnergyStreams();
		EnergyStream newNetwork = new EnergyStream(newChannel);
		this.energyStreams.add(newNetwork);
	}

	public void unregister(TileEntity tileEntity)
	{
		for (EnergyStream network : this.energyStreams)
		{
			network.stopProducingMagic(tileEntity);
			network.stopRequestingMagic(tileEntity);
		}
	}

	/**
	 * Merges two connection lines together into one.
	 * 
	 * @param networkA - The network to be merged into. This network will be kept.
	 * @param networkB - The network to be merged. This network will be deleted.
	 */
	public void mergeConnection(EnergyStream networkA, EnergyStream networkB)
	{
		if (networkA != networkB)
		{
			if (networkA != null && networkB != null)
			{
				networkA.channels.addAll(networkB.channels);
				networkA.setEnergyStream();
				this.energyStreams.remove(networkB);
				networkB = null;

				networkA.cleanChannels();
			}
			else
			{
				System.err.println("Failed to merge ManaStream Channel connections!");
			}
		}
	}

	/**
	 * Separate one connection line into two different ones between two channels. This function
	 * does this by resetting all channels in the connection line and making them each reconnect.
	 * 
	 * @param channelA - existing conductor
	 * @param channelB - broken/invalid conductor
	 */
	public void splitConnection(IManaChannel channelA, IManaChannel channelB)
	{
		try
		{
			EnergyStream network = channelA.getEnergyStream();

			if (network != null)
			{
				network.cleanChannels();
				network.resetChannels();

				Iterator it = network.channels.iterator();

				while (it.hasNext())
				{
					IManaChannel channel = (IManaChannel) it.next();

					for (byte i = 0; i < 6; i++)
					{
						channel.updateConnectionWithoutSplit(Vector3.getConnectorFromSide(((TileEntity) channel).worldObj, new Vector3((TileEntity) channel), ForgeDirection.getOrientation(i)), ForgeDirection.getOrientation(i));
					}
				}
			}
			else
			{
				FMLLog.severe("Conductor invalid EnergyStream while splitting connection!");
			}
		}
		catch (Exception e)
		{
			FMLLog.severe("Failed to split wire connection!");
			e.printStackTrace();
		}
	}

	/**
	 * Clean up and remove all useless and invalid connections.
	 */
	public void cleanUpEnergyStreams()
	{
		try
		{
			Iterator it = energyStreams.iterator();

			while (it.hasNext())
			{
				EnergyStream network = (EnergyStream) it.next();
				network.cleanChannels();

				if (network.channels.size() == 0)
				{
					it.remove();
				}
			}
		}
		catch (Exception e)
		{
			FMLLog.severe("Failed to clean up channel connections!");
			e.printStackTrace();
		}
	}

	public void resetChannels()
	{
		Iterator it = energyStreams.iterator();

		while (it.hasNext())
		{
			EnergyStream network = ((EnergyStream) it.next());
			network.resetChannels();
		}
	}
}

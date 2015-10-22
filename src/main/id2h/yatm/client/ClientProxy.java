package id2h.yatm.client;

import id2h.yatm.client.renderer.RenderCagedEnergyCell;
import id2h.yatm.common.CommonProxy;

import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy
{
	@Override
	public void initRenders()
	{
		RenderingRegistry.registerBlockHandler(new RenderCagedEnergyCell());
	}
}

package id2h.yatm.common;

import cpw.mods.fml.common.SidedProxy;

public class CommonProxy
{
	@SidedProxy(clientSide="id2h.yatm.client.ClientProxy", serverSide="id2h.yatm.common.CommonProxy")
	public static CommonProxy instance;

	public void initRenders() {}
}

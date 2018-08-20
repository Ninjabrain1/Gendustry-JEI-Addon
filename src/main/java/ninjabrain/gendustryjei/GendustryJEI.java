package ninjabrain.gendustryjei;

import org.apache.logging.log4j.Logger;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = GendustryJEI.MODID, name = GendustryJEI.NAME, version = GendustryJEI.VERSION)
public class GendustryJEI {
	public static final String MODID = "gendustryjei";
	public static final String NAME = "Gendustry JEI Addon";
	public static final String VERSION = "1.0";

	public static Logger logger;
	
	@SidedProxy(clientSide = "ninjabrain.gendustryjei.ClientProxy", serverSide = "ninjabrain.gendustryjei.CommonProxy")
	public static CommonProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		logger = event.getModLog();
	}
	
	@EventHandler 
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit(event);
	}
}

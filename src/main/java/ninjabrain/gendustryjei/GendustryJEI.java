package ninjabrain.gendustryjei;

import org.apache.logging.log4j.Logger;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = GendustryJEI.MODID, name = GendustryJEI.NAME, version = GendustryJEI.VERSION)
public class GendustryJEI {
	public static final String MODID = "gendustryjei";
	public static final String NAME = "Gendustry JEI Addon";
	public static final String VERSION = "1.0.1";

	public static Logger logger;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		logger = event.getModLog();
	}
	
	// TODO Add percentage chance of mutation happening in regular mutatron
	// TODO Add percentage chance of getting gene sample in Sampler
	// TODO Add recipes for converting Extra Bees Serums into Gendustry Samples
	// TODO Config to enable/disable showing recipes
	// TODO Combine gene samples with templates in crafting grid
}

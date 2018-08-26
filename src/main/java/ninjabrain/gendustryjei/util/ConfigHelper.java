package ninjabrain.gendustryjei.util;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.RequiresMcRestart;
import net.minecraftforge.common.config.Config.Type;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ninjabrain.gendustryjei.GendustryJEI;

@EventBusSubscriber
@Config(modid = GendustryJEI.MODID)
public class ConfigHelper {
	
	@RequiresMcRestart
	public static MachinesCategory machines = new MachinesCategory();
	
	public static class MachinesCategory{
		@Comment("Show recipes from the DNA Extractor in JEI")
		public boolean showDNAExtractorRecipes = true;
		@Comment("Show recipes from the Imprinter in JEI")
		public boolean showImprinterRecipes = true;
		@Comment("Show recipes from the Mutagen Producer in JEI")
		public boolean showMutagenProducerRecipes = true;
		@Comment("Show recipes from the Mutatron in JEI")
		public boolean showMutatronRecipes = true;
		@Comment("Show recipes from the Replicator in JEI")
		public boolean showReplicatorRecipes = true;
		@Comment("Show recipes from the Protein Liquifier in JEI")
		public boolean showProteinLiquifierRecipes = true;
		@Comment("Show recipes from the Sampler in JEI")
		public boolean showSamplerRecipes = true;
		@Comment("Show recipes from the Genetic Transposer in JEI")
		public boolean showGeneticTransposerRecipes = true;
		
	}
	
	@SubscribeEvent
	public static void onConfigChangedEvent(OnConfigChangedEvent event) {
		if (event.getModID().equals(GendustryJEI.MODID)) {
			ConfigManager.sync(GendustryJEI.MODID, Type.INSTANCE);
		}
	}
	
}

package ninjabrain.gendustryjei;

import java.util.ArrayList;

import org.apache.logging.log4j.Logger;

import net.bdew.gendustry.Gendustry;
import net.bdew.gendustry.api.GendustryAPI;
import net.bdew.gendustry.api.registries.IFluidSourceRegistry;
import net.bdew.gendustry.config.loader.Loader;
import net.bdew.gendustry.config.loader.RsMutagen;
import net.bdew.lib.recipes.ConfigStatement;
import net.bdew.lib.recipes.CsRecipeBlock;
import net.bdew.lib.recipes.RecipeLoader;
import net.bdew.lib.recipes.RecipeStatement;
import net.bdew.lib.recipes.RecipesHelper;
import net.bdew.lib.recipes.StackBlock;
import net.bdew.lib.recipes.StackRef;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import scala.collection.immutable.List;

@Mod(modid = GendustryJEI.MODID, name = GendustryJEI.NAME, version = GendustryJEI.VERSION)
public class GendustryJEI {
	public static final String MODID = "gendustryjei";
	public static final String NAME = "Gendustry JEI Addon";
	public static final String VERSION = "1.0";

	public static Logger logger;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		logger = event.getModLog();
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		// IFluidSourceRegistry reg = GendustryAPI.Registries.getMutagenRegistry();
		// //System.out.println("FLUID NAME: " + reg.getFluid().getName());
		// System.out.println("GENDUSTRY JEI ADDON INIT");
		// ArrayList<ConfigStatement> list = new ArrayList<ConfigStatement>();
		// RecipeLoader loader = new Loader() {
		// @Override
		// public void processConfigStatement(ConfigStatement s) {
		// //super.processRecipeStatement(s);
		// list.add(s);
		// if (s instanceof CsRecipeBlock) {
		// CsRecipeBlock block = (CsRecipeBlock)s;
		// List<RecipeStatement> recipes = block.list();
		// for (int i = 0; i < recipes.size(); i++) {
		// RecipeStatement rs = recipes.apply(i);
		// if (rs instanceof RsMutagen) {
		// RsMutagen rsMutagen = (RsMutagen)rs;
		// StackRef stack = rsMutagen.copy$default$1();
		// String name = "unknown";
		// if (stack instanceof StackBlock) {
		// name = ((StackBlock)stack).name();
		// }
		// System.out.println("PRODUCT ELEMENT 0: " + name + " will give: " +
		// rsMutagen.mb() + " mB.");
		// }
		// }
		// }
		//
		// }
		// };
		// RecipesHelper.loadConfigs(NAME, "/assets/gendustry/config/files.lst",
		// Gendustry.configDir(), "/assets/gendustry/config/", loader);
		// System.out.println("RECIPE STATEMENTS LOADED: " + list.size());
		
		RecipeReader.readGendustryRecipes();
	}
}

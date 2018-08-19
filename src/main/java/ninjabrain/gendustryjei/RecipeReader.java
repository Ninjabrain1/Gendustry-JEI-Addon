package ninjabrain.gendustryjei;

import java.util.ArrayList;

import net.bdew.gendustry.Gendustry;
import net.bdew.gendustry.config.loader.Loader;
import net.bdew.gendustry.config.loader.RsMutagen;
import net.bdew.lib.recipes.ConfigStatement;
import net.bdew.lib.recipes.CsRecipeBlock;
import net.bdew.lib.recipes.RecipeLoader;
import net.bdew.lib.recipes.RecipeStatement;
import net.bdew.lib.recipes.RecipesHelper;
import net.bdew.lib.recipes.StackBlock;
import net.bdew.lib.recipes.StackItem;
import net.bdew.lib.recipes.StackRef;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import scala.collection.immutable.List;

public class RecipeReader {
	
	public static ArrayList<MutagenWrapper> mutagenWrappers = new ArrayList<MutagenWrapper>();
	
	public static void readGendustryRecipes() {
		ArrayList<RsMutagen> recipesMutagen = new ArrayList<RsMutagen>();
		RecipeLoader loader = new CustomRecipeLoader(recipesMutagen);
		// Second, third and fourth arguments copied from net/bdew/gendustry/config/loader/TuningLoader.scala
        RecipesHelper.loadConfigs(GendustryJEI.NAME, "/assets/gendustry/config/files.lst", Gendustry.configDir(), "/assets/gendustry/config/", loader);
        
        for (RsMutagen recipe : recipesMutagen) {
        	processMutagenRecipe(recipe);
        }
	}
	
	private static void processMutagenRecipe(RsMutagen rsMutagen) {
		// Input item
		StackRef stack = rsMutagen.st();
		// Read registry name
		String name = "unknown";
		String modid = "unknown";
		if (stack instanceof StackBlock) {
			StackBlock block = (StackBlock)stack;
			name = block.name();
			modid = block.mod();
		} else if (stack instanceof StackItem) {
			StackItem item = (StackItem)stack;
			name = item.name();
			modid = item.mod();
		} else {
			GendustryJEI.logger.error("Unknown StackRef when processing mutagen recipe: " + stack);
		}
		String registryName = modid + ":" + name;
		mutagenWrappers.add(new MutagenWrapper(new ItemStack(Item.getByNameOrId(registryName)), rsMutagen.mb()));
	}
	
}
class CustomRecipeLoader extends Loader {
	
	ArrayList<RsMutagen> recipesMutagen;
	
	public CustomRecipeLoader(ArrayList<RsMutagen> recipesMutagen) {
		this.recipesMutagen = recipesMutagen;
	}
	
	@Override
	public void processConfigStatement(ConfigStatement s) {
		if (s instanceof CsRecipeBlock) {
			CsRecipeBlock block = (CsRecipeBlock)s;
			List<RecipeStatement> recipes = block.list();
			for (int i = 0; i < recipes.size(); i++) {
				RecipeStatement rs = recipes.apply(i);
				if (rs instanceof RsMutagen) {
					recipesMutagen.add((RsMutagen)rs);
					
				}
			}
		}
		
	}
}


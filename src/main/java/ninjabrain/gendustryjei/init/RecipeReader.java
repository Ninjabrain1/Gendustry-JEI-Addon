package ninjabrain.gendustryjei.init;

import java.util.List;

import net.bdew.gendustry.Gendustry;
import net.bdew.gendustry.config.loader.Loader;
import net.bdew.lib.recipes.ConfigStatement;
import net.bdew.lib.recipes.CsRecipeBlock;
import net.bdew.lib.recipes.RecipeLoader;
import net.bdew.lib.recipes.RecipeStatement;
import net.bdew.lib.recipes.RecipesHelper;
import ninjabrain.gendustryjei.GendustryJEI;

/**
 * 
 * Immitates the way Gendustry reads recipes to convert them to a format that JEI can understand.
 *
 */
public class RecipeReader {

	/**
	 * Converts Gendustry recipes to JEI Wrappers using the given converters.
	 */
	public static void convertGendustryRecipes(List<RecipeConverter<?>> converters) {
		RecipeLoader loader = new CustomRecipeLoader(converters);
		// Second, third and fourth arguments copied from
		// net/bdew/gendustry/config/loader/TuningLoader.scala
		RecipesHelper.loadConfigs(GendustryJEI.NAME, "/assets/gendustry/config/files.lst", Gendustry.configDir(),
				"/assets/gendustry/config/", loader);

	}
}

/**
 * 
 * Overrides the class that is used to read Gendustry recipes and uses it to
 * convert recipes to Wrappers
 *
 */
class CustomRecipeLoader extends Loader {

	List<RecipeConverter<?>> converters;

	public CustomRecipeLoader(List<RecipeConverter<?>> converters) {
		this.converters = converters;
	}

	@Override
	public void processConfigStatement(ConfigStatement statement) {
		if (statement instanceof CsRecipeBlock) {
			CsRecipeBlock recipeBlock = (CsRecipeBlock) statement;
			scala.collection.immutable.List<RecipeStatement> recipes = recipeBlock.list();
			for (int i = 0; i < recipes.size(); i++) {
				RecipeStatement rs = recipes.apply(i);
				for (RecipeConverter<?> recipeConverter : converters) {
					recipeConverter.processRecipeStatement(rs);
				}
			}
		}

	}
}

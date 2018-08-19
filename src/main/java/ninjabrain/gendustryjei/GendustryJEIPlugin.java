package ninjabrain.gendustryjei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;

@JEIPlugin
public class GendustryJEIPlugin implements IModPlugin {
	
	@Override
	public void registerCategories(IRecipeCategoryRegistration registry) {
		registry.addRecipeCategories(new CategoryMutagen());
	}
	
	@Override
	public void register(IModRegistry registry) {
		registry.addRecipes(RecipeReader.mutagenWrappers, CategoryMutagen.UUID);
		
	}
	
}

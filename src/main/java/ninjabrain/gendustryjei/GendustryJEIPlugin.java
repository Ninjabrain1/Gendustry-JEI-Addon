package ninjabrain.gendustryjei;

import java.util.ArrayList;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.ItemStack;
import ninjabrain.gendustryjei.categories.CategoryBase;
import ninjabrain.gendustryjei.categories.CategoryMutagen;
import ninjabrain.gendustryjei.init.RecipeConverter;
import ninjabrain.gendustryjei.init.RecipeConverterMutagen;
import ninjabrain.gendustryjei.init.RecipeReader;
import ninjabrain.gendustryjei.wrappers.WrapperMutagen;

@JEIPlugin
public class GendustryJEIPlugin implements IModPlugin {
	
	CategoryBase<?>[] categories;
	
	@Override
	public void registerCategories(IRecipeCategoryRegistration registry) {
		categories = new CategoryBase[] {
				new CategoryMutagen()
		};
		for (CategoryBase<?> category : categories) {
			registry.addRecipeCategories(category);
		}
	}
	
	@Override
	public void register(IModRegistry registry) {
		ArrayList<WrapperMutagen> mutagenWrappers = new ArrayList<WrapperMutagen>();
		
		RecipeConverter<?>[] converters = new RecipeConverter[] {
				new RecipeConverterMutagen(mutagenWrappers)
		};
		
		RecipeReader.convertGendustryRecipes(converters);
		
		registry.addRecipes(mutagenWrappers, CategoryMutagen.UUID);
		
		for (CategoryBase<?> category : categories) {
			registry.addRecipeCatalyst(new ItemStack(category.getMachine()), category.getUid());
		}
		
	}
	
}

package ninjabrain.gendustryjei;

import java.util.ArrayList;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.ItemStack;
import ninjabrain.gendustryjei.categories.CategoryBase;
import ninjabrain.gendustryjei.categories.CategoryDNA;
import ninjabrain.gendustryjei.categories.CategoryMutagen;
import ninjabrain.gendustryjei.categories.CategoryMutatron;
import ninjabrain.gendustryjei.categories.CategoryProtein;
import ninjabrain.gendustryjei.init.RecipeConverter;
import ninjabrain.gendustryjei.init.RecipeConverterDNA;
import ninjabrain.gendustryjei.init.RecipeConverterMutagen;
import ninjabrain.gendustryjei.init.RecipeConverterMutatron;
import ninjabrain.gendustryjei.init.RecipeConverterProtein;
import ninjabrain.gendustryjei.init.RecipeReader;
import ninjabrain.gendustryjei.wrappers.WrapperDNA;
import ninjabrain.gendustryjei.wrappers.WrapperMutagen;
import ninjabrain.gendustryjei.wrappers.WrapperMutatron;
import ninjabrain.gendustryjei.wrappers.WrapperProtein;

@JEIPlugin
public class GendustryJEIPlugin implements IModPlugin {
	
	CategoryBase<?>[] categories;
	
	@Override
	public void registerCategories(IRecipeCategoryRegistration registry) {
		CategoryBase.loadWidgets(registry);
		categories = new CategoryBase[] {
				new CategoryMutagen(),
				new CategoryProtein(),
				new CategoryDNA(),
				new CategoryMutatron()
		};
		for (CategoryBase<?> category : categories) {
			registry.addRecipeCategories(category);
		}
	}
	
	@Override
	public void register(IModRegistry registry) {
		ArrayList<WrapperMutagen> mutagenWrappers = new ArrayList<WrapperMutagen>();
		ArrayList<WrapperProtein> proteinWrappers = new ArrayList<WrapperProtein>();
		ArrayList<WrapperDNA> dnaWrappers = new ArrayList<WrapperDNA>();
		ArrayList<WrapperMutatron> mutatronWrappers = new ArrayList<WrapperMutatron>();
		
		RecipeConverter<?>[] converters = new RecipeConverter[] {
				new RecipeConverterMutagen(mutagenWrappers),
				new RecipeConverterProtein(proteinWrappers),
				new RecipeConverterDNA(dnaWrappers),
				new RecipeConverterMutatron(mutatronWrappers)
		};
		RecipeReader.convertGendustryRecipes(converters);
		
		registry.addRecipes(mutagenWrappers, CategoryMutagen.UUID);
		registry.addRecipes(proteinWrappers, CategoryProtein.UUID);
		registry.addRecipes(dnaWrappers, CategoryDNA.UUID);
		registry.addRecipes(mutatronWrappers, CategoryMutatron.UUID);
		
		for (CategoryBase<?> category : categories) {
			registry.addRecipeCatalyst(new ItemStack(category.getMachine()), category.getUid());
		}
		
	}
	
}

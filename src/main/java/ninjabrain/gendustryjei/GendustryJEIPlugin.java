package ninjabrain.gendustryjei;

import java.util.ArrayList;
import java.util.List;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.NonNullList;

@JEIPlugin
public class GendustryJEIPlugin implements IModPlugin {
	
	@Override
	public void registerCategories(IRecipeCategoryRegistration registry) {
		registry.addRecipeCategories(new TestCategory());
		registry.addRecipeCategories(new CategoryMutagen());
	}
	
	@Override
	public void register(IModRegistry registry) {
//		List<TestWrapper> wrappers = new ArrayList<TestWrapper>();
//		NonNullList<Ingredient> inputs = NonNullList.create();
//		inputs.add(Ingredient.fromItems(Item.getByNameOrId("minecraft:stick")));
//		wrappers.add(new TestWrapper(new ShapelessRecipes("2", new ItemStack(Item.getByNameOrId("minecraft:apple")), inputs)));
//		registry.addRecipes(wrappers, TestCategory.UUID);
		System.out.println("REGISTER");
		System.out.println(RecipeReader.mutagenWrappers.size());
		registry.addRecipes(RecipeReader.mutagenWrappers, CategoryMutagen.UUID);
		
	}
	
}

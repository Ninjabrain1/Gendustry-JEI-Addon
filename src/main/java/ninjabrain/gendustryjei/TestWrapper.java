package ninjabrain.gendustryjei;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;

class TestWrapper implements IRecipeWrapper {
	
	private IRecipe recipe;
	
	public TestWrapper(IRecipe recipe) {
		this.recipe = recipe;
	}
	
	@Override
	public void getIngredients(IIngredients ingredients) {
//		Ingredient i = recipe.getIngredients().get(0);
		NonNullList<ItemStack> inputs = NonNullList.create();
		for (Ingredient i : recipe.getIngredients()) {
			inputs.add(i.getMatchingStacks()[0]);
		}
		ingredients.setInputs(ItemStack.class, inputs);
		ingredients.setOutput(ItemStack.class, recipe.getRecipeOutput());
		System.out.println("GET INGREDIENTS");
	}
	
}
package ninjabrain.gendustryjei;

import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;

public class TestCategory implements IRecipeCategory<TestWrapper>{
	
	public static final String UUID = "GENDUSTRY_TEST";
	
	@Override
	public String getUid() {
		return UUID;
	}

	@Override
	public String getTitle() {
		return "Title!";
	}

	@Override
	public String getModName() {
		return GendustryJEI.NAME;
	}
	
	
	
	@Override
	public IDrawable getBackground() {
		return new IDrawable() {
			
			@Override
			public int getWidth() {
				return 200;
			}
			
			@Override
			public int getHeight() {
				return 200;
			}
			
			@Override
			public void draw(Minecraft minecraft, int xOffset, int yOffset) {
				
			}
		};
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, TestWrapper recipeWrapper, IIngredients ingredients) {
		
	}

}

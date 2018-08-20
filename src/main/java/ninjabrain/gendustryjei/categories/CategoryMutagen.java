package ninjabrain.gendustryjei.categories;

import mezz.jei.Internal;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.gui.GuiHelper;
import net.minecraft.util.ResourceLocation;
import ninjabrain.gendustryjei.GendustryJEI;
import ninjabrain.gendustryjei.wrappers.WrapperMutagen;

public class CategoryMutagen implements IRecipeCategory<WrapperMutagen>{
	
	public static final String UUID = "GENDUSTRY_MUTAGEN_PRODUCER";
	
	private static final ResourceLocation widgetTexture = new ResourceLocation(GendustryJEI.MODID, "textures/gui/widgets.png");
	
	private IDrawable tankOverlay;
	
	public CategoryMutagen() {
		GuiHelper helper = Internal.getHelpers().getGuiHelper();
		tankOverlay = helper.createDrawable(widgetTexture, 18, 0, 16, 58);
	}
	
	@Override
	public String getUid() {
		return UUID;
	}

	@Override
	public String getTitle() {
		// TODO Localize name
		return "Mutagen Producer";
	}

	@Override
	public String getModName() {
		return GendustryJEI.NAME;
	}

	@Override
	public IDrawable getBackground() {
		GuiHelper helper = Internal.getHelpers().getGuiHelper();
		IDrawableStatic background = helper.createBlankDrawable(100, 100);
		return background;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, WrapperMutagen recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();
		IGuiFluidStackGroup fluidStacks = recipeLayout.getFluidStacks();
		
		fluidStacks.init(0, false, 0, 0, 16, 58, 2000, false, tankOverlay);
		
		itemStacks.init(0, true, 50, 0);
		
		itemStacks.set(ingredients);
		fluidStacks.set(ingredients);
	}
	
	
	
}

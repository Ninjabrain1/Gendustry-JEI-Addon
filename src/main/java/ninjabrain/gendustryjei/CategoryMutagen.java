package ninjabrain.gendustryjei;

import forestry.core.render.ForestryResource;
import mezz.jei.Internal;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.gui.GuiHelper;
import net.bdew.gendustry.Gendustry;
import net.minecraft.util.ResourceLocation;

public class CategoryMutagen implements IRecipeCategory<MutagenWrapper>{
	
	public static final String UUID = "GENDUSTRY_MUTAGEN_PRODUCER";
	
	private static final ResourceLocation guiTexture = new ResourceLocation(Gendustry.modId(), "textures/gui/mutagen_producer.png");
	
	private IDrawable tankOverlay;
	
	public CategoryMutagen() {
		GuiHelper helper = Internal.getHelpers().getGuiHelper();
		tankOverlay = helper.createDrawable(guiTexture, 0, 0, 40, 40);
	}
	
	@Override
	public String getUid() {
		return UUID;
	}

	@Override
	public String getTitle() {
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
	public void setRecipe(IRecipeLayout recipeLayout, MutagenWrapper recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();
		IGuiFluidStackGroup fluidStacks = recipeLayout.getFluidStacks();
		
		tankOverlay = Internal.getHelpers().getGuiHelper().createDrawable(new ForestryResource("textures/gui/fermenter.png"), 192, 0, 16, 58);
		fluidStacks.init(0, false, 0, 0, 16, 58, 2000, false, tankOverlay);
		
		itemStacks.init(0, true, 50, 0);
		
		itemStacks.set(ingredients);
		fluidStacks.set(ingredients);
	}
	
	
	
}

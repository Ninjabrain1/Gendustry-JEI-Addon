package ninjabrain.gendustryjei.categories;

import forestry.core.utils.Translator;
import mezz.jei.Internal;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.gui.GuiHelper;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import ninjabrain.gendustryjei.GendustryJEI;
import ninjabrain.gendustryjei.init.RecipeConverter;

public abstract class CategoryBase<T extends IRecipeWrapper> implements IRecipeCategory<T>{
	
	private static final ResourceLocation widgetTexture = new ResourceLocation(GendustryJEI.MODID,
			"textures/gui/widgets.png");
	
	protected IDrawable tankOverlay, tankBackground, itemSlotBackground, arrowBackground;
	
	private final Item machine;
	private String localizedName;
	
	/**
	 * @param UUID
	 * @param machine The machine this caregory represents. Recipes of this category should be crafted in the given machine.
	 */
	public CategoryBase(Item machine) {
		GuiHelper helper = Internal.getHelpers().getGuiHelper();
		tankOverlay = helper.createDrawable(widgetTexture, 19, 1, 16, 58);
		tankBackground = helper.createDrawable(widgetTexture, 0, 0, 18, 60);
		itemSlotBackground = helper.createDrawable(widgetTexture, 36, 0, 18, 18);
		arrowBackground = helper.createDrawable(widgetTexture, 54, 0, 53, 15);
		
		this.machine = machine;
		localizedName = Translator.translateToLocal(machine.getUnlocalizedName() + ".name");
	}
	
	@Override
	public String getTitle() {
		return localizedName;
	}

	@Override
	public String getModName() {
		return GendustryJEI.NAME;
	}
	
	public Item getMachine() {
		return machine;
	}
	
}

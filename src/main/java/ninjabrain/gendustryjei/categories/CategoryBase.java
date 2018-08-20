package ninjabrain.gendustryjei.categories;

import java.util.ArrayList;
import java.util.List;

import forestry.core.utils.Translator;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableAnimated.StartDirection;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import ninjabrain.gendustryjei.GendustryJEI;

public abstract class CategoryBase<T extends IRecipeWrapper> implements IRecipeCategory<T>{
	
	private static final ResourceLocation widgetTexture = new ResourceLocation(GendustryJEI.MODID,
			"textures/gui/widgets.png");
	protected static IDrawable tankOverlay, tankBackground, energyOverlay, itemSlotBackground, arrowBackground, arrowAnimated;
	
	public static void loadWidgets(IRecipeCategoryRegistration registry) {
		IGuiHelper helper = registry.getJeiHelpers().getGuiHelper();
		tankOverlay = helper.createDrawable(widgetTexture, 19, 1, 16, 58);
		tankBackground = helper.createDrawable(widgetTexture, 0, 0, 18, 60);
		IDrawableStatic energyStatic = helper.createDrawable(widgetTexture, 0, 60, 18, 60);
		energyOverlay = helper.createAnimatedDrawable(energyStatic, 80, StartDirection.TOP, true);
		itemSlotBackground = helper.createDrawable(widgetTexture, 36, 0, 18, 18);
		arrowBackground = helper.createDrawable(widgetTexture, 54, 0, 53, 15);
		IDrawableStatic arrowWhite = helper.createDrawable(widgetTexture, 54, 30, 53, 15);
		arrowAnimated = helper.createAnimatedDrawable(arrowWhite, 54, StartDirection.LEFT, false);
	}
	
	// Coordinates of energy meter
	protected final int energyX = 0, energyY = 0;
	// machine specific (not recipe specific)
	protected int rfPerItem;
	
	private final Item machine;
	private String localizedName;
	
	/**
	 * @param machine The machine this caregory represents. Recipes of this category should be crafted in the given machine.
	 */
	public CategoryBase(Item machine) {
		this.machine = machine;
		localizedName = Translator.translateToLocal(machine.getUnlocalizedName() + ".name");
	}
	
	protected void drawArrow(Minecraft minecraft, int x, int y) {
		arrowBackground.draw(minecraft, x, y);
		arrowAnimated.draw(minecraft, x, y);
	}
	
	protected void drawEnergyMeter(Minecraft minecraft) {
		tankBackground.draw(minecraft, energyX, energyY);
		energyOverlay.draw(minecraft, energyX, energyY);
	}

	private static List<String> tooltipList = new ArrayList<String>();
	@Override
	public List<String> getTooltipStrings(int mouseX, int mouseY) {
		tooltipList.clear();
		if (energyX <= mouseX && energyX + 18 > mouseX && energyY <= mouseY && energyY + 60 > mouseY ) {
			tooltipList.add("Energy: " + rfPerItem + " RF");
			return tooltipList;
		}
		return tooltipList;
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

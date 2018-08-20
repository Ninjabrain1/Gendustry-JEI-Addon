package ninjabrain.gendustryjei.categories;

import java.util.ArrayList;
import java.util.List;

import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.gui.elements.DrawableBlank;
import net.bdew.gendustry.config.Tuning;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import ninjabrain.gendustryjei.wrappers.WrapperMutagen;

public class CategoryMutagen extends CategoryBase<WrapperMutagen> {

	public static final String UUID = "GENDUSTRY_MUTAGEN_PRODUCER";

	private IDrawable background;

	private final int tankX = 112, tankY = 0;
	private final int arrowX = tankX - 62, arrowY = tankY + 23;
	private final int slotX = arrowX - 26, slotY = arrowY - 1;
	private final int energyX = 0, energyY = 0;
	
	// The same for all recipes
	private int rfPerItem, maxStoredRf;

	public CategoryMutagen() {
		super(Item.getByNameOrId("gendustry:mutagen_producer"));
		background = new DrawableBlank(130, 60) {
			@Override
			public void draw(Minecraft minecraft, int xOffset, int yOffset) {
				super.draw(minecraft, xOffset, yOffset);
				tankBackground.draw(minecraft, tankX, tankY);
				arrowBackground.draw(minecraft, arrowX, arrowY);
				itemSlotBackground.draw(minecraft, slotX, slotY);
				tankBackground.draw(minecraft, energyX, energyY);
			}
		};
		rfPerItem = Tuning.getSection("Machines").getSection("MutagenProducer").getInt("MjPerItem");
		maxStoredRf = Tuning.getSection("Machines").getSection("MutagenProducer").getInt("MaxStoredEnergy");
	}

	@Override
	public IDrawable getBackground() {
		return background;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, WrapperMutagen recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();
		IGuiFluidStackGroup fluidStacks = recipeLayout.getFluidStacks();

		fluidStacks.init(0, false, tankX + 1, tankY + 1, tankOverlay.getWidth(), tankOverlay.getHeight(), 1000, false,
				tankOverlay);

		itemStacks.init(0, true, slotX, slotY);

		itemStacks.set(ingredients);
		fluidStacks.set(ingredients);
	}
	
	private List<String> tooltipList = new ArrayList<String>();
	@Override
	public List<String> getTooltipStrings(int mouseX, int mouseY) {
		if (energyX <= mouseX && energyX + 18 > mouseX && energyY <= mouseY && energyY + 60 > mouseY ) {
			tooltipList.clear();
			tooltipList.add("Energy: " + rfPerItem + " RF");
			return tooltipList;
		}
		return super.getTooltipStrings(mouseX, mouseY);
	}

	@Override
	public String getUid() {
		return UUID;
	}

}

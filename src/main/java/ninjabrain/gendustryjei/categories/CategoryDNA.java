package ninjabrain.gendustryjei.categories;

import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.gui.elements.DrawableBlank;
import net.bdew.gendustry.config.Tuning;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import ninjabrain.gendustryjei.wrappers.WrapperDNA;

public class CategoryDNA extends CategoryBase<WrapperDNA> {
	
	public static final String UID = "GENDUSTRY_DNA_LIQUIFIER";

	private IDrawable background;

	private final int tankX = energyX + 112, tankY = energyY + 0;
	private final int arrowX = tankX - 62, arrowY = tankY + 23;
	private final int slotX = arrowX - 26, slotY = arrowY - 1;
	private final int labwareX = tankX - 48, labwareY = tankY + 4;
	
	private int labwareConsumeChance;
	
	public CategoryDNA() {
		super(Item.getByNameOrId("gendustry:extractor"));
		background = new DrawableBlank(130, 60) {
			@Override
			public void draw(Minecraft minecraft, int xOffset, int yOffset) {
				super.draw(minecraft, xOffset, yOffset);
				tankBackground.draw(minecraft, tankX, tankY);
				drawArrow(minecraft, arrowX, arrowY);
				itemSlotBackground.draw(minecraft, slotX, slotY);
				itemSlotBackground.draw(minecraft, labwareX, labwareY);
				drawEnergyMeter(minecraft);
			}
		};
		rfPerItem = Tuning.getSection("Machines").getSection("Extractor").getInt("MjPerItem");
		labwareConsumeChance = Tuning.getSection("Machines").getSection("Extractor").getInt("LabwareConsumeChance");
	}

	@Override
	public IDrawable getBackground() {
		return background;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, WrapperDNA recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();
		IGuiFluidStackGroup fluidStacks = recipeLayout.getFluidStacks();

		fluidStacks.init(0, false, tankX + 1, tankY + 1, tankOverlay.getWidth(), tankOverlay.getHeight(), 1000, false,
				tankOverlay);

		itemStacks.init(0, true, slotX, slotY);
		itemStacks.init(1, true, labwareX, labwareY);
		itemStacks.addTooltipCallback((slotIndex, input, ingredient, tooltip) -> {
			if (slotIndex == 1) tooltip.add("Chance to consume: " + labwareConsumeChance + "%");
		});

		itemStacks.set(ingredients);
		fluidStacks.set(ingredients);
	}

	@Override
	public String getUid() {
		return UID;
	}
	
}

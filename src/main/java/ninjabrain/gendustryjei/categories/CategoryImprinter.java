package ninjabrain.gendustryjei.categories;

import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.gui.elements.DrawableBlank;
import net.bdew.gendustry.config.Tuning;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import ninjabrain.gendustryjei.wrappers.WrapperImprinter;

public class CategoryImprinter extends CategoryBase<WrapperImprinter> {
	
	public static final String UID = "GENDUSTRY_IMPRINTER";

	private IDrawable background;

	private final int arrowX = energyX + 48, arrowY = energyY + 23;
	private final int slotSpecimenX = arrowX - 24, slotSpecimenY = arrowY - 1;
	private final int slotOutputX = arrowX + 60, slotOutputY = arrowY - 1;
	private final int labwareX = arrowX + 25, labwareY = arrowY - 18;
	private final int slotBlankSampleX = labwareX - 22, slotBlankSampleY = labwareY;

	private int labwareConsumeChance;
	
	public CategoryImprinter() {
		super(Item.getByNameOrId("gendustry:imprinter"));
		background = new DrawableBlank(130, 60) {
			@Override
			public void draw(Minecraft minecraft, int xOffset, int yOffset) {
				super.draw(minecraft, xOffset, yOffset);
				drawArrow(minecraft, arrowX, arrowY);
				itemSlotBackground.draw(minecraft, slotSpecimenX, slotSpecimenY);
				itemSlotBackground.draw(minecraft, slotBlankSampleX, slotBlankSampleY);
				itemSlotBackground.draw(minecraft, slotOutputX, slotOutputY);
				itemSlotBackground.draw(minecraft, labwareX, labwareY);
				drawEnergyMeter(minecraft);
			}
		};
		rfPerItem = Tuning.getSection("Machines").getSection("Imprinter").getInt("MjPerItem");
		labwareConsumeChance = Tuning.getSection("Machines").getSection("Imprinter").getInt("LabwareConsumeChance");
	}

	@Override
	public String getUid() {
		return UID;
	}

	@Override
	public IDrawable getBackground() {
		return background;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, WrapperImprinter recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();
		
		itemStacks.init(0, true, slotSpecimenX, slotSpecimenY);
		itemStacks.init(1, true, slotBlankSampleX, slotBlankSampleY);
		itemStacks.init(2, true, labwareX, labwareY);
		itemStacks.init(3, false, slotOutputX, slotOutputY);
		itemStacks.addTooltipCallback((slotIndex, input, ingredient, tooltip) -> {
			if (slotIndex == 2)
				tooltip.add("Chance to consume: " + labwareConsumeChance + "%");
			else if (slotIndex == 1)
				tooltip.add("Will not be consumed");
		});
		
		itemStacks.set(ingredients);
		
	}
}
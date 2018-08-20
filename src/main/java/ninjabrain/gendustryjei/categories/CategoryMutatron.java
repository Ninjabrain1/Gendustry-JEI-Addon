package ninjabrain.gendustryjei.categories;

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
import net.minecraft.item.ItemStack;
import ninjabrain.gendustryjei.wrappers.WrapperMutatron;

public class CategoryMutatron extends CategoryBase<WrapperMutatron> {
	
	public static final String UUID = "GENDUSTRY_MUTATRON";

	private IDrawable background;

	private final int tankX = energyX + 21, tankY = energyY + 0;
	private final int arrowX = tankX + 46, arrowY = tankY + 23;
	private final int slotPrincessX = arrowX - 26, slotPrincessY = arrowY - 12;
	private final int slotDroneX = slotPrincessX, slotDroneY = slotPrincessY + 22;
	private final int slotQueenX = arrowX + 60, slotQueenY = arrowY - 2;
	private final int labwareX = arrowX + 15, labwareY = arrowY - 18;
	
	private int labwareConsumeChance;
	
	public CategoryMutatron() {
		super(Item.getByNameOrId("gendustry:mutatron"));
		background = new DrawableBlank(150, 60) {
			@Override
			public void draw(Minecraft minecraft, int xOffset, int yOffset) {
				super.draw(minecraft, xOffset, yOffset);
				tankBackground.draw(minecraft, tankX, tankY);
				drawArrow(minecraft, arrowX, arrowY);
				itemSlotBackground.draw(minecraft, slotDroneX, slotDroneY);
				itemSlotBackground.draw(minecraft, slotPrincessX, slotPrincessY);
				itemSlotBackground.draw(minecraft, slotQueenX, slotQueenY);
				itemSlotBackground.draw(minecraft, labwareX, labwareY);
				drawEnergyMeter(minecraft);
			}
		};
		rfPerItem = Tuning.getSection("Machines").getSection("Mutatron").getInt("MjPerItem");
		labwareConsumeChance = Tuning.getSection("Machines").getSection("Mutatron").getInt("LabwareConsumeChance");
	}

	@Override
	public IDrawable getBackground() {
		return background;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, WrapperMutatron recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();
		IGuiFluidStackGroup fluidStacks = recipeLayout.getFluidStacks();

		fluidStacks.init(0, true, tankX + 1, tankY + 1, tankOverlay.getWidth(), tankOverlay.getHeight(), 2000, false,
				tankOverlay);
		
		itemStacks.init(0, true, slotDroneX, slotDroneY);
		itemStacks.init(1, true, slotPrincessX, slotPrincessY);
		itemStacks.init(2, true, labwareX, labwareY);
		itemStacks.init(3, false, slotQueenX, slotQueenY);
		itemStacks.addTooltipCallback((slotIndex, input, ingredient, tooltip) -> {
			if (slotIndex == 2) tooltip.add("Chance to consume: " + labwareConsumeChance + "%");
		});
		
		// Set output to show the queen
		List<List<ItemStack>> outputs = ingredients.getOutputs(ItemStack.class);
		if (outputs.size() != 0) {
			if (outputs.get(0).size() != 0) {
				ingredients.setOutput(ItemStack.class, ingredients.getOutputs(ItemStack.class).get(0).get(0));
			}
		}
		
		itemStacks.set(ingredients);
		fluidStacks.set(ingredients);
		
	}

	@Override
	public String getUid() {
		return UUID;
	}
	
}
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
import ninjabrain.gendustryjei.wrappers.WrapperReplicator;

public class CategoryReplicator extends CategoryBase<WrapperReplicator> {

	public static final String UID = "GENDUSTRY_REPLICATOR";

	private IDrawable background;

	private final int tank1X = energyX + 21, tank1Y = energyY + 0;
	private final int tank2X = tank1X + 21, tank2Y = tank1Y + 0;
	private final int arrowX = tank2X + 23, arrowY = tank2Y + 23;
	private final int slotSpecimenX = arrowX + 60, slotSpecimenY = arrowY - 2;
	private final int templateX = arrowX + 15, templateY = arrowY - 18;
	
	public CategoryReplicator() {
		super(Item.getByNameOrId("gendustry:replicator"));
		background = new DrawableBlank(150, 60) {
			@Override
			public void draw(Minecraft minecraft, int xOffset, int yOffset) {
				super.draw(minecraft, xOffset, yOffset);
				tankBackground.draw(minecraft, tank1X, tank1Y);
				tankBackground.draw(minecraft, tank2X, tank2Y);
				drawArrow(minecraft, arrowX, arrowY);
				itemSlotBackground.draw(minecraft, slotSpecimenX, slotSpecimenY);
				itemSlotBackground.draw(minecraft, templateX, templateY);
				drawEnergyMeter(minecraft);
			}
		};
		rfPerItem = Tuning.getSection("Machines").getSection("Replicator").getInt("MjPerItem");
	}

	@Override
	public IDrawable getBackground() {
		return background;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, WrapperReplicator recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();
		IGuiFluidStackGroup fluidStacks = recipeLayout.getFluidStacks();

		fluidStacks.init(0, true, tank1X + 1, tank1Y + 1, tankOverlay.getWidth(), tankOverlay.getHeight(), 5000, false,
				tankOverlay);
		fluidStacks.init(1, true, tank2X + 1, tank2Y + 1, tankOverlay.getWidth(), tankOverlay.getHeight(), 5000, false,
				tankOverlay);

		itemStacks.init(0, true, templateX, templateY);
		itemStacks.init(1, false, slotSpecimenX, slotSpecimenY);
		itemStacks.addTooltipCallback((slotIndex, input, ingredient, tooltip) -> {
			if (slotIndex == 0)
				tooltip.add("Will not be consumed");
		});

		fluidStacks.set(ingredients);
		itemStacks.set(ingredients);

	}

	@Override
	public String getUid() {
		return UID;
	}
}
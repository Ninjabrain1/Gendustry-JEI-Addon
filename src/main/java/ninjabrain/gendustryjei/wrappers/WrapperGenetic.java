package ninjabrain.gendustryjei.wrappers;

import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public abstract class WrapperGenetic implements IRecipeWrapper {
	
	protected static ItemStack createLabwareStack() {
		return new ItemStack(Item.getByNameOrId("gendustry:labware"));
	}
	
}
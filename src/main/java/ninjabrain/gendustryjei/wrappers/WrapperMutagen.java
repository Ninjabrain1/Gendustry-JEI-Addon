package ninjabrain.gendustryjei.wrappers;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class WrapperMutagen implements IRecipeWrapper {

	ItemStack input;
	FluidStack output;

	/**
	 * @param input
	 *            The item that will produce mutagen inside a Mutagen Producer from
	 *            gendustry
	 * @param mb
	 *            The amount of mutagen one item will produce in millibuckets
	 */
	public WrapperMutagen(ItemStack input, int mb) {
		this.input = input;
		this.output = new FluidStack(FluidRegistry.getFluid("mutagen"), mb);
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInput(ItemStack.class, input);
		ingredients.setOutput(FluidStack.class, output);
	}

}

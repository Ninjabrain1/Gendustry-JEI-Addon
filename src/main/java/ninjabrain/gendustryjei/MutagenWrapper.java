package ninjabrain.gendustryjei;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.bdew.gendustry.config.loader.RsMutagen;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class MutagenWrapper implements IRecipeWrapper{
	
	ItemStack input;
	FluidStack output;
	
	/**
	 * @param input The item that will produce mutagen inside a Mutagen Producer from gendustry
	 * @param mb The amount of mutagen one item will produce in millibuckets
	 */
	public MutagenWrapper(ItemStack input, int mb) {
		this.input = input;
		this.output = new FluidStack(FluidRegistry.getFluid("mutagen"), mb);
	}
	
	@Override
	public void getIngredients(IIngredients ingredients) {
		// TODO Auto-generated method stub
		ingredients.setInput(ItemStack.class, input);
		ingredients.setOutput(FluidStack.class, output);
	}
	
	

}

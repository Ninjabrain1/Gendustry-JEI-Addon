package ninjabrain.gendustryjei.wrappers;

import java.util.ArrayList;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

public class WrapperDNA implements IRecipeWrapper {
	
	ArrayList<ItemStack> inputs;
	FluidStack output;

	/**
	 * @param input
	 *            The item that will produce liquid DNA inside a DNA Extractor from
	 *            gendustry
	 * @param mb
	 *            The amount of liquid DNA one item will produce in millibuckets
	 */
	public WrapperDNA(ItemStack input, int mb) {
		inputs = new ArrayList<ItemStack>();
		input.setItemDamage(OreDictionary.WILDCARD_VALUE);
		inputs.add(input);
		inputs.add(new ItemStack(Item.getByNameOrId("gendustry:labware")));
		this.output = new FluidStack(FluidRegistry.getFluid("liquiddna"), mb);
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputs(ItemStack.class, inputs);
		ingredients.setOutput(FluidStack.class, output);
	}

	
}

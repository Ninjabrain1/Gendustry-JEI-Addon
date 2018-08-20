package ninjabrain.gendustryjei.wrappers;

import java.util.ArrayList;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.bdew.gendustry.config.Tuning;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class WrapperMutatron implements IRecipeWrapper{
	
	ArrayList<ItemStack> inputItems;
	FluidStack inputFluid;
	ItemStack output;
	
	public WrapperMutatron(ItemStack input1, ItemStack input2, ItemStack output) {
		inputItems = new ArrayList<ItemStack>();
		inputItems.add(input1);
		inputItems.add(input2);
		inputItems.add(new ItemStack(Item.getByNameOrId("gendustry:labware")));
		int fluidAmount = Tuning.getSection("Machines").getSection("Mutatron").getInt("MutagenPerItem");
		inputFluid = new FluidStack(FluidRegistry.getFluid("mutagen"), fluidAmount);
		this.output = output;
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputs(ItemStack.class, inputItems);
		ingredients.setInput(FluidStack.class, inputFluid);
		ingredients.setOutput(ItemStack.class, output);
	}
	
}

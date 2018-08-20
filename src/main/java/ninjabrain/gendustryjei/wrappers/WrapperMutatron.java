package ninjabrain.gendustryjei.wrappers;

import java.util.ArrayList;
import java.util.List;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.bdew.gendustry.config.Tuning;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import scala.actors.threadpool.Arrays;

public class WrapperMutatron implements IRecipeWrapper{
	
	ArrayList<List<ItemStack>> inputItems;
	FluidStack inputFluid;
	List<ItemStack> output;
	
	@SuppressWarnings("unchecked")
	public WrapperMutatron(List<ItemStack> input1, List<ItemStack> input2, List<ItemStack> output) {
		inputItems = new ArrayList<List<ItemStack>>();
		inputItems.add(input1);
		inputItems.add(input2);
		inputItems.add(Arrays.asList(new ItemStack[] {new ItemStack(Item.getByNameOrId("gendustry:labware"))}));
		int fluidAmount = Tuning.getSection("Machines").getSection("Mutatron").getInt("MutagenPerItem");
		inputFluid = new FluidStack(FluidRegistry.getFluid("mutagen"), fluidAmount);
		this.output = output;
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputLists(ItemStack.class, inputItems);
		ingredients.setInput(FluidStack.class, inputFluid);
		ingredients.setOutput(ItemStack.class, Arrays.asList(new List<?>[] {output}));
	}
	
}

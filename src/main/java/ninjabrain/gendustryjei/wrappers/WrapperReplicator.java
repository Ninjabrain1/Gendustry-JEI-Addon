package ninjabrain.gendustryjei.wrappers;

import java.util.ArrayList;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.bdew.gendustry.config.Tuning;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class WrapperReplicator implements IRecipeWrapper {
	
	ItemStack fullTemplate;
	ItemStack output;
	
	FluidStack inputDNA;
	FluidStack inputProtein;
	
	private static boolean initialized;
	private static int dnaAmount;
	private static int proteinAmount;
	
	public WrapperReplicator(ItemStack template, ItemStack outputStack) {
		fullTemplate = template;
		output = outputStack;
		if (!initialized) {
			dnaAmount = Tuning.getSection("Machines").getSection("Replicator").getInt("DNAPerItem");
			proteinAmount = Tuning.getSection("Machines").getSection("Replicator").getInt("ProteinPerItem");
			initialized = true;
		}
		inputDNA = new FluidStack(FluidRegistry.getFluid("liquiddna"), dnaAmount);
		inputProtein = new FluidStack(FluidRegistry.getFluid("protein"), proteinAmount);
	}
	
	@Override
	public void getIngredients(IIngredients ingredients) {
		ArrayList<FluidStack> inputFluids = new ArrayList<FluidStack>();
		inputFluids.add(inputDNA);
		inputFluids.add(inputProtein);
		ingredients.setInputs(FluidStack.class, inputFluids);
		ingredients.setInput(ItemStack.class, fullTemplate);
		ingredients.setOutput(ItemStack.class, output);
	}
	
	public ItemStack getFullTemplate() {
		return fullTemplate;
	}

}

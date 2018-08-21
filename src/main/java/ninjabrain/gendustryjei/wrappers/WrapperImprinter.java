package ninjabrain.gendustryjei.wrappers;

import java.util.ArrayList;

import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.item.ItemStack;

public class WrapperImprinter extends WrapperGenetic {
	
	ItemStack input;
	ItemStack template;
	ItemStack output;
	static ItemStack labware = createLabwareStack();
	
	public WrapperImprinter(ItemStack inputStack, ItemStack template, ItemStack outputStack) {
		this.template = template;
		output = outputStack;
		input = inputStack;
	}
	
	@Override
	public void getIngredients(IIngredients ingredients) {
		ArrayList<ItemStack> inputs = new ArrayList<ItemStack>();
		inputs.add(input);
		inputs.add(template);
		inputs.add(labware);
		ingredients.setInputs(ItemStack.class, inputs);
		ingredients.setOutput(ItemStack.class, output);
	}
	
	
}
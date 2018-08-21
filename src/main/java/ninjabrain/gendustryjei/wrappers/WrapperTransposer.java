package ninjabrain.gendustryjei.wrappers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.item.ItemStack;

public class WrapperTransposer extends WrapperGenetic {
	
	List<ItemStack> blankInput;
	List<ItemStack> toCopy;
	static List<ItemStack> labware = Collections.singletonList(createLabwareStack());
	
	public WrapperTransposer(ItemStack blankInput, ItemStack toCopy) {
		this(blankInput, Collections.singletonList(toCopy));
	}
	
	public WrapperTransposer(ItemStack blankInput, List<ItemStack> toCopy) {
		this.blankInput = Collections.singletonList(blankInput);
		this.toCopy = toCopy;
	}
	
	@Override
	public void getIngredients(IIngredients ingredients) {
		ArrayList<List<ItemStack>> inputs = new ArrayList<List<ItemStack>>();
		inputs.add(blankInput);
		inputs.add(toCopy);
		inputs.add(labware);
		ingredients.setInputLists(ItemStack.class, inputs);
		ingredients.setOutput(ItemStack.class, toCopy);
	}
	
	public List<ItemStack> getCopyingStack() {
		return toCopy;
	}
	
	
}

package ninjabrain.gendustryjei.wrappers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import forestry.api.genetics.IAllele;
import forestry.api.genetics.ISpeciesRoot;
import mezz.jei.api.ingredients.IIngredients;
import net.bdew.gendustry.forestry.GeneSampleInfo;
import net.bdew.gendustry.items.GeneSample;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import ninjabrain.gendustryjei.GeneticHelper;

public class WrapperSampler extends WrapperGenetic {
	
	ISpeciesRoot root;
	
	IAllele[] input;
	ItemStack output;
	
	static List<ItemStack> labware = Collections.singletonList(createLabwareStack());
	static List<ItemStack> blankSample = Collections.singletonList(new ItemStack(Item.getByNameOrId("gendustry:gene_sample_blank")));
	
	/**
	 * @param input Template of specimen that is consumed in the Sampler
	 * @param allele The allele that has a chance of being produced
	 */
	public WrapperSampler(IAllele[] input, IAllele allele, int chromosome, ISpeciesRoot root) {
		this.root = root;
		this.input = input;
		output = GeneSample.newStack(new GeneSampleInfo(root, chromosome, allele));
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ArrayList<List<ItemStack>> inputs = new ArrayList<List<ItemStack>>();
		inputs.add(GeneticHelper.getAllItemsFromTemplate(root, input));
		inputs.add(blankSample);
		inputs.add(labware);
		
		ingredients.setInputLists(ItemStack.class, inputs);
		ingredients.setOutput(ItemStack.class, output);
	}
	
	
}

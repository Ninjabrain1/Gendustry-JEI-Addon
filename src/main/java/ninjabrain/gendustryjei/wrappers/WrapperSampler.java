package ninjabrain.gendustryjei.wrappers;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IChromosome;
import forestry.api.genetics.ISpeciesRoot;
import mezz.jei.api.ingredients.IIngredients;
import net.bdew.gendustry.forestry.GeneSampleInfo;
import net.bdew.gendustry.items.GeneSample;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import ninjabrain.gendustryjei.util.GeneticHelper;

public class WrapperSampler extends WrapperGenetic {

	ISpeciesRoot root;

	IAllele[] input;
	ItemStack output;

	static List<ItemStack> labware = Collections.singletonList(createLabwareStack());
	static List<ItemStack> blankSample = Collections
			.singletonList(new ItemStack(Item.getByNameOrId("gendustry:gene_sample_blank")));

	/**
	 * @param input
	 *            Template of specimen that is consumed in the Sampler
	 * @param allele
	 *            The allele that has a chance of being produced
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

	private float chance = 0;

	public void setInputStack(ItemStack inputStack) {
		if (AlleleManager.alleleRegistry.isIndividual(inputStack)) {
			GeneSampleInfo info = GeneSample.getInfo(output);
			IAllele outputAllele = info.allele();
			int chromosomeNumber = info.chromosome();
			IChromosome[] chromosomes = AlleleManager.alleleRegistry.getIndividual(inputStack).getGenome()
					.getChromosomes();
			int matchingAlleles = 0;
			if (chromosomeNumber < chromosomes.length) {
				if (chromosomes[chromosomeNumber].getPrimaryAllele() == outputAllele)
					matchingAlleles++;
				if (chromosomes[chromosomeNumber].getSecondaryAllele() == outputAllele)
					matchingAlleles++;
			}
			// Chance to get specific outputAllele. Divide by 2 because each chromosome has 2 alleles
			chance = matchingAlleles / 2f / chromosomes.length;
		} else {
			chance = -1;
		}
	}

	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
		super.drawInfo(minecraft, recipeWidth, recipeHeight, mouseX, mouseY);
		if (chance != -1) {
			minecraft.fontRenderer.drawString(String.format("Chance: %.1f%%", chance*100f), 40, 46, Color.darkGray.getRGB());
		}
	}

}

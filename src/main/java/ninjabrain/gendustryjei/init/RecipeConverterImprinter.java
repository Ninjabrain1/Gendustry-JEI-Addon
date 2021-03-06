package ninjabrain.gendustryjei.init;

import java.util.ArrayList;
import java.util.HashSet;

import forestry.api.genetics.IAllele;
import forestry.api.genetics.ISpeciesRoot;
import forestry.api.genetics.ISpeciesType;
import net.bdew.gendustry.items.GeneTemplate;
import net.bdew.gendustry.misc.GendustryCreativeTabs;
import net.bdew.lib.recipes.RecipeStatement;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;
import ninjabrain.gendustryjei.util.GeneticHelper;
import ninjabrain.gendustryjei.wrappers.WrapperImprinter;

public class RecipeConverterImprinter extends RecipeConverter<WrapperImprinter> {

	public RecipeConverterImprinter(ArrayList<WrapperImprinter> wrapperList) {
		super(wrapperList);
		NonNullList<ItemStack> templates = NonNullList.create();
		GeneTemplate.getSubItems(GendustryCreativeTabs.templates(), templates);
		HashSet<ISpeciesRoot> addedRoots = new HashSet<ISpeciesRoot>();
		for (ItemStack template : templates) {
			ISpeciesRoot root = GeneTemplate.getSpecies(template);
			if (root != null && !addedRoots.contains(root)) {
				NBTTagCompound compound = template.getTagCompound();
				if (compound != null) {
					NBTTagCompound chromosome0 = (NBTTagCompound)compound.getTagList("samples", 10).get(0);
					String species = ((NBTTagCompound)chromosome0).getString("allele");
					IAllele[] genome = root.getGenomeTemplates().get(species);
					
					for(ISpeciesType type : GeneticHelper.getAllTypes(root)) {
						ItemStack inputStack = root.getMemberStack(root.templateAsIndividual(root.getDefaultTemplate()), type);
						inputStack.setItemDamage(OreDictionary.WILDCARD_VALUE);
						ItemStack outputStack = root.getMemberStack(root.templateAsIndividual(genome), type);
						wrapperList.add(new WrapperImprinter(inputStack, template, outputStack));
					}
					addedRoots.add(root);
				}
			}
		}
	}

	@Override
	public void processRecipeStatement(RecipeStatement rs) {
		
	}

}

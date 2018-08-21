package ninjabrain.gendustryjei.init;

import java.util.ArrayList;

import forestry.api.apiculture.BeeManager;
import forestry.api.apiculture.EnumBeeType;
import forestry.api.apiculture.IBee;
import forestry.api.arboriculture.EnumGermlingType;
import forestry.api.arboriculture.TreeManager;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.ISpeciesRoot;
import forestry.api.genetics.ISpeciesType;
import forestry.api.lepidopterology.ButterflyManager;
import forestry.api.lepidopterology.EnumFlutterType;
import net.bdew.gendustry.config.Tuning;
import net.bdew.gendustry.items.GeneTemplate;
import net.bdew.gendustry.misc.GendustryCreativeTabs;
import net.bdew.lib.recipes.RecipeStatement;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import ninjabrain.gendustryjei.wrappers.WrapperReplicator;

public class RecipeConverterReplicator extends RecipeConverter<WrapperReplicator> {

	public RecipeConverterReplicator(ArrayList<WrapperReplicator> wrapperList) {
		super(wrapperList);
		NonNullList<ItemStack> templates = NonNullList.create();
		GeneTemplate.getSubItems(GendustryCreativeTabs.templates(), templates);
		boolean makePristineBees = Tuning.getSection("Machines").getSection("Replicator").getBoolean("MakePristineBees");
		for (ItemStack template : templates) {
			ISpeciesRoot root = GeneTemplate.getSpecies(template);
			if (root != null) {
				NBTTagCompound compound = template.getTagCompound();
				if (compound != null) {
					NBTTagCompound chromosome0 = (NBTTagCompound) compound.getTagList("samples", 10).get(0);
					String species = ((NBTTagCompound) chromosome0).getString("allele");
					IAllele[] genome = root.getGenomeTemplates().get(species);
					IIndividual individual = root.templateAsIndividual(genome);

					ISpeciesType type;
					if (root == BeeManager.beeRoot) {
						type = EnumBeeType.QUEEN;
						IBee bee = (IBee) individual;
						bee.setIsNatural(makePristineBees);
					} else if (root == TreeManager.treeRoot) {
						type = EnumGermlingType.SAPLING;
					} else if (root == ButterflyManager.butterflyRoot) {
						type = EnumFlutterType.BUTTERFLY;
					} else {
						continue;
					}

					ItemStack outputStack = root.getMemberStack(individual, type);
					wrapperList.add(new WrapperReplicator(template, outputStack));
				}
			}
		}
	}

	@Override
	public void processRecipeStatement(RecipeStatement rs) {

	}

}

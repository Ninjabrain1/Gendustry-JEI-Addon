package ninjabrain.gendustryjei;

import java.util.ArrayList;
import java.util.List;

import forestry.api.apiculture.EnumBeeType;
import forestry.api.apiculture.IBeeRoot;
import forestry.api.arboriculture.EnumGermlingType;
import forestry.api.arboriculture.ITreeRoot;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.ISpeciesRoot;
import forestry.api.genetics.ISpeciesType;
import forestry.api.lepidopterology.EnumFlutterType;
import forestry.api.lepidopterology.IButterflyRoot;
import net.minecraft.item.ItemStack;

public abstract class GeneticHelper {
	
	public static List<ItemStack> getAllItemsFromSpecies(ISpeciesRoot root, IAlleleSpecies species) {
		ISpeciesType[] allTypes = getAllTypes(root);
		ArrayList<ItemStack> allItemsFromSpecies = new ArrayList<ItemStack>();
		for (int i = 0; i < allTypes.length; i++) {
			allItemsFromSpecies.add(getItemStackFromSpecies(root, species, allTypes[i]));
		}
		return allItemsFromSpecies;
	}

	public static List<ItemStack> getAllItemsFromTemplate(ISpeciesRoot root, IAllele[] template) {
		ISpeciesType[] allTypes = getAllTypes(root);
		ArrayList<ItemStack> allItemsFromTemplate = new ArrayList<ItemStack>();
		for (int i = 0; i < allTypes.length; i++) {
			allItemsFromTemplate.add(getItemStackFromTemplate(root, template, allTypes[i]));
		}
		return allItemsFromTemplate;
	}
	
	// Cannot use EnumBeeType.VALUES because I want to exclude larvae
	public static final EnumBeeType[] BEETYPES = new EnumBeeType[] {
			EnumBeeType.DRONE,
			EnumBeeType.PRINCESS,
			EnumBeeType.QUEEN};
	// Let me know if there is a general way to get all species types other than to
	// hard code it
	public static ISpeciesType[] getAllTypes(ISpeciesRoot root) {
		if (root instanceof IBeeRoot) {
			return BEETYPES;
		} else if (root instanceof ITreeRoot) {
			return EnumGermlingType.VALUES;
		} else if (root instanceof IButterflyRoot) {
			return EnumFlutterType.VALUES;
		}
		return new ISpeciesType[] { root.getIconType() };
	}

	public static ItemStack getItemStackFromSpecies(ISpeciesRoot root, IAlleleSpecies species, ISpeciesType type) {
		IAllele[] template = root.getTemplate(species.getUID());
		return getItemStackFromTemplate(root, template, type);
	}

	public static ItemStack getItemStackFromTemplate(ISpeciesRoot root, IAllele[] template, ISpeciesType type) {
		IIndividual individual = root.templateAsIndividual(template);
		individual.analyze();
		return root.getMemberStack(individual, type);
	}
	
	
}

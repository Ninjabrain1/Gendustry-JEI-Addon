package ninjabrain.gendustryjei.wrappers;

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
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public abstract class WrapperGenetic implements IRecipeWrapper {
	
	protected List<ItemStack> getAllItemsFromSpecies(ISpeciesRoot root, IAlleleSpecies species) {
		ISpeciesType[] allTypes = getAllTypes(root);
		ArrayList<ItemStack> allItemsFromSpecies = new ArrayList<ItemStack>();
		for (int i = 0; i < allTypes.length; i++) {
			allItemsFromSpecies.add(getItemStackFromSpecies(root, species, allTypes[i]));
		}
		return allItemsFromSpecies;
	}

	protected List<ItemStack> getAllItemsFromTemplate(ISpeciesRoot root, IAllele[] template) {
		ISpeciesType[] allTypes = getAllTypes(root);
		ArrayList<ItemStack> allItemsFromTemplate = new ArrayList<ItemStack>();
		for (int i = 0; i < allTypes.length; i++) {
			allItemsFromTemplate.add(getItemStackFromTemplate(root, template, allTypes[i]));
		}
		return allItemsFromTemplate;
	}
	
	// Cannot use EnumBeeType.VALUES because I want to exclude larvae
	protected static final EnumBeeType[] BEETYPES = new EnumBeeType[] {
			EnumBeeType.DRONE,
			EnumBeeType.PRINCESS,
			EnumBeeType.QUEEN};
	// Let me know if there is a general way to get all species types other than to
	// hard code it
	protected ISpeciesType[] getAllTypes(ISpeciesRoot root) {
		if (root instanceof IBeeRoot) {
			return BEETYPES;
		} else if (root instanceof ITreeRoot) {
			return EnumGermlingType.VALUES;
		}
		return new ISpeciesType[] { root.getIconType() };
	}

	protected ISpeciesType getSpeciesTypeForSlot(int slot, ISpeciesRoot root) {
		if (root instanceof IBeeRoot) {
			switch (slot) {
			case 0:
				return EnumBeeType.DRONE;
			case 1:
				return EnumBeeType.PRINCESS;
			}
			return EnumBeeType.QUEEN;
		} else if (root instanceof ITreeRoot) {
			switch (slot) {
			case 0:
				return EnumGermlingType.POLLEN;
			case 1:
				return EnumGermlingType.SAPLING;
			}
			return EnumGermlingType.SAPLING;
		}
		// Might result in invalid recipes
		return root.getIconType();
	}

	protected ItemStack getItemStackFromSpecies(ISpeciesRoot root, IAlleleSpecies species, ISpeciesType type) {
		IAllele[] template = root.getTemplate(species.getUID());
		return getItemStackFromTemplate(root, template, type);
	}

	protected ItemStack getItemStackFromTemplate(ISpeciesRoot root, IAllele[] template, ISpeciesType type) {
		IIndividual individual = root.templateAsIndividual(template);
		individual.analyze();
		return root.getMemberStack(individual, type);
	}
	
	protected static ItemStack createLabwareStack() {
		return new ItemStack(Item.getByNameOrId("gendustry:labware"));
	}
	
}

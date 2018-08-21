package ninjabrain.gendustryjei.wrappers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import forestry.api.apiculture.EnumBeeType;
import forestry.api.apiculture.IBeeRoot;
import forestry.api.arboriculture.EnumGermlingType;
import forestry.api.arboriculture.ITreeRoot;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.IMutation;
import forestry.api.genetics.ISpeciesRoot;
import forestry.api.genetics.ISpeciesType;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.bdew.gendustry.config.Tuning;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class WrapperMutatron implements IRecipeWrapper {

	// ArrayList<List<ItemStack>> inputItems;
	// FluidStack inputFluid;
	// List<ItemStack> output;

	ISpeciesRoot root;
	IMutation mutation;
	ItemStack parent0Stack;
	ItemStack parent1Stack;
	ItemStack resultStack;
	
	ItemStack labware;
	FluidStack inputFluid;

	public WrapperMutatron(IMutation mutation) {
		root = mutation.getRoot();
		this.mutation = mutation;
		parent0Stack = getItemStackFromSpecies(root, mutation.getAllele0(), getSpeciesTypeForSlot(0, root));
		parent1Stack = getItemStackFromSpecies(root, mutation.getAllele1(), getSpeciesTypeForSlot(1, root));
		resultStack = getItemStackFromTemplate(root, mutation.getTemplate(), getSpeciesTypeForSlot(2, root));
		
		labware = new ItemStack(Item.getByNameOrId("gendustry:labware"));
		int fluidAmount = Tuning.getSection("Machines").getSection("Mutatron").getInt("MutagenPerItem");
		inputFluid = new FluidStack(FluidRegistry.getFluid("mutagen"), fluidAmount);
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ArrayList<List<ItemStack>> inputs = new ArrayList<List<ItemStack>>();
		inputs.add(getAllItemsFromSpecies(root, mutation.getAllele0()));
		inputs.add(getAllItemsFromSpecies(root, mutation.getAllele1()));
		inputs.add(Collections.singletonList(labware));
		ingredients.setInputLists(ItemStack.class, inputs);

		List<List<ItemStack>> outputs = Collections.singletonList(getAllItemsFromTemplate(root, mutation.getTemplate()));
		ingredients.setOutputLists(ItemStack.class, outputs);

		ingredients.setInput(FluidStack.class, inputFluid);
	}

	private List<ItemStack> getAllItemsFromSpecies(ISpeciesRoot root, IAlleleSpecies species) {
		ISpeciesType[] allTypes = getAllTypes(root);
		ArrayList<ItemStack> allItemsFromSpecies = new ArrayList<ItemStack>();
		for (int i = 0; i < allTypes.length; i++) {
			allItemsFromSpecies.add(getItemStackFromSpecies(root, species, allTypes[i]));
		}
		return allItemsFromSpecies;
	}

	private List<ItemStack> getAllItemsFromTemplate(ISpeciesRoot root, IAllele[] template) {
		ISpeciesType[] allTypes = getAllTypes(root);
		ArrayList<ItemStack> allItemsFromTemplate = new ArrayList<ItemStack>();
		for (int i = 0; i < allTypes.length; i++) {
			allItemsFromTemplate.add(getItemStackFromTemplate(root, template, allTypes[i]));
		}
		return allItemsFromTemplate;
	}
	
	// Cannot use EnumBeeType.VALUES because I want to exclude larvae
	private static final EnumBeeType[] BEETYPES = new EnumBeeType[] {
			EnumBeeType.DRONE,
			EnumBeeType.PRINCESS,
			EnumBeeType.QUEEN};
	// Let me know if there is a general way to get all species types other than to
	// hard code it
	private ISpeciesType[] getAllTypes(ISpeciesRoot root) {
		if (root instanceof IBeeRoot) {
			return BEETYPES;
		} else if (root instanceof ITreeRoot) {
			return EnumGermlingType.VALUES;
		}
		return new ISpeciesType[] { root.getIconType() };
	}

	private ISpeciesType getSpeciesTypeForSlot(int slot, ISpeciesRoot root) {
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

	private ItemStack getItemStackFromSpecies(ISpeciesRoot root, IAlleleSpecies species, ISpeciesType type) {
		IAllele[] template = root.getTemplate(species.getUID());
		return getItemStackFromTemplate(root, template, type);
	}

	private ItemStack getItemStackFromTemplate(ISpeciesRoot root, IAllele[] template, ISpeciesType type) {
		IIndividual individual = root.templateAsIndividual(template);
		individual.analyze();
		return root.getMemberStack(individual, type);
	}
	
	public ItemStack getParent0Stack() {
		return parent0Stack;
	}

	public ItemStack getParent1Stack() {
		return parent1Stack;
	}

	public ItemStack getResultStack() {
		return resultStack;
	}
	
	public ItemStack getLabware() {
		return labware;
	}
	
}

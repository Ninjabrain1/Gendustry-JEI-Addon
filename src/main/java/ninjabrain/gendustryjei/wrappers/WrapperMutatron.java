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

public class WrapperMutatron extends WrapperGenetic {
	
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
		
		labware = createLabwareStack();
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

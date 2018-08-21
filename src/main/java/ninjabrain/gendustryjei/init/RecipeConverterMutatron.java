package ninjabrain.gendustryjei.init;

import java.util.ArrayList;
import java.util.List;

import forestry.api.apiculture.BeeManager;
import forestry.api.arboriculture.TreeManager;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IMutation;
import forestry.api.genetics.ISpeciesRoot;
import net.bdew.gendustry.config.Tuning;
import net.bdew.lib.recipes.RecipeStatement;
import net.bdew.lib.recipes.gencfg.ConfigSection;
import ninjabrain.gendustryjei.wrappers.WrapperMutatron;

public class RecipeConverterMutatron extends RecipeConverter<WrapperMutatron> {

	public RecipeConverterMutatron(ArrayList<WrapperMutatron> wrapperList) {
		super(wrapperList);
		// long startTime = System.nanoTime();
		// Read banned mutations from config
		ConfigSection bannedMutations = Tuning.getSection("Genetics").getSection("MutatronOverrides");
		// Only bees and trees can be mutated in the mutatron
		ISpeciesRoot[] roots = new ISpeciesRoot[] {BeeManager.beeRoot, TreeManager.treeRoot};
		for (ISpeciesRoot root : roots) {
			List<? extends IMutation> mutations = root.getMutations(false);
			for (IMutation mutation : mutations) {
				if (mutation.getTemplate() != null && mutation.getTemplate().length != 0) {
					IAllele resultSpecies = mutation.getTemplate()[0];
					String uidResultSpecies = resultSpecies.getUID();
					// Whether the mutation should be included or not
					boolean include = true;
					// TODO check if its efficient to use 'hasValue' function repeatedly
					if (bannedMutations.hasValue(uidResultSpecies)) {
						String value = bannedMutations.getString(uidResultSpecies);
						if (value.equalsIgnoreCase("DISABLED")) {
							include = false;
						} else if (value.equalsIgnoreCase("REQUIREMENTS")) {
							// TODO add support for mutations with special requirements
							include = false;
						}
					}
					if (include) {
						wrapperList.add(new WrapperMutatron(mutation));
					}
				}
			}
		}
		// AlleleManager.alleleRegistry.Root();
		// System.out.println(System.nanoTime()-startTime);
	}

	@Override
	public void processRecipeStatement(RecipeStatement rs) {
		// Currently no RsMutation recipes to load
		
//		if (rs instanceof RsMutation) {
//			RsMutation rsMutation = (RsMutation) rs;
//			// Input item
//			String input1 = rsMutation.parent1();
//			String input2 = rsMutation.parent2();
//			String output = rsMutation.result();
//			 String inputRegistryName = getRegistryName(input);
//			 wrapperList.add(new WrapperDNA(new
//			 ItemStack(Item.getByNameOrId(inputRegistryName)), rsMutation.mb()));
//		}
	}

}

package ninjabrain.gendustryjei.init;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import forestry.api.apiculture.BeeManager;
import forestry.api.apiculture.EnumBeeType;
import forestry.api.apiculture.IBeeMutation;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IIndividual;
import net.bdew.gendustry.config.Tuning;
import net.bdew.lib.recipes.RecipeStatement;
import net.bdew.lib.recipes.gencfg.ConfigSection;
import net.minecraft.item.ItemStack;
import ninjabrain.gendustryjei.wrappers.WrapperMutatron;
import scala.actors.threadpool.Arrays;

public class RecipeConverterMutatron extends RecipeConverter<WrapperMutatron> {

	@SuppressWarnings("unchecked")
	public RecipeConverterMutatron(ArrayList<WrapperMutatron> wrapperList) {
		super(wrapperList);
		// long startTime = System.nanoTime();
		// Read banned mutations from config
		ConfigSection bannedMutations = Tuning.getSection("Genetics").getSection("MutatronOverrides");
		List<IBeeMutation> mutations = BeeManager.beeRoot.getMutations(false);
		Map<String, IAllele[]> genomes = BeeManager.beeRoot.getGenomeTemplates();
		for (IBeeMutation mutation : mutations) {
			if (mutation.getTemplate() != null && mutation.getTemplate().length != 0) {
				IAllele speciesQueen = mutation.getTemplate()[0];
				String uidQueen = speciesQueen.getUID();
				// Whether the mutation should be included or not
				boolean include = true;
				// TODO check if its efficient to use 'hasValue' function repeatedly
				if (bannedMutations.hasValue(uidQueen)) {
					String value = bannedMutations.getString(uidQueen);
					if (value.equalsIgnoreCase("DISABLED")) {
						include = false;
					} else if (value.equalsIgnoreCase("REQUIREMENTS")) {
						// TODO add support for mutations with special requirements
						include = false;
					}
				}
				if (include) {
					IAlleleSpecies speciesDrone = mutation.getAllele0();
					IAlleleSpecies speciesPrincess = mutation.getAllele1();
					String uidDrone = speciesDrone.getUID();
					String uidPrincess = speciesPrincess.getUID();

					IIndividual input1 = BeeManager.beeRoot.templateAsIndividual(genomes.get(uidDrone));
					IIndividual input2 = BeeManager.beeRoot.templateAsIndividual(genomes.get(uidPrincess));
					IIndividual result = BeeManager.beeRoot.templateAsIndividual(mutation.getTemplate());
					ItemStack input1Drone = BeeManager.beeRoot.getMemberStack(input1, EnumBeeType.DRONE);
					ItemStack input1Princess = BeeManager.beeRoot.getMemberStack(input1, EnumBeeType.PRINCESS);
					ItemStack input1Queen = BeeManager.beeRoot.getMemberStack(input1, EnumBeeType.QUEEN);
					ItemStack input2Drone = BeeManager.beeRoot.getMemberStack(input2, EnumBeeType.DRONE);
					ItemStack input2Princess = BeeManager.beeRoot.getMemberStack(input2, EnumBeeType.PRINCESS);
					ItemStack input2Queen = BeeManager.beeRoot.getMemberStack(input2, EnumBeeType.QUEEN);
					ItemStack outputDrone = BeeManager.beeRoot.getMemberStack(result, EnumBeeType.DRONE);
					ItemStack outputPrincess = BeeManager.beeRoot.getMemberStack(result, EnumBeeType.PRINCESS);
					ItemStack outputQueen = BeeManager.beeRoot.getMemberStack(result, EnumBeeType.QUEEN);
					// Add princess and drone as output for convenience
					// TODO add queens as input ingredients (for convenience)
					wrapperList.add(new WrapperMutatron(
							Arrays.asList(new ItemStack[] { input1Drone, input1Princess, input1Queen}),
							Arrays.asList(new ItemStack[] { input2Drone, input2Princess, input2Queen}),
							Arrays.asList(new ItemStack[] { outputDrone, outputPrincess, outputQueen})));
				}
			}
		}
		// AlleleManager.alleleRegistry.getSpeciesRoot();
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

package ninjabrain.gendustryjei.init;

import java.util.ArrayList;
import java.util.Iterator;

import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.ISpeciesRoot;
import net.bdew.lib.recipes.RecipeStatement;
import ninjabrain.gendustryjei.wrappers.WrapperSampler;

public class RecipeConverterSampler extends RecipeConverter<WrapperSampler> {

	public RecipeConverterSampler(ArrayList<WrapperSampler> wrapperList) {
		super(wrapperList);
		Iterator<ISpeciesRoot> rootsIterator = AlleleManager.alleleRegistry.getSpeciesRoot().values().iterator();
		while (rootsIterator.hasNext()) {
			ISpeciesRoot root = rootsIterator.next();
			Iterator<IAllele[]> templatesIterator = root.getGenomeTemplates().values().iterator();
			while (templatesIterator.hasNext()) {
				IAllele[] template = templatesIterator.next();
				for (int i = 0; i < template.length; i++) {
					wrapperList.add(new WrapperSampler(template, template[i], i, root));
				}
			}
		}
	}

	@Override
	public void processRecipeStatement(RecipeStatement rs) {
		// No RsSampler recipes added by Gendustry as far as i know
	}

}

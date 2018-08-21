package ninjabrain.gendustryjei.init;

import java.util.ArrayList;
import java.util.Iterator;

import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.ISpeciesRoot;
import net.bdew.gendustry.forestry.GeneSampleInfo;
import net.bdew.gendustry.items.GeneSample;
import net.bdew.lib.recipes.RecipeStatement;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;
import ninjabrain.gendustryjei.wrappers.WrapperTransposer;

public class RecipeConverterTransposer extends RecipeConverter<WrapperTransposer>{

	public RecipeConverterTransposer(ArrayList<WrapperTransposer> wrapperList) {
		super(wrapperList);
		// Recipe to copy templates
		ItemStack blankTemplate = new ItemStack(Item.getByNameOrId("gendustry:gene_template"));
		ItemStack copyTemplate = new ItemStack(Item.getByNameOrId("gendustry:gene_template"));
		copyTemplate.setItemDamage(OreDictionary.WILDCARD_VALUE);
		wrapperList.add(new WrapperTransposer(blankTemplate, copyTemplate));
		// Recipe to copy samples
		NonNullList<ItemStack> allSamples = NonNullList.create();
		Iterator<ISpeciesRoot> rootsIterator = AlleleManager.alleleRegistry.getSpeciesRoot().values().iterator();
		while (rootsIterator.hasNext()) {
			ISpeciesRoot root = rootsIterator.next();
			Iterator<IAllele[]> templatesIterator = root.getGenomeTemplates().values().iterator();
			while (templatesIterator.hasNext()) {
				IAllele[] template = templatesIterator.next();
				for (int i = 0; i < template.length; i++) {
					ItemStack sampleStack = GeneSample.newStack(new GeneSampleInfo(root, i, template[i]));
					allSamples.add(sampleStack);
				}
			}
		}
		ItemStack blankSample = new ItemStack(Item.getByNameOrId("gendustry:gene_sample_blank"));
		wrapperList.add(new WrapperTransposer(blankSample, allSamples));
	}

	@Override
	public void processRecipeStatement(RecipeStatement rs) {
		
	}

}

package ninjabrain.gendustryjei.init;

import java.util.ArrayList;

import net.bdew.gendustry.config.loader.RsMutagen;
import net.bdew.lib.recipes.RecipeStatement;
import net.bdew.lib.recipes.StackRef;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import ninjabrain.gendustryjei.wrappers.WrapperMutagen;

/**
 * 
 * Converts recipes from Gendustry's Mutagen Producer to JEI Wrappers
 *
 */
public class RecipeConverterMutagen extends RecipeConverter<WrapperMutagen> {

	public RecipeConverterMutagen(ArrayList<WrapperMutagen> wrapperList) {
		super(wrapperList);
	}

	@Override
	public void processRecipeStatement(RecipeStatement rs) {
		if (rs instanceof RsMutagen) {
			RsMutagen rsMutagen = (RsMutagen) rs;
			// Input item
			StackRef input = rsMutagen.st();
			String inputRegistryName = getRegistryName(input);
			wrapperList.add(new WrapperMutagen(new ItemStack(Item.getByNameOrId(inputRegistryName)), rsMutagen.mb()));
		}
	}

}

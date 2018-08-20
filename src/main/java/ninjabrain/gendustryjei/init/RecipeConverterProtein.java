package ninjabrain.gendustryjei.init;

import java.util.ArrayList;

import net.bdew.gendustry.config.loader.RsProtein;
import net.bdew.lib.recipes.RecipeStatement;
import net.bdew.lib.recipes.StackRef;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import ninjabrain.gendustryjei.wrappers.WrapperProtein;

public class RecipeConverterProtein extends RecipeConverter<WrapperProtein> {

	public RecipeConverterProtein(ArrayList<WrapperProtein> wrapperList) {
		super(wrapperList);
	}
	
	@Override
	public void processRecipeStatement(RecipeStatement rs) {
		if (rs instanceof RsProtein) {
			RsProtein rsProtein = (RsProtein) rs;
			// Input item
			StackRef input = rsProtein.st();
			String inputRegistryName = getRegistryName(input);
			wrapperList.add(new WrapperProtein(new ItemStack(Item.getByNameOrId(inputRegistryName)), rsProtein.mb()));
		}
	}

}

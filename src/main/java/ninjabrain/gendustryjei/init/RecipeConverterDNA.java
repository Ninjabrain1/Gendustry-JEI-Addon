package ninjabrain.gendustryjei.init;

import java.util.ArrayList;

import net.bdew.gendustry.config.loader.RsLiquidDNA;
import net.bdew.lib.recipes.RecipeStatement;
import net.bdew.lib.recipes.StackRef;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import ninjabrain.gendustryjei.wrappers.WrapperDNA;

public class RecipeConverterDNA extends RecipeConverter<WrapperDNA>{

	public RecipeConverterDNA(ArrayList<WrapperDNA> wrapperList) {
		super(wrapperList);
	}

	@Override
	public void processRecipeStatement(RecipeStatement rs) {
		if (rs instanceof RsLiquidDNA) {
			RsLiquidDNA rsLiquidDNA = (RsLiquidDNA) rs;
			// Input item
			StackRef input = rsLiquidDNA.st();
			String inputRegistryName = getRegistryName(input);
			wrapperList.add(new WrapperDNA(new ItemStack(Item.getByNameOrId(inputRegistryName)), rsLiquidDNA.mb()));
		}
	}

}

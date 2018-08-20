package ninjabrain.gendustryjei.init;

import java.util.ArrayList;

import mezz.jei.api.recipe.IRecipeWrapper;
import net.bdew.lib.recipes.RecipeStatement;
import net.bdew.lib.recipes.StackBlock;
import net.bdew.lib.recipes.StackItem;
import net.bdew.lib.recipes.StackRef;
import ninjabrain.gendustryjei.GendustryJEI;

/**
 * 
 * Interface for converting RecipeStatements from bdlib to IRecipeWrappers from
 * JEI
 * 
 * @param <T>
 *            the type of recipe wrapper to convert to
 *
 */

public abstract class RecipeConverter<T extends IRecipeWrapper> {

	protected ArrayList<T> wrapperList;

	public RecipeConverter(ArrayList<T> wrapperList) {
		this.wrapperList = wrapperList;
	}

	/**
	 * Processes RecipeStatements. If the RecipeStatement is of the correct subtype
	 * it converts them to wrappers and stores them in the list that was provided at
	 * construction. What is a "correct subtype" is determined by subclasses of this
	 * class.
	 */
	public abstract void processRecipeStatement(RecipeStatement rs);

	/**
	 * Returns the registry name of the Item in the given stack
	 */
	protected static String getRegistryName(StackRef stack) {
		String name = "unknown";
		String modid = "unknown";
		if (stack instanceof StackBlock) {
			StackBlock block = (StackBlock) stack;
			name = block.name();
			modid = block.mod();
		} else if (stack instanceof StackItem) {
			StackItem item = (StackItem) stack;
			name = item.name();
			modid = item.mod();
		} else {
			GendustryJEI.logger.error("Unknown StackRef when processing recipe: " + stack);
		}
		return modid + ":" + name;
	}

}

package ninjabrain.gendustryjei;

import java.util.ArrayList;

import forestry.api.arboriculture.TreeManager;
import forestry.api.genetics.IAlleleSpecies;
import forestry.core.genetics.Genome;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.ISubtypeRegistry;
import mezz.jei.api.ISubtypeRegistry.ISubtypeInterpreter;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import ninjabrain.gendustryjei.categories.CategoryBase;
import ninjabrain.gendustryjei.categories.CategoryDNA;
import ninjabrain.gendustryjei.categories.CategoryImprinter;
import ninjabrain.gendustryjei.categories.CategoryMutagen;
import ninjabrain.gendustryjei.categories.CategoryMutatron;
import ninjabrain.gendustryjei.categories.CategoryProtein;
import ninjabrain.gendustryjei.categories.CategoryReplicator;
import ninjabrain.gendustryjei.categories.CategorySampler;
import ninjabrain.gendustryjei.categories.CategoryTransposer;
import ninjabrain.gendustryjei.init.RecipeConverter;
import ninjabrain.gendustryjei.init.RecipeConverterDNA;
import ninjabrain.gendustryjei.init.RecipeConverterImprinter;
import ninjabrain.gendustryjei.init.RecipeConverterMutagen;
import ninjabrain.gendustryjei.init.RecipeConverterMutatron;
import ninjabrain.gendustryjei.init.RecipeConverterProtein;
import ninjabrain.gendustryjei.init.RecipeConverterReplicator;
import ninjabrain.gendustryjei.init.RecipeConverterSampler;
import ninjabrain.gendustryjei.init.RecipeConverterTransposer;
import ninjabrain.gendustryjei.init.RecipeReader;
import ninjabrain.gendustryjei.wrappers.WrapperDNA;
import ninjabrain.gendustryjei.wrappers.WrapperImprinter;
import ninjabrain.gendustryjei.wrappers.WrapperMutagen;
import ninjabrain.gendustryjei.wrappers.WrapperMutatron;
import ninjabrain.gendustryjei.wrappers.WrapperProtein;
import ninjabrain.gendustryjei.wrappers.WrapperReplicator;
import ninjabrain.gendustryjei.wrappers.WrapperSampler;
import ninjabrain.gendustryjei.wrappers.WrapperTransposer;

@JEIPlugin
public class GendustryJEIPlugin implements IModPlugin {
	
	CategoryBase<?>[] categories;
	
	@Override
	public void registerItemSubtypes(ISubtypeRegistry subtypeRegistry) {
		ISubtypeInterpreter treeSpeciesInterpreter = new ISubtypeInterpreter() {
			@Override
			public String apply(ItemStack itemStack) {
				IAlleleSpecies species = Genome.getSpeciesDirectly(TreeManager.treeRoot, itemStack);
				if (species == null) {
					return ISubtypeInterpreter.NONE;
				}
				return species.getUID();
			}
		};
		ISubtypeInterpreter geneSampleInterpreter = new ISubtypeInterpreter() {
			@Override
			public String apply(ItemStack itemStack) {
				if (itemStack.hasTagCompound()) {
					String alleleUID = itemStack.getTagCompound().getString("allele");
					return alleleUID;
				}
				return ISubtypeInterpreter.NONE;
			}
		};
		ISubtypeInterpreter geneTemplateInterpreter = new ISubtypeInterpreter() {
			@Override
			public String apply(ItemStack template) {
				NBTTagCompound compound = template.getTagCompound();
				if (compound != null) {
					NBTTagCompound chromosome0 = (NBTTagCompound)compound.getTagList("samples", 10).get(0);
					String species = ((NBTTagCompound)chromosome0).getString("allele");
					return species;
				}
				return ISubtypeInterpreter.NONE;
			}
		};
		
		// Drones, princesses, queens are already registered subtypes by Forestry, register tree types:
		subtypeRegistry.registerSubtypeInterpreter(Item.getByNameOrId("forestry:sapling"), treeSpeciesInterpreter);
		subtypeRegistry.registerSubtypeInterpreter(Item.getByNameOrId("forestry:pollen_fertile"), treeSpeciesInterpreter);
		subtypeRegistry.registerSubtypeInterpreter(Item.getByNameOrId("gendustry:gene_sample"), geneSampleInterpreter);
		subtypeRegistry.registerSubtypeInterpreter(Item.getByNameOrId("gendustry:gene_template"), geneTemplateInterpreter);
	}
	
	@Override
	public void registerCategories(IRecipeCategoryRegistration registry) {
		CategoryBase.loadWidgets(registry);
		categories = new CategoryBase[] {
				new CategoryMutagen(),
				new CategoryProtein(),
				new CategoryDNA(),
				new CategoryMutatron(),
				new CategorySampler(),
				new CategoryTransposer(),
				new CategoryImprinter(),
				new CategoryReplicator()
		};
		registry.addRecipeCategories(categories);
	}
	
	@Override
	public void register(IModRegistry registry) {
		ArrayList<WrapperMutagen> mutagenWrappers = new ArrayList<WrapperMutagen>();
		ArrayList<WrapperProtein> proteinWrappers = new ArrayList<WrapperProtein>();
		ArrayList<WrapperDNA> dnaWrappers = new ArrayList<WrapperDNA>();
		ArrayList<WrapperMutatron> mutatronWrappers = new ArrayList<WrapperMutatron>();
		ArrayList<WrapperSampler> samplerWrappers = new ArrayList<WrapperSampler>();
		ArrayList<WrapperTransposer> transposerWrappers = new ArrayList<WrapperTransposer>();
		ArrayList<WrapperImprinter> imprinterWrappers = new ArrayList<WrapperImprinter>();
		ArrayList<WrapperReplicator> replicatorWrappers = new ArrayList<WrapperReplicator>();
		
		RecipeConverter<?>[] converters = new RecipeConverter[] {
				new RecipeConverterMutagen(mutagenWrappers),
				new RecipeConverterProtein(proteinWrappers),
				new RecipeConverterDNA(dnaWrappers),
				new RecipeConverterMutatron(mutatronWrappers),
				new RecipeConverterSampler(samplerWrappers),
				new RecipeConverterTransposer(transposerWrappers),
				new RecipeConverterImprinter(imprinterWrappers),
				new RecipeConverterReplicator(replicatorWrappers)
		};
		RecipeReader.convertGendustryRecipes(converters);
		
		registry.addRecipes(mutagenWrappers, CategoryMutagen.UID);
		registry.addRecipes(proteinWrappers, CategoryProtein.UID);
		registry.addRecipes(dnaWrappers, CategoryDNA.UID);
		registry.addRecipes(mutatronWrappers, CategoryMutatron.UID);
		registry.addRecipes(samplerWrappers, CategorySampler.UID);
		registry.addRecipes(transposerWrappers, CategoryTransposer.UID);
		registry.addRecipes(imprinterWrappers, CategoryImprinter.UID);
		registry.addRecipes(replicatorWrappers, CategoryReplicator.UID);
		
		for (CategoryBase<?> category : categories) {
			registry.addRecipeCatalyst(new ItemStack(category.getMachine()), category.getUid());
		}
		// Mutatron recipes also belong to the Advanced Mutatron
		registry.addRecipeCatalyst(new ItemStack(Item.getByNameOrId("gendustry:mutatron_advanced")), CategoryMutatron.UID);
		
	}
	
}

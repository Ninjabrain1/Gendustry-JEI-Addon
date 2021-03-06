package ninjabrain.gendustryjei;

import java.util.ArrayList;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.ISubtypeRegistry;
import mezz.jei.api.ISubtypeRegistry.ISubtypeInterpreter;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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
import ninjabrain.gendustryjei.util.ConfigHelper;
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
		// ISubtypeInterpreter treeSpeciesInterpreter = new ISubtypeInterpreter() {
		// @Override
		// public String apply(ItemStack itemStack) {
		// IAlleleSpecies species = Genome.getSpeciesDirectly(TreeManager.treeRoot,
		// itemStack);
		// if (species == null) {
		// return ISubtypeInterpreter.NONE;
		// }
		// return species.getUID();
		// }
		// };
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
		// ISubtypeInterpreter geneTemplateInterpreter = new ISubtypeInterpreter() {
		// @Override
		// public String apply(ItemStack template) {
		// NBTTagCompound compound = template.getTagCompound();
		// if (compound != null) {
		// String root = compound.getString("species");
		// return root;
		// }
		// return ISubtypeInterpreter.NONE;
		// }
		// };

		// Drones, princesses, queens are already registered subtypes by Forestry,
		// register tree types:
		// subtypeRegistry.registerSubtypeInterpreter(Item.getByNameOrId("forestry:sapling"),
		// treeSpeciesInterpreter);
		// subtypeRegistry.registerSubtypeInterpreter(Item.getByNameOrId("forestry:pollen_fertile"),
		// treeSpeciesInterpreter);
		subtypeRegistry.registerSubtypeInterpreter(Item.getByNameOrId("gendustry:gene_sample"), geneSampleInterpreter);
		// subtypeRegistry.registerSubtypeInterpreter(Item.getByNameOrId("gendustry:gene_template"),
		// geneTemplateInterpreter);
	}

	@Override
	public void registerCategories(IRecipeCategoryRegistration registry) {
		CategoryBase.loadWidgets(registry);
		categories = new CategoryBase[] { new CategoryMutagen(), new CategoryProtein(), new CategoryDNA(),
				new CategoryMutatron(), new CategorySampler(), new CategoryTransposer(), new CategoryImprinter(),
				new CategoryReplicator() };
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

		ArrayList<RecipeConverter<?>> converters = new ArrayList<RecipeConverter<?>>();
		if (ConfigHelper.machines.showMutagenProducerRecipes)
			converters.add(new RecipeConverterMutagen(mutagenWrappers));
		if (ConfigHelper.machines.showProteinLiquifierRecipes)
			converters.add(new RecipeConverterProtein(proteinWrappers));
		if (ConfigHelper.machines.showDNAExtractorRecipes)
			converters.add(new RecipeConverterDNA(dnaWrappers));
		if (ConfigHelper.machines.showMutatronRecipes)
			converters.add(new RecipeConverterMutatron(mutatronWrappers));
		if (ConfigHelper.machines.showSamplerRecipes)
			converters.add(new RecipeConverterSampler(samplerWrappers));
		if (ConfigHelper.machines.showGeneticTransposerRecipes)
			converters.add(new RecipeConverterTransposer(transposerWrappers));
		if (ConfigHelper.machines.showImprinterRecipes)
			converters.add(new RecipeConverterImprinter(imprinterWrappers));
		if (ConfigHelper.machines.showReplicatorRecipes)
			converters.add(new RecipeConverterReplicator(replicatorWrappers));

		RecipeReader.convertGendustryRecipes(converters);

		if (ConfigHelper.machines.showMutagenProducerRecipes)
			registry.addRecipes(mutagenWrappers, CategoryMutagen.UID);
		if (ConfigHelper.machines.showProteinLiquifierRecipes)
			registry.addRecipes(proteinWrappers, CategoryProtein.UID);
		if (ConfigHelper.machines.showDNAExtractorRecipes)
			registry.addRecipes(dnaWrappers, CategoryDNA.UID);
		if (ConfigHelper.machines.showMutatronRecipes)
			registry.addRecipes(mutatronWrappers, CategoryMutatron.UID);
		if (ConfigHelper.machines.showSamplerRecipes)
			registry.addRecipes(samplerWrappers, CategorySampler.UID);
		if (ConfigHelper.machines.showGeneticTransposerRecipes)
			registry.addRecipes(transposerWrappers, CategoryTransposer.UID);
		if (ConfigHelper.machines.showImprinterRecipes)
			registry.addRecipes(imprinterWrappers, CategoryImprinter.UID);
		if (ConfigHelper.machines.showReplicatorRecipes)
			registry.addRecipes(replicatorWrappers, CategoryReplicator.UID);

		for (CategoryBase<?> category : categories) {
			registry.addRecipeCatalyst(new ItemStack(category.getMachine()), category.getUid());
		}
		// Mutatron recipes also belong to the Advanced Mutatron
		if (ConfigHelper.machines.showMutatronRecipes)
			registry.addRecipeCatalyst(new ItemStack(Item.getByNameOrId("gendustry:mutatron_advanced")),
					CategoryMutatron.UID);

	}

}

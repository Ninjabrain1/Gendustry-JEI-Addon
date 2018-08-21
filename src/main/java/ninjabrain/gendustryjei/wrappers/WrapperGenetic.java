package ninjabrain.gendustryjei.wrappers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import forestry.api.apiculture.BeeManager;
import forestry.api.apiculture.EnumBeeType;
import forestry.api.apiculture.IBeeRoot;
import forestry.api.arboriculture.EnumGermlingType;
import forestry.api.arboriculture.ITreeRoot;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IChromosome;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.ISpeciesRoot;
import forestry.api.genetics.ISpeciesType;
import forestry.api.lepidopterology.EnumFlutterType;
import forestry.api.lepidopterology.IButterflyRoot;
import forestry.apiculture.genetics.BeeRoot;
import forestry.core.genetics.Chromosome;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.bdew.gendustry.forestry.GeneSampleInfo;
import net.bdew.gendustry.machines.imprinter.TileImprinter;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public abstract class WrapperGenetic implements IRecipeWrapper {
	
	protected static ItemStack createLabwareStack() {
		return new ItemStack(Item.getByNameOrId("gendustry:labware"));
	}
	
}
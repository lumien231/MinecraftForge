package net.minecraftforge.debug;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = "brewingreciperegistrytest", name = "BrewingRecipeRegistryTest", version = "0.0.0", acceptableRemoteVersions = "*")
public class BrewingRecipeRegistryTest
{
    public static final boolean ENABLE = false;
    private static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        if (!ENABLE)
        {
            return;
        }

        // The following adds a recipe that brews a piece of rotten flesh "into" a diamond sword resulting in a diamond hoe
        BrewingRecipeRegistry.addRecipe(new ItemStack(Items.DIAMOND_SWORD), new ItemStack(Items.ROTTEN_FLESH), new ItemStack(Items.DIAMOND_HOE));

        ItemStack output0 = BrewingRecipeRegistry.getOutput(new ItemStack(Items.DIAMOND_SWORD), new ItemStack(Items.ROTTEN_FLESH));
        if (output0.getItem() == Items.DIAMOND_HOE)
        {
            logger.info("Recipe successfully registered and working. Diamond Hoe obtained.");
        }

        // Testing if OreDictionary support is working. Register a recipe that brews a gemDiamond into a gold sword resulting in a diamond sword
        BrewingRecipeRegistry.addRecipe(new ItemStack(Items.GOLDEN_SWORD), "gemDiamond", new ItemStack(Items.DIAMOND_SWORD));

        ItemStack output1 = BrewingRecipeRegistry.getOutput(new ItemStack(Items.GOLDEN_SWORD), new ItemStack(Items.DIAMOND));
        if (output1.getItem() == Items.DIAMOND_SWORD)
        {
            logger.info("Recipe successfully registered and working. Diamond Sword obtained.");
        }

        // In vanilla, brewing netherwart into a water bottle results in an awkward potion (with metadata 16). The following tests if that still happens
        ItemStack output2 = BrewingRecipeRegistry.getOutput(new ItemStack(Items.POTIONITEM, 1, 0), new ItemStack(Items.NETHER_WART));
        if (output2 != null && output2.getItem() == Items.POTIONITEM && output2.getItemDamage() == 16)
        {
            logger.info("Vanilla behaviour still in place. Brewed Water Bottle with Nether Wart and got Awkward Potion");
        }
    }
}

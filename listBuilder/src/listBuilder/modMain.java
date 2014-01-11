package listBuilder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.logging.Logger;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.LanguageRegistry;

@NetworkMod(clientSideRequired=false)
@Mod(modid ="listBuilder", name = "List Builder", version = "0.0.1" )
public class modMain {

	@Instance(value = "listBuilder")
	public static modMain instance;
	@SidedProxy(clientSide="listBuilder.Proxy", serverSide="listBuilder.Proxy")
	public static Proxy proxy;
	public static Logger logger;
	public static File directory;
	@EventHandler
    public void preInit(FMLPreInitializationEvent event) {
		logger = Logger.getLogger("WorldControl");
		logger.setParent(FMLLog.getLogger());
		directory = event.getModConfigurationDirectory();
	}
	
	@EventHandler 
    public void load(FMLInitializationEvent event) {
		
	}
	
	 @EventHandler
     public void postInit(FMLPostInitializationEvent event) {
		 
	 }
	 
	 @EventHandler
     public void serverStart(FMLServerStartingEvent event)
     {
		 try {
			writeYML();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     }

	public static void writeYML() throws IOException
	{
		 LinkedList<ItemStack> list;
		 list = new LinkedList<ItemStack>();
		 
		 File outputFile;
		 outputFile = new File(directory.getAbsolutePath() + "\\names.yml");
		 BufferedWriter writer;
		 
			writer = new BufferedWriter(new FileWriter(outputFile));
		 
		 writer.append("items:\n");
		 writer.append("  'UNKNOWN': Unknown\n");
		 for (Item item : Item.itemsList)
		 {
			 if (item == null)
				 continue;
			 if (item.itemID == 0)
				 continue;
			 item.getSubItems(item.itemID, item.getCreativeTab(), list);
			 for(ItemStack curItem : list)
			 {
				 try {
				 writer.append("  '" + curItem.itemID + ";" + curItem.getItemDamage() + "': " + curItem.getDisplayName().replace(':', '-') +"\n" );
				 }
				 catch (NullPointerException npe)
				 {
					 
				 }
			 }
			 list.clear();
		 }
		 writer.append("enchantments:\n");
		 for (Enchantment ench :Enchantment.enchantmentsList)
		 {
			 if (ench == null)
				 continue;
			 
			 writer.append("  '" + ench.effectId +"': " + ench.getTranslatedName(0).substring(0, ench.getTranslatedName(0).lastIndexOf(' ')) + "\n");
		 }
	
		 writer.flush();
		 writer.close();
	}
}

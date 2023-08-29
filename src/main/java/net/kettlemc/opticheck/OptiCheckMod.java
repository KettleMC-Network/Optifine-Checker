package net.kettlemc.opticheck;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.kettlemc.opticheck.config.Config;
import net.kettlemc.opticheck.screen.OptiCheckScreen;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.Logger;

@Mod(modid = OptiCheckMod.MODID, name = OptiCheckMod.NAME, version = OptiCheckMod.VERSION)
public class OptiCheckMod {

    public static final String MODID = "opticheck";
    public static final String NAME = "Optifine Checker";
    public static final String VERSION = "1.7.10-1.2.1";

    private static Logger logger;

    private boolean alreadyDisplayed = false;
    private boolean classesDetected = false;

    private boolean shouldDisplay() {
        return classesDetected && !alreadyDisplayed;
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        try {
            Config.setup(new Configuration(event.getSuggestedConfigurationFile()));
        } catch (Exception e) {
            getLogger().error("Couldn't load config file.");
            e.printStackTrace();
        }
        Utils.fixOldConfigs();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        this.alreadyDisplayed = Config.ONLY_DISPLAY_ONCE.getBoolean() && Config.ALREADY_DISPLAYED;
        if (!alreadyDisplayed) this.classesDetected = !ModDetector.checkClasses();
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void openMainMenu(GuiOpenEvent event) {
        if (shouldDisplay()) {
            if (event.gui instanceof GuiMainMenu) {
                event.gui = new OptiCheckScreen();
                this.alreadyDisplayed = true;
            }
        }
    }

    public static Logger getLogger() {
        return logger;
    }
}

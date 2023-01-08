package net.kettlemc.opticheck;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.Logger;

@Mod(modid = OptiCheckMod.MODID, name = OptiCheckMod.NAME, version = OptiCheckMod.VERSION)
public class OptiCheckMod {

    public static final String MODID = "opticheck";
    public static final String NAME = "Optifine Checker";
    public static final String VERSION = "1.7.10-1.0.1";

    private static Logger logger;

    private boolean alreadyDisplayed = false;
    private boolean classesDetected = false;

    /**
     * Checks if all the classes are available
     * If detection mode is MISSING, it will return true if all classes are missing
     * If detection mode is AVAILABLE, it will return true if all classes are available
     *
     * @return boolean - if the class was found
     */
    private boolean checkClasses() {
        boolean availableMode = Config.instance().detectionMode.equals(Config.AVAILABLE);
        try {
            for (String clazz : Config.instance().classes)
                Class.forName(clazz);
            if (availableMode)
                return true;
            return false;
        } catch (ClassNotFoundException e) {
            logger.warn("Failed to find class " + e.getMessage());
            if (availableMode)
                return false;
            return true;
        }
    }

    private boolean shouldDisplay() {
        return !classesDetected && !alreadyDisplayed;
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

    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        this.classesDetected = !checkClasses();
        this.alreadyDisplayed = Config.instance().onlyDisplayOnce && Config.instance().alreadyDisplayed;
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

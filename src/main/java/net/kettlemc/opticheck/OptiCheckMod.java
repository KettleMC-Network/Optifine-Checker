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
import org.apache.logging.log4j.Logger;

@Mod(modid = OptiCheckMod.MODID, name = OptiCheckMod.NAME, version = OptiCheckMod.VERSION)
public class OptiCheckMod
{
    public static final String MODID = "opticheck";
    public static final String NAME = "Optifine Checker";
    public static final String VERSION = "1.7.10-1.0.1";

    private static Logger logger;

    private boolean alreadyChecked = false;
    private boolean optifinePresent = false;

    private static OptiCheckScreen screen = new OptiCheckScreen();
    private static OptiCheckConfig config = new OptiCheckConfig();

    /**
     * Checks if Optifine is installed (Should only be called once!)
     * @return boolean - if the class was found
     */
    private boolean isOptifineInstalled() {
        try {
            Class.forName(config.getValue("class"));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean shouldDisplay() {
        return !optifinePresent && !alreadyChecked;
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        this.optifinePresent = isOptifineInstalled();
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void openMainMenu(GuiOpenEvent event) {
        if (shouldDisplay()) {
            if (event.gui instanceof GuiMainMenu) {
                event.gui = screen;
                this.alreadyChecked = true;
                return;
            }
        }
    }

    public static Logger getLogger() {
        return logger;
    }

    public static OptiCheckConfig getConfig() {
        return config;
    }
}

package net.kettlemc.opticheck;

import net.minecraft.client.gui.GuiMainMenu;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.Logger;

@Mod(modid = OptiCheckMod.MODID, name = OptiCheckMod.NAME, version = OptiCheckMod.VERSION, clientSideOnly = true)
public class OptiCheckMod
{
    public static final String MODID = "opticheck";
    public static final String NAME = "Optifine Checker";
    public static final String VERSION = "1.12.2-1.0.1";

    private static Logger logger;

    private boolean alreadyChecked = false;
    private boolean optifinePresent = false;

    private static OptiCheckScreen screen = new OptiCheckScreen();


    /**
     * Checks if Optifine is installed (Should only be called once!)
     * @return boolean - if the class was found
     */
    private boolean isOptifineInstalled() {
        try {
            Class.forName(OptiCheckConfig.clazz);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean shouldDisplay() {
        return !optifinePresent && !alreadyChecked;
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        this.optifinePresent = isOptifineInstalled();
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void openMainMenu(GuiOpenEvent event) {
        if (shouldDisplay()) {
            if (event.getGui() instanceof GuiMainMenu) {
                event.setGui(screen);
                this.alreadyChecked = true;
                return;
            }
        }
    }

    public static Logger getLogger() {
        return logger;
    }
}

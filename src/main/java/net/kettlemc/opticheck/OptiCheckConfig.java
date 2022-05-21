package net.kettlemc.opticheck;

import net.minecraftforge.common.config.Config;

@Config(modid = OptiCheckMod.MODID, type = Config.Type.INSTANCE, category = "general")
public class OptiCheckConfig {

    @Config.Comment({
            "This settings changes the mod that should be used.",
            "0 -> Remind the user to install Optifine",
            "1 -> Force the user to install Optifine or Quit the Game"
    })
    @Config.Name("mode")
    public static int mode = 0;

    @Config.Comment({"The link that should be used for downloading optifine"})
    @Config.Name("url")
    public static String url = "https://optifine.net/adloadx?f=OptiFine_1.12.2_HD_U_G5.jar";

    @Config.Comment({"The title you want to use."})
    @Config.Name("title")
    public static String title = "&c&lOptifine couldn't be detected!";

    @Config.Comment({"The message you want to use."})
    @Config.Name("message")
    public static String message = "The author of this modpack recommends Optifine! Please install it using the Link below!";

    @Config.Comment({
            "This settings changes the class which indicated Optifine.",
            "You shouldn't change this, if you don't have to."
    })
    @Config.Name("optifine-class")
    public static String clazz = "net.optifine.Log";

}

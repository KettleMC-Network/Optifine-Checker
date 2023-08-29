package net.kettlemc.opticheck.config;

import com.google.common.io.Files;
import net.kettlemc.opticheck.OptiCheckMod;
import net.minecraftforge.common.config.Config;

import java.io.File;
import java.io.IOException;
import java.time.Instant;

@Config(modid = OptiCheckMod.MODID, type = Config.Type.INSTANCE)
public class OptiCheckConfig {

    public static boolean alreadyDisplayed() {
        return new File("opticheck-already-displayed").exists();
    }

    @Config(modid = OptiCheckMod.MODID, type = Config.Type.INSTANCE, category = "settings")
    public static class Settings {
        @Config.Comment({
                "Which mode should be used for the display.",
                "REMIND: Shows a Link and Continue button",
                "FORCE: Shows a Link and Quit button"
        })
        @Config.Name("download_mode")
        public static DownloadMode DOWNLOAD_MODE = DownloadMode.REMIND;

        @Config.Comment({
                "Which mode should be used for the detection.",
                "ALL_MISSING: Displays the screen if all the classes are missing",
                "ONE_MISSING: Displays the screen if one of the classes is missing",
                "ALL_AVAILABLE: Displays the screen if all the classes are available",
                "ONE_AVAILABLE: Displays the screen if one of the classes is available"
        })
        @Config.Name("detection_mode")
        public static DetectionMode DETECTION_MODE = DetectionMode.ONE_MISSING;

        @Config.Comment("The url that should be displayed when clicking the Link button.")
        @Config.Name("url")
        public static String DOWNLOAD_URL = "https://optifine.net/adloadx?f=OptiFine_1.12.2_HD_U_G5.jar";

        @Config.Comment("Whether the screen should only be display once.")
        @Config.Name("only_display_once")
        public static boolean ONLY_DISPLAY_ONCE = false;
    }

    @Config(modid = OptiCheckMod.MODID, type = Config.Type.INSTANCE, category = "advanced")
    public static class Advanced {
        @Config.Comment("The id of the button that continues/closes the game.")
        @Config.Name("other_button_id")
        @Config.RangeInt(min = 0)
        public static int CONTINUE_QUIT_BUTTON_ID = 421;

        @Config.Comment("The id of the button that opens the link.")
        @Config.Name("link_button_id")
        @Config.RangeInt(min = 0)
        public static int LINK_BUTTON_ID = 420;

        @Config.Comment("The id of the button that opens the mods folder.")
        @Config.Name("mods_button_id")
        @Config.RangeInt(min = 0)
        public static int MODS_FOLDER_BUTTON_ID = 422;

        @Config.Comment("The classes used for detecting the mods (Don't change if you don't know what you're doing!)")
        @Config.Name("classes")
        public static String[] CLASSES = new String[]{
                "net.optifine.Log"
        };
    }

    @Config(modid = OptiCheckMod.MODID, type = Config.Type.INSTANCE, category = "messages")
    public static class Messages {
        @Config.Comment("The title that should be displayed if the detection failed.")
        @Config.Name("title")
        public static String SCREEN_TITLE = "&c&lOptifine couldn't be detected!";

        @Config.Comment("The message that should be displayed if the detection failed.")
        @Config.Name("message")
        public static String SCREEN_MESSAGE = "The author of this modpack recommends Optifine! Please install it using the button below, put it in your mods folder and restart the game!";

        @Config.Comment("The text of the download button.")
        @Config.Name("link_button_text")
        public static String LINK_BUTTON_TEXT = "Download Optifine";

        @Config.Comment("The text of the continue button.")
        @Config.Name("continue_button_text")
        public static String CONTINUE_BUTTON_TEXT = "Continue";

        @Config.Comment("The text of the quit button.")
        @Config.Name("quit_button_text")
        public static String QUIT_BUTTON_TEXT = "Quit";

        @Config.Comment("The text of the mods button.")
        @Config.Name("mods_button_text")
        public static String MODS_FOLDER_BUTTON_TEXT = "Open Mods Folder";

    }

    @SuppressWarnings({"ResultOfMethodCallIgnored", "UnstableApiUsage"})
    public static void setAlreadyDisplayed(boolean alreadyDisplayed) {
        File file = new File("opticheck-already-displayed"); // Not included in the config directory so that it won't be redistributed by accident
        if (alreadyDisplayed) {
            try {
                file.createNewFile();
                Files.write(Instant.now().toString().getBytes(), file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else
            file.delete();
    }


}

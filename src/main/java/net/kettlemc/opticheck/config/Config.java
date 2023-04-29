package net.kettlemc.opticheck.config;

import com.google.common.io.Files;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import java.io.File;
import java.io.IOException;
import java.time.Instant;

public class Config {

    private static Config instance;
    private static Configuration config;

    public static boolean ALREADY_DISPLAYED;
    public static Property ONLY_DISPLAY_ONCE;

    public static Property DOWNLOAD_MODE;
    public static Property DETECTION_MODE;
    public static Property CLASSES;

    public static Property DOWNLOAD_URL;

    public static Property SCREEN_TITLE;
    public static Property SCREEN_MESSAGE;

    public static Property LINK_BUTTON_TEXT;
    public static Property QUIT_BUTTON_TEXT;
    public static Property CONTINUE_BUTTON_TEXT;
    public static Property MODS_FOLDER_BUTTON_TEXT;
    public static Property CONTINUE_QUIT_BUTTON_ID;
    public static Property LINK_BUTTON_ID;
    public static Property MODS_FOLDER_BUTTON_ID;

    private Config(Configuration config) {
        this.config = config;
        this.config.load();

         CONTINUE_QUIT_BUTTON_ID = config.get("advanced", "other_button_id", 421,
                "The id of the button that continues/closes the game.",
                0, Integer.MAX_VALUE
        );

         LINK_BUTTON_ID = config.get("advanced", "link_button_id", 420,
                "The id of the button that opens the link.",
                0, Integer.MAX_VALUE
        );

         MODS_FOLDER_BUTTON_ID = config.get("advanced", "mods_button_id", 422,
                "The id of the button that opens the mods folder.",
                0, Integer.MAX_VALUE
        );

        ONLY_DISPLAY_ONCE = config.get("settings", "only_display_once", false,
                "Whether the screen should only be display once.");

        ALREADY_DISPLAYED = new File("opticheck-already-displayed").exists();

        DOWNLOAD_MODE = config.get("settings", "download_mode", DownloadMode.FORCE.toString(),
                "Which mode should be used for the display." +
                        "\nFORCE: Shows a Link and Quit button" +
                        "\nREMIND: Shows a Link and Continue button"
        );

        DETECTION_MODE = config.get("advanced", "detection_mode", DetectionMode.ALL_MISSING.toString(),
                "Which mode should be used for the detection." +
                        "\nALL_MISSING: Displays the screen if all the classes are missing" +
                        "\nONE_MISSING: Displays the screen if one of the classes is missing" +
                        "\nALL_AVAILABLE: Displays the screen if all the classes are available" +
                        "\nONE_AVAILABLE: Displays the screen if one of the classes is available"
        );

        DOWNLOAD_URL = config.get("settings", "url", "https://optifine.net/adloadx?f=OptiFine_1.7.10_HD_U_E7.jar",
                "The url that should be displayed when clicking the Link button."
        );

        CLASSES = config.get("advanced", "classes", new String[]{"net.optifine.entity.model.anim.ConstantFloat"},
                "The classes used for detecting the mods (Don't change if you don't know what you're doing!)"
        );

        SCREEN_TITLE = config.get("messages", "title", "&c&lOptifine couldn't be detected!",
                "The title that should be displayed if the detection failed."
        );

        SCREEN_MESSAGE = config.get("messages", "message", "The author of this modpack recommends Optifine! Please install it using the button below, put it in your mods folder and restart the game!",
                "The message that should be displayed if the detection failed."
        );

        LINK_BUTTON_TEXT = config.get("messages", "link_button_text", "Download Optifine",
                "The text of the download button."
        );

        QUIT_BUTTON_TEXT = config.get("messages", "quit_button_text", "Quit",
                "The text of the quit button."
        );

        CONTINUE_BUTTON_TEXT = config.get("messages", "continue_button_text", "Continue",
                "The text of the continue button."
        );

        MODS_FOLDER_BUTTON_TEXT = config.get("messages", "mods_button_text", "Open Mods Folder",
                "The text of the mods button."
        );

        this.config.setCategoryComment("advanced", "Don't change these settings unless you know what you're doing.");
        this.config.setCategoryComment("messages", "The messages that will be displayed upon detecting the classes");
        this.config.setCategoryComment("settings", "All settings needed to configure the optifine detection.");
        this.config.save();

    }

    public static void setup(Configuration configuration) {
        if (instance == null)
            instance = new Config(configuration);
    }

    public static Config instance() {
        return instance;
    }

    public static Configuration getConfig() {
        return config;
    }

    public void setAlreadyDisplayed(boolean alreadyDisplayed) {
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
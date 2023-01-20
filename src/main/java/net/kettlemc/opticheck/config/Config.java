package net.kettlemc.opticheck.config;

import net.minecraftforge.common.config.Configuration;

import java.io.File;
import java.io.IOException;

public class Config {

    private static Config instance;
    private final Configuration config;

    public final int buttonContinue;
    public final int buttonLink;

    public final boolean onlyDisplayOnce;
    public final boolean alreadyDisplayed;

    public final String displayMode;
    public final String detectionMode;

    public final String url;
    public final String[] classes;

    public final String title;
    public final String message;

    public static final String MISSING = "MISSING";
    public static final String AVAILABLE = "AVAILABLE";
    public static final String FORCE = "FORCE";
    public static final String REMIND = "REMIND";

    private Config(Configuration config) {
        this.config = config;
        this.config.load();

        this.onlyDisplayOnce = onlyDisplayOnce();
        this.alreadyDisplayed = alreadyDisplayed();

        this.displayMode = displayMode();
        this.detectionMode = detectionMode();

        this.classes = classes();
        this.url = url();

        this.title = title();
        this.message = message();

        this.buttonContinue = buttonOtherId();
        this.buttonLink = buttonLinkId();

        this.config.setCategoryComment("advanced", "Don't change these settings unless you know what you're doing.");
        this.config.setCategoryComment("messages", "The messages that will be displayed upon detecting the classes");
        this.config.setCategoryComment("settings", "All settings needed to configure the optifine detection.");
        this.config.save();

    }

    private String getString(String key, String category, String defaultValue, String comment) {
        String s = defaultValue;
        try {
            s = config.getString(key, category, defaultValue, comment);
        } catch (Exception e) {
        }
        return s;
    }

    private int getInt(String key, String category, int defaultValue, int min, int max, String comment) {
        int s = defaultValue;
        try {
            s = config.getInt(key, category, defaultValue, min, max, comment);
        } catch (Exception e) {
        }
        return s;
    }

    private boolean getBoolean(String key, String category, boolean defaultValue, String comment) {
        boolean s = defaultValue;
        try {
            s = config.getBoolean(key, category, defaultValue, comment);
        } catch (Exception ignored) {
        }
        return s;
    }

    private String[] getStringArray(String key, String category, String[] defaultValues, String comment) {
        String[] s = defaultValues;
        try {
            s = config.getStringList(key, category, defaultValues, comment);
        } catch (Exception ignored) {
        }
        return s;
    }

    private boolean onlyDisplayOnce() {
        return getBoolean("only_display_once", "settings", false,
                "Whether the screen should only be display once.");
    }

    private boolean alreadyDisplayed() {
        return new File("opticheck-already-displayed").exists();
    }

    public void setAlreadyDisplayed(boolean alreadyDisplayed) {
        File file = new File("opticheck-already-displayed");
        if (alreadyDisplayed) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else
            file.delete();
    }

    private String displayMode() {
        return getString("download_mode", "settings", "FORCE",
                "Which mode should be used for the display." +
                        "\nFORCE: Shows a Link and Quit button" +
                        "\nREMIND: Shows a Link and Continue button"
        );
    }

    private String title() {
        return getString("title", "messages", "&c&lOptifine couldn't be detected!",
                "The title that should be displayed if the detection failed."
        );
    }

    private String message() {
        return getString("message", "messages", "The author of this modpack recommends Optifine! Please install it using the Link below!",
                "The message that should be displayed if the detection failed."
        );
    }

    private String detectionMode() {
        return getString("detection_mode", "advanced", "MISSING",
                "Which mode should be used for the detection." +
                        "\nMISSING: Displays the screen if one of the classes is missing" +
                        "\nAVAILABLE: Displays the screen if one of the classes is available"
        );
    }

    private String[] classes() {
        return getStringArray("classes", "advanced", new String[]{"net.optifine.entity.model.anim.ConstantFloat"},
                "The classes used for detecting the mods (Don't change if you don't know what you're doing!)");
    }

    private String url() {
        return getString("url", "settings", "https://optifine.net/adloadx?f=OptiFine_1.7.10_HD_U_E7.jar",
                "The url that should be displayed when clicking the Link button.");
    }

    private int buttonLinkId() {
        return config.getInt("link_button_id", "advanced", 420, 0, Integer.MAX_VALUE, "The id of the button that opens the link.");
    }

    private int buttonOtherId() {
        return config.getInt("other_button_id", "advanced", 421, 0, Integer.MAX_VALUE, "The id of the button that continues/closes the game.");
    }

    public static void setup(Configuration configuration) {
        if (instance == null)
            instance = new Config(configuration);
    }

    public static Config instance() {
        return instance;
    }

}
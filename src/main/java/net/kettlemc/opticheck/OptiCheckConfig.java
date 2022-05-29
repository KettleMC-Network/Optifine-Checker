package net.kettlemc.opticheck;


import java.io.*;
import java.util.Properties;

// TODO: Port this to the forge config system (maybe...)
public class OptiCheckConfig {

    public OptiCheckConfig() {
        loadConfig();
    }

    private static final String CONFIG_PATH = "config/opticheck.properties";

    private static final String COMMENT =
            " Config for the Optifine Checker Mod\n" +
            " mode: This settings changes the mod that should be used\n" +
            "   0 -> Remind the user to install Optifine\n" +
            "   1 -> Force the user to install Optifine or Quit the Game\n" +
            " url: The link that should be used for downloading optifine\n" +
            " title: The title you want to use\n" +
            " message: The message you want to use\n" +
            " class: Do not change this unless you know what you're doing!\n" +
            " only-display-once: If the screen should only be shown once\n"+
            " already-displayed: If the screen has been displayed already\n";

    private File file;
    private Properties properties;

    public void loadConfig() {
        try {
            this.file = new File(CONFIG_PATH);
            this.properties = new Properties();
            if (createIfNotExists(file)) {
                populateConfig();
            }
            properties.load(new FileReader(file));
        } catch (IOException exception) {
            OptiCheckMod.getLogger().error("Failed to load config.");
            exception.printStackTrace();
        }
    }

    /**
     * Creates a file if it doesn't exist already
     *
     * @param file - The file that should be checked
     * @return boolean - If the file had to be created
     * @throws IOException
     */
    private boolean createIfNotExists(File file) throws IOException {
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
            return true;
        }
        return false;
    }

    private void populateConfig() {
        this.properties.setProperty("mode", String.valueOf(0));
        this.properties.setProperty("message", "The author of this modpack recommends Optifine! Please install it using the Link below!");
        this.properties.setProperty("title", "&c&lOptifine couldn't be detected!");
        this.properties.setProperty("link", "https://optifine.net/adloadx?f=OptiFine_1.7.10_HD_U_E7.jar");
        this.properties.setProperty("class", "net.optifine.entity.model.anim.ConstantFloat");
        this.properties.setProperty("only-display-once", "false");
        this.properties.setProperty("already-displayed", "false");
        saveConfig();
    }

    private void saveConfig() {
        try {
            this.properties.store(new FileWriter(this.file), COMMENT);
        } catch (IOException e) {
            OptiCheckMod.getLogger().error("Couldn't save config!");
            e.printStackTrace();
        }
    }

    public String getValue(String key) {
        return this.properties == null ? "" : this.properties.getProperty(key);
    }

    public String getValueAsString(String key, String defaultValue) {
        return getValue(key).isEmpty() ? defaultValue : key;
    }

    public int getValueAsInt(String key, int defaultValue) {
        return isInteger(getValue(key)) ? Integer.valueOf(getValue(key)) : defaultValue;
    }

    public boolean getValueAsBoolean(String key, boolean defaultValue) {
        return isBoolean(getValue(key)) ? Boolean.valueOf(getValue(key)) : defaultValue;
    }

    public void setValue(String key, Object value) {
        this.properties.setProperty(key, String.valueOf(value));
        saveConfig();
    }

    private boolean isInteger(String integer) {
        try {
            Integer.valueOf(integer);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isBoolean(String bool) {
        try {
            Boolean.valueOf(bool);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}

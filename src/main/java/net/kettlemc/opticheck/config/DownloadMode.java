package net.kettlemc.opticheck.config;

/**
 * This enum describes when the screen should be displayed.
 */
public enum DownloadMode {

    /**
     * The user will be forced to install the missing mods.
     */
    FORCE,

    /**
     * The user will be reminded to install the missing mods.
     */
    REMIND;


    /**
     * Wrapper for valueOf
     * @param mode The string to convert
     * @return The mode represented by the String
     */
    public static DownloadMode fromString(String mode) {
        try {
            return DownloadMode.valueOf(mode);
        } catch (Exception e) {
            return null;
        }
    }

}
package net.kettlemc.opticheck.config;


/**
 * This enum describes when the screen should be displayed.
 */
public enum DetectionMode {

    /**
     * The screen will be displayed if one of the listed classes is missing.
     */
    ONE_MISSING,

    /**
     * The screen will be displayed if all the listed classes are missing.
     */
    ALL_MISSING,

    /**
     * The screen will be displayed if one of the listed classes is available.
     */
    ONE_AVAILABLE,

    /**
     * The screen will be displayed if all the listed classes are available.
     */
    ALL_AVAILABLE;


    /**
     * Wrapper for valueOf
     *
     * @param mode The string to convert
     * @return The mode represented by the String
     */
    public static DetectionMode fromString(String mode) {
        try {
            return DetectionMode.valueOf(mode);
        } catch (Exception e) {
            return null;
        }
    }

}
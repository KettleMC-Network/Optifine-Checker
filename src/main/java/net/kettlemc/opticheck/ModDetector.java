package net.kettlemc.opticheck;

import net.kettlemc.opticheck.config.Config;
import net.kettlemc.opticheck.config.DetectionMode;

public class ModDetector {

    /**
     * Checks if the listed classes are available and determines if the screen should be displayed.
     * If detection mode is ONE_MISSING, it will return true if one of the classes are missing
     * If detection mode is ALL_MISSING, it will return true if all classes are missing
     * If detection mode is ONE_AVAILABLE, it will return true if one of the classes are available
     * If detection mode is ALL_AVAILABLE, it will return true if all classes are available
     *
     * @return Whether the screen should be displayed
     */
    public static boolean checkClasses() {
        DetectionMode mode = DetectionMode.fromString(Config.DETECTION_MODE.getString());

        if (mode == null) {
            OptiCheckMod.getLogger().error("Detected config error, please use a valid detection mode!");
            return false;
        }

        int clazzAmount = Config.CLASSES.getStringList().length;
        int clazzCounter = 0;

        for (String clazz : Config.CLASSES.getStringList()) {
            try {
                Class.forName(clazz);
                clazzCounter++;
                OptiCheckMod.getLogger().warn(String.format("Found class %s.", clazz));
            } catch (ClassNotFoundException e) {
                OptiCheckMod.getLogger().warn(String.format("Couldn't find class %s.", clazz));
            }
        }

        switch (mode) {
            case ONE_MISSING:
                return clazzCounter != clazzAmount;
            case ALL_MISSING:
                return clazzCounter == 0;
            case ONE_AVAILABLE:
                return clazzCounter != 0;
            case ALL_AVAILABLE:
                return clazzCounter == clazzAmount;
            default:
                return false;
        }
    }
}
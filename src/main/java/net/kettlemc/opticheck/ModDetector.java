package net.kettlemc.opticheck;

import net.kettlemc.opticheck.config.Config;

public class ModDetector {

    /**
     * Checks if all the classes are available
     * If detection mode is MISSING, it will return true if all classes are missing
     * If detection mode is AVAILABLE, it will return true if all classes are available
     *
     * @return boolean - if the class was found
     */
    public static boolean checkClasses() {
        boolean availableMode = Config.instance().detectionMode.equals(Config.AVAILABLE);
        try {
            for (String clazz : Config.instance().classes)
                Class.forName(clazz);
            if (availableMode)
                return true;
            return false;
        } catch (ClassNotFoundException e) {
            OptiCheckMod.getLogger().warn("Failed to find class " + e.getMessage());
            if (availableMode)
                return false;
            return true;
        }
    }
}

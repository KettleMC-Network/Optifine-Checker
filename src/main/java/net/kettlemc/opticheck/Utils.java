package net.kettlemc.opticheck;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.awt.*;
import java.io.File;
import java.net.URI;
import java.util.List;

public class Utils {

    private static final String LINE_SEPARATOR = "%nl";

    public static void handleGuiText(String text, FontRenderer fontRenderer, Gui gui, int width, int height) {

        int heightLoc = 85;

        String[] lines = color(text).split(LINE_SEPARATOR);
        for (String s : lines) {

            List<String> info = fontRenderer.listFormattedStringToWidth(s, width - 40);
            for (String infoCut : info) {
                gui.drawCenteredString(fontRenderer, infoCut, width / 2, height / 2 - heightLoc, 0xFFFFFF);
                heightLoc = heightLoc - 12;
            }
        }
    }

    public static String color(String text) {
        return color('&', text);
    }

    // Inspired by https://hub.spigotmc.org/stash/projects/SPIGOT/repos/bukkit/browse/src/main/java/org/bukkit/ChatColor.java#216
    public static String color(char colorChar, String text) {

        char[] chars = text.toCharArray();
        for (int i = 0; i < chars.length - 1; i++) {
            if (chars[i] == colorChar && "0123456789AaBbCcDdEeFfKkLlMmNnOoRrXx".indexOf(chars[i + 1]) > -1) {
                chars[i] = '\u00A7';
                chars[i + 1] = Character.toLowerCase(chars[i + 1]);
            }
        }
        return new String(chars);
    }


    @SideOnly(Side.CLIENT)
    public static boolean openModsFolder() {
        OptiCheckMod.getLogger().info("Trying to open mods folder");
        try {
            if (!Desktop.isDesktopSupported() || !Desktop.getDesktop().isSupported(Desktop.Action.OPEN)) {
                OptiCheckMod.getLogger().error("Couldn't open mods folder!");
                return false;
            }

            Desktop.getDesktop().open(new File(Minecraft.getMinecraft().mcDataDir + "/mods/"));
            OptiCheckMod.getLogger().info("Opened mods folder successfully!");
            return true;

        } catch (Exception exception) {
            OptiCheckMod.getLogger().error("Error while opening the mods folder.", exception);
            exception.printStackTrace();
            return false;
        }
    }

    public static boolean openUrl(URI uri) {

        OptiCheckMod.getLogger().info("Trying to load URL " + uri);
        try {
            if (!Desktop.isDesktopSupported() || !Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                OptiCheckMod.getLogger().error("Couldn't open URL!");
                return false;
            }

            Desktop.getDesktop().browse(uri);
            OptiCheckMod.getLogger().info("Opened URL successfully!");
            return true;

        } catch (Exception exception) {
            OptiCheckMod.getLogger().error("Error while opening the url.", exception);
            exception.printStackTrace();
            return false;
        }
    }
}

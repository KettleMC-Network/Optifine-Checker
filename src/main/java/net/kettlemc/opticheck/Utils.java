package net.kettlemc.opticheck;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.text.TextFormatting;

import java.awt.*;
import java.net.URI;
import java.util.List;

public class Utils {

    private static final String LINE_SEPERATOR = "%nl";

    public static void handleGuiText(String text, FontRenderer fontRenderer, Gui gui, int width, int height) {

        int heightLoc = 85;

        String[] lines = color(text).split(LINE_SEPERATOR);
        for (String s : lines) {

            List<String> info = fontRenderer.listFormattedStringToWidth(s, width - 40);
            for (String infoCut : info) {
                gui.drawCenteredString(fontRenderer, infoCut, width / 2, height / 2 - heightLoc, 0xFFFFFF);
                heightLoc = heightLoc - 12;
            }
        }
    }

    public static String color(String text) {
        return text.replace("&", "\u00a7");
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

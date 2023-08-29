package net.kettlemc.opticheck.screen;

import net.kettlemc.opticheck.OptiCheckMod;
import net.kettlemc.opticheck.Utils;
import net.kettlemc.opticheck.config.DownloadMode;
import net.kettlemc.opticheck.config.OptiCheckConfig;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.net.URI;

@SideOnly(Side.CLIENT)
public class OptiCheckScreen extends GuiScreen {

    @Override
    public void initGui() {
        if (OptiCheckConfig.Settings.DOWNLOAD_MODE == DownloadMode.REMIND) {
            this.buttonList.add(new GuiButton(OptiCheckConfig.Advanced.CONTINUE_QUIT_BUTTON_ID, this.width / 2 - 204, this.height / 2 + 96, 124, 20, Utils.color(OptiCheckConfig.Messages.CONTINUE_BUTTON_TEXT)));
        } else {
            buttonList.clear();
            this.buttonList.add(new GuiButton(OptiCheckConfig.Advanced.CONTINUE_QUIT_BUTTON_ID, this.width / 2 - 204, this.height / 2 + 96, 124, 20, Utils.color(OptiCheckConfig.Messages.QUIT_BUTTON_TEXT)));
        }
        this.buttonList.add(new GuiButton(OptiCheckConfig.Advanced.MODS_FOLDER_BUTTON_ID, this.width / 2 - 62, this.height / 2 + 96, 124, 20, Utils.color(OptiCheckConfig.Messages.MODS_FOLDER_BUTTON_TEXT)));
        this.buttonList.add(new GuiButton(OptiCheckConfig.Advanced.LINK_BUTTON_ID, this.width / 2 + 80, this.height / 2 + 96, 124, 20, Utils.color(OptiCheckConfig.Messages.LINK_BUTTON_TEXT)));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        drawCenteredString(this.fontRenderer, Utils.color(OptiCheckConfig.Messages.SCREEN_TITLE), this.width / 2, this.height / 2 - 100, 0xFFFFFF);
        Utils.handleGuiText(OptiCheckConfig.Messages.SCREEN_MESSAGE, fontRenderer, this, this.width, this.height);

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        int linkId = OptiCheckConfig.Advanced.LINK_BUTTON_ID;
        int otherId = OptiCheckConfig.Advanced.CONTINUE_QUIT_BUTTON_ID;
        int modsId = OptiCheckConfig.Advanced.MODS_FOLDER_BUTTON_ID;

        // Continue/Quit
        if (button.id == otherId) {

            // Disable all button
            for (GuiButton b : buttonList) {
                b.enabled = false;
            }

            // Shutdown or display main menu
            if (OptiCheckConfig.Settings.DOWNLOAD_MODE == DownloadMode.REMIND) {
                this.mc.displayGuiScreen(null); // Clears the current screen
                OptiCheckConfig.setAlreadyDisplayed(true); // Screen has been displayed already
            } else {
                this.mc.shutdown(); // Closes the Game
            }

        }

        // Open Link
        else if (button.id == linkId) {
            try {
                Utils.openUrl(getUrl());
            } catch (Exception exception) {
                OptiCheckMod.getLogger().error("Couldn't open link!");
                exception.printStackTrace();
            }
        }

        // Open the mods folder
        else if (button.id == modsId) {
            try {
                Utils.openModsFolder();
            } catch (Exception exception) {
                OptiCheckMod.getLogger().error("Couldn't open mods folder!");
                exception.printStackTrace();
            }
        }
    }

    public URI getUrl() {
        return URI.create(OptiCheckConfig.Settings.DOWNLOAD_URL);
    }

    @Override
    protected void keyTyped(char typedChar, int keycode) {
        // We don't do this here.
    }

}
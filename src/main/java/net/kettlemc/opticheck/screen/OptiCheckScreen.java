package net.kettlemc.opticheck.screen;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.kettlemc.opticheck.OptiCheckMod;
import net.kettlemc.opticheck.Utils;
import net.kettlemc.opticheck.config.Config;
import net.kettlemc.opticheck.config.DownloadMode;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

import java.net.URI;

@SideOnly(Side.CLIENT)
public class OptiCheckScreen extends GuiScreen {

    @Override
    public void initGui() {
        if (DownloadMode.fromString(Config.instance().DOWNLOAD_MODE.getString()) == DownloadMode.REMIND) {
            this.buttonList.add(new GuiButton(Config.instance().CONTINUE_QUIT_BUTTON_ID.getInt(), this.width / 2 - 204, this.height / 2 + 96, 124, 20, Utils.color(Config.instance().CONTINUE_BUTTON_TEXT.getString())));
        } else {
            buttonList.clear();
            this.buttonList.add(new GuiButton(Config.instance().CONTINUE_QUIT_BUTTON_ID.getInt(), this.width / 2 - 204, this.height / 2 + 96, 124, 20, Utils.color(Config.instance().QUIT_BUTTON_TEXT.getString())));
        }
        this.buttonList.add(new GuiButton(Config.instance().MODS_FOLDER_BUTTON_ID.getInt(), this.width / 2 - 62, this.height / 2 + 96, 124, 20, Utils.color(Config.instance().MODS_FOLDER_BUTTON_TEXT.getString())));
        this.buttonList.add(new GuiButton(Config.instance().LINK_BUTTON_ID.getInt(), this.width / 2 + 80, this.height / 2 + 96, 124, 20, Utils.color(Config.instance().LINK_BUTTON_TEXT.getString())));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        drawCenteredString(this.fontRendererObj, Utils.color(Config.instance().SCREEN_TITLE.getString()), this.width / 2, this.height / 2 - 100, 0xFFFFFF);
        Utils.handleGuiText(Config.instance().SCREEN_MESSAGE.getString(), fontRendererObj, this, this.width, this.height);

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        int linkId = Config.instance().LINK_BUTTON_ID.getInt();
        int otherId = Config.instance().CONTINUE_QUIT_BUTTON_ID.getInt();
        int modsId = Config.instance().MODS_FOLDER_BUTTON_ID.getInt();

        // Continue/Quit
        if (button.id == otherId) {

            // Disable all button
            for (Object b : buttonList) {
                ((GuiButton) b).enabled = false;
            }

            // Shutdown or display main menu
            if (DownloadMode.fromString(Config.instance().DOWNLOAD_MODE.getString()) == DownloadMode.REMIND) {
                this.mc.displayGuiScreen(null); // Clears the current screen
                Config.instance().setAlreadyDisplayed(true); // Screen has been displayed already
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
        return URI.create(Config.instance().DOWNLOAD_URL.getString());
    }

    @Override
    protected void keyTyped(char typedChar, int keycode) {
        // We don't do this here.
    }

}

package net.kettlemc.opticheck.screen;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.kettlemc.opticheck.OptiCheckMod;
import net.kettlemc.opticheck.Utils;
import net.kettlemc.opticheck.config.Config;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

import java.net.URI;

@SideOnly(Side.CLIENT)
public class OptiCheckScreen extends GuiScreen {

    @Override
    public void initGui() {
        if (Config.instance().displayMode.equals(Config.REMIND)) {
            this.buttonList.add(new GuiButton(Config.instance().buttonContinue, this.width / 2 - 154, this.height / 2 + 96, 144, 20, "Continue"));
        } else {
            buttonList.clear();
            this.buttonList.add(new GuiButton(Config.instance().buttonContinue, this.width / 2 - 154, this.height / 2 + 96, 144, 20, "Quit Game"));
        }
        this.buttonList.add(new GuiButton(Config.instance().buttonLink, this.width / 2 + 10, this.height / 2 + 96, 144, 20, "Link"));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        drawCenteredString(this.fontRendererObj, Utils.color(Config.instance().title), this.width / 2, this.height / 2 - 100, 0xFFFFFF);
        Utils.handleGuiText(Config.instance().message, fontRendererObj, this, this.width, this.height);

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        int linkId = Config.instance().buttonLink;
        int otherId = Config.instance().buttonContinue;
        if (button.id == otherId) { // Continue/Quit
            for (Object b : buttonList) {
                ((GuiButton) b).enabled = false;
            }
            if (Config.instance().displayMode.equals(Config.REMIND))
                this.mc.displayGuiScreen(null); // Clears the current screen
            else
                this.mc.shutdown(); // Closes the Game
        } else if (button.id == linkId) { // Link
            try {
                Utils.openUrl(getUrl());
            } catch (Exception exception) {
                OptiCheckMod.getLogger().error("Couldn't open link!");
                exception.printStackTrace();
            }
        }
        Config.instance().setAlreadyDisplayed(true);
    }

    public URI getUrl() {
        return URI.create(Config.instance().url);
    }

    @Override
    protected void keyTyped(char typedChar, int keycode) {
        // We don't do this here.
    }

}

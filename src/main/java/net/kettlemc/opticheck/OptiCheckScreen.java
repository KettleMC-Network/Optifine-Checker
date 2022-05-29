package net.kettlemc.opticheck;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

import java.net.URI;

@SideOnly(Side.CLIENT)
public class OptiCheckScreen extends GuiScreen {

    @Override
    public void initGui() {
        if (OptiCheckMod.getConfig().getValueAsInt("mode", 0) == 0) {
            this.buttonList.add(new GuiButton(0, this.width / 2 - 154, this.height / 2 + 96, 144, 20, "Continue"));
        } else {
            buttonList.clear();
            this.buttonList.add(new GuiButton(0, this.width / 2 - 154, this.height / 2 + 96, 144, 20, "Quit Game"));
        }
        this.buttonList.add(new GuiButton(1, this.width / 2 + 10, this.height / 2 + 96, 144, 20, "Link"));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        drawCenteredString(this.fontRendererObj, Utils.color(OptiCheckMod.getConfig().getValue("title")), this.width / 2, this.height / 2 - 100, 0xFFFFFF);
        Utils.handleGuiText(OptiCheckMod.getConfig().getValue("message"), fontRendererObj, this, this.width, this.height);

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        switch (button.id) {
            // Continue or Quit
            case 0: {
                for (GuiButton b : buttonList) {
                    b.enabled = false;
                }
                if (OptiCheckMod.getConfig().getValueAsInt("mode", 0) == 0)
                    this.mc.displayGuiScreen(null); // Clears the current screen
                else
                    this.mc.shutdown(); // Closes the Game
                break;
            }
            // Link
            case 1: {
                try {
                    Utils.openUrl(URI.create(OptiCheckMod.getConfig().getValue("url")));
                } catch (Exception exception) {
                    OptiCheckMod.getLogger().error("Couldn't open link!");
                    exception.printStackTrace();
                }
                break;
            }
        }
        if (OptiCheckMod.getConfig().getValueAsBoolean("only-display-once", false)) {
            OptiCheckMod.getConfig().setValue("already-displayed", true); // only display the screen once
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keycode) {
        // We don't do this here.
    }

}

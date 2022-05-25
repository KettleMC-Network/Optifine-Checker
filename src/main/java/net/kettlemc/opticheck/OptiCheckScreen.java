package net.kettlemc.opticheck;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.net.URI;

@SideOnly(Side.CLIENT)
public class OptiCheckScreen extends GuiScreen {

    @Override
    public void initGui() {
        if (OptiCheckConfig.mode == 0) {
            this.buttonList.add(new GuiButton(0, this.width / 2 - 154, this.height / 2 + 96, 144, 20, "Continue"));
        } else {
            buttonList.clear();
            this.buttonList.add(new GuiButton(0, this.width / 2 - 154, this.height / 2 + 96, 144, 20, "Quit Game"));
        }
        this.buttonList.add(new GuiButton(1, this.width / 2 + 10, this.height / 2 + 96, 144, 20, "Link"));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        GlStateManager.disableTexture2D();
        GlStateManager.enableTexture2D();

        drawDefaultBackground();
        drawCenteredString(this.fontRenderer, Utils.color(OptiCheckConfig.title), this.width / 2, this.height / 2 - 100, 0xFFFFFF);
        Utils.handleGuiText(OptiCheckConfig.message, fontRenderer, this, this.width, this.height);

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
                if (OptiCheckConfig.mode == 0)
                    this.mc.displayGuiScreen(null); // Clears the current screen
                else
                    this.mc.shutdown(); // Closes the Game
                break;
            }
            // Link
            case 1: {
                try {
                    Utils.openUrl(URI.create(OptiCheckConfig.url));
                } catch (Exception exception) {
                    OptiCheckMod.getLogger().error("Couldn't open link!");
                    exception.printStackTrace();
                }
                break;
            }
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keycode) {
        // We don't do this here.
    }

}

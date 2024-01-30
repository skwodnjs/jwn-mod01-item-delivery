package net.jwn.mod.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.jwn.mod.Main;
import net.jwn.mod.networking.ModMessages;
import net.jwn.mod.networking.packet.ClosedC2SPacket;
import net.jwn.mod.util.KeyBindings;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.*;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.server.ServerLifecycleHooks;

public class TestScreen extends AbstractContainerScreen<TestMenu> {
    private int leftPos, topPos;
    private static final ResourceLocation BACKGROUND_RESOURCE = new ResourceLocation(Main.MOD_ID, "textures/gui/gui.png");

    String[] playerList;
    private int max;
    private int index = 0;

    public TestScreen(TestMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();
        this.leftPos = (width - 176) / 2;
        this.topPos = (height - 166) / 2;

        playerList = ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayerNamesArray();
        max = playerList.length - 1;

        ImageButton imageButton = new ImageButton(leftPos + 134, topPos + 45, 10, 15, 0, 166, 0,
                BACKGROUND_RESOURCE, 256, 256, pButton -> {
            System.out.println("send to " + playerList[index]);
        });
        addRenderableWidget(imageButton);

        ImageButton backButton = new ImageButton(leftPos + 65, topPos + 26, 4, 7, 10, 166, 0,
                BACKGROUND_RESOURCE, 256, 256, pButton -> {
            System.out.println("back!");
            if (index <= 0) index = max;
            else index -= 1;
        });

        ImageButton frontButton = new ImageButton(leftPos + 107, topPos + 26, 4, 7, 14, 166, 0,
                BACKGROUND_RESOURCE, 256, 256, pButton -> {
            System.out.println("front!");
            if (index < max) index += 1;
            else index = 0;
        });

        addRenderableWidget(backButton);
        addRenderableWidget(frontButton);
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pGuiGraphics);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);

        pGuiGraphics.drawString(font, playerList[index], leftPos + 88 - font.width(playerList[index]) / 2, topPos + 25, 0xFFFFFF, true);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float v, int i, int i1) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, BACKGROUND_RESOURCE);
        guiGraphics.blit(BACKGROUND_RESOURCE, leftPos, topPos, 0, 0, 176, 166);
    }

    @Override
    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
        if (pKeyCode == KeyBindings.TEST_KEY.getKey().getValue()) {
            onClose();
            return true;
        }
        return super.keyPressed(pKeyCode, pScanCode, pModifiers);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void onClose() {
        super.onClose();
        ModMessages.sendToServer(new ClosedC2SPacket(menu.container));
    }
}

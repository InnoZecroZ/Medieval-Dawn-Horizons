package com.medievaldawngroup.medievaldawnhorizons.mixin;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.gui.components.LogoRenderer;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(TitleScreen.class)
public class TitleScreenMixin {
    private static final ResourceLocation CUSTOM_LOGO = new ResourceLocation("medievaldawnhorizons", "textures/gui/mdhe.png");

    @Inject(method = "render", at = @At("HEAD"))
    private void onRender(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        // วาดพื้นหลัง Navy blue ทับ panorama
        guiGraphics.fill(0, 0, guiGraphics.guiWidth(), guiGraphics.guiHeight(), 0xFF001F4D);

        // วาด custom logo แทนโลโก้หลัก (ตำแหน่งเดียวกับโลโก้ Minecraft เดิม)
        int logoWidth = 400;
        int logoHeight = 74;
        int x = (guiGraphics.guiWidth() - logoWidth) / 2;
        int y = 30;
        guiGraphics.blit(CUSTOM_LOGO, x, y, 0, 0, logoWidth, logoHeight, logoWidth, logoHeight);
    }

    // ข้ามการวาดโลโก้ Minecraft เดิม
    @Redirect(
        method = "render",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/components/LogoRenderer;renderLogo(Lnet/minecraft/client/gui/GuiGraphics;IF)V"
        )
    )
    private void skipVanillaLogo(LogoRenderer instance, GuiGraphics guiGraphics, int width, float alpha) {
        // ไม่ทำอะไร = ไม่วาดโลโก้เดิม
    }
}
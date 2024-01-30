package net.jwn.mod.event;

import net.jwn.mod.Main;
import net.jwn.mod.networking.ModMessages;
import net.jwn.mod.networking.packet.ScreenC2SPacket;
import net.jwn.mod.util.KeyBindings;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

    public class ClientEvents {
        @Mod.EventBusSubscriber(modid = Main.MOD_ID, value = Dist.CLIENT)
        public static class ClientForgeEvents {
            @SubscribeEvent
            public static void onKeyInput(InputEvent.Key event) {
                Player player = Minecraft.getInstance().player;
                if (player == null) return;

                if (KeyBindings.TEST_KEY.consumeClick()) {
                    ModMessages.sendToServer(new ScreenC2SPacket());
                }
            }
        }

        @Mod.EventBusSubscriber(modid = Main.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
        public static class ClientModBusEvents {
            @SubscribeEvent
            public static void onKeyRegister(RegisterKeyMappingsEvent event) {
                event.register(KeyBindings.TEST_KEY);
            }
        }
}

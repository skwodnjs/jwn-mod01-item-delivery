package net.jwn.mod.networking.packet;

import net.jwn.mod.gui.TestMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkHooks;

import java.util.function.Supplier;

public class ScreenC2SPacket {
    public ScreenC2SPacket() {
    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public ScreenC2SPacket(FriendlyByteBuf buf) {
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE SERVER!
            ServerPlayer serverPlayer = context.getSender();

            NetworkHooks.openScreen(serverPlayer, new SimpleMenuProvider(
                    (containerId, playerInventory, player) -> new TestMenu(containerId, playerInventory),
                    Component.literal("택배")
            ));
        });
        return true;
    }
}

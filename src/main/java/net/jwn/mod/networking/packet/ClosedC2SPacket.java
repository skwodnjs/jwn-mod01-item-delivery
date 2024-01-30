package net.jwn.mod.networking.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ClosedC2SPacket {
    ItemStack itemStack0;
    ItemStack itemStack1;
    public ClosedC2SPacket(Container container) {
        itemStack0 = container.getItem(0);
        itemStack1 = container.getItem(1);
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeItem(itemStack0);
        buf.writeItem(itemStack1);
    }

    public ClosedC2SPacket(FriendlyByteBuf buf) {
        itemStack0 = buf.readItem();
        itemStack1 = buf.readItem();
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE SERVER!
            ServerPlayer serverPlayer = context.getSender();

            serverPlayer.addItem(itemStack0);
            serverPlayer.addItem(itemStack1);
        });
        return true;
    }
}

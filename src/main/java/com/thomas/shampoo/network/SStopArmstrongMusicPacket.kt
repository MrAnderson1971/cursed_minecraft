package com.thomas.shampoo.network;

import com.thomas.shampoo.entity.StevenArmstrong;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.network.CustomPayloadEvent;

public class SStopArmstrongMusicPacket {
    public SStopArmstrongMusicPacket() {
    }

    public SStopArmstrongMusicPacket(FriendlyByteBuf buf) {
    }

    public void encode(FriendlyByteBuf buf) {
    }

    public void handle(CustomPayloadEvent.Context context) {
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player != null) {
                Minecraft.getInstance().getMusicManager().stopPlaying(StevenArmstrong.ARMSTRONG_MUSIC);
            }
        });
        context.setPacketHandled(true);
    }
}

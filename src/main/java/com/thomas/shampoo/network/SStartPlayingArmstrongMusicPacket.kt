package com.thomas.shampoo.network;

import com.thomas.shampoo.entity.StevenArmstrong;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.network.CustomPayloadEvent;

public class SStartPlayingArmstrongMusicPacket {
    public SStartPlayingArmstrongMusicPacket() {}

    public SStartPlayingArmstrongMusicPacket(FriendlyByteBuf buf) {
    }

    public void encode(FriendlyByteBuf buf) {
    }

    public void handle(CustomPayloadEvent.Context context) {
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player != null) {
                if (!Minecraft.getInstance().getMusicManager().isPlayingMusic(StevenArmstrong.ARMSTRONG_MUSIC)) {
                    Minecraft.getInstance().getMusicManager().startPlaying(StevenArmstrong.ARMSTRONG_MUSIC);
                }
            }
        });
        context.setPacketHandled(true);
    }
}

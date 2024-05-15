package com.thomas.shampoo.network;

import com.thomas.shampoo.entity.StevenArmstrong;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.event.network.CustomPayloadEvent;

public class SStopArmstrongMusicPacket {
    public SStopArmstrongMusicPacket() {}

    public SStopArmstrongMusicPacket(FriendlyByteBuf buf) {
    }

    public void encode(FriendlyByteBuf buf) {

    }

    public void handle(CustomPayloadEvent.Context context) {
        Minecraft.getInstance().getMusicManager().stopPlaying(StevenArmstrong.ARMSTRONG_MUSIC);
    }
}

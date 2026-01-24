package com.thomas.shampoo.network

import com.thomas.shampoo.entity.StevenArmstrong
import net.minecraft.client.Minecraft
import net.minecraft.network.FriendlyByteBuf
import net.minecraftforge.event.network.CustomPayloadEvent

class SStopArmstrongMusicPacket {
  constructor()

  constructor(buf: FriendlyByteBuf?)

  fun encode(buf: FriendlyByteBuf?) {}

  fun handle(context: CustomPayloadEvent.Context) {
    context.enqueueWork {
        val player = context.getSender()
        if (player != null) {
            Minecraft.getInstance().musicManager.stopPlaying(StevenArmstrong.ARMSTRONG_MUSIC)
        }
    }
      context.packetHandled = true
  }
}

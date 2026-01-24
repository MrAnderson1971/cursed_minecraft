package com.thomas.shampoo.network

import com.thomas.shampoo.entity.StevenArmstrong
import net.minecraft.client.Minecraft
import net.minecraft.network.FriendlyByteBuf
import net.minecraftforge.event.network.CustomPayloadEvent

class SStartPlayingArmstrongMusicPacket {
  constructor()

  constructor(buf: FriendlyByteBuf?)

  fun encode(buf: FriendlyByteBuf?) {}

  fun handle(context: CustomPayloadEvent.Context) {
    context.enqueueWork {
      val player = context.getSender()
      if (player != null) {
        if (!Minecraft.getInstance().musicManager.isPlayingMusic(StevenArmstrong.ARMSTRONG_MUSIC)) {
          Minecraft.getInstance().musicManager.startPlaying(StevenArmstrong.ARMSTRONG_MUSIC)
        }
      }
    }
    context.packetHandled = true
  }
}

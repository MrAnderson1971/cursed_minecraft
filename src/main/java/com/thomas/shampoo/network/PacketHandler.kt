package com.thomas.shampoo.network

import com.thomas.shampoo.ShampooMod
import java.util.function.BiConsumer
import net.minecraft.resources.ResourceLocation
import net.minecraftforge.network.ChannelBuilder
import net.minecraftforge.network.NetworkDirection
import net.minecraftforge.network.PacketDistributor
import net.minecraftforge.network.SimpleChannel

object PacketHandler {
  private val INSTANCE: SimpleChannel =
      ChannelBuilder.named(ResourceLocation(ShampooMod.MODID, "main"))
          .serverAcceptedVersions { _, _ -> true }
          .clientAcceptedVersions { _, _ -> true }
          .networkProtocolVersion(1)
          .simpleChannel()

  fun register() {
    INSTANCE.messageBuilder(
            SStopArmstrongMusicPacket::class.java,
            NetworkDirection.PLAY_TO_SERVER,
        )
        .encoder { obj, buf -> obj.encode(buf) }
        .decoder { buf -> SStopArmstrongMusicPacket(buf) }
        .consumerNetworkThread(BiConsumer { obj, context -> obj.handle(context) })
        .add()
    INSTANCE.messageBuilder(
            SStartPlayingArmstrongMusicPacket::class.java,
            NetworkDirection.PLAY_TO_SERVER,
        )
        .encoder { obj, buf -> obj.encode(buf) }
        .decoder { buf -> SStartPlayingArmstrongMusicPacket(buf) }
        .consumerNetworkThread(BiConsumer { obj, context -> obj.handle(context) })
        .add()
  }

  @JvmStatic
  fun sendToServer(msg: Any?) {
    INSTANCE.send(msg, PacketDistributor.SERVER.noArg())
  }

  fun sendToAllClients(msg: Any?) {
    INSTANCE.send(msg, PacketDistributor.ALL.noArg())
  }
}

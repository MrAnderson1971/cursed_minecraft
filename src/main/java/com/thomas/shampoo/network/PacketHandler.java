package com.thomas.shampoo.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.SimpleChannel;

import static com.thomas.shampoo.ShampooMod.MODID;

public class PacketHandler {
    private static final SimpleChannel INSTANCE = ChannelBuilder.named(
            new ResourceLocation(MODID, "main"))
            .serverAcceptedVersions((status, version) -> true)
            .clientAcceptedVersions((status, version) -> true)
            .networkProtocolVersion(1)
            .simpleChannel();

    public static void register() {
        INSTANCE.messageBuilder(SStopArmstrongMusicPacket.class, NetworkDirection.PLAY_TO_SERVER)
                .encoder(SStopArmstrongMusicPacket::encode)
                .decoder(SStopArmstrongMusicPacket::new)
                .consumerNetworkThread(SStopArmstrongMusicPacket::handle)
                .add();
    }

    public static void sendToServer(Object msg) {
        INSTANCE.send(msg, PacketDistributor.SERVER.noArg());
    }

    public static void sendToAllClients(Object msg) {
        INSTANCE.send(msg, PacketDistributor.ALL.noArg());
    }
}

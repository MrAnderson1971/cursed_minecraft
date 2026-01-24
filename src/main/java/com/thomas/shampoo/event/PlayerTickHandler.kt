package com.thomas.shampoo.event;

import com.thomas.shampoo.ShampooMod;
import com.thomas.shampoo.effect.EffectInit;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ShampooMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerTickHandler {

    @SubscribeEvent
    public static void playerTick(TickEvent.PlayerTickEvent event) {
        handleFlying(event);
    }

    // Allows flying with the FLYING status effect.
    private static void handleFlying(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.START) {
            return;
        }

        Player player = event.player;
        if (player.isSpectator() || player.isCreative()) {
            return;
        }
        MobEffectInstance flyingEffect = player.getEffect(EffectInit.FLYING.get());

        // Handle enabling/disabling flight
        boolean canFly = flyingEffect != null;
        player.getAbilities().mayfly = canFly;

        if (!player.getAbilities().flying && canFly && !player.onGround()) {
            // Allow flying only if not on the ground and capable of flight
            player.getAbilities().flying = true;
        } else if (player.getAbilities().flying && !canFly) {
            // Disable flying if currently flying but should no longer be able to
            player.getAbilities().flying = false;
        }

        if (player.getAbilities().mayfly != canFly || player.getAbilities().flying != canFly) {
            player.onUpdateAbilities(); // Update player abilities if there's a change
        }

        if (canFly) {
            player.fallDistance = 0.0F;
        }
    }
}

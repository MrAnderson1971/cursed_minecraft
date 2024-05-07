package com.thomas.shampoo.event;

import com.thomas.shampoo.entity.StevenArmstrong;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.thomas.shampoo.ShampooMod.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class LivingFallHandler {

    // Cancel fall damage
    @SubscribeEvent
    public void onLivingFall(LivingFallEvent event) {
        if (event.getEntity() instanceof StevenArmstrong) {
            return;
        }
    }
}

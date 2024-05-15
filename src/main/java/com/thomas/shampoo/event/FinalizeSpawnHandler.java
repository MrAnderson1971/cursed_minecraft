package com.thomas.shampoo.event;

import com.thomas.shampoo.ShampooMod;
import com.thomas.shampoo.entity.StevenArmstrong;
import net.minecraft.world.entity.Mob;
import net.minecraftforge.event.entity.living.MobSpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ShampooMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class FinalizeSpawnHandler {

    @SubscribeEvent
    public void onMobSpawnFinalize(MobSpawnEvent.FinalizeSpawn event) {
        Mob mob = event.getEntity();
        if (mob instanceof StevenArmstrong stevenArmstrong) {
            stevenArmstrong.hasTriggeredLowHealthState = false;
            stevenArmstrong.hasTriggeredStartState = false;
        }
    }
}

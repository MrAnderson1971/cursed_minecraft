package com.thomas.shampoo.world;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.thomas.shampoo.ShampooMod.MODID;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, MODID);

    // Dynamically generated sound events
    public static final RegistryObject<SoundEvent> ARMSTRONG_MUSIC = SOUNDS.register("armstrong_music",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MODID, "armstrong_music")));

    public static final RegistryObject<SoundEvent> ARMSTRONG_AGITATED = SOUNDS.register("armstrong_agitated",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MODID, "armstrong_agitated")));

    public static final RegistryObject<SoundEvent> ARMSTRONG_AMBIENT = SOUNDS.register("armstrong_ambient",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MODID, "armstrong_ambient")));

    public static final RegistryObject<SoundEvent> ARMSTRONG_ANGRY = SOUNDS.register("armstrong_angry",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MODID, "armstrong_angry")));

    public static final RegistryObject<SoundEvent> ARMSTRONG_ATTACK_IMPACT = SOUNDS.register("armstrong_attack_impact",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MODID, "armstrong_attack_impact")));

    public static final RegistryObject<SoundEvent> ARMSTRONG_DEATH = SOUNDS.register("armstrong_death",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MODID, "armstrong_death")));

    public static final RegistryObject<SoundEvent> ARMSTRONG_DIG = SOUNDS.register("armstrong_dig",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MODID, "armstrong_dig")));

    public static final RegistryObject<SoundEvent> ARMSTRONG_EMERGE = SOUNDS.register("armstrong_emerge",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MODID, "armstrong_emerge")));

    public static final RegistryObject<SoundEvent> ARMSTRONG_HEARTBEAT = SOUNDS.register("armstrong_heartbeat",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MODID, "armstrong_heartbeat")));

    public static final RegistryObject<SoundEvent> ARMSTRONG_HURT = SOUNDS.register("armstrong_hurt",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MODID, "armstrong_hurt")));

    public static final RegistryObject<SoundEvent> ARMSTRONG_LISTENING = SOUNDS.register("armstrong_listening",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MODID, "armstrong_listening")));

    public static final RegistryObject<SoundEvent> ARMSTRONG_LISTENING_ANGRY = SOUNDS.register("armstrong_listening_angry",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MODID, "armstrong_listening_angry")));

    public static final RegistryObject<SoundEvent> ARMSTRONG_NEARBY_CLOSER = SOUNDS.register("armstrong_nearby_closer",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MODID, "armstrong_nearby_closer")));

    public static final RegistryObject<SoundEvent> ARMSTRONG_NEARBY_CLOSEST = SOUNDS.register("armstrong_nearby_closest",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MODID, "armstrong_nearby_closest")));

    public static final RegistryObject<SoundEvent> ARMSTRONG_NEARBY_CLOSE = SOUNDS.register("armstrong_nearby_close",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MODID, "armstrong_nearby_close")));

    public static final RegistryObject<SoundEvent> ARMSTRONG_ROAR = SOUNDS.register("armstrong_roar",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MODID, "armstrong_roar")));

    public static final RegistryObject<SoundEvent> ARMSTRONG_SNIFF = SOUNDS.register("armstrong_sniff",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MODID, "armstrong_sniff")));

    public static final RegistryObject<SoundEvent> ARMSTRONG_SONIC = SOUNDS.register("armstrong_sonic",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MODID, "armstrong_sonic")));

    public static final RegistryObject<SoundEvent> ARMSTRONG_STEP = SOUNDS.register("armstrong_step",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MODID, "armstrong_step")));

    public static final RegistryObject<SoundEvent> ARMSTRONG_TENDRIL_CLICKS = SOUNDS.register("armstrong_tendril_clicks",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MODID, "armstrong_tendril_clicks")));

}

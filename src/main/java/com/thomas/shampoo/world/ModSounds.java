package com.thomas.shampoo.world;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.thomas.shampoo.ShampooMod.MODID;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, MODID);

    public static final RegistryObject<SoundEvent> ARMSTRONG_MUSIC = SOUNDS.register("armstrong_music",
            ()-> SoundEvent.createVariableRangeEvent(new ResourceLocation(MODID, "armstrong_music")));
}

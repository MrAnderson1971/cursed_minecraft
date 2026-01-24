package com.thomas.shampoo.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.thomas.shampoo.ShampooMod.MODID;

public class EffectInit {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS
            = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, MODID);

    public static final RegistryObject<MobEffect> FLYING = MOB_EFFECTS.register("flying",
            () -> new BasicCustomEffect(MobEffectCategory.BENEFICIAL, 0x2FADCF));

    public static final RegistryObject<MobEffect> LASER = MOB_EFFECTS.register("laser",
            () -> new LaserEffect(MobEffectCategory.BENEFICIAL, 0xE80505));
}

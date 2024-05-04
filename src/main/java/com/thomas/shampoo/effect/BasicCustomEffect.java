package com.thomas.shampoo.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class BasicCustomEffect extends MobEffect {

    // Effects handled in PlayerTickHandler.java
    public BasicCustomEffect(MobEffectCategory mobEffectCategory, int i) {
        super(mobEffectCategory, i);
    }
}

package com.thomas.shampoo.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class FlyingEffect extends MobEffect {

    // Effects handled in PlayerTickHandler.java
    public FlyingEffect(MobEffectCategory mobEffectCategory, int i) {
        super(mobEffectCategory, i);
    }
}

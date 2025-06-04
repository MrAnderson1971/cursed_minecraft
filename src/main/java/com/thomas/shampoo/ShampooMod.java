package com.thomas.shampoo;

import com.thomas.shampoo.block.BlockInit;
import com.thomas.shampoo.effect.EffectInit;
import com.thomas.shampoo.entity.EntityInit;
import com.thomas.shampoo.item.CreativeTabs;
import com.thomas.shampoo.item.ItemInit;
import com.thomas.shampoo.world.ModSounds;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(ShampooMod.MODID)
public class ShampooMod {
    public static final String MODID = "shampoo";

    public ShampooMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ItemInit.ITEMS.register(modEventBus);
        BlockInit.BLOCKS.register(modEventBus);
        EffectInit.MOB_EFFECTS.register(modEventBus);
        EntityInit.ENTITIES.register(modEventBus);
        ModSounds.SOUNDS.register(modEventBus);
        CreativeTabs.CREATIVE_TABS.register(modEventBus);
    }
}

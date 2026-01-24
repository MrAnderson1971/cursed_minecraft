package com.thomas.shampoo.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import static com.thomas.shampoo.ShampooMod.MODID;

public class CreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    public static final RegistryObject<CreativeModeTab> HEISENBERGS_RV = CREATIVE_TABS.register("heisenbergs_rv",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ItemInit.HEISENBERG.get()))
                    .title(Component.translatable("creativetab.heisenbergs_rv"))
                    .displayItems((pitems, poutput) -> {
                        poutput.accept(ItemInit.TETRODOTOXIN.get());
                    }).build());
}

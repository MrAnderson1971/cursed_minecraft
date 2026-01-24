package com.thomas.shampoo.item

import com.thomas.shampoo.ShampooMod
import java.util.function.Supplier
import net.minecraft.core.registries.Registries
import net.minecraft.network.chat.Component
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.ItemStack
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.RegistryObject

object CreativeTabs {
  val CREATIVE_TABS: DeferredRegister<CreativeModeTab?> =
      DeferredRegister.create(
          Registries.CREATIVE_MODE_TAB,
          ShampooMod.MODID,
      )

  val HEISENBERGS_RV: RegistryObject<CreativeModeTab> =
      CREATIVE_TABS.register(
          "heisenbergs_rv",
          Supplier {
            CreativeModeTab.builder()
                .icon { ItemStack(ItemInit.HEISENBERG.get()) }
                .title(Component.translatable("creativetab.heisenbergs_rv"))
                .displayItems { _, poutput -> poutput.accept(ItemInit.TETRODOTOXIN.get()) }
                .build()
          },
      )
}

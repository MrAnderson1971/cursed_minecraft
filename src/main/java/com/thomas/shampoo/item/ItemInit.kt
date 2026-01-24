package com.thomas.shampoo.item

import com.thomas.shampoo.ShampooMod
import com.thomas.shampoo.block.BlockInit
import com.thomas.shampoo.effect.EffectInit
import com.thomas.shampoo.entity.EntityInit
import java.util.function.Supplier
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.food.FoodProperties
import net.minecraft.world.item.*
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.common.ForgeSpawnEggItem
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod.EventBusSubscriber
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import net.minecraftforge.registries.RegistryObject

@EventBusSubscriber(
    modid = ShampooMod.MODID,
    bus = EventBusSubscriber.Bus.MOD,
    value = [Dist.CLIENT],
)
object ItemInit {
  val ITEMS: DeferredRegister<Item> =
      DeferredRegister.create(ForgeRegistries.ITEMS, ShampooMod.MODID)

  // Block item for the Thomas ore
  val THOMAS_ORE_ITEM: RegistryObject<Item> =
      ITEMS.register(
          "thomas_ore",
      ) {
        BlockItem(BlockInit.THOMAS_ORE.get(), Item.Properties())
      }

  val JADE_PYRAMID_ITEM: RegistryObject<Item> =
      ITEMS.register(
          "jade_pyramid",
      ) {
        BlockItem(BlockInit.JADE_PYRAMID.get(), Item.Properties())
      }

  val JADE_ORE_ITEM: RegistryObject<Item> =
      ITEMS.register(
          "jade_ore",
      ) {
        BlockItem(BlockInit.JADE_ORE.get(), Item.Properties())
      }

  val JADE: RegistryObject<Item> = ITEMS.register("jade", Supplier { Item(Item.Properties()) })

  val DEEP_SUBSTRATE_FOLIATED_KALKITE_ITEM: RegistryObject<Item> =
      ITEMS.register(
          "deep_substrate_foliated_kalkite",
      ) {
        BlockItem(BlockInit.DEEP_SUBSTRATE_FOLIATED_KALKITE.get(), Item.Properties())
      }

  // Smelted Thomas
  val SMELTED_THOMAS: RegistryObject<Item> =
      ITEMS.register("smelted_thomas", Supplier { Item(Item.Properties()) })

  val EXPLODING_BELL: RegistryObject<Item> =
      ITEMS.register(
          "exploding_bell",
      ) {
        ExplodingBellItem(Item.Properties().stacksTo(1))
      }

  val WUCKET: RegistryObject<Item> =
      ITEMS.register("wucket") { WucketItem(Item.Properties().stacksTo(16)) }

  @JvmField
  val BATER_WUCKET: RegistryObject<Item> =
      ITEMS.register(
          "bater_wucket",
      ) {
        BaterWucketItem(Item.Properties().stacksTo(1))
      }

  // Thomas & Friends 3 in 1 Shampoo, Hair, and Body Wash
  val THOMAS_AND_FRIENDS_3_IN_1_SHAMPOO_HAIR_AND_BODY_WASH: RegistryObject<Item> =
      ITEMS.register(
          "thomas_and_friends_3_in_1_shampoo_hair_and_body_wash",
      ) {
        DrinkableItem(
            Item.Properties()
                .stacksTo(16)
                .rarity(Rarity.EPIC)
                .food(
                    FoodProperties.Builder()
                        .alwaysEat() // Can be eaten even when not hungry
                        // Enables creative-mode like flying when drunk.
                        .effect(
                            { MobEffectInstance(EffectInit.FLYING.get(), 200, 0) },
                            1.0f,
                        )
                        .build()
                )
        )
      }

  val TETRODOTOXIN: RegistryObject<Item> =
      ITEMS.register(
          "tetrodotoxin",
      ) {
        Item(
            Item.Properties()
                .stacksTo(16)
                .food(
                    FoodProperties.Builder()
                        .alwaysEat()
                        .effect(
                            { MobEffectInstance(EffectInit.LASER.get(), 200, 0) },
                            1f,
                        )
                        .build()
                )
        )
      }

  // Water
  val WATER_ITEM: RegistryObject<Item> =
      ITEMS.register("water_item", Supplier { Item(Item.Properties()) })

  val BIDEN_SPAWN_EGG: RegistryObject<ForgeSpawnEggItem> =
      ITEMS.register(
          "biden_spawn_egg",
      ) {
        ForgeSpawnEggItem(EntityInit.BIDEN, 0x001489, 0xFFFFFF, Item.Properties())
      }

  val OBAMA_SPAWN_EGG: RegistryObject<ForgeSpawnEggItem> =
      ITEMS.register(
          "obama_spawn_egg",
      ) {
        ForgeSpawnEggItem(EntityInit.OBAMA, 0x0015BC, 0xFF0000, Item.Properties())
      }

  val ARMSTRONG_SPAWN_EGG: RegistryObject<ForgeSpawnEggItem> =
      ITEMS.register(
          "steven_armstrong_spawn_egg",
      ) {
        ForgeSpawnEggItem(EntityInit.STEVEN_ARMSTRONG, 0xFFFFFF, 0xFAB5A9, Item.Properties())
      }

  // just for icon
  val HEISENBERG: RegistryObject<Item> = ITEMS.register("heisenberg") { Item(Item.Properties()) }

  @SubscribeEvent
  fun buildContents(event: BuildCreativeModeTabContentsEvent) {
    // Put my stuff in their proper creative tabs.
    if (event.tabKey === CreativeModeTabs.BUILDING_BLOCKS) {
      addItemToTab(event, THOMAS_ORE_ITEM)
      addItemToTab(event, JADE_PYRAMID_ITEM)
      addItemToTab(event, JADE_ORE_ITEM)
      addItemToTab(event, DEEP_SUBSTRATE_FOLIATED_KALKITE_ITEM)
    } else if (event.tabKey === CreativeModeTabs.INGREDIENTS) {
      addItemToTab(event, SMELTED_THOMAS)
      addItemToTab(event, JADE)
      addItemToTab(event, EXPLODING_BELL)
      addItemToTab(event, WATER_ITEM)
      addItemToTab(event, BATER_WUCKET)
      addItemToTab(event, WUCKET)
    } else if (event.tabKey === CreativeModeTabs.FOOD_AND_DRINKS) {
      addItemToTab(event, THOMAS_AND_FRIENDS_3_IN_1_SHAMPOO_HAIR_AND_BODY_WASH)
    } else if (event.tabKey === CreativeModeTabs.SPAWN_EGGS) {
      addItemToTab(event, BIDEN_SPAWN_EGG)
      addItemToTab(event, OBAMA_SPAWN_EGG)
      addItemToTab(event, ARMSTRONG_SPAWN_EGG)
    }
  }

  private fun addItemToTab(
      event: BuildCreativeModeTabContentsEvent,
      itemSupplier: RegistryObject<out Item>,
  ) {
    event.accept(
        itemSupplier.get().defaultInstance,
        CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS,
    )
  }
}

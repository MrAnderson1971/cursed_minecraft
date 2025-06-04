package com.thomas.shampoo.item;

import com.thomas.shampoo.ShampooMod;
import com.thomas.shampoo.block.BlockInit;
import com.thomas.shampoo.effect.EffectInit;
import com.thomas.shampoo.entity.EntityInit;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = ShampooMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ItemInit {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ShampooMod.MODID);

    // Block item for the Thomas ore
    public static final RegistryObject<Item> THOMAS_ORE_ITEM = ITEMS.register("thomas_ore",
            () -> new BlockItem(BlockInit.THOMAS_ORE.get(), new Item.Properties()));

    public static final RegistryObject<Item> JADE_PYRAMID_ITEM = ITEMS.register("jade_pyramid",
            () -> new BlockItem(BlockInit.JADE_PYRAMID.get(), new Item.Properties()));

    public static final RegistryObject<Item> JADE_ORE_ITEM = ITEMS.register("jade_ore",
            () -> new BlockItem(BlockInit.JADE_ORE.get(), new Item.Properties()));

    public static final RegistryObject<Item> JADE = ITEMS.register("jade",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> DEEP_SUBSTRATE_FOLIATED_KALKITE_ITEM = ITEMS.register("deep_substrate_foliated_kalkite",
            () -> new BlockItem(BlockInit.DEEP_SUBSTRATE_FOLIATED_KALKITE.get(), new Item.Properties()));

    // Smelted Thomas
    public static final RegistryObject<Item> SMELTED_THOMAS = ITEMS.register("smelted_thomas",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> EXPLODING_BELL = ITEMS.register("exploding_bell",
            () -> new ExplodingBellItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> WUCKET = ITEMS.register("wucket",
            () -> new WucketItem(new Item.Properties().stacksTo(16)));

    public static final RegistryObject<Item> BATER_WUCKET = ITEMS.register("bater_wucket",
            () -> new BaterWucketItem(new Item.Properties().stacksTo(1)));

    // Thomas & Friends 3 in 1 Shampoo, Hair, and Body Wash
    public static final RegistryObject<Item> THOMAS_AND_FRIENDS_3_IN_1_SHAMPOO_HAIR_AND_BODY_WASH = ITEMS.register("thomas_and_friends_3_in_1_shampoo_hair_and_body_wash",
            () -> new DrinkableItem(new Item.Properties()
                    .stacksTo(16)
                    .rarity(Rarity.EPIC)
                    .food(new FoodProperties.Builder()
                            .alwaysEat() // Can be eaten even when not hungry
                            // Enables creative-mode like flying when drunk.
                            .effect(() -> new MobEffectInstance(EffectInit.FLYING.get(), 200, 0), 1.0f)
                            .build())
            ));

    public static final RegistryObject<Item> TETRODOTOXIN = ITEMS.register("tetrodotoxin",
            () -> new Item(new Item.Properties()
                    .stacksTo(16)
                    .food(new FoodProperties.Builder()
                            .alwaysEat()
                            .effect(() -> new MobEffectInstance(EffectInit.LASER.get(), 200, 0), 1)
                            .build())
            ));

    // Water
    public static final RegistryObject<Item> WATER_ITEM = ITEMS.register("water_item",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<ForgeSpawnEggItem> BIDEN_SPAWN_EGG = ITEMS.register("biden_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityInit.BIDEN, 0x001489, 0xFFFFFF, new Item.Properties()));

    public static final RegistryObject<ForgeSpawnEggItem> OBAMA_SPAWN_EGG = ITEMS.register("obama_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityInit.OBAMA, 0x0015BC, 0xFF0000, new Item.Properties()));

    public static final RegistryObject<ForgeSpawnEggItem> ARMSTRONG_SPAWN_EGG = ITEMS.register("steven_armstrong_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityInit.STEVEN_ARMSTRONG, 0xFFFFFF, 0xFAB5A9, new Item.Properties()));

    // just for icon
    public static final RegistryObject<Item> HEISENBERG = ITEMS.register("heisenberg",
            () -> new Item(new Item.Properties()));

    @SubscribeEvent
    public static void buildContents(BuildCreativeModeTabContentsEvent event) {
        // Put my stuff in their proper creative tabs.
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            addItemToTab(event, THOMAS_ORE_ITEM);
            addItemToTab(event, JADE_PYRAMID_ITEM);
            addItemToTab(event, JADE_ORE_ITEM);
            addItemToTab(event, DEEP_SUBSTRATE_FOLIATED_KALKITE_ITEM);
        } else if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            addItemToTab(event, SMELTED_THOMAS);
            addItemToTab(event, JADE);
            addItemToTab(event, EXPLODING_BELL);
            addItemToTab(event, WATER_ITEM);
            addItemToTab(event, BATER_WUCKET);
            addItemToTab(event, WUCKET);
        } else if (event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS) {
            addItemToTab(event, THOMAS_AND_FRIENDS_3_IN_1_SHAMPOO_HAIR_AND_BODY_WASH);
        } else if (event.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {
            addItemToTab(event, BIDEN_SPAWN_EGG);
            addItemToTab(event, OBAMA_SPAWN_EGG);
            addItemToTab(event, ARMSTRONG_SPAWN_EGG);
        }
    }

    private static void addItemToTab(BuildCreativeModeTabContentsEvent event, RegistryObject<? extends Item> itemSupplier) {
        event.accept(itemSupplier.get().getDefaultInstance(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
    }
}

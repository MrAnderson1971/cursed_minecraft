package com.thomas.shampoo.entity;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.thomas.shampoo.ShampooMod.MODID;

public class EntityInit {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MODID);

    public static final RegistryObject<EntityType<CustomFireball>> CUSTOM_FIREBALL = ENTITIES.register("custom_fireball",
            () -> EntityType.Builder.<CustomFireball>of(CustomFireball::new, MobCategory.MISC)
                    .sized(1.0f, 1.0f)
                    .build(new ResourceLocation("minecraft", "textures/entity/fireball.png").toString())
    );

    public static final RegistryObject<EntityType<Biden>> BIDEN = ENTITIES.register("biden",
            () -> EntityType.Builder.of(Biden::new, MobCategory.MONSTER)
                    .sized(0.6F,1.8F) // These are typical dimensions for a player.
                    .build(new ResourceLocation(MODID, "biden").toString())
    );

    public static final RegistryObject<EntityType<Obama>> OBAMA = ENTITIES.register("obama",
            () -> EntityType.Builder.of(Obama::new, MobCategory.MONSTER)
                    .sized(0.6F,1.8F)
                    .build(new ResourceLocation(MODID, "obama").toString())
    );

    public static final RegistryObject<EntityType<StevenArmstrong>> STEVEN_ARMSTRONG = ENTITIES.register("steven_armstrong",
            () -> EntityType.Builder.of(StevenArmstrong::new, MobCategory.MONSTER)
                    .sized(0.9F,2.9F)
                    .build(new ResourceLocation(MODID, "steven_armstrong").toString())
    );
}

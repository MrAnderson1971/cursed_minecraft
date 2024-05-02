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
}

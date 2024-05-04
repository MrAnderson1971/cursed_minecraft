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

    public static final RegistryObject<EntityType<Biden>> BIDEN = ENTITIES.register("biden",
            () -> EntityType.Builder.of(Biden::new, MobCategory.MONSTER)
                    .sized(0.6F,1.8F) // These are typical dimensions for a player.
                    .build(new ResourceLocation(MODID, "biden").toString())
    );
}

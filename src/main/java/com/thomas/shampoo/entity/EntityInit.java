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

    public static final RegistryObject<EntityType<PinkSheep>> PINK_SHEEP = ENTITIES.register("pink_sheep",
            () -> EntityType.Builder.<PinkSheep>of(PinkSheep::new, MobCategory.MONSTER)
                    .sized(0.9F, 1.3F) // These are typical dimensions for a sheep.
                    .build(new ResourceLocation("minecraft", "textures/entity/sheep/sheep.png").toString())
    );
}

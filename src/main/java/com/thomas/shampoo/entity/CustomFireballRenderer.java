package com.thomas.shampoo.entity;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.projectile.LargeFireball;

public class CustomFireballRenderer extends EntityRenderer<CustomFireball> {
    private static final ResourceLocation FIREBALL_TEXTURE = new ResourceLocation("minecraft", "textures/entity/fireball.png");

    public CustomFireballRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(CustomFireball entity) {
        return FIREBALL_TEXTURE; // points directly to the vanilla fireball texture
    }
}

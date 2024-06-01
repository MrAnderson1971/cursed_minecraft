package com.thomas.shampoo.renderer;

import com.thomas.shampoo.entity.CustomFireball;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class CustomFireballRenderer extends EntityRenderer<CustomFireball> {
    private static final ResourceLocation FIREBALL_TEXTURE = new ResourceLocation("minecraft", "textures/entity/fireball.png");

    public CustomFireballRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull CustomFireball entity) {
        return FIREBALL_TEXTURE; // points directly to the vanilla fireball texture
    }
}

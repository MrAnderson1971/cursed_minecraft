package com.thomas.shampoo.renderer;

import com.thomas.shampoo.entity.PinkSheep;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class CustomSheepRenderer extends MobRenderer<PinkSheep, CustomSheepModel<PinkSheep>> {
    private static final ResourceLocation PINK_SHEEP_TEXTURES = new ResourceLocation("textures/entity/sheep/sheep.png");

    public CustomSheepRenderer(EntityRendererProvider.Context context) {
        super(context, new CustomSheepModel<>(context.bakeLayer(ModelLayers.SHEEP)), 0.7F);
    }

    @Override
    public ResourceLocation getTextureLocation(PinkSheep entity) {
        return PINK_SHEEP_TEXTURES; // This forces all instances of this mob to use the pink sheep texture
    }
}

package com.thomas.shampoo.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.thomas.shampoo.entity.Biden;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import static com.thomas.shampoo.ShampooMod.MODID;

public class BidenRenderer extends LivingEntityRenderer<Biden, BidenModel<Biden>> {
    private static final ResourceLocation BIDEN_TEXTURE = new ResourceLocation(MODID, "textures/entity/biden.png");

    public BidenRenderer(EntityRendererProvider.Context context) {
        super(context, new BidenModel<>(context.bakeLayer(ModelLayers.PLAYER)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(Biden entity) {
        return BIDEN_TEXTURE;  // Assigning Biden specific texture.
    }

    @Override
    protected void renderNameTag(Biden entity, Component displayName, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        // Do nothing, this will prevent the name tag from rendering
    }
}

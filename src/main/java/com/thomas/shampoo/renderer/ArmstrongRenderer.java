package com.thomas.shampoo.renderer;

import com.thomas.shampoo.entity.StevenArmstrong;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import static com.thomas.shampoo.ShampooMod.MODID;

@OnlyIn(Dist.CLIENT)
public class ArmstrongRenderer extends MobRenderer<StevenArmstrong, ArmstrongModel<StevenArmstrong>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(MODID,"textures/entity/steven_armstrong.png");

    public ArmstrongRenderer(EntityRendererProvider.Context context) {
        super(context, new ArmstrongModel<>(context.bakeLayer(ModModelLayers.ARMSTRONG_LAYER)), 0.9F);
    }

    public @NotNull ResourceLocation getTextureLocation(@NotNull StevenArmstrong entity) {
        return TEXTURE;
    }
}

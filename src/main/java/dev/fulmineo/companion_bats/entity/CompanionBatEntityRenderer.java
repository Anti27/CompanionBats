package dev.fulmineo.companion_bats.entity;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class CompanionBatEntityRenderer extends MobEntityRenderer<CompanionBatEntity, CompanionBatEntityModel> {
    private static final Identifier TEXTURE = new Identifier("textures/entity/bat.png");

    public CompanionBatEntityRenderer(EntityRenderDispatcher context) {
        super(context, new CompanionBatEntityModel(), 0.25F);
        this.addFeature(new CompanionBatArmorFeatureRenderer(this));
		this.addFeature(new CompanionBatAccessoryFeatureRenderer(this));
    }

    public Identifier getTexture(CompanionBatEntity batEntity) {
       return TEXTURE;
    }

    protected void scale(CompanionBatEntity batEntity, MatrixStack matrixStack, float f) {
       matrixStack.scale(0.35F, 0.35F, 0.35F);
    }

    protected void setupTransforms(CompanionBatEntity batEntity, MatrixStack matrixStack, float f, float g, float h) {
        if (batEntity.isRoosting()) {
            matrixStack.translate(0.0D, -0.10000000149011612D, 0.0D);
        } else {
            matrixStack.translate(0.0D, (double)(MathHelper.cos(f * 0.3F) * 0.1F), 0.0D);
        }
        super.setupTransforms(batEntity, matrixStack, f, g, h);
    }
}
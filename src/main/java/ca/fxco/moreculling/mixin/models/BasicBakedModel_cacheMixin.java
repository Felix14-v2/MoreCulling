package ca.fxco.moreculling.mixin.models;

import ca.fxco.moreculling.api.model.BakedOpacity;
import ca.fxco.moreculling.api.sprite.SpriteOpacity;
import ca.fxco.moreculling.utils.SpriteUtils;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.render.model.BasicBakedModel;
import net.minecraft.client.render.model.json.ModelOverrideList;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.Sprite;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Map;

@Mixin(BasicBakedModel.class)
public abstract class BasicBakedModel_cacheMixin implements BakedOpacity {

    @Unique
    private boolean hasTranslucency;

    @Override
    public boolean hasTextureTranslucency() {
        return hasTranslucency;
    }


    @Inject(
            method = "<init>",
            at = @At("RETURN")
    )
    private void onInit(List<BakedQuad> quads, Map<Direction, List<BakedQuad>> faceQuads, boolean usesAo,
                        boolean isSideLit, boolean hasDepth, Sprite sprite, ModelTransformation transformation,
                        ModelOverrideList itemPropertyOverrides, CallbackInfo ci) {
        hasTranslucency = ((SpriteOpacity)sprite).hasTranslucency();
        if (!hasTranslucency) {
            for (BakedQuad baked : quads) {
                if (((SpriteOpacity)baked.getSprite()).hasTranslucency()) {
                    hasTranslucency = true;
                    break;
                }
            }
        }
        if (!hasTranslucency) {
            for (List<BakedQuad> bakedList : faceQuads.values()) {
                for (BakedQuad baked : bakedList) {
                    if (((SpriteOpacity)baked.getSprite()).hasTranslucency()) {
                        hasTranslucency = true;
                        break;
                    }
                }
                if (hasTranslucency) break;
            }
        }
    }
}

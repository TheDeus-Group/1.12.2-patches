package me.allink.patches.mixin.skin;

import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import net.minecraft.client.texture.PlayerSkinProvider;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerSkinProvider.class)
public class PlayerSkinProviderMixin {
    @Inject(method = "loadSkin(Lcom/mojang/authlib/minecraft/MinecraftProfileTexture;Lcom/mojang/authlib/minecraft/MinecraftProfileTexture$Type;Lnet/minecraft/client/texture/PlayerSkinProvider$SkinTextureAvailableCallback;)Lnet/minecraft/util/Identifier;", at = @At("HEAD"), cancellable = true)
    public void loadSkin(MinecraftProfileTexture profileTexture, MinecraftProfileTexture.Type type, PlayerSkinProvider.SkinTextureAvailableCallback callback, CallbackInfoReturnable<Identifier> cir) {
        if(profileTexture.getUrl().contains("education")) {
            cir.cancel();
        }
    }
}

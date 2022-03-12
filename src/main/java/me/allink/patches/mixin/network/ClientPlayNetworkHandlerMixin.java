package me.allink.patches.mixin.network;

import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.ParticleS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {
    @Inject(method = "onParticle(Lnet/minecraft/network/packet/s2c/play/ParticleS2CPacket;)V", at = @At("HEAD"), cancellable = true)
    public void onParticle(ParticleS2CPacket packet, CallbackInfo ci) {
        if(packet.getCount() > 20) {
            ci.cancel();
        }
    }
}

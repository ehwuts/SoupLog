package yeoldesoupe.souplog.mixin;

import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.packet.s2c.play.EntityPassengersSetS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//import org.slf4j.Logger;

/*
Prevents activation of this line:
// LOGGER.warn("Received passengers for unknown entity");
*/

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {
	@Inject(
		method = "onEntityPassengersSet(Lnet/minecraft/network/packet/s2c/play/EntityPassengersSetS2CPacket;)V", 
		at = @At(
			value = "INVOKE", 
			target = "Lorg/slf4j/Logger;warn(Ljava/lang/String;)V",
			ordinal = 0,
			remap = false
		), 
		cancellable = true
	)
	private void dontWarn(CallbackInfo info) {
		info.cancel();
	}
}

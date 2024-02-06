package yeoldesoupe.souplog.mixin;

import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.packet.s2c.play.EntityPassengersSetS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Shadow;

/*
Prevents activation of this line:
// LOGGER.warn("Received passengers for unknown entity");
*/

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {
	/*
	@Inject(method = "onEntityPassengersSet", at = @At(value="INVOKE", target="L/org/apache/logging/log4j/Logger;warn(I)I", ordinal = 0), cancellable = true)
	public void onEntityPassengersSet(EntityPassengersSetS2CPacket packet, CallbackInfo info) {
		info.cancel();
	}
	*/
	@Shadow
	private ClientWorld world;
	
	@Inject(method = "onEntityPassengersSet", at = @At("HEAD"), cancellable = true)
	public void onEntityPassengersSet(EntityPassengersSetS2CPacket packet, CallbackInfo info) {
		if (this.world != null && this.world.getEntityById(packet.getId()) == null) {
			info.cancel();
		}
	}
}

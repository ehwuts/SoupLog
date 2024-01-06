package net.fabricmc.example.mixin;

import yeoldesoupe.souplog.SoupLog;
import net.minecraft.client.gui.hud.ChatHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//private void logChatMessage(Text message, @Nullable MessageIndicator indicator) {
@Mixin(ChatHud.class)
public class ChatHudMixin {
	@Inject(method = "logChatMessage", at = @At("HEAD"), cancellable = true)
	private void logChatMessage(CallbackInfo info) {
		//ChatHud.LOGGER.info("This line is printed by an example mod mixin!");
	}
}

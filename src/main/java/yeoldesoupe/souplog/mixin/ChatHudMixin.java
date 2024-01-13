package yeoldesoupe.souplog.mixin;

import java.util.List;
import yeoldesoupe.souplog.SoupLog;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.MessageIndicator;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.HoverEvent;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//private void logChatMessage(Text message, @Nullable MessageIndicator indicator) {
@Mixin(ChatHud.class)
public class ChatHudMixin {
	private void bruteForceParse(Text checkText) {
		if (checkText == null) return;
		
		if (checkText.getStyle() != null) {			
			HoverEvent checkHover = checkText.getStyle().getHoverEvent();
			if (checkHover != null) {				
				if (checkHover.getAction().getName().contains("show_text")) {
					SoupLog.LOGGER.info("HoverEvent {} {}", (Object)(checkHover.getAction().getName()), (Object)(checkHover.toJson().getAsJsonObject("contents").get("text")));
					//return;
				}
			}
		}
		
		//shamelessly stolen from minerdwarf
		List<Text> checkNext = checkText.getSiblings();
		for (Text newText : checkNext) {
			bruteForceParse(newText);
		}
	}
	
	@Inject(method = "logChatMessage", at = @At("HEAD"), cancellable = true)
	//private void logChatMessage(Text message, @Nullable MessageIndicator indicator) {
	private void logChatMessage(Text message, @Nullable MessageIndicator indicator, CallbackInfo info) {
		bruteForceParse(message);
	}
}

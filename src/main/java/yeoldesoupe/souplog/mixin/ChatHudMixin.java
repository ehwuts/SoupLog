package yeoldesoupe.souplog.mixin;

import java.util.List;
import java.util.ArrayList;
import yeoldesoupe.souplog.SoupLog;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.MessageIndicator;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.MutableText;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//private void logChatMessage(Text message, @Nullable MessageIndicator indicator) {
@Mixin(ChatHud.class)
public class ChatHudMixin {
	//based on code shamelessly stolen from minerdwarf
	private ArrayList<String> bruteForceParse(Text checkText) {
		ArrayList<String> result = new ArrayList<String>();

		if (checkText != null) {
			if (checkText.getStyle() != null) {
				HoverEvent checkHover = checkText.getStyle().getHoverEvent();
				if (checkHover != null) {
					if (checkHover.getAction().getName().contains("show_text")) {
						result.add(checkHover.toJson().getAsJsonObject("contents").get("text").getAsString());
					}
				}
			}

			List<Text> checkNext = checkText.getSiblings();
			for (Text newText : checkNext) {
				result.addAll(bruteForceParse(newText));
			}
		}
		return result;
	}

	@Inject(method = "logChatMessage", at = @At("HEAD"), cancellable = true)
	//private void logChatMessage(Text message, @Nullable MessageIndicator indicator) {
	private void logChatMessage(Text message, @Nullable MessageIndicator indicator, CallbackInfo info) {
		if (message.getString().contains("§")) {
			//SoupLog.LOGGER.info(message.toString());
			//SoupLog.LOGGER.info(message.getString());
			message = (Text)(((MutableText)message).append(" " + String.join(" ", bruteForceParse(message))));
		}
	}
}

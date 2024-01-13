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
	//shamelessly stolen from minerdwarf
	private void bruteForceParse(Text checkText) {
		if (checkText == null) return;
		
		SoupLog.LOGGER.info("Soup says {}", (Object)(checkText.toString()));
		
		HoverEvent checkHover = checkText.getStyle().getHoverEvent();
		if (checkHover != null) {
			SoupLog.LOGGER.info("Soup shows {}", (Object)(checkHover.toString()));
		}
		
		List<Text> checkNext = checkText.getSiblings();

		for (Text newText : checkNext) {
			bruteForceParse(newText);
		}
	}
	/*
	private void bruteForceParse(Text checkText) {
		if (checkText == null) return;

		//If the Text does not have style or the style does not have a hoverEvent, try to split otherwise return.
		if (checkText.getStyle() != null && checkText.getStyle().getHoverEvent() != null) {
			HoverEvent checkHover = checkText.getStyle().getHoverEvent();

			if (!checkHover.getAction().toString().contains("show_item")) {
				bruteForceLoop(checkText); 
				return;
			}

			ItemStack stack = checkHover.getValue(HoverEvent.Action.SHOW_ITEM).asStack();

			if (stack.getNbt() == null 
			 || !stack.getNbt().contains("Monumenta") 
			 || !stack.getNbt().getCompound("Monumenta").getString("Tier").equals("zenithcharm")
//			 || DwarfHighlightMod.uniqueZenithCharms.contains(stack.getNbt().getCompound("Monumenta").getCompound("PlayerModified").getLong("DEPTHS_CHARM_UUID"))
			) {
				bruteForceLoop(checkText); 
				return;
			}

			// DwarfHighlightMod.LOGGER.info("Message charm: " + stack.getNbt().getCompound("plain").getCompound("display").getString("Name"));

			//outputAllCharms(stack);

			SoupLog.LOGGER.info("New charm");
		}

		bruteForceLoop(checkText);

	}
	*/
	@Inject(method = "logChatMessage", at = @At("HEAD"), cancellable = true)
	//private void logChatMessage(Text message, @Nullable MessageIndicator indicator) {
	private void logChatMessage(Text message, @Nullable MessageIndicator indicator, CallbackInfo info) {
		bruteForceParse(message);
	}
}

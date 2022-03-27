package yeoldesoupe.souperrorsuppressor.mixin;

import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Shadow;
import java.util.Map;

@Mixin(Scoreboard.class)
public class ScoreboardMixin {
	@Shadow
	private Map<String, Team> teamsByPlayer;
	
	@Inject(method = "removePlayerFromTeam", at = @At("HEAD"), cancellable = true)
	public void removePlayerFromTeam(String playerName, Team team, CallbackInfo info) {
		try {
			if (this.teamsByPlayer.get(playerName) != team) {
				info.cancel();
			}
		} catch (Exception e) {
			System.out.println("butts");
		}
	}
}

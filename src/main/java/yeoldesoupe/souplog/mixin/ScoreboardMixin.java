package yeoldesoupe.souplog.mixin;

import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Shadow;
import java.util.Map;

/*
Suppresses activation of this line:
// throw new IllegalStateException("Player is either on another team or not on any team. Cannot remove from team '" + team.getName() + "'.");
*/
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
			//this shouldnt ever be able to trigger since we're canceling the case that throws
			//but on the version i wrote it the compiler insisted ¯\_(ツ)_/¯ 
			System.out.println("butts");
		}
	}
}

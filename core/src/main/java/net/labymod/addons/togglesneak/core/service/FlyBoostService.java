package net.labymod.addons.togglesneak.core.service;

import net.labymod.addons.togglesneak.core.ToggleSneakConfiguration;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.entity.player.GameMode;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.entity.player.abilities.PlayerAbilities;

public class FlyBoostService {

  private final LabyAPI labyAPI;
  private final ToggleSneakConfiguration configuration;

  private float originalFlySpeed = -1F;

  public FlyBoostService(LabyAPI labyAPI, ToggleSneakConfiguration configuration) {
    this.labyAPI = labyAPI;
    this.configuration = configuration;
  }

  public void setOriginalFlySpeed(float originalFlySpeed) {
    this.originalFlySpeed = originalFlySpeed;
  }

  public void applyFlyBoost(Player player) {
    PlayerAbilities abilities = player.abilities();

    if (!abilities.flying().get()
        || player.gameMode() != GameMode.CREATIVE
        || this.originalFlySpeed < 0.0F) {
      return;
    }

    abilities.flyingSpeed().set(
        this.originalFlySpeed *
            (this.isEnabled() ? this.configuration.flyBoostFactor().get() : 1.0F)
    );
  }

  private boolean isEnabled() {
    return this.labyAPI.permissionRegistry().isPermissionEnabled(
        ToggleSneakConfiguration.FLYBOOST_PERMISSION,
        this.configuration.flyBoost()
    );
  }
}

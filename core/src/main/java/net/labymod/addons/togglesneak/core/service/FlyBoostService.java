/*
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package net.labymod.addons.togglesneak.core.service;

import net.labymod.addons.togglesneak.core.ToggleSneakConfiguration;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.entity.player.GameMode;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.entity.player.abilities.PlayerAbilities;

public class FlyBoostService {

  private final LabyAPI labyAPI;
  private final ToggleSneakConfiguration configuration;

  private Float originalFlySpeed = null;

  public FlyBoostService(LabyAPI labyAPI, ToggleSneakConfiguration configuration) {
    this.labyAPI = labyAPI;
    this.configuration = configuration;
  }

  public Float getOriginalFlySpeed() {
    return this.originalFlySpeed;
  }

  public void setOriginalFlySpeed(Float originalFlySpeed) {
    this.originalFlySpeed = originalFlySpeed;
  }

  public void applyFlyBoost(Player player) {
    PlayerAbilities abilities = player.abilities();

    if (!abilities.flying().get()
        || player.gameMode() != GameMode.CREATIVE
        || this.originalFlySpeed == null) {
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

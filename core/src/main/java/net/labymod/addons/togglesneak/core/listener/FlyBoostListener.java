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

package net.labymod.addons.togglesneak.core.listener;

import net.labymod.addons.togglesneak.core.service.FlyBoostService;
import net.labymod.api.LabyAPI;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.entity.player.ClientPlayerAbilitiesUpdateEvent;
import net.labymod.api.event.client.lifecycle.GameTickEvent;
import net.labymod.api.event.client.world.WorldEnterEvent;
import net.labymod.api.event.client.world.WorldEnterEvent.Type;
import net.labymod.api.event.client.world.WorldLeaveEvent;

public class FlyBoostListener {

  private final LabyAPI labyAPI;
  private final FlyBoostService service;

  public FlyBoostListener(LabyAPI labyAPI, FlyBoostService service) {
    this.labyAPI = labyAPI;
    this.service = service;
  }

  @Subscribe
  public void onAbilitiesUpdate(ClientPlayerAbilitiesUpdateEvent event) {
    this.service.setOriginalFlySpeed(event.abilities().flyingSpeed().get());
  }

  @Subscribe
  public void onGameTick(GameTickEvent event) {
    var clientPlayer = this.labyAPI.minecraft().getClientPlayer();
    if (clientPlayer != null) {
      this.service.applyFlyBoost(clientPlayer);
    }
  }

  @Subscribe
  public void onWorldEnter(WorldEnterEvent event) {
    var clientPlayer = this.labyAPI.minecraft().getClientPlayer();
    // No packet is sent in Singleplayer, use default instead
    if (clientPlayer != null && event.type() == Type.SINGLEPLAYER) {
      this.service.setOriginalFlySpeed(clientPlayer.abilities().flyingSpeed().get());
    }
  }

  @Subscribe
  public void onWorldLeave(WorldLeaveEvent event) {
    var clientPlayer = this.labyAPI.minecraft().getClientPlayer();
    Float flySpeed = this.service.getOriginalFlySpeed();
    if (clientPlayer != null && flySpeed != null) {
      // Reset fly speed to original value, if present
      clientPlayer.abilities().flyingSpeed().set(flySpeed);
    }
    this.service.setOriginalFlySpeed(null);
  }
}

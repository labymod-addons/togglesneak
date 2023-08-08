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

import net.labymod.addons.togglesneak.core.ToggleSneak;
import net.labymod.addons.togglesneak.core.controller.ToggleSneakController;
import net.labymod.addons.togglesneak.core.service.ToggleSneakService;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.lifecycle.GameTickEvent;

public class ToggleSneakListener {

  private final ToggleSneak toggleSneak;
  private final LabyAPI labyAPI;
  private final ToggleSneakService service;
  private final ToggleSneakController controller;

  public ToggleSneakListener(
      ToggleSneak toggleSneak,
      ToggleSneakController controller,
      ToggleSneakService service,
      LabyAPI labyAPI
  ) {
    this.toggleSneak = toggleSneak;
    this.labyAPI = labyAPI;
    this.service = service;
    this.controller = controller;
  }

  @Subscribe
  public void onGameTick(GameTickEvent event) {
    if (event.phase() != this.controller.phase()) {
      return;
    }

    Minecraft minecraft = this.labyAPI.minecraft();
    ClientPlayer clientPlayer = minecraft.clientPlayer();
    if (clientPlayer == null) {
      return;
    }

    boolean reset = minecraft.minecraftWindow().currentScreen() != null;
    this.applyToggleSprint(clientPlayer, reset);
    this.applyToggleSneak(clientPlayer, reset);
  }

  private void applyToggleSprint(ClientPlayer clientPlayer, boolean reset) {
    if (!this.toggleSneak.configuration().toggleSprint().get()) {
      this.service.setSprintPressed(false);
      this.service.toggleSprint(false);
      return;
    }

    if (!reset) {
      boolean lastPressed = this.service.isSprintPressed();
      boolean sprintPressed = this.controller.isSprintPressed();
      if (sprintPressed) {
        if (!lastPressed) {
          this.service.setSprintPressed(true);
          this.service.toggleSprint();
        }
      } else if (lastPressed) {
        this.service.setSprintPressed(false);
      }
    }

    this.controller.setSprinting(
        !reset && clientPlayer.getForwardMovingSpeed() > 0.0F && (this.service.isSprintToggled()
            || this.controller.isSprintPressed())
    );
  }

  private void applyToggleSneak(ClientPlayer clientPlayer, boolean reset) {
    if (!this.toggleSneak.configuration().toggleSneak().get() || clientPlayer.isSprinting()) {
      this.service.setSneakPressed(false);
      this.service.toggleSneak(false);
      return;
    }

    boolean sneakPressed = this.controller.isSneakPressed();
    if (!reset) {
      boolean lastPressed = this.service.isSneakPressed();
      if (sneakPressed) {
        if (!lastPressed) {
          this.service.setSneakPressed(true);
          this.service.toggleSneak();
        }
      } else if (lastPressed) {
        this.service.setSneakPressed(false);
      }
    }

    this.controller.setSneaking(!reset && (sneakPressed || this.service.isSneakToggled()));
  }
}

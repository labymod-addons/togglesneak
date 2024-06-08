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

package net.labymod.addons.togglesneak.core.hudwidget;

import net.labymod.addons.togglesneak.core.service.ToggleSneakService;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.text.TextLine;
import net.labymod.api.util.TextFormat;
import net.labymod.api.util.bounds.area.RectangleAreaPosition;

public class ToggleSneakHudWidget extends TextHudWidget<TextHudWidgetConfig> {

  private final ToggleSneakService service;

  private TextLine sprintingLine;
  private TextLine sneakingLine;

  private State lastSprintState;
  private State lastSneakState;

  public ToggleSneakHudWidget(ToggleSneakService service) {
    super("toggleSneak");
    this.service = service;
  }

  @Override
  public void initializePreConfigured(TextHudWidgetConfig config) {
    super.initializePreConfigured(config);

    config.setEnabled(true);
    config.setAreaIdentifier(RectangleAreaPosition.TOP_RIGHT);
    config.setX(-2);
    config.setY(2);
    config.setParentToTailOfChainIn(RectangleAreaPosition.TOP_RIGHT);
  }

  @Override
  public void load(TextHudWidgetConfig config) {
    super.load(config);

    this.lastSprintState = null;
    this.sprintingLine = super.createLine("Sprinting", "");
    this.updateSprintingTextLine(State.NO);

    this.lastSneakState = null;
    this.sneakingLine = super.createLine("Sneaking", "");
    this.updateSneakingTextLine(State.NO);
  }

  @Override
  public void onTick(boolean isEditorContext) {
    State currentSneakState = State.NO;
    ClientPlayer clientPlayer = this.labyAPI.minecraft().getClientPlayer();
    if (clientPlayer != null && !clientPlayer.isSprinting()) {
      if (this.service.isSneakToggled()) {
        currentSneakState = State.TOGGLED;
      } else if (this.service.isSneakPressed()) {
        currentSneakState = State.HOLDING;
      }
    }

    this.updateSneakingTextLine(currentSneakState);

    State currentCrouchState = State.NO;
    if (clientPlayer != null && currentSneakState == State.NO) {
      if (this.service.isSprintToggled()) {
        currentCrouchState = State.TOGGLED;
      } else if (this.service.isSprintPressed()) {
        currentCrouchState = State.HOLDING;
      } else if (clientPlayer.isSprinting()) {
        currentCrouchState = State.VANILLA;
      }
    }

    this.updateSprintingTextLine(currentCrouchState);
  }

  private void updateSprintingTextLine(State currentState) {
    if (this.lastSprintState == currentState) {
      return;
    }

    this.lastSprintState = currentState;
    this.sprintingLine.updateAndFlush(TextFormat.SNAKE_CASE.toUpperCamelCase(currentState.name()));
    this.sprintingLine.setVisible(currentState != State.NO);
  }

  private void updateSneakingTextLine(State currentState) {
    if (this.lastSneakState == currentState) {
      return;
    }

    this.lastSneakState = currentState;
    this.sneakingLine.updateAndFlush(TextFormat.SNAKE_CASE.toUpperCamelCase(currentState.name()));
    this.sneakingLine.setVisible(currentState != State.NO);
  }

  private enum State {
    VANILLA, HOLDING, TOGGLED, NO
  }
}

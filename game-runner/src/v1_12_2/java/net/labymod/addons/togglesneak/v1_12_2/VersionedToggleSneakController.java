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

package net.labymod.addons.togglesneak.v1_12_2;

import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.addons.togglesneak.core.controller.ToggleSneakController;
import net.labymod.api.event.Phase;
import net.labymod.api.models.Implements;
import net.minecraft.client.Minecraft;
import net.minecraft.util.MovementInput;

@Singleton
@Implements(ToggleSneakController.class)
public class VersionedToggleSneakController extends ToggleSneakController {

  @Inject
  public VersionedToggleSneakController() {
  }

  @Override
  public Phase phase() {
    return Phase.POST;
  }

  @Override
  public void test() {
    // this is to prevent log spam while in development environment, as the addon will be
    // obfuscated on build, and those obfuscated classes don't exist in the development environment
    Minecraft.getMinecraft();
  }

  @Override
  public void setSprinting(boolean sprinting) {
    super.setSprinting(sprinting);
    this.getMovementInput().updatePlayerMoveState();
  }

  @Override
  public void setSneaking(boolean sneaking) {
    super.setSneaking(sneaking);
    this.getMovementInput().updatePlayerMoveState();
  }

  private MovementInput getMovementInput() {
    return Minecraft.getMinecraft().player.movementInput;
  }
}

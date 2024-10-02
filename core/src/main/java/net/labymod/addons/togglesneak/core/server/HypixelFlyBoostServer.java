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

package net.labymod.addons.togglesneak.core.server;

import net.labymod.addons.togglesneak.core.ToggleSneakConfiguration;
import net.labymod.api.Laby;
import net.labymod.api.client.network.server.AbstractServer;
import net.labymod.api.event.Phase;

public class HypixelFlyBoostServer extends AbstractServer {

  private static final String NAME = "hypixel";

  public HypixelFlyBoostServer() {
    super(NAME);
  }

  @Override
  public void loginOrSwitch(LoginPhase phase) {
    var flyBoostPermission = Laby.labyAPI().permissionRegistry()
        .getPermission(ToggleSneakConfiguration.FLYBOOST_PERMISSION);
    if (flyBoostPermission != null) {
      flyBoostPermission.setEnabled(false);
    }
  }

  @Override
  public void disconnect(Phase phase) {

  }
}

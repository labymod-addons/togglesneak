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

import net.labymod.addons.togglesneak.core.ToggleSneakConfiguration;
import net.labymod.api.Laby;
import net.labymod.api.client.chat.ChatExecutor;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.labymod.serverapi.PermissionStateChangeEvent;

public class PermissionStateChangeListener {

  private static final Component PREFIX = Component.text("[", NamedTextColor.GRAY)
      .append(Component.text("ToggleSneak", NamedTextColor.DARK_AQUA))
      .append(Component.text("] ", NamedTextColor.GRAY));
  private boolean warningSent = false;

  @Subscribe
  public void onPermissionStateChange(PermissionStateChangeEvent event) {
    if (this.warningSent || event.state() != PermissionStateChangeEvent.State.DISALLOWED) {
      return;
    }

    String permissionId = event.permission().getIdentifier();
    if (!ToggleSneakConfiguration.FLYBOOST_PERMISSION.equals(permissionId)) {
      return;
    }

    this.warningSent = true;
    ChatExecutor chatExecutor = Laby.labyAPI().minecraft().chatExecutor();
    chatExecutor.displayClientMessage(
        Component.empty()
            .append(PREFIX)
            .append(Component.translatable(
                "togglesneak.permissions.flyboost.denied",
                NamedTextColor.RED
            ))
    );
  }

  public void resetWarningSent() {
    this.warningSent = false;
  }
}

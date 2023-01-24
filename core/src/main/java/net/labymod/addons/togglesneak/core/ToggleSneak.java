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

package net.labymod.addons.togglesneak.core;

import net.labymod.addons.togglesneak.core.controller.DefaultToggleSneakController;
import net.labymod.addons.togglesneak.core.controller.ToggleSneakController;
import net.labymod.addons.togglesneak.core.generated.DefaultReferenceStorage;
import net.labymod.addons.togglesneak.core.hudwidget.ToggleSneakHudWidget;
import net.labymod.addons.togglesneak.core.listener.ToggleSneakListener;
import net.labymod.addons.togglesneak.core.service.ToggleSneakService;
import net.labymod.api.addon.LabyAddon;
import net.labymod.api.models.addon.annotation.AddonMain;

@AddonMain
public class ToggleSneak extends LabyAddon<ToggleSneakConfiguration> {


  @Override
  protected void enable() {
    this.registerSettingCategory();

    DefaultReferenceStorage references = this.getReferenceStorageAccessor();
    ToggleSneakService service = new ToggleSneakService();
    ToggleSneakController controller = references.getToggleSneakController();
    if (controller == null) {
      controller = new DefaultToggleSneakController();
    }

    this.registerListener(new ToggleSneakListener(this, controller, service, this.labyAPI()));
    this.labyAPI().hudWidgetRegistry().register(new ToggleSneakHudWidget(service));
  }

  @Override
  protected Class<ToggleSneakConfiguration> configurationClass() {
    return ToggleSneakConfiguration.class;
  }
}

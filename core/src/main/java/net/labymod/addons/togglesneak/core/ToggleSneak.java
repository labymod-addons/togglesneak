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
import net.labymod.addons.togglesneak.core.listener.FlyBoostListener;
import net.labymod.addons.togglesneak.core.listener.ToggleSneakListener;
import net.labymod.addons.togglesneak.core.server.HypixelFlyBoostServer;
import net.labymod.addons.togglesneak.core.service.FlyBoostService;
import net.labymod.addons.togglesneak.core.service.ToggleSneakService;
import net.labymod.api.addon.LabyAddon;
import net.labymod.api.models.addon.annotation.AddonMain;

@AddonMain
public class ToggleSneak extends LabyAddon<ToggleSneakConfiguration> {

  @Override
  protected void enable() {
    this.registerSettingCategory();

    this.labyAPI().permissionRegistry()
        .register(ToggleSneakConfiguration.FLYBOOST_PERMISSION, false);
    this.labyAPI().serverController().registerServer(new HypixelFlyBoostServer());

    ToggleSneakController controller = this.references().getToggleSneakController();
    if (controller == null) {
      controller = new DefaultToggleSneakController();
    }
    controller.test();

    ToggleSneakService service = new ToggleSneakService();
    this.registerListener(new ToggleSneakListener(this, controller, service, this.labyAPI()));
    this.labyAPI().hudWidgetRegistry().register(new ToggleSneakHudWidget(service));

    FlyBoostService flyBoostService = new FlyBoostService(this.labyAPI(), this.configuration());
    this.registerListener(new FlyBoostListener(flyBoostService));
  }

  private DefaultReferenceStorage references() {
    return this.referenceStorageAccessor();
  }

  @Override
  protected Class<ToggleSneakConfiguration> configurationClass() {
    return ToggleSneakConfiguration.class;
  }
}

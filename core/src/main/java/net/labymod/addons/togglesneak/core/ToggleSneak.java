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

import com.google.inject.Singleton;
import net.labymod.addons.togglesneak.core.hudwidget.ToggleSneakHudWidget;
import net.labymod.addons.togglesneak.core.listener.ToggleSneakListener;
import net.labymod.api.addon.LabyAddon;
import net.labymod.api.inject.LabyGuice;
import net.labymod.api.models.addon.annotation.AddonListener;

@Singleton
@AddonListener
public class ToggleSneak extends LabyAddon<ToggleSneakConfiguration> {

  @Override
  protected void enable() {
    this.registerSettingCategory();
    this.registerListener(ToggleSneakListener.class);
    this.labyAPI().hudWidgetRegistry().register(LabyGuice.getInstance(ToggleSneakHudWidget.class));
  }

  @Override
  protected Class<ToggleSneakConfiguration> configurationClass() {
    return ToggleSneakConfiguration.class;
  }
}

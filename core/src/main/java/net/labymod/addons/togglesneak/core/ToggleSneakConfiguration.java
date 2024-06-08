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

import net.labymod.api.addon.AddonConfig;
import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget.SliderSetting;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget.SwitchSetting;
import net.labymod.api.configuration.loader.annotation.ConfigName;
import net.labymod.api.configuration.loader.annotation.PermissionRequired;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.settings.annotation.SettingRequires;
import net.labymod.api.configuration.settings.annotation.SettingSection;

@SuppressWarnings("FieldMayBeFinal")
@ConfigName("settings")
public class ToggleSneakConfiguration extends AddonConfig {

  public static final String FLYBOOST_PERMISSION = "flyboost";

  @SwitchSetting(hotkey = true)
  private final ConfigProperty<Boolean> enabled = new ConfigProperty<>(true);

  @SwitchSetting
  private final ConfigProperty<Boolean> toggleSprint = new ConfigProperty<>(true);

  @SwitchSetting
  private final ConfigProperty<Boolean> toggleSneak = new ConfigProperty<>(true);

  @SettingSection("flyBoost")
  @PermissionRequired(FLYBOOST_PERMISSION)
  @SwitchSetting(hotkey = true)
  private final ConfigProperty<Boolean> flyBoost = new ConfigProperty<>(false);

  @SettingRequires("flyBoost")
  @SliderSetting(min = 0.1F, max = 3.0F, steps = 0.1F)
  private final ConfigProperty<Float> flyBoostFactor = new ConfigProperty<>(1.5F);

  @Override
  public ConfigProperty<Boolean> enabled() {
    return this.enabled;
  }

  public ConfigProperty<Boolean> toggleSprint() {
    return this.toggleSprint;
  }

  public ConfigProperty<Boolean> toggleSneak() {
    return this.toggleSneak;
  }

  public ConfigProperty<Boolean> flyBoost() {
    return this.flyBoost;
  }

  public ConfigProperty<Float> flyBoostFactor() {
    return this.flyBoostFactor;
  }
}

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

package net.labymod.addons.togglesneak.core.service;

public class ToggleSneakService {

  private boolean toggledSprint;
  private boolean toggledSneak;

  private boolean pressedSprint;
  private boolean pressedSneak;

  public boolean isSprintToggled() {
    return this.toggledSprint;
  }

  public void toggleSprint(boolean toggledSprint) {
    this.toggledSprint = toggledSprint;
  }

  public void toggleSprint() {
    this.toggleSprint(!this.toggledSprint);
  }

  public boolean isSneakToggled() {
    return this.toggledSneak;
  }

  public void toggleSneak(boolean toggledSneak) {
    this.toggledSneak = toggledSneak;
  }

  public void toggleSneak() {
    this.toggleSneak(!this.toggledSneak);
  }

  public boolean isSprintPressed() {
    return this.pressedSprint;
  }

  public void setSprintPressed(boolean pressedSprint) {
    this.pressedSprint = pressedSprint;
  }

  public boolean isSneakPressed() {
    return this.pressedSneak;
  }

  public void setSneakPressed(boolean pressedSneak) {
    this.pressedSneak = pressedSneak;
  }
}

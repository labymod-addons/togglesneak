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

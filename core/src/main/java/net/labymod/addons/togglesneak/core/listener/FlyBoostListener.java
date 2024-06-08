package net.labymod.addons.togglesneak.core.listener;

import net.labymod.addons.togglesneak.core.service.FlyBoostService;
import net.labymod.api.Laby;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.entity.player.ClientPlayerAbilitiesUpdateEvent;
import net.labymod.api.event.client.lifecycle.GameTickEvent;
import net.labymod.api.event.client.world.WorldEnterEvent;
import net.labymod.api.event.client.world.WorldEnterEvent.Type;
import net.labymod.api.event.client.world.WorldLeaveEvent;

public class FlyBoostListener {

  private final FlyBoostService service;

  public FlyBoostListener(FlyBoostService service) {
    this.service = service;
  }

  @Subscribe
  public void onAbilitiesUpdate(ClientPlayerAbilitiesUpdateEvent event) {
    this.service.setOriginalFlySpeed(event.abilities().flyingSpeed().get());
  }

  @Subscribe
  public void onGameTick(GameTickEvent event) {
    var clientPlayer = Laby.labyAPI().minecraft().getClientPlayer();
    if (clientPlayer != null) {
      this.service.applyFlyBoost(clientPlayer);
    }
  }

  @Subscribe
  public void onWorldEnter(WorldEnterEvent event) {
    var clientPlayer = Laby.labyAPI().minecraft().getClientPlayer();
    if (clientPlayer != null && event.type() == Type.SINGLEPLAYER) {
      this.service.setOriginalFlySpeed(clientPlayer.abilities().flyingSpeed().get());
    }
  }

  @Subscribe
  public void onWorldLeave(WorldLeaveEvent event) {
    this.service.setOriginalFlySpeed(-1.0F);
  }
}

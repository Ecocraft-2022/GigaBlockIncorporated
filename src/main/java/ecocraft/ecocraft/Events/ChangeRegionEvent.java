package ecocraft.ecocraft.Events;

import ecocraft.ecocraft.Pollution.Region;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.io.IOException;

public final class ChangeRegionEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    private final Region region;

    private final Player player;

    public ChangeRegionEvent(Player player) throws IOException {
        this.player = player;
        this.region = Region.getRegionBy(player);

    }


    public Region getRegion() {
        return region;
    }

    public Player getPlayer() {
        return player;
    }


    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}

package ecocraft.ecocraft.Events;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class NightEvent extends Event implements Cancellable {

    private boolean isNight;
    private boolean cancelled;

    private static final HandlerList HANDLERS = new HandlerList();

    public NightEvent(boolean isNight){
        this.isNight = isNight;
    }
    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public boolean isNight(){
        return this.isNight;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    public void setNight(boolean b) {
        this.isNight = b;
    }
}

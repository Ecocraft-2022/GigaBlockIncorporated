package ecocraft.ecocraft.Handlers;

import ecocraft.ecocraft.Events.AcidRain;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

public class TexturePackHandler implements Listener {

    Plugin plugin;

    public TexturePackHandler(Plugin plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void applyTexturePack(PlayerJoinEvent e){
        //will be working when repo will be public
        e.getPlayer().setResourcePack("https://github.com/Ecocraft-2022/GigaBlockIncorporated/raw/main/src/main/resources/giga-resourcepackv4.zip");
    }

}

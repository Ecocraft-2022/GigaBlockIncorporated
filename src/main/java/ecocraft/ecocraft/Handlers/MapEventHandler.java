package ecocraft.ecocraft.Handlers;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.server.MapInitializeEvent;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapView;
import org.bukkit.plugin.Plugin;


public class MapEventHandler implements Listener {
    private Plugin plugin;


    public MapEventHandler(Plugin plugin) {
            this.plugin =plugin;
            plugin.getServer().getPluginManager().registerEvents(this, plugin);

    }




    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e){
        Player p = e.getPlayer();
        if(p.getInventory().getItemInMainHand().getType().equals(Material.FILLED_MAP) || p.getInventory().getItemInOffHand().getType().equals(Material.FILLED_MAP) ){
            MapMeta map = null;
           if(p.getInventory().getItemInMainHand().getType().equals(Material.FILLED_MAP)){
             map = (MapMeta)  p.getInventory().getItemInMainHand().getItemMeta();

           }
            if(p.getInventory().getItemInOffHand().getType().equals(Material.FILLED_MAP)){
                map = (MapMeta)  p.getInventory().getItemInOffHand().getItemMeta();
            }
            MapView mapView = map.getMapView();
            mapView.setCenterX(p.getLocation().getBlockX());
            mapView.setCenterZ(p.getLocation().getBlockZ());
        }
    }

}

package ecocraft.ecocraft.Handlers;

import ecocraft.ecocraft.CustomBlocks.SolarPanel;
import ecocraft.ecocraft.CustomBlocks.SolarPanelBase;
import ecocraft.ecocraft.Ecocraft;
import ecocraft.ecocraft.Utils.Util;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class SolarPanelEventHandler implements Listener {

    public SolarPanelEventHandler(Ecocraft plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public boolean onSolarPanelPlaced(BlockPlaceEvent e){

        if(e.getBlockPlaced().getType() == SolarPanel.getSolarPanel().getType()) {
            if (e.getBlockPlaced().getRelative(BlockFace.DOWN).getType() != SolarPanelBase.getSolarPanel().getType()) {
                e.getPlayer().sendMessage("Solar panel will not produce power");
                return true;
            }
            Util u = new Util();
            u.findDesiredBlocks(e.getBlockPlaced().getRelative(BlockFace.DOWN));
            e.getPlayer().sendMessage("Solar panel is producing power");

            u.getFurnaces().stream().forEach(
                    f ->{
                        f.setBurnTime(Short.MAX_VALUE);
                        f.update();
                    }
            );
        }



        return true;
    }
}

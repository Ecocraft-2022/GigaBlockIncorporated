package ecocraft.ecocraft.Utils;


import ecocraft.ecocraft.Events.NightEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.yaml.snakeyaml.external.com.google.gdata.util.common.base.UnicodeEscaper;

public class NightDetector {

    private Plugin plugin;

    private boolean night;

    private static NightDetector detector;

    private NightDetector(Plugin plugin) {

        this.plugin = plugin;

    }

    public static NightDetector getInstance(Plugin plugin){
        if(detector == null){
            detector = new NightDetector(plugin);
        }




        return detector;
    }
    public void detectNight() {
        BukkitScheduler runnable = Bukkit.getScheduler();

        runnable.runTaskTimer(plugin, () -> {
            long time = plugin.getServer().getWorld(plugin.getServer().getWorlds().stream().findFirst().get().getName()).getTime();
            NightEvent event = new NightEvent(false);

            if (time > 12300 && time < 23850) {
                night = true;
                event.setNight(true);
            } else {
                night = false;
                event.setNight(false);
            }
            Bukkit.getPluginManager().callEvent(event);

        }, 0, 100L);

    }

    public boolean isNight() {
        return night;
    }
}

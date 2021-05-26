package net.thejrdev.kits;

import net.thejrdev.EasySpigot.EasyYML;
import org.bukkit.plugin.java.JavaPlugin;

public class KitPlugin extends JavaPlugin {

    private EasyYML loader;

    @Override
    public void onEnable() {
        super.onEnable();
        Kit.init(this);

        new KitCommand(this);
        new ModifyKitCommand(this);

        saveDefaultConfig();
    }

    public EasyYML getLoader() {
        return loader;
    }


    /*

    Kit commands

    /kit <Kit Name>

     */

}

package net.thejrdev.kits;

import net.thejrdev.EasySpigot.EasyYML;
import net.thejrdev.EasySpigot.MissingConfigArgument;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Kit {

    private static final HashMap<String, Kit> kits = new HashMap<String, Kit>();
    private static final List<String> armor = new ArrayList<String>(Arrays.asList("head", "chest", "legs", "boots"));

    private FileConfiguration config;
    private JavaPlugin plugin;
    private String name;

    private ItemStack head, chest, legs, boots, off;
    private List<ItemStack> items;

    public Kit(JavaPlugin plugin, String name) {
        this.plugin = plugin;
        this.name = name;
        this.config = plugin.getConfig();
        ConfigurationSection section = plugin.getConfig().getConfigurationSection("kits." + name);
        if(section == null){
            return;
        }

        items = new ArrayList<>();

        head = section.getItemStack("head");
        chest = section.getItemStack("chest");
        legs = section.getItemStack("legs");
        boots = section.getItemStack("boots");
        off = section.getItemStack("off");
        for(String s: section.getKeys(false)){
            if(armor.contains(s)){
                continue;
            }
            items.add(section.getItemStack(s));
        }

        head.getItemMeta().setUnbreakable(true);
        chest.getItemMeta().setUnbreakable(true);
        legs.getItemMeta().setUnbreakable(true);
        boots.getItemMeta().setUnbreakable(true);

        for(ItemStack i: items){
            i.getItemMeta().setUnbreakable(true);
        }

        kits.put(name, this);
    }

    public Kit(String name, PlayerInventory inventory, KitPlugin plugin){

        this.plugin = plugin;
        this.config = plugin.getConfig();

        kits.put(name, this);
        this.name = name;
        head = inventory.getHelmet();
        chest = inventory.getChestplate();
        legs = inventory.getLeggings();
        boots = inventory.getBoots();
        off = inventory.getItemInOffHand();

        items = new ArrayList<>();
        for(ItemStack item: inventory.getStorageContents()){
            if(item == null){continue;}
            items.add(item);
        }
        head.getItemMeta().setUnbreakable(true);
        chest.getItemMeta().setUnbreakable(true);
        legs.getItemMeta().setUnbreakable(true);
        boots.getItemMeta().setUnbreakable(true);

        for(ItemStack i: items){
            i.getItemMeta().setUnbreakable(true);
        }

        save();
    }

    public static void init(JavaPlugin plugin){

        ConfigurationSection section = plugin.getConfig().getConfigurationSection("kits");
        if(section == null){
            System.out.println("NULL SECTION");
            return;
        }

        for(String s: section.getKeys(false)) {

            new Kit(plugin, s);

        }

    }
    public static Kit getKit(String name) {
        return kits.get(name);
    }
    public static void remove(String name){
        kits.remove(name);
    }


    public void apply(Player player){
        PlayerInventory inventory = player.getInventory();
        inventory.clear();

        inventory.setHelmet(head);
        inventory.setChestplate(chest);
        inventory.setLeggings(legs);
        inventory.setBoots(boots);
        inventory.setItemInOffHand(off);
        for(ItemStack item: items){
            if(item == null){continue;}
            inventory.addItem(item);
        }
    }

    public HashMap<String, Kit> getKits() {
        return kits;
    }

    public void save(){
        ConfigurationSection section = config.getConfigurationSection("kits." + name);
        if(section == null){
            section = config.createSection("kits." + name);
        }
        for(String s: section.getKeys(false)){
            section.set(s, null);
        }
        section.set("head", head);
        section.set("chest", chest);
        section.set("legs", legs);
        section.set("boots", boots);

        int i = 0;
        for(ItemStack item: items){
            section.set(String.valueOf(i), item);
            i++;
        }

        plugin.saveConfig();

    }
}

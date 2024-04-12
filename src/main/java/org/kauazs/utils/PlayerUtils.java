package org.kauazs.utils;

import com.sun.tools.javac.jvm.Items;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.kauazs.Pvp;
import org.kauazs.managers.Arena;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;


import static org.kauazs.utils.LocationUtils.getFromLocation;

public class PlayerUtils {
    private Arena arena;

    public PlayerUtils(Arena arena) {
        this.arena = arena;

        this.TpToPositions();
    }

    public void giveItems() {
        for (UUID player : this.arena.getPlayers()) {
            Player p = Pvp.getInstance().getServer().getPlayer(player);

            ItemStack sword = new ItemStack(Material.IRON_SWORD);

            ItemMeta swordMeta = sword.getItemMeta();
            swordMeta.addEnchant(Enchantment.DAMAGE_ALL, 2, false);
            sword.setItemMeta(swordMeta);

            ItemStack fishing = new ItemStack(Material.FISHING_ROD, 1);
            ItemStack apple = new ItemStack(Material.GOLDEN_APPLE, 6);
            ItemStack wood = new ItemStack(Material.WOOD, 64);
            ItemStack bow = new ItemStack(Material.BOW, 1);
            ItemStack arrow = new ItemStack(Material.ARROW, 16);
            p.setGameMode(GameMode.SURVIVAL);
            p.setHealth(20.0);
            p.setFoodLevel(20);

            p.getInventory().clear();

            p.getInventory().setItem(0, sword);
            p.getInventory().setItem(1, fishing);
            p.getInventory().setItem(7, wood);
            p.getInventory().setItem(2, bow);
            p.getInventory().setItem(10, arrow);
            p.getInventory().setItem(8, apple);

            p.getInventory().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
            p.getInventory().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
            p.getInventory().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
            p.getInventory().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));

        }
    }
    public static void resetPlayer(Player p) {
        File file = new File(Pvp.getInstance().getDataFolder(), "config.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        String spawn = config.getString("lobby.location");
        if (spawn == null) {
            p.teleport(p.getWorld().getSpawnLocation());
        } else {
            Location local = LocationUtils.getFromLocation(spawn);
            p.teleport(local);
        }

        p.getInventory().setHelmet(new ItemStack(null, 1));
        p.getInventory().setChestplate(new ItemStack(null, 1));
        p.getInventory().setLeggings(new ItemStack(null, 1));
        p.getInventory().setBoots(new ItemStack(null, 1));
        p.getInventory().clear();
        p.setHealth(20.0);

    }

    public void TpToPositions() {
        int count = 1;
        for (UUID id : arena.getPlayers()) {
            Player p = Pvp.getInstance().getServer().getPlayer(id);
            if (count <= 1) {
                Location spawnOne = getFromLocation(arena.getSpawnOne());
                p.teleport(spawnOne);
            } else {
                Location spawnTwo = getFromLocation(arena.getSpawnTwo());
                p.teleport(spawnTwo);
            }
            count++;
        }
    }

    public static boolean inArena(Player p) {
        HashMap<String, Arena> arenas = Pvp.getArenaManager().getArenas();
        for (Arena arena : arenas.values()) {
            if (arena.getPlayers().contains(p.getUniqueId())) {
                return true;
            }
        }
        return false;
    }

    public static Arena getArena(Player p) {
        HashMap<String, Arena> arenas = Pvp.getArenaManager().getArenas();
        for (Arena arena : arenas.values()) {
            if (arena.getPlayers().contains(p.getUniqueId())) {
                return arena;
            }
        }
        return null;
    }

}

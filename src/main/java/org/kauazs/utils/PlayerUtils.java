package org.kauazs.utils;

import com.sun.tools.javac.jvm.Items;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.kauazs.Pvp;
import org.kauazs.managers.Arena;

import java.util.HashMap;
import java.util.UUID;


import static org.kauazs.utils.LocationUtils.getFromLocation;

public class PlayerUtils {
    private Arena arena;

    public PlayerUtils(Arena arena) {
        this.arena = arena;

        this.TpToPositions();
        for (UUID id : arena.getPlayers()) {
            Pvp.getInstance().playersIn.put(id, arena);
        }
    }

    public void giveItems() {
        for (UUID player : this.arena.getPlayers()) {
            Player p = Pvp.getInstance().getServer().getPlayer(player);

            ItemStack sword = new ItemStack(Material.IRON_SWORD);

            ItemMeta swordMeta = sword.getItemMeta();
            swordMeta.addEnchant(Enchantment.DAMAGE_ALL, 2, false);
            sword.setItemMeta(swordMeta);

            ItemStack apple = new ItemStack(Material.GOLDEN_APPLE, 6);
            ItemStack wood = new ItemStack(Material.WOOD, 64);

            p.getInventory().clear();

            p.getInventory().setItem(0, sword);
            p.getInventory().setItem(7, wood);
            p.getInventory().setItem(8, apple);

            p.getInventory().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
            p.getInventory().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
            p.getInventory().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
            p.getInventory().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));

        }
    }
    public void TpToPositions() {
        int count = 1;
        for (UUID id : arena.getPlayers()) {
            Player p = Pvp.getInstance().getServer().getPlayer(id);
            if (count <= 1) {
                Location spawnOne = getFromLocation(p.getWorld(), arena.getSpawnOne());
                p.teleport(spawnOne);
            } else {
                Location spawnTwo = getFromLocation(p.getWorld(), arena.getSpawnTwo());
                p.teleport(spawnTwo);
            }
            count++;
        }
    }

    public boolean inArena(Player p) {
        return Pvp.getInstance().playersIn.containsKey(p.getUniqueId());
    }
}

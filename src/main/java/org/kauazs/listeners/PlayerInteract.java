package org.kauazs.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.kauazs.Pvp;
import org.kauazs.managers.ArenaSetupManager;

import java.util.Arrays;


public class PlayerInteract implements Listener {

    @EventHandler
    public void onPlayerInteract (PlayerInteractEvent event) {
        if(!(event.hasItem()) || event.getAction().equals(Action.LEFT_CLICK_AIR) || event.getAction().equals(Action.LEFT_CLICK_BLOCK)) return;

        Player player = event.getPlayer();
        ArenaSetupManager arena = Pvp.getArenaManager();

        if(event.getItem().getType().equals(Material.STONE_SWORD)) {
            Integer i = arena.getId(player);
            arena.addArenaPos(i, player.getLocation(), 1);

            player.sendMessage(ChatColor.BOLD + "" + ChatColor.YELLOW + "SETUP:" + ChatColor.RESET + " Posição do " + ChatColor.RED + "primeiro" + ChatColor.RESET + " oponente configurada!");
        }
        else if(event.getItem().getType().equals(Material.IRON_SWORD)) {
            int i = arena.getId(player);
            arena.addArenaPos(i, player.getLocation(), 2);

            player.sendMessage(ChatColor.BOLD + "" + ChatColor.YELLOW + "SETUP:" + ChatColor.RESET + " Posição do " + ChatColor.RED + "segundo" + ChatColor.RESET + " oponente configurada!");
        } else if(event.getItem().getType().equals(Material.ARROW)) {
            int i = arena.getId(player) + 1;
            arena.setId(player, i);

            ItemStack arrow = event.getItem();
            ItemMeta arrowMeta = arrow.getItemMeta();
            arrowMeta.setLore(Arrays.asList(String.format("%s Arena", i)));
            arrow.setItemMeta(arrowMeta);

            player.getInventory().setItem(7, arrow);
            player.sendMessage(ChatColor.BOLD + "" + ChatColor.YELLOW + "Arena:" + ChatColor.RESET + " Você configurou a arena " + ChatColor.GREEN + (i - 1) + ChatColor.RESET + " com sucesso, iniciando configuração da arena " + i);
        } else if(event.getItem().getType().equals(Material.REDSTONE_BLOCK)) {
            event.setCancelled(true);

            player.getInventory().clear();
            player.sendMessage(ChatColor.BOLD + "" + ChatColor.YELLOW + "Arena:" + ChatColor.RESET + " Você configurou a arena com " + ChatColor.GREEN + "sucesso!");
            player.playSound(player.getLocation(), Sound.LEVEL_UP, 100, 0);
        }

    }
}

package org.kauazs.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.kauazs.Pvp;
import org.kauazs.managers.ArenaSetupManager;

import java.util.Arrays;

import static org.kauazs.utils.ItemManager.ItemCreate;

public class SetupArena implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if(!(sender instanceof Player)) {
            sender.sendMessage("Apenas players podemm utilizar este comando");
            return true;
        }
        ArenaSetupManager arena = Pvp.getArenaManager();
        ItemStack stoneSword = ItemCreate(Material.STONE_SWORD, ChatColor.BOLD + "" + ChatColor.RED + "Primeiro oponente");
        ItemStack ironSword = ItemCreate(Material.IRON_SWORD, ChatColor.BOLD + "" + ChatColor.BLUE + "Segundo oponente");
        ItemStack redstoneBlock = ItemCreate(Material.REDSTONE_BLOCK, ChatColor.BOLD + "" + ChatColor.RED + "Finalizar");

        ItemStack arrow = ItemCreate(Material.ARROW, ChatColor.BOLD + "" + ChatColor.GREEN + "Proxima arena");
        ItemMeta arrowMeta = arrow.getItemMeta();
        arrowMeta.setLore(Arrays.asList("{} Arena"));
        arrow.setItemMeta(arrowMeta);
        player.getInventory().clear();

        player.getInventory().setItem(0, stoneSword);
        player.getInventory().setItem(1, ironSword);
        player.getInventory().setItem(7, arrow);
        player.getInventory().setItem(8, redstoneBlock);

        player.sendMessage(ChatColor.BOLD + "" + ChatColor.YELLOW + "SETUP:" + ChatColor.RESET + " Configure as arenas utilizando os itens fornecidos");

        arena.startSetup(player);
        return true;
    }
}

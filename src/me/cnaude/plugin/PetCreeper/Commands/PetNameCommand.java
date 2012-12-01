/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.cnaude.plugin.PetCreeper.Commands;

import me.cnaude.plugin.PetCreeper.Pet;
import me.cnaude.plugin.PetCreeper.PetConfig;
import me.cnaude.plugin.PetCreeper.PetMain;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

/**
 *
 * @author cnaude
 */
public class PetNameCommand implements CommandExecutor {

    private final PetMain plugin;

    public PetNameCommand(PetMain instance) {
        plugin = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (plugin.isPetOwner(p)) {
                if (args.length > 1 && args[0].matches("\\d+")) {
                    int idx = Integer.parseInt(args[0]) - 1;
                    if (idx >= 0 && idx < plugin.getPetsOf(p).size()) {
                        String s = "";
                        for (int i = 1; i < args.length; i++) {
                            s += args[i] + " ";
                        }
                        s = s.substring(0, s.length() - 1);
                        Pet pet = plugin.getPetsOf(p).get(idx);
                        if (!s.isEmpty()) {
                            Entity e = plugin.getEntityOfPet(pet);
                            if (s.equalsIgnoreCase("random")) {
                                String rnd = plugin.getRandomName();
                                if (!rnd.isEmpty()) {
                                    pet.petName = rnd;
                                } else {
                                    pet.petName = e.getType().getName();
                                }
                            } else {
                                pet.petName = s;
                            }                            
                            if (plugin.petNameList.containsKey(e)) {
                                plugin.petNameList.remove(e);
                                plugin.petNameList.put(e, pet.petName);
                            }                            
                            plugin.message(p, ChatColor.GREEN + "You named your pet " + ChatColor.YELLOW + pet.petName + ChatColor.GREEN + "!");
                        } else {
                            plugin.message(p, ChatColor.RED + "Invalid pet name.");
                        }
                    } else {
                        plugin.message(p, ChatColor.RED + "Invalid pet ID.");
                    }
                } else {
                    plugin.message(p, ChatColor.YELLOW + "Usage: " + ChatColor.WHITE + "/" + PetConfig.commandPrefix + "name [id] [name]");
                }
            } else {
                plugin.message(p, ChatColor.RED + "You have no pets. :(");
            }
        }
        return true;
    }
}
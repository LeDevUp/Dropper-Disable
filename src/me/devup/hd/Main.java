package me.devup.hd;

import java.util.Iterator;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Recipe;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
	
	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
		
		Iterator<Recipe> recipes = getServer().recipeIterator();
		
		while (recipes.hasNext()) {
			Recipe r = recipes.next();
			
			if(r.getResult().getType() == Material.DROPPER) {
				recipes.remove();
				
				break;
			}
		}
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		if(e.getBlock().getType().equals(Material.DROPPER)) {
			if(!(e.isCancelled())) {
				e.setCancelled(true);
				
				e.getPlayer().getInventory().clear(e.getPlayer().getInventory().getHeldItemSlot());
			}
		}
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		if(e.getBlock().getType().equals(Material.DROPPER)) {
			if(!(e.isCancelled())) {
				e.setCancelled(true);
				
				e.getBlock().setType(Material.AIR);
			}
		}
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Block b = e.getClickedBlock();
			
			if(b == null || b.getType() == Material.AIR)
				return;
			
			if(b.getType() == Material.DROPPER) {
				if(!(e.isCancelled())) {
					e.setCancelled(true);
					
					b.setType(Material.AIR);
				}
			}
		}
	}

}

package me.number1_Master.TenJava;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class PlayerListener implements Listener
{
	private final Map<String, EntityBlockSpawn> entityTasks = new HashMap<String, EntityBlockSpawn>();

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e)
	{
		if(e.getAction() == Action.RIGHT_CLICK_AIR && e.getItem() == null)
		{
			Location loc = e.getPlayer().getLocation().clone();
			Fireball fireball = (Fireball) loc.getWorld().spawnEntity(loc, EntityType.FIREBALL);
			fireball.setDirection(loc.getDirection());
			fireball.setShooter(e.getPlayer());
			fireball.setIsIncendiary(false);
			fireball.setBounce(false);
			fireball.setYield(0);
		}
	}

	@EventHandler
	public void onPlayerInteractEntity(PlayerInteractEntityEvent e)
	{ e.getRightClicked().setPassenger(e.getPlayer()); }

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e)
	{ TenJava.spawnFirework(e.getPlayer().getLocation()); }

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e)
	{
		Player player		= e.getPlayer();
		String playerName	= player.getName();
		Block under			= e.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN);
		ItemStack boots		= player.getInventory().getBoots();

		if(this.entityTasks.containsKey(playerName))
		{
			if(this.entityTasks.get(playerName).getMaterial() == under.getType() &&
					boots != null && boots.getType() == Material.DIAMOND_BOOTS)
				return;
			else
			{
				this.entityTasks.get(playerName).cancel();
				this.entityTasks.remove(player.getName());
				return;
			}
		}

		switch(under.getType())
		{
			case GRASS:
				this.entityTasks.put(playerName, new EntityBlockSpawn(player, Material.GRASS, EntityType.VILLAGER));
				break;

			case COBBLESTONE:
				this.entityTasks.put(playerName, new EntityBlockSpawn(player, Material.COBBLESTONE, EntityType.ZOMBIE));
				break;

			case DIRT:
				this.entityTasks.put(playerName, new EntityBlockSpawn(player, Material.DIRT, EntityType.SPIDER));
				break;

			case ICE:
				this.entityTasks.put(playerName, new EntityBlockSpawn(player, Material.ICE, EntityType.SKELETON));
				break;

			case MOSSY_COBBLESTONE:
				this.entityTasks.put(playerName, new EntityBlockSpawn(player, Material.MOSSY_COBBLESTONE, EntityType.SILVERFISH));
				break;

			case WATER:
				this.entityTasks.put(playerName, new EntityBlockSpawn(player, Material.WATER, EntityType.SQUID));
				break;

			case LEAVES:
				this.entityTasks.put(playerName, new EntityBlockSpawn(player, Material.LEAVES, EntityType.SPIDER));
				break;

			case SNOW:
				this.entityTasks.put(playerName, new EntityBlockSpawn(player, Material.SNOW, EntityType.WOLF));
				break;

			case AIR:
				if(under.getRelative(BlockFace.DOWN).getType() == Material.AIR)
					this.entityTasks.put(playerName, new EntityBlockSpawn(player, Material.AIR, EntityType.WITHER));
				break;

			case NETHERRACK:
				this.entityTasks.put(playerName, new EntityBlockSpawn(player, Material.NETHERRACK, EntityType.MAGMA_CUBE));
				break;

			case NETHER_BRICK:
				this.entityTasks.put(playerName, new EntityBlockSpawn(player, Material.NETHER_BRICK, EntityType.BLAZE));
				break;
		}
	}
}

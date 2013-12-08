package me.number1_Master.TenJava;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class PlayerListener implements Listener
{
	private final Map<String, EntityBlockSpawn> entityTasks = new HashMap<String, EntityBlockSpawn>();

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e)
	{
		// If the player punches the air, then shoot a fireball. //
		if(e.getAction() == Action.LEFT_CLICK_AIR && e.getItem() == null)
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
	{
		// If a player right clicks an entity, make the player ride the entity. //
		e.getRightClicked().setPassenger(e.getPlayer());
		// If the random integer is less than 10, create an explosion. //
		if(new Random().nextInt(100) < 10) e.getPlayer().getWorld().createExplosion(e.getPlayer().getLocation(), 4);
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e)
	{ TenJava.spawnFirework(e.getPlayer().getLocation()); }

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e)
	{
		Player player = e.getPlayer();

		// If a player is already inside a boat, go in the direction of the boat.
		if(player.isInsideVehicle() && player.getVehicle().getType() == EntityType.BOAT)
		{
			player.getVehicle().setVelocity(player.getLocation().getDirection());
			return;
		}

		String playerName	= player.getName();
		ItemStack boots = player.getInventory().getBoots();
		Block under			= e.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN);

		// If the player is spawning entities, check if they should continue spawning entities. //
		if(this.entityTasks.containsKey(playerName))
		{
			if((boots == null || (boots.getType() != Material.DIAMOND_BOOTS)) &&
					this.entityTasks.get(playerName).getMaterial() != under.getType())
			{
				this.entityTasks.get(playerName).cancel();
				this.entityTasks.remove(player.getName());
			}

			return;
		}

		if(boots == null || boots.getType() != Material.DIAMOND_BOOTS) return;

		// According to block type, begin spawning entites. //
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

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e)
	{
		// If the player is spawning mobs, remove him or her from mermoy. //
		String playerName = e.getPlayer().getName();
		if(this.entityTasks.containsKey(playerName))
		{
			this.entityTasks.get(playerName).cancel();
			this.entityTasks.remove(playerName);
		}
	}
}

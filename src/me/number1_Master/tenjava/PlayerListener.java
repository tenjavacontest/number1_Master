package me.number1_Master.TenJava;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;

public class PlayerListener implements Listener
{
	private ArrayList<String> safe = new ArrayList<String>();
	private HashMap<String, EntityBlockSpawn> entityTasks = new HashMap<String, EntityBlockSpawn>();

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
	public void onBlockPlace(BlockPlaceEvent e)
	{ this.placeBreakBlock(e.getBlock()); }

	@EventHandler
	public void onBlockBreak(BlockBreakEvent e)
	{ this.placeBreakBlock(e.getBlock());}

	private void placeBreakBlock(Block block)
	{
		Location loc = block.getLocation().clone();

		for(int i = 0; i < 5; i++)
		{
			FallingBlock fallingBlock = loc.getWorld().spawnFallingBlock(loc, block.getType(), block.getData());
			fallingBlock.setVelocity(new Vector(Math.PI, Math.PI, Math.PI));
		}

		if(block.getType() == Material.TNT) loc.getWorld().spawnEntity(loc, EntityType.CREEPER);

		TenJava.spawnFirework(loc);
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e)
	{
		e.getPlayer().sendMessage(TenJava.getPrefix() + "You have 10 seconds to get to a safe spot!");
		TenJava.spawnFirework(e.getPlayer().getLocation());

		final String playerName = e.getPlayer().getName();

		new BukkitRunnable()
		{
			@Override
			public void run()
			{ if(safe.contains(playerName)) safe.remove(playerName); }

		}.runTaskLater(TenJava.p(), 200);
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e)
	{
		Player player		= e.getPlayer();
		String playerName	= player.getName();

		if(safe.contains(playerName)) return;

		Location loc		= player.getLocation().clone();
		Block under			= e.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN);

		if(this.entityTasks.containsKey(playerName))
		{
			if(this.entityTasks.get(playerName).getMaterial() == under.getType()) return;
			else
			{
				this.entityTasks.get(playerName).cancel();
				this.entityTasks.remove(player.getName());
			}
		}

		switch(under.getType())
		{
			case COBBLESTONE:
				this.entityTasks.put(playerName, new EntityBlockSpawn(Material.GRASS, loc, EntityType.ZOMBIE));
				break;

			case DIRT:
				this.entityTasks.put(playerName, new EntityBlockSpawn(Material.DIRT, loc, EntityType.SPIDER));
				break;

			case ICE:
				this.entityTasks.put(playerName, new EntityBlockSpawn(Material.SAND, loc, EntityType.SKELETON));
				break;

			case MOSSY_COBBLESTONE:
				this.entityTasks.put(playerName, new EntityBlockSpawn(Material.STONE, loc, EntityType.SILVERFISH));
				break;

			case WATER:
				this.entityTasks.put(playerName, new EntityBlockSpawn(Material.WATER, loc, EntityType.SQUID));
				break;

			case LEAVES:
				this.entityTasks.put(playerName, new EntityBlockSpawn(Material.LEAVES, loc, EntityType.SPIDER));
				break;

			case SNOW:
				this.entityTasks.put(playerName, new EntityBlockSpawn(Material.SNOW, loc, EntityType.WOLF));

			case AIR:
				if(under.getRelative(BlockFace.DOWN).getType() == Material.AIR)
					this.entityTasks.put(playerName, new EntityBlockSpawn(Material.AIR, loc, EntityType.WITHER));
				break;

			case NETHERRACK:
				this.entityTasks.put(playerName, new EntityBlockSpawn(Material.NETHERRACK, loc, EntityType.MAGMA_CUBE));
				break;

			case NETHER_BRICK:
				this.entityTasks.put(playerName, new EntityBlockSpawn(Material.NETHER_BRICK, loc, EntityType.BLAZE));
				break;
		}
	}
}

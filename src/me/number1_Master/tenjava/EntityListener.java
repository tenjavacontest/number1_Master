package me.number1_Master.tenjava;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Pig;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EntityListener implements Listener
{
	private final List<String> entityNames;

	public EntityListener()
	{
		this.entityNames = new ArrayList<String>();
		// Possible names for mobs. //
		this.entityNames.add(this.getRandomColor() + "Maurquitte");
		this.entityNames.add(this.getRandomColor() + "George");
		this.entityNames.add(this.getRandomColor() + "Jerome");
		this.entityNames.add(this.getRandomColor() + "Jeral");
		this.entityNames.add(this.getRandomColor() + "Harry");
		this.entityNames.add(this.getRandomColor() + "Beautiful");
		this.entityNames.add(this.getRandomColor() + "Witch");
		this.entityNames.add(this.getRandomColor() + "Defaq");
		this.entityNames.add(this.getRandomColor() + "Reality");
		this.entityNames.add(this.getRandomColor() + "Maurice");
		this.entityNames.add(this.getRandomColor() + "DeShawn");
		this.entityNames.add(this.getRandomColor() + "Reginald");
		this.entityNames.add(this.getRandomColor() + "Tyrone");
		this.entityNames.add(this.getRandomColor() + "Marquis");
		this.entityNames.add(this.getRandomColor() + "Bradley");
		this.entityNames.add(this.getRandomColor() + "Judge Dread");
		this.entityNames.add(this.getRandomColor() + "Dustin");
		this.entityNames.add(this.getRandomColor() + "Tierra");
		this.entityNames.add(this.getRandomColor() + "Deja");
		this.entityNames.add(this.getRandomColor() + "Holly");
		this.entityNames.add(this.getRandomColor() + "Madeline");
		this.entityNames.add(this.getRandomColor() + "Caesar");
		this.entityNames.add(this.getRandomColor() + "Kacper");
	}

	private ChatColor getRandomColor()
	{
		int color = new Random().nextInt(21);
		if(color == 0) return ChatColor.AQUA;
		else if(color == 1) return ChatColor.BLACK;
		else if(color == 2) return ChatColor.BLUE;
		else if(color == 3) return ChatColor.BOLD;
		else if(color == 4) return ChatColor.DARK_AQUA;
		else if(color == 5) return ChatColor.DARK_BLUE;
		else if(color == 6) return ChatColor.DARK_GRAY;
		else if(color == 7) return ChatColor.DARK_GREEN;
		else if(color == 8) return ChatColor.DARK_PURPLE;
		else if(color == 9) return ChatColor.DARK_RED;
		else if(color == 10) return ChatColor.GOLD;
		else if(color == 11) return ChatColor.GRAY;
		else if(color == 12) return ChatColor.GREEN;
		else if(color == 13) return ChatColor.ITALIC;
		else if(color == 14) return ChatColor.LIGHT_PURPLE;
		else if(color == 15) return ChatColor.MAGIC;
		else if(color == 16) return ChatColor.RED;
		else if(color == 17) return ChatColor.STRIKETHROUGH;
		else if(color == 18) return ChatColor.UNDERLINE;
		else if(color == 19) return ChatColor.WHITE;
		else return ChatColor.YELLOW;

	}

	@EventHandler
	public void onEntitySpawn(CreatureSpawnEvent e)
	{
		final LivingEntity entity = e.getEntity();

		// Prevent Mob spawning if there are too many mobs. //
		if(entity.getWorld().getEntities().size() >= 2048 * (Runtime.getRuntime().totalMemory() / 1024))
		{
			e.setCancelled(true);
			return;
		}

		Random random = new Random();

		switch(e.getEntityType())
		{
			// Zombies will be randomly named but also spawn pigs. //
			case ZOMBIE:
				e.getEntity().setCustomName(this.entityNames.get(random.nextInt(this.entityNames.size())));

				new BukkitRunnable()
				{
					@Override
					public void run()
					{
						if(entity.isDead()) this.cancel();
						else
						{
							Pig pig = (Pig) entity.getLocation().getWorld().spawnEntity(entity.getLocation(), EntityType.PIG);
							pig.setBaby();
						}
					}

				}.runTaskTimer(TenJava.p(), 100, 600);
				break;

			// Slimes are named Jerry. //
			case SLIME:
				entity.setCustomName("Jerry");
				break;

			// Withers are named Big Daddy. //
			case WITHER:
				entity.setCustomName("[Pimp] Big Daddy");
				break;

			// Ender Dragons are named Mama Endy. //
			case ENDER_DRAGON:
				entity.setCustomName("Mama Endy");
				break;

			// Villagers are Clones. //
			case VILLAGER:
				entity.setCustomName("Clone #" + random.nextInt(9876));
				break;

			// By default, name the mob randomly. //
			default:
				e.getEntity().setCustomName(this.entityNames.get(random.nextInt(this.entityNames.size())));
				break;
		}
	}

	private final List<FallingBlock> fallingBlocks = new ArrayList<FallingBlock>();

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e)
	{ this.placeBreakBlock(e.getBlock()); }

	@EventHandler
	public void onBlockBreak(BlockBreakEvent e)
	{ this.placeBreakBlock(e.getBlock());}

	@EventHandler
	public void onEntityExplode(EntityExplodeEvent e)
	{
		e.getLocation().getWorld().spawnEntity(e.getLocation(), EntityType.CREEPER);

		for(Block block : e.blockList()) this.placeBreakBlock(block);
	}

	// When a block is placed or destroyed, create falling sand entities and spawn a firework. //
	private void placeBreakBlock(Block block)
	{
		Location loc = block.getLocation().clone();

		for(int i = 0; i < 5; i++)
		{
			FallingBlock fallingBlock = loc.getWorld().spawnFallingBlock(loc, block.getType(), block.getData());

			Random r = new Random();
			Vector velocity = new Vector();
			velocity.setX((r.nextBoolean()) ? Math.random() : - Math.random());
			velocity.setY((r.nextBoolean()) ? Math.random() : -Math.random());
			velocity.setZ((r.nextBoolean()) ? Math.random() : -Math.random());
			fallingBlock.setVelocity(velocity);

			this.fallingBlocks.add(fallingBlock);
		}

		TenJava.spawnFirework(loc);

		// If the block is TNT, spawn a creeper! //
		if(block.getType() == Material.TNT) loc.getWorld().spawnEntity(loc, EntityType.CREEPER);
	}

	@EventHandler
	public void onEntityChangeBlock(EntityChangeBlockEvent e)
	{
		// Clear falling blocks before they are placed. //
		if(e.getEntity().getType() == EntityType.FALLING_BLOCK && this.fallingBlocks.contains(e.getEntity()))
		{
			this.fallingBlocks.remove(e.getEntity());
			e.getEntity().remove();
			e.getBlock().setType(Material.AIR);
			e.setCancelled(true);
		}
	}
}

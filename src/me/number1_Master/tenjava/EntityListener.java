package me.number1_Master.TenJava;

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
		this.entityNames.add("Maurquitte");
		this.entityNames.add("George");
		this.entityNames.add("Jerome");
		this.entityNames.add("Jeral");
		this.entityNames.add("Harry");
		this.entityNames.add("Beautiful");
		this.entityNames.add("Witch");
		this.entityNames.add("Defaq");
		this.entityNames.add("Reality");
		this.entityNames.add("Maurice");
		this.entityNames.add("DeShawn");
		this.entityNames.add("Reginald");
		this.entityNames.add("Tyrone");
		this.entityNames.add("Marquis");
		this.entityNames.add("Bradley");
		this.entityNames.add("Judge Dread");
		this.entityNames.add("Dustin");
		this.entityNames.add("Tierra");
		this.entityNames.add("Deja");
		this.entityNames.add("Holly");
		this.entityNames.add("Madeline");
		this.entityNames.add("Caesar");
		this.entityNames.add("Kacper");
	}

	@EventHandler
	public void onEntityExplode(EntityExplodeEvent e)
	{ e.getLocation().getWorld().spawnEntity(e.getLocation(), EntityType.CREEPER); }

	@EventHandler
	public void onEntitySpawn(CreatureSpawnEvent e)
	{
		final LivingEntity entity = e.getEntity();
		Random random = new Random();

		switch(e.getEntityType())
		{
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

			case SLIME:
				entity.setCustomName("Jerry");
				break;

			case WITHER:
				entity.setCustomName("Big Daddy");
				break;

			case ENDER_DRAGON:
				entity.setCustomName("Mama Endy");
				break;

			case VILLAGER:
				entity.setCustomName("Clone #" + random.nextInt(9876));
				break;

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

		if(block.getType() == Material.TNT) loc.getWorld().spawnEntity(loc, EntityType.CREEPER);

		TenJava.spawnFirework(loc);
	}

	@EventHandler
	public void onEntityChangeBlock(EntityChangeBlockEvent e)
	{
		if(fallingBlocks.contains(e.getEntity()))
		{
			e.setCancelled(true);
			e.getBlock().setType(Material.AIR);
			e.getEntity().remove();
		}
	}
}

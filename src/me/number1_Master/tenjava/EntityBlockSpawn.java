package me.number1_Master.tenjava;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class EntityBlockSpawn
{
	private final Material material;
	private final BukkitTask task;

	public EntityBlockSpawn(final Player player, Material material, final EntityType entity)
	{
		this.material	= material;

		this.task = new BukkitRunnable()
		{
			@Override
			public void run()
			{
				Location loc	= player.getLocation();
				World world		= player.getWorld();

				// Spawn the entity 6 blocks in the x, -x, z, and -z directions of the player. //
				Location loc1	= loc.clone().add(6, 0, 0);
				Location loc2	= loc.clone().subtract(6, 0, 0);
				Location loc3	= loc.clone().add(0, 0, 6);
				Location loc4	= loc.clone().subtract(0, 0, 6);

				world.spawnEntity(loc1, entity);
				world.spawnEntity(loc2, entity);
				world.spawnEntity(loc3, entity);
				world.spawnEntity(loc4, entity);
			}

		}.runTaskTimer(TenJava.p(), 100, 100);
	}

	public Material getMaterial()
	{ return this.material; }

	public void cancel()
	{ this.task.cancel(); }
}

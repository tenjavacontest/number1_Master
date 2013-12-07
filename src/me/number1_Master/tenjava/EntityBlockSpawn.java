package me.number1_Master.TenJava;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class EntityBlockSpawn
{
	private final Material material;
	private final BukkitTask task;

	public EntityBlockSpawn(Material material, Location loc, final EntityType entity)
	{
		this.material	= material;

		final World world	= loc.getWorld();
		final Location loc1	= loc.add(8, 0, 0);
		final Location loc2	= loc.add(-8, 0, 0);
		final Location loc3	= loc.add(0, 0, 8);
		final Location loc4	= loc.add(0, 0, -8);

		this.task = new BukkitRunnable()
		{
			@Override
			public void run()
			{
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

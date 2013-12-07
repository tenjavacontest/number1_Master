package me.number1_Master.TenJava;

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
				Location loc1	= loc.clone().add(8, 0, 0);
				Location loc2	= loc.clone().subtract(8, 0, 0);
				Location loc3	= loc.clone().add(0, 0, 8);
				Location loc4	= loc.clone().subtract(0, 0, 8);

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

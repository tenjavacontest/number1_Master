package me.number1_Master.TenJava;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

public class TenJava extends JavaPlugin
{
	private static TenJava p;

	@Override
	public void onEnable()
	{
		// JavaPlugin Instance. //
		p = this;

		// Registering /entity Command. /
		this.getCommand("entity").setExecutor(new EntityCommand());
		this.getCommand("entity").setTabCompleter(new EntityTabCompleter());

		// Registering Listeners. //
		this.getServer().getPluginManager().registerEvents(new PlayerListener(), this);
		this.getServer().getPluginManager().registerEvents(new EntityListener(), this);
	}

	public static TenJava p()
	{ return p; }

	public static String getPrefix()
	{ return ChatColor.DARK_GREEN + "[Entites] " + ChatColor.RED; }

	public static void spawnFirework(Location loc)
	{
		Firework firework = (Firework) loc.getWorld().spawnEntity(loc, EntityType.FIREWORK);
		FireworkMeta fwMeta = firework.getFireworkMeta();

		// Get a random explosion and fade color for the firework. //
		Color c1 = getFireworkColor(new Random().nextInt(18) + 1);
		Color c2 = getFireworkColor(new Random().nextInt(18) + 1);

		// Create and add the firework effect.
		FireworkEffect effect = FireworkEffect.builder().withColor(c1).withFade(c2).with(FireworkEffect.Type.BALL_LARGE).build();
		fwMeta.addEffect(effect);

		fwMeta.setPower(1);

		firework.setFireworkMeta(fwMeta);
	}

	private static Color getFireworkColor(int index)
	{
		if(index == 1) return Color.AQUA;
		else if(index == 2) return Color.BLACK;
		else if(index == 4) return Color.BLUE;
		else if(index == 5) return Color.FUCHSIA;
		else if(index == 6) return Color.GRAY;
		else if(index == 7) return Color.GREEN;
		else if(index == 8) return Color.LIME;
		else if(index == 9) return Color.MAROON;
		else if(index == 10) return Color.NAVY;
		else if(index == 11) return Color.OLIVE;
		else if(index == 12) return Color.ORANGE;
		else if(index == 13) return Color.PURPLE;
		else if(index == 14) return Color.RED;
		else if(index == 15) return Color.SILVER;
		else if(index == 16) return Color.TEAL;
		else if(index == 17) return Color.WHITE;
		else return Color.YELLOW;
	}
}

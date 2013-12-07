package me.number1_Master.tenjava;

import org.bukkit.plugin.java.JavaPlugin;

public class TenJava extends JavaPlugin
{
	private static TenJava p;

	@Override
	public void onEnable()
	{
		p = this;


	}

	public static TenJava p()
	{ return p; }
}

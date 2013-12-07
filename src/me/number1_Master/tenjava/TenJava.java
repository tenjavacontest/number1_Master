package me.number1_Master.TenJava;

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

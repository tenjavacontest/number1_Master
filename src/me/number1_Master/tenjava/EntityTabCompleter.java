package me.number1_Master.TenJava;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.List;

public class EntityTabCompleter implements TabCompleter
{
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String cmdLabel, String[] args)
	{
		List<String> possibilities = new ArrayList<String>();

		// For each existing entity, check for tab completion.
		for(EntityType entity : EntityType.values())
		{
			String entityName = entity.toString().toLowerCase();
			if(entityName.startsWith(args[args.length - 1])) possibilities.add(entityName);
		}

		return possibilities;
	}
}

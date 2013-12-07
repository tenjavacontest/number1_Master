package me.number1_Master.TenJava;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class EntityCommand implements CommandExecutor
{
	private final String prefix = TenJava.getPrefix();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args)
	{
		if(sender instanceof Player)
		{
			Player player = (Player) sender;

			if(args.length == 0)
			{
				player.sendMessage(prefix + "Not enough arguments!");
				return true;
			}
			else if(args.length == 1)
			{
				EntityType entity = EntityType.valueOf(args[0]);
				if(entity != null)
				{
					player.getWorld().spawnEntity(player.getLocation(), entity);
					player.sendMessage(prefix + "Spawned entity " + args[0] + "!");
				}
				else player.sendMessage(prefix + "Unable to spawn entity " + args[0] + "!");
				return true;
			}
			else if(args.length == 2)
			{
				EntityType entity = EntityType.valueOf(args[0]);
				if(entity != null)
				{
					int amount = Integer.valueOf(args[1]);
					for(int i = 0; i < amount; i++) player.getWorld().spawnEntity(player.getLocation(), entity);
					player.sendMessage(prefix + "Spawned entity " + args[0] + "!");
				}
				else player.sendMessage(prefix + "Unable to spawn entity " + args[0] + "!");
				return true;
			}
			else
			{
				Entity lastEntity = null;

				for(int i = 0, l = args.length; i < l; i++)
				{
					EntityType entity = EntityType.valueOf(args[i]);
					if(entity != null)
					{
						Entity e = player.getWorld().spawnEntity(player.getLocation(), entity);
						if(!(lastEntity == null)) lastEntity.setPassenger(e);
						lastEntity = e;
					}
					else player.sendMessage(prefix + "Unable to spawn entity " + args[i] + "!");
				}

				player.sendMessage(prefix + args.length + " stacked entites spawned!");
				return true;
			}
		}
		else
		{
			sender.sendMessage(prefix + "You must be a player to spawn an Entity!");
			return true;
		}
	}
}

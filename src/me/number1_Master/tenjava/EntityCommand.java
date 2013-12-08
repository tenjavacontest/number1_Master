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
				if(!(player.hasPermission("entity.single")))
				{
					player.sendMessage(prefix + "You do not have permission to do that!");
					return true;
				}

				// Try to get an entity value, otherwise state the entity is unknown. //
				try
				{
					EntityType entity = EntityType.valueOf(args[0].toUpperCase());
					player.getWorld().spawnEntity(player.getLocation(), entity);
					TenJava.spawnFirework(player.getLocation());

					player.sendMessage(prefix + "Spawned entity " + args[0] + "!");
				}
				catch(Exception err)
				{ player.sendMessage(prefix + "Unable to spawn entity " + args[0] + "!"); }
				return true;
			}
			else
			{
				if(!(player.hasPermission("entity.multiple")))
				{
					player.sendMessage(prefix + "You do not have permission to do that!");
					return true;
				}

				// Parse the amount of entities asked for. //
				int amount;
				try
				{ amount = Integer.valueOf(args[0]); }
				catch(NumberFormatException err)
				{ amount = 1; }

				// Loop through each amount of entities. //
				for(int i = 0; i < amount; i++)
				{
					// If a stacked entity, place an entity on itself until finished. //
					Entity lastEntity = null;
					for(int l = 1, a = args.length; l < a; l++)
					{
						try
						{
							EntityType entity = EntityType.valueOf(args[l].toUpperCase());
							Entity e = player.getWorld().spawnEntity(player.getLocation(), entity);
							if(!(lastEntity == null)) lastEntity.setPassenger(e);
							lastEntity = e;
						}
						catch(Exception err)
						{ player.sendMessage(prefix + "Unable to spawn entity " + args[l] + "!"); }
					}

					TenJava.spawnFirework(player.getLocation());
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

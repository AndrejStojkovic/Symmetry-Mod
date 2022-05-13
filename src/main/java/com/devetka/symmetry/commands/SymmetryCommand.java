package com.devetka.symmetry.commands;

import com.devetka.symmetry.SymmetryMod;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Player;

public class SymmetryCommand {
	public SymmetryCommand(CommandDispatcher<CommandSourceStack> dispatcher) {	
		dispatcher.register(
			Commands.literal("symmetry")
				.then(Commands.literal("on").executes(c -> {
					Player player = c.getSource().getPlayerOrException();
					player.getPersistentData().putInt(SymmetryMod.MOD_ID + "symmetryEnabled", 1);
					
					String message = "Symmetry building enabled!";
					player.sendMessage(new TextComponent(message), player.getUUID());
					
					return 0;
				}))
				.then(Commands.literal("off").executes(c -> {
					Player player = c.getSource().getPlayerOrException();
					player.getPersistentData().putInt(SymmetryMod.MOD_ID + "symmetryEnabled", 0);
					
					String message = "Symmetry building disabled!";
					player.sendMessage(new TextComponent(message), player.getUUID());
					
					return 0;
				}))
				.then(Commands.literal("get").executes(c -> {
					Player player = c.getSource().getPlayerOrException();
					boolean hasSymmetryCoords = player.getPersistentData().getIntArray(SymmetryMod.MOD_ID + "symmetryPos").length != 0;
					
					if(hasSymmetryCoords) {
						int[] symmetryCoords = player.getPersistentData().getIntArray(SymmetryMod.MOD_ID + "symmetryPos");
						String message = "Symmetry coordinates: (" + symmetryCoords[0] + ", " + symmetryCoords[1] + ", " + symmetryCoords[2] + ")";
						player.sendMessage(new TextComponent(message), player.getUUID());
						
						return 1;
					} else {
						String message = "Error: No symmetry coordinates set. Use /symmetry!";
						player.sendMessage(new TextComponent(message), player.getUUID());
						
						return -1;
					}
				}))
				.then(Commands.literal("type").then(Commands.argument("number", IntegerArgumentType.integer(1, 4))
					.executes(c -> {
						Player player = c.getSource().getPlayerOrException();
						player.getPersistentData().putInt(SymmetryMod.MOD_ID + "symmetryType", IntegerArgumentType.getInteger(c, "number"));

						String message = "Symmetry Type set to: " + IntegerArgumentType.getInteger(c, "number");
						player.sendMessage(new TextComponent(message), player.getUUID());
						
						return 0;
					})).executes(c -> { return 0; }))
				.then(Commands.literal("set")
						.then(Commands.argument("x", IntegerArgumentType.integer())
						.then(Commands.argument("y", IntegerArgumentType.integer())
						.then(Commands.argument("z", IntegerArgumentType.integer())
							.executes(c -> {
								Player player = c.getSource().getPlayerOrException();
								
								int coordX = IntegerArgumentType.getInteger(c, "x");
								int coordY = IntegerArgumentType.getInteger(c, "y");
								int coordZ = IntegerArgumentType.getInteger(c, "z");
								
								String message = "Symmetry set to (" + coordX + ", " + coordY + ", " + coordZ + ")";
					            player.sendMessage(new TextComponent(message), player.getUUID());
								
								player.getPersistentData().putIntArray(SymmetryMod.MOD_ID + "symmetryPos",
	            						new int[] { coordX, coordY, coordZ });
								
								return 0;
						})))).executes(c -> { return 0; }))
		        .executes(c -> {
		            Player player = c.getSource().getPlayerOrException();
		            
		            String message = "Symmetry set to (" + player.getBlockX() + ", " + player.getBlockY() + ", " + player.getBlockZ() + ")";
		            player.sendMessage(new TextComponent(message), player.getUUID());
		            
		            player.getPersistentData().putIntArray(SymmetryMod.MOD_ID + "symmetryPos",
		            						new int[] { player.getBlockX(), player.getBlockY(), player.getBlockZ() });
		            
		            return 0;
		        }));
	}
}

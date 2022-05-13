package com.devetka.symmetry.events;

import com.devetka.symmetry.SymmetryMod;
import com.devetka.symmetry.commands.SymmetryCommand;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SymmetryMod.MOD_ID)
public class ModEvents {
	@SubscribeEvent
	public static void onCommandsRegister(RegisterCommandsEvent event) {
		new SymmetryCommand(event.getDispatcher());
	}
	
	@SubscribeEvent
    public static void BlockRightClickEvent(PlayerInteractEvent.RightClickBlock event) {
        
    }
	
	@SubscribeEvent
	public static void BlockPlace(BlockEvent.EntityPlaceEvent event) {
		Player player = (Player) event.getEntity();
        boolean hasSymmetryCoords = player.getPersistentData().getIntArray(SymmetryMod.MOD_ID + "symmetryPos").length != 0;
        
        if(hasSymmetryCoords) {
        	int type = player.getPersistentData().getInt(SymmetryMod.MOD_ID + "symmetryType");
        	int[] symmetryCoords = player.getPersistentData().getIntArray(SymmetryMod.MOD_ID + "symmetryPos");
        	
        	int difX = symmetryCoords[0];
        	//int difY = symmetryCoords[1];
        	int difZ = symmetryCoords[2];
        	
        	if(player.getPersistentData().getInt(SymmetryMod.MOD_ID + "symmetryEnabled") == 1 && player.isCreative()) { 
        		BlockState block = event.getPlacedBlock();
        		
	        	if(type == 1) {
	        		if(event.getPos().getX() == difX) {
	        			BlockPos bz = new BlockPos(event.getPos().getX(), event.getPos().getY(), getSymCoord(event.getPos().getZ(), difZ));
	        			
	        			int dist = event.getPos().getZ() - difZ;
	        			BlockPos bx1 = new BlockPos(event.getPos().getX() + dist, event.getPos().getY(), difZ);
	        			BlockPos bx2 = new BlockPos(event.getPos().getX() - dist, event.getPos().getY(), difZ);
	        			
	        			event.getWorld().setBlock(bz, block.getBlock().defaultBlockState(), 3);
		        		event.getWorld().setBlock(bx1, block.getBlock().defaultBlockState(), 3);
		        		event.getWorld().setBlock(bx2, block.getBlock().defaultBlockState(), 3);
	        		} else if(event.getPos().getZ() == difZ) {
	        			BlockPos bx = new BlockPos(getSymCoord(event.getPos().getX(), difX), event.getPos().getY(), event.getPos().getZ());
	        			
	        			int dist = event.getPos().getX() - difX;
	        			BlockPos bz1 = new BlockPos(difX, event.getPos().getY(), event.getPos().getZ() + dist);
	        			BlockPos bz2 = new BlockPos(difX, event.getPos().getY(), event.getPos().getZ() - dist);
	        			
	        			event.getWorld().setBlock(bx, block.getBlock().defaultBlockState(), 3);
		        		event.getWorld().setBlock(bz1, block.getBlock().defaultBlockState(), 3);
		        		event.getWorld().setBlock(bz2, block.getBlock().defaultBlockState(), 3);
	        		} else if(event.getPos().getX() != difX && event.getPos().getZ() != difZ) {
	        			BlockPos bp1 = new BlockPos(getSymCoord(event.getPos().getX(), difX), event.getPos().getY(), event.getPos().getZ());
		        		BlockPos bp2 = new BlockPos(event.getPos().getX(), event.getPos().getY(), getSymCoord(event.getPos().getZ(), difZ));
		        		BlockPos bp3 = new BlockPos(getSymCoord(event.getPos().getX(), difX), event.getPos().getY(), getSymCoord(event.getPos().getZ(), difZ));
		        		
		        		event.getWorld().setBlock(bp1, block.getBlock().defaultBlockState(), 3);
		        		event.getWorld().setBlock(bp2, block.getBlock().defaultBlockState(), 3);
		        		event.getWorld().setBlock(bp3, block.getBlock().defaultBlockState(), 3);
	        		}
	        	} else if(type == 2) {
	        		if(event.getPos().getX() == difX) {
	        			BlockPos bz = new BlockPos(event.getPos().getX(), event.getPos().getY(), getSymCoord(event.getPos().getZ(), difZ));
	        			event.getWorld().setBlock(bz, block.getBlock().defaultBlockState(), 3);
	        		} else if(event.getPos().getZ() == difZ) {
	        			BlockPos bx = new BlockPos(getSymCoord(event.getPos().getX(), difX), event.getPos().getY(), event.getPos().getZ());
	        			event.getWorld().setBlock(bx, block.getBlock().defaultBlockState(), 3);
	        		} else if(event.getPos().getX() != difX && event.getPos().getZ() != difZ) {
	        			BlockPos bd = new BlockPos(getSymCoord(event.getPos().getX(), difX), event.getPos().getY(), getSymCoord(event.getPos().getZ(), difZ));
	        			event.getWorld().setBlock(bd, block.getBlock().defaultBlockState(), 3);
	        		}
	        	} else if(type == 3) {
	        		BlockPos bx = new BlockPos(getSymCoord(event.getPos().getX(), difX), event.getPos().getY(), event.getPos().getZ());
	        		event.getWorld().setBlock(bx, block.getBlock().defaultBlockState(), 3);
	        	} else if(type == 4) {
	        		BlockPos bz = new BlockPos(event.getPos().getX(), event.getPos().getY(), getSymCoord(event.getPos().getZ(), difZ));
	        		event.getWorld().setBlock(bz, block.getBlock().defaultBlockState(), 3);
	        	}
        	}
        } else {
        	String message = "Error: No symmetry coordinates set. Use /symmetry (set [x] [y] [z])!";
			player.sendMessage(new TextComponent(message), player.getUUID());
        }
	}
	
	public static int getSymCoord(int coordAxis, int difAxis) {
		int a = 0;
		
		if(coordAxis > 0) { a = (-(coordAxis - difAxis)) + difAxis; }
		else if(coordAxis < 0) { a = (-(coordAxis + difAxis)) + difAxis; }
		
		return a;
	}
	
	@SubscribeEvent
	public static void OnPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
		event.getPlayer().getPersistentData().putInt(SymmetryMod.MOD_ID + "symmetryEnabled", 0);
		event.getPlayer().getPersistentData().putInt(SymmetryMod.MOD_ID + "symmetryType", 1);
	}
}

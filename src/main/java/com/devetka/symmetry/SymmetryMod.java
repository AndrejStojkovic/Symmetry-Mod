package com.devetka.symmetry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(SymmetryMod.MOD_ID)
public class SymmetryMod {
	
	public static final Logger LOGGER = LogManager.getLogger();
	public static final String MOD_ID = "symmetry";
	
	public SymmetryMod() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		MinecraftForge.EVENT_BUS.register(this);
	}
}

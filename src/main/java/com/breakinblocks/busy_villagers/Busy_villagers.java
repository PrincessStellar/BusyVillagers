package com.breakinblocks.busy_villagers;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;

@Mod(Busy_villagers.MODID)
public class Busy_villagers {
    public static final String MODID = "busy_villagers";

    public Busy_villagers(IEventBus modEventBus, ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }
}

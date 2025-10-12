package com.breakinblocks.busy_villagers;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;


@EventBusSubscriber(modid = Busy_villagers.MODID)
public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final ModConfigSpec.IntValue RESTOCK_COOLDOWN_MINUTES;
    private static final ModConfigSpec.IntValue RESTOCK_RESET_MINUTES;
    private static final ModConfigSpec.IntValue MAX_RESTOCKS_PER_PERIOD;
    private static final ModConfigSpec.BooleanValue PREVENT_VILLAGER_SLEEP;
    private static final ModConfigSpec.BooleanValue ALWAYS_WORKING;

    static {
        BUILDER.comment("Villager Restocking Configuration")
                .push("restocking");

        RESTOCK_COOLDOWN_MINUTES = BUILDER
                .comment("Cooldown between individual restocks in minutes (default: 2 minutes = 2400 ticks)")
                .defineInRange("restockCooldownMinutes", 2, 0, Integer.MAX_VALUE);

        RESTOCK_RESET_MINUTES = BUILDER
                .comment("Time after which the restock counter resets in minutes (default: 10 minutes = 12000 ticks)")
                .defineInRange("restockResetMinutes", 10, 0, Integer.MAX_VALUE);

        MAX_RESTOCKS_PER_PERIOD = BUILDER
                .comment("Maximum number of restocks allowed per reset period (default: 2)")
                .defineInRange("maxRestocksPerPeriod", 2, 1, Integer.MAX_VALUE);

        BUILDER.pop();

        BUILDER.comment("Villager Behavior Configuration")
                .push("behavior");

        PREVENT_VILLAGER_SLEEP = BUILDER
                .comment("Prevent villagers from sleeping at night (default: true)")
                .define("preventVillagerSleep", true);

        ALWAYS_WORKING = BUILDER
                .comment("Make villagers work all day without schedule changes (No more \"Meeting\" or \"Idle\", overwrites preventVillagerSleep) (default: false)")
                .define("alwaysWorking", false);

        BUILDER.pop();
    }

    static final ModConfigSpec SPEC = BUILDER.build();

    public static int restockCooldownMinutes;
    public static int restockResetMinutes;
    public static int maxRestocksPerPeriod;
    public static boolean preventVillagerSleep;
    public static boolean alwaysWorking;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        restockCooldownMinutes = RESTOCK_COOLDOWN_MINUTES.get();
        restockResetMinutes = RESTOCK_RESET_MINUTES.get();
        maxRestocksPerPeriod = MAX_RESTOCKS_PER_PERIOD.get();
        preventVillagerSleep = PREVENT_VILLAGER_SLEEP.get();
        alwaysWorking = ALWAYS_WORKING.get();
    }
}

package com.breakinblocks.busy_villagers.mixin;

import com.breakinblocks.busy_villagers.Config;
import net.minecraft.world.entity.npc.Villager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Villager.class)
public abstract class VillagerMixin {

    @Shadow
    private long lastRestockGameTime;

    @Shadow
    private int numberOfRestocksToday;

    @Shadow
    protected abstract boolean needsToRestock();

    @Shadow
    protected abstract void resetNumberOfRestocks();

    /**
     * @author BreakinBlocks
     * @reason Remove day-time dependency from villager restocking, making it purely cooldown-based
     */
    @Overwrite
    public boolean shouldRestock() {
        Villager villager = (Villager) (Object) this;
        long restockResetTime = this.lastRestockGameTime + (Config.restockResetMinutes * 1200L);
        long currentGameTime = villager.level().getGameTime();
        boolean shouldReset = currentGameTime > restockResetTime;

        if (shouldReset) {
            this.lastRestockGameTime = currentGameTime;
            this.resetNumberOfRestocks();
        }

        return this.allowedToRestock() && this.needsToRestock();
    }

    /**
     * @author BreakinBlocks
     * @reason Use configurable values for restock cooldown and max restocks per period
     */
    @Overwrite
    private boolean allowedToRestock() {
        Villager villager = (Villager) (Object) this;
        return this.numberOfRestocksToday == 0
            || this.numberOfRestocksToday < Config.maxRestocksPerPeriod
                && villager.level().getGameTime() > this.lastRestockGameTime + (Config.restockCooldownMinutes * 1200L);
    }
}

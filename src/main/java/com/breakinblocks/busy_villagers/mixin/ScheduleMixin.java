package com.breakinblocks.busy_villagers.mixin;

import com.breakinblocks.busy_villagers.Config;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.entity.schedule.Schedule;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Schedule.class)
public class ScheduleMixin {

    private static final ThreadLocal<Boolean> IN_MIXIN = ThreadLocal.withInitial(() -> false);

    /**
     * @author BreakinBlocks
     * @reason Modify villager schedule based on config settings
     */
    @Inject(method = "getActivityAt", at = @At("HEAD"), cancellable = true)
    private void modifyVillagerActivity(int time, CallbackInfoReturnable<Activity> cir) {
        if (IN_MIXIN.get()) {
            return;
        }

        Schedule schedule = (Schedule) (Object) this;

        if (schedule == Schedule.VILLAGER_DEFAULT) {
            if (Config.alwaysWorking) {
                cir.setReturnValue(Activity.WORK);
                return;
            }

            if (Config.preventVillagerSleep) {
                try {
                    IN_MIXIN.set(true);
                    Activity originalActivity = schedule.getActivityAt(time);
                    if (originalActivity == Activity.REST) {
                        cir.setReturnValue(Activity.WORK);
                    }
                } finally {
                    IN_MIXIN.set(false);
                }
            }
        }
    }
}
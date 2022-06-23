package mc.rpgstats.event;

import mc.rpgstats.main.RPGStats;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.quiltmc.qsl.base.api.event.Event;

/**
 * Callback for player level up
 * Called after messages are sent, and is purely a way to listen, you can not influence the level up
 * Lambda params - PlayerEntity player, ComponentType<? extends IStatComponent> type, int newLevel
 */
public interface LevelUpCallback {
    Event<LevelUpCallback> EVENT = Event.create(LevelUpCallback.class,
        (listeners) -> (player, id, newLevel, hideMessages) -> {
            RPGStats.levelUpCriterion.trigger((ServerPlayerEntity)player);
            for (LevelUpCallback listener : listeners) {
                listener.onLevelUp(player, id, newLevel, hideMessages);
            }
        });
    
    void onLevelUp(PlayerEntity player, Identifier id, int newLevel, boolean hideMessages);
}
package io.github.silverandro.rpgstats.event

import mc.rpgstats.main.RPGStats
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.Identifier
import org.quiltmc.qsl.base.api.event.Event

/**
 * Callback for player level up
 * Called after messages are sent, and is purely a way to listen, you can not influence the level up
 * Lambda params - PlayerEntity player, ComponentType type, int newLevel
 */
@FunctionalInterface
fun interface LevelUpCallback {
    fun onLevelUp(player: PlayerEntity, id: Identifier, newLevel: Int, hideMessages: Boolean)

    companion object {
        @JvmField
        val EVENT: Event<LevelUpCallback> = Event.create(LevelUpCallback::class.java) { listeners ->
            return@create LevelUpCallback { player, id, newLevel, hideMessages ->
                RPGStats.levelUpCriterion.trigger(player as ServerPlayerEntity?)
                for (listener in listeners) {
                    listener.onLevelUp(player, id, newLevel, hideMessages)
                }
            }
        }
    }
}
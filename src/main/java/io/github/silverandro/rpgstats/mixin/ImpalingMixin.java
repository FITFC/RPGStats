package io.github.silverandro.rpgstats.mixin;

import io.github.silverandro.rpgstats.LevelUtils;
import io.github.silverandro.rpgstats.RPGStatsMain;
import io.github.silverandro.rpgstats.stats.Components;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import org.apache.commons.lang3.mutable.MutableFloat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(EnchantmentHelper.class)
public class ImpalingMixin {
    @Inject(
            method = "getAttackDamage",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/enchantment/EnchantmentHelper;forEachEnchantment(Lnet/minecraft/enchantment/EnchantmentHelper$Consumer;Lnet/minecraft/item/ItemStack;)V"
            ),
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    private static void rpgstats$makeImpalingEffectAll(ItemStack stack, EntityGroup group, CallbackInfoReturnable<Float> cir, MutableFloat mutableFloat) {
        if (EnchantmentHelper.get(stack).containsKey(Enchantments.IMPALING) && !(group == EntityGroup.AQUATIC)) {
            if (stack.getHolder() != null && stack.getHolder() instanceof ServerPlayerEntity) {
                if (
                        LevelUtils.INSTANCE.getComponentLevel(Components.RANGED, (ServerPlayerEntity) stack.getHolder()) >= 25
                                && RPGStatsMain.levelConfig.ranged.enableLv25Buff
                ) {
                    int level = EnchantmentHelper.get(stack).get(Enchantments.IMPALING);
                    mutableFloat.add(level * 2.5F);
                }
            }
        }
    }
}
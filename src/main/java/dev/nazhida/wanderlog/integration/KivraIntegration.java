package dev.nazhida.wanderlog.integration;

import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.fml.ModList;

import java.lang.reflect.Method;
import java.math.BigDecimal;

/**
 * Optional bridge to Kivra.
 * Wanderlog does not depend on Kivra at compile time and continues to work
 * normally when Kivra is not installed.
 */
public final class KivraIntegration {
    private static final String KIVRA_MOD_ID = "kivra";

    private KivraIntegration() {}

    public static boolean isLoaded() {
        return ModList.get().isLoaded(KIVRA_MOD_ID);
    }

    /**
     * Deposits a reward through Kivra's public economy service when available.
     * Reflection keeps the integration optional instead of making Kivra a hard dependency.
     */
    public static boolean reward(ServerPlayer player, BigDecimal amount) {
        if (!isLoaded() || amount.signum() <= 0) return false;
        try {
            Class<?> kivra = Class.forName("dev.nazhida.kivra.Kivra");
            Method economyMethod = kivra.getMethod("economy");
            Object economy = economyMethod.invoke(null);
            Method deposit = economy.getClass().getMethod("deposit", java.util.UUID.class, BigDecimal.class);
            Object result = deposit.invoke(economy, player.getUUID(), amount);
            return result instanceof Boolean b && b;
        } catch (ReflectiveOperationException | LinkageError ignored) {
            return false;
        }
    }
}

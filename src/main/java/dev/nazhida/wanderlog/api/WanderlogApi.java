package dev.nazhida.wanderlog.api;

import dev.nazhida.wanderlog.player.ExplorerData;
import dev.nazhida.wanderlog.player.ExplorerSavedData;
import net.minecraft.server.MinecraftServer;

import java.util.UUID;

/** Lightweight public API for optional integrations such as Kivra. */
public final class WanderlogApi {
    private WanderlogApi() {}

    public static ExplorerStats getStats(MinecraftServer server, UUID playerId) {
        ExplorerData data = ExplorerSavedData.get(server).get(playerId);
        return new ExplorerStats(
                data.getLevel(),
                data.getXp(),
                data.xpRequiredForNextLevel(),
                data.getDiscoveredBiomeCount()
        );
    }

    public record ExplorerStats(int level, int xp, int xpForNextLevel, int discoveredBiomes) {}
}

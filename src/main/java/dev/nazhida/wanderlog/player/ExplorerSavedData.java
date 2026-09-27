package dev.nazhida.wanderlog.player;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class ExplorerSavedData extends SavedData {
    private static final String DATA_NAME = "wanderlog_explorer_data";

    private final Map<UUID, ExplorerData> players = new HashMap<>();

    public ExplorerData get(UUID playerId) {
        return players.computeIfAbsent(playerId, ignored -> new ExplorerData());
    }

    public static ExplorerSavedData get(MinecraftServer server) {
        return server.overworld().getDataStorage().computeIfAbsent(
                ExplorerSavedData::load,
                ExplorerSavedData::new,
                DATA_NAME
        );
    }

    public static ExplorerSavedData load(CompoundTag root) {
        ExplorerSavedData data = new ExplorerSavedData();
        CompoundTag playersTag = root.getCompound("players");

        for (String key : playersTag.getAllKeys()) {
            try {
                UUID uuid = UUID.fromString(key);
                data.players.put(uuid, ExplorerData.load(playersTag.getCompound(key)));
            } catch (IllegalArgumentException ignored) {
            }
        }

        return data;
    }

    @Override
    public CompoundTag save(CompoundTag root) {
        CompoundTag playersTag = new CompoundTag();
        for (Map.Entry<UUID, ExplorerData> entry : players.entrySet()) {
            playersTag.put(entry.getKey().toString(), entry.getValue().save());
        }
        root.put("players", playersTag);
        return root;
    }
}

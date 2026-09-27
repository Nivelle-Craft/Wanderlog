package dev.nazhida.wanderlog.player;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;

import java.util.HashSet;
import java.util.Set;

public final class ExplorerData {
    private int xp;
    private int level = 1;
    private final Set<String> discoveredBiomes = new HashSet<>();

    public int getXp() {
        return xp;
    }

    public int getLevel() {
        return level;
    }

    public int getDiscoveredBiomeCount() {
        return discoveredBiomes.size();
    }

    public boolean discoverBiome(String id) {
        return discoveredBiomes.add(id);
    }

    public int addXp(int amount) {
        xp += Math.max(0, amount);

        int gainedLevels = 0;
        while (xp >= xpRequiredForNextLevel()) {
            xp -= xpRequiredForNextLevel();
            level++;
            gainedLevels++;
        }

        return gainedLevels;
    }

    public int xpRequiredForNextLevel() {
        return level * 100;
    }

    public CompoundTag save() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("xp", xp);
        tag.putInt("level", level);

        ListTag biomes = new ListTag();
        discoveredBiomes.stream().sorted().forEach(id -> biomes.add(StringTag.valueOf(id)));
        tag.put("biomes", biomes);

        return tag;
    }

    public static ExplorerData load(CompoundTag tag) {
        ExplorerData data = new ExplorerData();
        data.xp = Math.max(0, tag.getInt("xp"));
        data.level = Math.max(1, tag.getInt("level"));

        ListTag biomes = tag.getList("biomes", Tag.TAG_STRING);
        for (int i = 0; i < biomes.size(); i++) {
            data.discoveredBiomes.add(biomes.getString(i));
        }

        return data;
    }
}

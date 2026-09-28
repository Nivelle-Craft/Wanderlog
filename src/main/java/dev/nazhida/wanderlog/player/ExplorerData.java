package dev.nazhida.wanderlog.player;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public final class ExplorerData {
    private int xp;
    private int level = 1;
    private final Set<String> discoveredBiomes = new HashSet<>();
    private final Set<String> overworldBiomes = new HashSet<>();
    private final Set<String> netherBiomes = new HashSet<>();
    private final Set<String> endBiomes = new HashSet<>();
    private final Set<Integer> claimedMilestones = new HashSet<>();

    public int getXp() { return xp; }
    public int getLevel() { return level; }
    public int getDiscoveredBiomeCount() { return discoveredBiomes.size(); }
    public Set<String> getDiscoveredBiomes() { return Collections.unmodifiableSet(discoveredBiomes); }
    public int getOverworldBiomeCount() { return overworldBiomes.size(); }
    public int getNetherBiomeCount() { return netherBiomes.size(); }
    public int getEndBiomeCount() { return endBiomes.size(); }

    public boolean discoverBiome(String id) { return discoverBiome(id, "overworld"); }
    public boolean discoverBiome(String id, String dimension) {
        if (!discoveredBiomes.add(id)) return false;
        switch (dimension) {
            case "nether" -> netherBiomes.add(id);
            case "end" -> endBiomes.add(id);
            default -> overworldBiomes.add(id);
        }
        return true;
    }

    public boolean claimMilestone(int milestone) {
        return getDiscoveredBiomeCount() >= milestone && claimedMilestones.add(milestone);
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

    public int xpRequiredForNextLevel() { return level * 100; }

    public CompoundTag save() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("xp", xp);
        tag.putInt("level", level);
        putStrings(tag, "biomes", discoveredBiomes);
        putStrings(tag, "overworldBiomes", overworldBiomes);
        putStrings(tag, "netherBiomes", netherBiomes);
        putStrings(tag, "endBiomes", endBiomes);
        ListTag milestones = new ListTag();
        claimedMilestones.stream().sorted().forEach(v -> milestones.add(StringTag.valueOf(String.valueOf(v))));
        tag.put("milestones", milestones);
        return tag;
    }

    private static void putStrings(CompoundTag tag, String key, Set<String> values) {
        ListTag list = new ListTag();
        values.stream().sorted().forEach(id -> list.add(StringTag.valueOf(id)));
        tag.put(key, list);
    }

    private static void readStrings(CompoundTag tag, String key, Set<String> target) {
        ListTag list = tag.getList(key, Tag.TAG_STRING);
        for (int i = 0; i < list.size(); i++) target.add(list.getString(i));
    }

    public static ExplorerData load(CompoundTag tag) {
        ExplorerData data = new ExplorerData();
        data.xp = Math.max(0, tag.getInt("xp"));
        data.level = Math.max(1, tag.getInt("level"));
        readStrings(tag, "biomes", data.discoveredBiomes);
        readStrings(tag, "overworldBiomes", data.overworldBiomes);
        readStrings(tag, "netherBiomes", data.netherBiomes);
        readStrings(tag, "endBiomes", data.endBiomes);
        ListTag milestones = tag.getList("milestones", Tag.TAG_STRING);
        for (int i = 0; i < milestones.size(); i++) {
            try { data.claimedMilestones.add(Integer.parseInt(milestones.getString(i))); } catch (NumberFormatException ignored) {}
        }
        // Old saves did not store dimensions. Keep their discoveries visible.
        if (data.overworldBiomes.isEmpty() && data.netherBiomes.isEmpty() && data.endBiomes.isEmpty()) data.overworldBiomes.addAll(data.discoveredBiomes);
        return data;
    }
}

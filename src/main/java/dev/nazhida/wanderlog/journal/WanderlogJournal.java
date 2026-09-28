package dev.nazhida.wanderlog.journal;

import dev.nazhida.wanderlog.player.ExplorerData;
import dev.nazhida.wanderlog.player.ExplorerSavedData;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class WanderlogJournal {
    private WanderlogJournal() {}

    public static void open(ServerPlayer player) {
        ExplorerData data = ExplorerSavedData.get(player.server).get(player.getUUID());
        player.openMenu(new SimpleMenuProvider((id, inventory, ignored) -> {
            SimpleContainer c = new SimpleContainer(54);
            ChestMenu menu = ChestMenu.sixRows(id, inventory, c);
            item(c, 4, Items.COMPASS, "Wanderlog Journal", "Explorer Level " + data.getLevel());
            item(c, 10, Items.EXPERIENCE_BOTTLE, "Explorer XP", data.getXp() + " / " + data.xpRequiredForNextLevel());
            item(c, 12, Items.GRASS_BLOCK, "Overworld", data.getOverworldBiomeCount() + " biomes discovered");
            item(c, 13, Items.NETHERRACK, "Nether", data.getNetherBiomeCount() + " biomes discovered");
            item(c, 14, Items.END_STONE, "The End", data.getEndBiomeCount() + " biomes discovered");
            int next = nextMilestone(data.getDiscoveredBiomeCount());
            item(c, 16, Items.GOLD_INGOT, "Next Milestone", next < 0 ? "All milestones completed" : data.getDiscoveredBiomeCount() + " / " + next + " biomes");

            List<String> biomes = new ArrayList<>(data.getDiscoveredBiomes());
            biomes.sort(Comparator.naturalOrder());
            int slot = 27;
            for (String biome : biomes) {
                if (slot >= 45) break;
                item(c, slot++, Items.FILLED_MAP, pretty(biome), biome);
            }
            if (biomes.isEmpty()) item(c, 31, Items.PAPER, "No discoveries yet", "Go explore the world!");
            if (biomes.size() > 18) item(c, 49, Items.WRITABLE_BOOK, "+" + (biomes.size() - 18) + " more discoveries", "Journal pagination is the next page upgrade");
            return menu;
        }, Component.literal("Wanderlog Journal")));
    }

    private static int nextMilestone(int count) {
        int[] milestones = {5, 10, 25, 50, 75, 100};
        for (int milestone : milestones) if (count < milestone) return milestone;
        return -1;
    }

    private static String pretty(String id) {
        String value = id.contains(":") ? id.substring(id.indexOf(':') + 1) : id;
        String[] words = value.split("_");
        StringBuilder out = new StringBuilder();
        for (String word : words) {
            if (word.isEmpty()) continue;
            if (!out.isEmpty()) out.append(' ');
            out.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1));
        }
        return out.toString();
    }

    private static void item(SimpleContainer c, int slot, net.minecraft.world.item.Item type, String name, String description) {
        ItemStack stack = new ItemStack(type);
        stack.setHoverName(Component.literal(name));
        stack.getOrCreateTag().putString("WanderlogDescription", description);
        c.setItem(slot, stack);
    }
}

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
            SimpleContainer container = new SimpleContainer(54);
            ChestMenu menu = ChestMenu.sixRows(id, inventory, container);

            item(container, 4, Items.COMPASS, "Wanderlog Journal",
                    "Explorer Level " + data.getLevel());
            item(container, 11, Items.EXPERIENCE_BOTTLE, "Explorer XP",
                    data.getXp() + " / " + data.xpRequiredForNextLevel());
            item(container, 13, Items.MAP, "Discovered Biomes",
                    data.getDiscoveredBiomeCount() + " unique biomes");

            int nextMilestone = nextMilestone(data.getDiscoveredBiomeCount());
            item(container, 15, Items.GOLD_INGOT, "Next Milestone",
                    nextMilestone < 0 ? "All current milestones completed" : data.getDiscoveredBiomeCount() + " / " + nextMilestone + " biomes");

            List<String> biomes = new ArrayList<>(data.getDiscoveredBiomes());
            biomes.sort(Comparator.naturalOrder());
            int slot = 27;
            for (String biome : biomes) {
                if (slot >= 45) break;
                item(container, slot++, Items.FILLED_MAP, pretty(biome), biome);
            }

            if (biomes.isEmpty()) {
                item(container, 31, Items.PAPER, "No discoveries yet", "Go explore the world!");
            } else if (biomes.size() > 18) {
                item(container, 49, Items.BOOK, "+" + (biomes.size() - 18) + " more biomes",
                        "More journal pages are coming soon");
            }
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

    private static void item(SimpleContainer container, int slot, net.minecraft.world.item.Item type, String name, String description) {
        ItemStack stack = new ItemStack(type);
        stack.setHoverName(Component.literal(name));
        stack.getOrCreateTag().putString("WanderlogDescription", description);
        container.setItem(slot, stack);
    }
}

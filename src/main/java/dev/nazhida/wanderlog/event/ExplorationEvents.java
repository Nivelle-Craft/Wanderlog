package dev.nazhida.wanderlog.event;

import dev.nazhida.wanderlog.integration.KivraIntegration;
import dev.nazhida.wanderlog.player.ExplorerData;
import dev.nazhida.wanderlog.player.ExplorerSavedData;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.math.BigDecimal;

public final class ExplorationEvents {
    private static final int CHECK_INTERVAL_TICKS = 20;
    private static final int BIOME_XP = 10;
    private static final BigDecimal KIVRA_BIOME_REWARD = new BigDecimal("5.00");

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END || event.player.level().isClientSide()) return;

        ServerPlayer player = (ServerPlayer) event.player;
        if (player.tickCount % CHECK_INTERVAL_TICKS != 0) return;

        ResourceLocation biomeId = player.level()
                .registryAccess()
                .registryOrThrow(Registries.BIOME)
                .getKey(player.level().getBiome(player.blockPosition()).value());
        if (biomeId == null) return;

        ExplorerSavedData savedData = ExplorerSavedData.get(player.server);
        ExplorerData explorer = savedData.get(player.getUUID());
        if (!explorer.discoverBiome(biomeId.toString())) return;

        int levelsGained = explorer.addXp(BIOME_XP);
        savedData.setDirty();

        player.sendSystemMessage(
                Component.literal("🧭 Nuevo bioma descubierto: ").withStyle(ChatFormatting.GOLD)
                        .append(Component.literal(biomeId.toString()).withStyle(ChatFormatting.YELLOW))
                        .append(Component.literal("  +" + BIOME_XP + " XP").withStyle(ChatFormatting.GREEN))
        );

        if (KivraIntegration.reward(player, KIVRA_BIOME_REWARD)) {
            player.sendSystemMessage(
                    Component.literal("💰 +" + KIVRA_BIOME_REWARD.stripTrailingZeros().toPlainString() + " Kivra coins por explorar")
                            .withStyle(ChatFormatting.GREEN)
            );
        }

        if (levelsGained > 0) {
            player.sendSystemMessage(
                    Component.literal("✨ Explorer Level " + explorer.getLevel() + "!")
                            .withStyle(ChatFormatting.AQUA, ChatFormatting.BOLD)
            );
        }
    }
}

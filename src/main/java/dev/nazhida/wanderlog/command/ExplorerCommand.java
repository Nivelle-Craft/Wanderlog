package dev.nazhida.wanderlog.command;

import com.mojang.brigadier.CommandDispatcher;
import dev.nazhida.wanderlog.player.ExplorerData;
import dev.nazhida.wanderlog.player.ExplorerSavedData;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public final class ExplorerCommand {
    private ExplorerCommand() {}

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("explorer")
                        .requires(source -> source.hasPermission(0))
                        .executes(context -> {
                            ServerPlayer player = context.getSource().getPlayerOrException();
                            ExplorerData data = ExplorerSavedData.get(player.server).get(player.getUUID());

                            context.getSource().sendSuccess(
                                    () -> Component.literal(
                                            "Wanderlog — Level " + data.getLevel()
                                                    + " | XP " + data.getXp() + "/" + data.xpRequiredForNextLevel()
                                                    + " | Biomas " + data.getDiscoveredBiomeCount()
                                    ),
                                    false
                            );
                            return 1;
                        })
        );
    }
}

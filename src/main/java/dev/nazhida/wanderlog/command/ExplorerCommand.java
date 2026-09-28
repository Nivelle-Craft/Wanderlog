package dev.nazhida.wanderlog.command;

import com.mojang.brigadier.CommandDispatcher;
import dev.nazhida.wanderlog.journal.WanderlogJournal;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;

public final class ExplorerCommand {
    private ExplorerCommand() {}

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("wanderlog")
                        .requires(source -> source.hasPermission(0))
                        .executes(context -> open(context.getSource().getPlayerOrException()))
        );

        // Backwards-compatible alias for the original command.
        dispatcher.register(
                Commands.literal("explorer")
                        .requires(source -> source.hasPermission(0))
                        .executes(context -> open(context.getSource().getPlayerOrException()))
        );
    }

    private static int open(ServerPlayer player) {
        WanderlogJournal.open(player);
        return 1;
    }
}

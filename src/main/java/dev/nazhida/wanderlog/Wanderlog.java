package dev.nazhida.wanderlog;

import dev.nazhida.wanderlog.command.ExplorerCommand;
import dev.nazhida.wanderlog.event.ExplorationEvents;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod(Wanderlog.MOD_ID)
public final class Wanderlog {
    public static final String MOD_ID = "wanderlog";

    public Wanderlog() {
        MinecraftForge.EVENT_BUS.register(new ExplorationEvents());
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onRegisterCommands(RegisterCommandsEvent event) {
        ExplorerCommand.register(event.getDispatcher());
    }
}

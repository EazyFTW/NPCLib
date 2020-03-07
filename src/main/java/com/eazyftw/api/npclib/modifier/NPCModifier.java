package com.eazyftw.api.npclib.modifier;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.eazyftw.api.npclib.NPC;
import com.google.common.collect.ImmutableList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * An NPCModifier queues packets for NPC modification which can then be send to players via the {@link NPCModifier#send(Player...)} method.
 */
public class NPCModifier {

    public static final int MINECRAFT_VERSION = ProtocolLibrary.getProtocolManager().getMinecraftVersion().getMinor();

    protected NPC npc;

    private List<PacketContainer> packetContainers = new ArrayList<>();

    public NPCModifier(@NotNull NPC npc) {
        this.npc = npc;
    }

    protected PacketContainer newContainer(@NotNull PacketType packetType) {
        return this.newContainer(packetType, true);
    }

    protected PacketContainer newContainer(@NotNull PacketType packetType, boolean withEntityId) {
        PacketContainer packetContainer = new PacketContainer(packetType);

        if (withEntityId) {
            packetContainer.getIntegers().write(0, this.npc.getEntityId());
        }
        this.packetContainers.add(packetContainer);

        return packetContainer;
    }

    protected PacketContainer lastContainer() {
        return this.packetContainers.get(this.packetContainers.size() - 1);
    }

    protected PacketContainer lastContainer(PacketContainer def) {
        if (this.packetContainers.isEmpty()) {
            return def;
        }
        return this.lastContainer();
    }

    /**
     * Sends the queued modifications to all players
     */
    public void send() {
        this.send((Player[]) Bukkit.getOnlinePlayers().toArray());
    }

    /**
     * Sends the queued modifications to certain players
     *
     * @param targetPlayers the players which should see the modification
     */
    public void send(@NotNull Player... targetPlayers) {
        for (Player targetPlayer : ImmutableList.copyOf(targetPlayers)) {
            try {
                for (PacketContainer packetContainer : this.packetContainers) {
                    ProtocolLibrary.getProtocolManager().sendServerPacket(targetPlayer, packetContainer);
                }
            } catch (InvocationTargetException exception) {
                exception.printStackTrace();
            }
        }

        this.packetContainers.clear();
    }
}

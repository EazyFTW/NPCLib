package com.eazyftw.api.npclib;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public interface SpawnCustomizer {

    /**
     * Being called when a NPC was spawned for a certain player.
     * Permanent NPC modifications should be done in this method, otherwise they will be lost at the next respawn of the NPC.
     * Modifications should only be sent to this one player.
     *
     * @param npc    the NPC that has been spawned
     * @param player the player the NPC has been spawned for
     */
    void handleSpawn(@NotNull NPC npc, @NotNull Player player);

}

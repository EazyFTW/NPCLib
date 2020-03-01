package com.eazyftw.api.npclib.modifier;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.eazyftw.api.npclib.NPC;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class EquipmentModifier extends NPCModifier {

    public EquipmentModifier(@NotNull NPC npc) {
        super(npc);
    }

    public EquipmentModifier queue(@NotNull EnumWrappers.ItemSlot itemSlot, @NotNull ItemStack equipment) {
        PacketContainer packetContainer = super.newContainer(PacketType.Play.Server.ENTITY_EQUIPMENT);

        packetContainer.getItemSlots().write(0, itemSlot);
        packetContainer.getItemModifier().write(0, equipment);

        return this;
    }
}

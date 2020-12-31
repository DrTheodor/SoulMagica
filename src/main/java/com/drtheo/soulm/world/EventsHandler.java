package com.drtheo.soulm.world;

import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class EventsHandler {

    @SubscribeEvent
    public void onLoot(LootTableLoadEvent e)
    {
        if (LootTables.CHESTS_IGLOO_CHEST.equals(e.getName()))
        {
            ResourceLocation loc = new ResourceLocation("soulm", "chests/sm_spawn_igloo");
            LootTable customLootTable = e.getLootTableManager().getLootTableFromLocation(loc);
            e.setTable(customLootTable);
        }
    }

}

package com.drtheo.soulm.setup;

import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class FMLCommonHandler {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event)
    {
        KeyBinds.register();
    }
}

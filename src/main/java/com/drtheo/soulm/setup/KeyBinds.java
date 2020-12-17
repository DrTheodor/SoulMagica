package com.drtheo.soulm.setup;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class KeyBinds {
    private static final String catergory = "The My KeyBinds for Mcmodding";
    public static final KeyBinding
            MY_KEY_FIRST = new KeyBinding("key.first", 70, catergory),
            MY_KEY_SECOND = new KeyBinding("key.second", 71, catergory);

    public static void register()
    {
        setRegister(MY_KEY_FIRST);
        setRegister(MY_KEY_SECOND);
    }

    private static void setRegister(KeyBinding binding)
    {
        ClientRegistry.registerKeyBinding(binding);
    }
}
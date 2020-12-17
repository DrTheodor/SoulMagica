package com.drtheo.soulm.setup;

import com.drtheo.soulm.SoulMagica;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class SoundInit {

    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS,
            SoulMagica.MOD_ID);

    //public static final RegistryObject<SoundEvent> AMBIENT = SOUNDS.register("entity.example_entity.ambient",
            //() -> new SoundEvent(new ResourceLocation(SoulMagica.MOD_ID, "entity.example_entity.ambient")));

    public static final Lazy<SoundEvent> LAZY_UNKNOWN_MUSIC_1 = Lazy
            .of(() -> new SoundEvent(new ResourceLocation(SoulMagica.MOD_ID, "disc.unknown_1")));

    public static final RegistryObject<SoundEvent> UNKNOWN_MUSIC_1 = SOUNDS.register("unknown_music_1",
            LAZY_UNKNOWN_MUSIC_1);
}

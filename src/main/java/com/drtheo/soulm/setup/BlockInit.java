package com.drtheo.soulm.setup;

import com.drtheo.soulm.SoulMagica;
import com.drtheo.soulm.setup.pedestals.itempedestal.ItemPedestalBlock;
import com.drtheo.soulm.setup.pedestals.pentagrams.PentagramAltarBlock;
import com.drtheo.soulm.setup.pedestals.soulcharger.SoulChargerBlock;
import net.minecraft.block.*;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;


public class BlockInit {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,
            SoulMagica.MOD_ID);

    public static final RegistryObject<Block> SOUL_CHARGER = BLOCKS.register("soul_charger",
            () -> new SoulChargerBlock(Block.Properties.from(Blocks.ANVIL)));
    public static final RegistryObject<Block> ITEM_PEDESTAL = BLOCKS.register("item_pedestal",
            () -> new ItemPedestalBlock(Block.Properties.from(Blocks.ANVIL)));
    public static final RegistryObject<Block> PENTAGRAM_ALTAR = BLOCKS.register("pentagram_altar",
            () -> new PentagramAltarBlock(Block.Properties.from(Blocks.ANVIL)));




    public BlockInit() {
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
package com.drtheo.soulm.setup;

import com.drtheo.soulm.SoulMagica;
import net.minecraft.block.Block;
import net.minecraft.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class Registration {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, SoulMagica.MOD_ID);
    public static boolean flyOn = false;

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, SoulMagica.MOD_ID);


    public static void register() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ITEMS.register(modEventBus);
        BLOCKS.register(modEventBus);

        ModItems.register();
        ModBlocks.register();
    }



    //public static final RegistryObject<Block> SOUL_CHARGER = BLOCKS.register("soul_charger", SoulChargerBlock::new);
    public static final RegistryObject<Item> SOUL_CHARGER_ITEM = ITEMS.register("soul_charger", () -> new BlockItem(BlockInit.SOUL_CHARGER.get(), new Item.Properties().group(SoulMagica.BASICS)));
    public static final RegistryObject<Item> ITEM_PEDESTAL_ITEM = ITEMS.register("item_pedestal", () -> new BlockItem(BlockInit.ITEM_PEDESTAL.get(), new Item.Properties().group(SoulMagica.BASICS)));
    //public static final RegistryObject<Item> DARK_PLANKS_ITEM = ITEMS.register("dark_planks", () -> new BlockItem(ModBlocks.DARK_PLANKS.get(), new Item.Properties().group(SoulMagica.TAB)));
    //public static final RegistryObject<Item> NECROMANTS_WAND = ITEMS.register("necromants_wand", NecromantsWand::new);
    //public static final RegistryObject<NecromantsWandItem> NECROMANTS_WAND = ITEMS.register("necromants_wand", NecromantsWandItem::new);
    public static final RegistryObject<Item> NECROMANTS_WAND = ITEMS.register("necromants_wand",
            () -> new NecromantsWandItem(new Item.Properties().group(SoulMagica.INSTRUMENTS).setNoRepair().rarity(Rarity.EPIC).maxStackSize(1).maxDamage(5)));
    public static final RegistryObject<Item> NECROMANTS_WAND_1 = ITEMS.register("necromants_wand_1",
            () -> new NecromantsWandItem(new Item.Properties().group(SoulMagica.INSTRUMENTS).setNoRepair().rarity(Rarity.EPIC).maxStackSize(1).maxDamage(5).isImmuneToFire()));
    public static final RegistryObject<Item> DEBUG_WAND = ITEMS.register("debug_wand",
            () -> new DebugWandItem(new Item.Properties().group(SoulMagica.BASICS).setNoRepair().rarity(Rarity.EPIC).maxStackSize(1).maxDamage(5)));
    public static final RegistryObject<Item> HEALER_WAND = ITEMS.register("healer_wand",
            () -> new HealerWandItem(new Item.Properties().group(SoulMagica.INSTRUMENTS).setNoRepair().rarity(Rarity.EPIC).maxStackSize(1).maxDamage(5).isImmuneToFire()));

    public static final RegistryObject<Item> PENTAGRAM_ALTAR_ITEM = ITEMS.register("pentagram_altar", () -> new BlockItem(BlockInit.PENTAGRAM_ALTAR.get(), new Item.Properties().group(SoulMagica.BASICS)));
}

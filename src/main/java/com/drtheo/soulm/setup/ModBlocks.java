package com.drtheo.soulm.setup;

import com.drtheo.soulm.SoulMagica;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,
            SoulMagica.MOD_ID);

    public static final RegistryObject<Block> SILVER_ORE = register("silver_ore", () ->
            new Block(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(3, 10).harvestLevel(2).sound(SoundType.STONE)));
    public static final RegistryObject<Block> SILVER_BLOCK = register("silver_block", () ->
            new Block(AbstractBlock.Properties.create(Material.IRON).hardnessAndResistance(3, 10).sound(SoundType.METAL)));
    public static final RegistryObject<Block> SILVER_ORE_NETHER = register("silver_ore_nether", () ->
            new Block(AbstractBlock.Properties.create(Material.IRON).hardnessAndResistance(3, 10).sound(SoundType.METAL)));
    public static final RegistryObject<Block> SILVER_ORE_END = register("silver_ore_end", () ->
            new Block(AbstractBlock.Properties.create(Material.IRON).hardnessAndResistance(3, 10).sound(SoundType.METAL)));
    //public static final RegistryObject<Block> SOUL_CHARGER = register("soul_charger", () ->
            //new Block(AbstractBlock.Properties.create(Material.IRON).hardnessAndResistance(3, 10).sound(SoundType.BASALT)));
    //public static final RegistryObject<Block> SOUL_CHARGER = BLOCKS.register("soul_charger",
            //() -> new SoulChargerBlock(Block.Properties.from(Blocks.ANVIL)));
    public static final RegistryObject<Block> DARK_PLANKS = register("dark_planks",
            () -> new Block(AbstractBlock.Properties.create(Material.NETHER_WOOD).hardnessAndResistance(2, 8).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> DARK_LOG = register("dark_log",
            () -> new Block(AbstractBlock.Properties.create(Material.NETHER_WOOD).hardnessAndResistance(3, 10).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> DARK_LOG_FULL = register("dark_log_full",
            () -> new Block(AbstractBlock.Properties.create(Material.NETHER_WOOD).hardnessAndResistance(3, 10).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> DARK_LEAVES = register("dark_leaves",
            () -> new LeavesBlock(AbstractBlock.Properties.from(Blocks.OAK_LEAVES)));

    public static final RegistryObject<Block> LIGHT_PLANKS = register("light_planks",
            () -> new Block(AbstractBlock.Properties.create(Material.NETHER_WOOD).hardnessAndResistance(2, 8).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> LIGHT_LOG = register("light_log",
            () -> new Block(AbstractBlock.Properties.create(Material.NETHER_WOOD).hardnessAndResistance(3, 10).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> LIGHT_LOG_FULL = register("light_log_full",
            () -> new Block(AbstractBlock.Properties.create(Material.NETHER_WOOD).hardnessAndResistance(3, 10).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> LIGHT_LEAVES = register("light_leaves",
            () -> new LeavesBlock(AbstractBlock.Properties.from(Blocks.OAK_LEAVES)));





    static void register() {}

    private static <T extends Block> RegistryObject<T> registerNoItem(String name, Supplier<T> block) {
        return Registration.BLOCKS.register(name, block);
    }

    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> block) {
        RegistryObject<T> ret = registerNoItem(name, block);
        Registration.ITEMS.register(name, () -> new BlockItem(ret.get(), new Item.Properties().group(SoulMagica.BASICS)));
        return ret;
    }
}
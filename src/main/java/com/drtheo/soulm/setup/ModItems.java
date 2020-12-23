package com.drtheo.soulm.setup;

import com.drtheo.soulm.SoulMagica;
import com.drtheo.soulm.armor.ModArmorMaterial;
import com.drtheo.soulm.tools.ModItemTools;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, SoulMagica.MOD_ID);

    //just items
    public static final RegistryObject<Item> SILVER_INGOT = Registration.ITEMS.register("silver_ingot", () ->
            new Item(new Item.Properties().group(SoulMagica.MISC)));
    public static final RegistryObject<Item> DARK_METAL = Registration.ITEMS.register("dark_metal", () ->
            new Item(new Item.Properties().group(SoulMagica.MISC).maxStackSize(16).rarity(Rarity.RARE)));
    public static final RegistryObject<Item> LIGHT_METAL = Registration.ITEMS.register("light_metal", () ->
            new Item(new Item.Properties().group(SoulMagica.MISC).maxStackSize(16).rarity(Rarity.RARE)));

    public static final RegistryObject<Item> DARK_LENSE = Registration.ITEMS.register("dark_lense", () ->
            new Item(new Item.Properties().group(SoulMagica.BASICS).maxStackSize(16).rarity(Rarity.RARE)));
    public static final RegistryObject<Item> MAGIC_CLOTH = Registration.ITEMS.register("magic_cloth", () ->
            new Item(new Item.Properties().group(SoulMagica.MISC).maxStackSize(16).rarity(Rarity.RARE)));
    public static final RegistryObject<Item> DARK_STICK = Registration.ITEMS.register("dark_stick", () ->
            new Item(new Item.Properties().group(SoulMagica.MISC).maxStackSize(16).rarity(Rarity.RARE)));
    public static final RegistryObject<Item> DARK_METALLED_STICK = Registration.ITEMS.register("dark_metalled_stick", () ->
            new Item(new Item.Properties().group(SoulMagica.MISC).maxStackSize(16).rarity(Rarity.RARE)));

    public static final RegistryObject<Item> LIGHT_STICK = Registration.ITEMS.register("light_stick", () ->
            new Item(new Item.Properties().group(SoulMagica.MISC).maxStackSize(16).rarity(Rarity.RARE)));
    public static final RegistryObject<Item> LIGHT_METALLED_STICK = Registration.ITEMS.register("light_metalled_stick", () ->
            new Item(new Item.Properties().group(SoulMagica.MISC).maxStackSize(16).rarity(Rarity.RARE)));
    public static final RegistryObject<Item> RAW_GOLDVER_INGOT = Registration.ITEMS.register("raw_goldver_ingot", () ->
            new Item(new Item.Properties().group(SoulMagica.MISC).maxStackSize(16).rarity(Rarity.RARE)));
    public static final RegistryObject<Item> GOLDVER_INGOT = Registration.ITEMS.register("goldver_ingot", () ->
            new Item(new Item.Properties().group(SoulMagica.MISC).maxStackSize(16).rarity(Rarity.RARE)));
    public static final RegistryObject<Item> GOLDVER_MATRIX = Registration.ITEMS.register("goldver_matrix", () ->
            new Item(new Item.Properties().group(SoulMagica.MISC).maxStackSize(16).rarity(Rarity.RARE)));
    public static final RegistryObject<Item> LIGHT_WAND_CRYSTAL = Registration.ITEMS.register("light_wand_crystal", () ->
            new Item(new Item.Properties().group(SoulMagica.MISC).maxStackSize(16).rarity(Rarity.RARE)));

    //tools

    public static final RegistryObject<SwordItem> SILVER_SWORD = Registration.ITEMS.register("silver_sword", () ->
            new SwordItem(ModItemTools.SILVER, 2, -2.4F, new Item.Properties().group(SoulMagica.INSTRUMENTS)));
    public static final RegistryObject<PickaxeItem> SILVER_PICKAXE = Registration.ITEMS.register("silver_pickaxe", () ->
            new PickaxeItem(ModItemTools.SILVER, 2, -2.4F, new Item.Properties().group(SoulMagica.INSTRUMENTS)));
    public static final RegistryObject<AxeItem> SILVER_AXE = Registration.ITEMS.register("silver_axe", () ->
            new AxeItem(ModItemTools.SILVER, 2, -2.4F, new Item.Properties().group(SoulMagica.INSTRUMENTS)));
    public static final RegistryObject<HoeItem> SILVER_HOE = Registration.ITEMS.register("silver_hoe", () ->
            new HoeItem(ModItemTools.SILVER, 2, -2.4F, new Item.Properties().group(SoulMagica.INSTRUMENTS)));
    public static final RegistryObject<ShovelItem> SILVER_SHOVEL = Registration.ITEMS.register("silver_shovel", () ->
            new ShovelItem(ModItemTools.SILVER, 2, -2.4F, new Item.Properties().group(SoulMagica.INSTRUMENTS)));


    //armor
    public static final RegistryObject<ArmorItem> NECROMANTS_HOOD = Registration.ITEMS.register("necromants_hood", () ->
            new ArmorItem(ModArmorMaterial.DARK_METAL, EquipmentSlotType.HEAD, new Item.Properties().group(SoulMagica.INSTRUMENTS)));
    public static final RegistryObject<ArmorItem> NECROMANTS_CHESTPLATE = Registration.ITEMS.register("necromants_chestplate", () ->
            new ArmorItem(ModArmorMaterial.DARK_METAL, EquipmentSlotType.CHEST, new Item.Properties().group(SoulMagica.INSTRUMENTS)));
    public static final RegistryObject<ArmorItem> NECROMANTS_LEGGINS = Registration.ITEMS.register("necromants_leggins", () ->
            new ArmorItem(ModArmorMaterial.DARK_METAL, EquipmentSlotType.LEGS, new Item.Properties().group(SoulMagica.INSTRUMENTS)));
    public static final RegistryObject<ArmorItem> NECROMANTS_BOOTS = Registration.ITEMS.register("necromants_boots", () ->
            new ArmorItem(ModArmorMaterial.DARK_METAL, EquipmentSlotType.FEET, new Item.Properties().group(SoulMagica.INSTRUMENTS)));



    //music discs
    public static final RegistryObject<Item> UNKNOWN_DISС_1 = Registration.ITEMS.register("unknown_disc_1", () ->
            new ModMusicDiscItem(0, SoundInit.LAZY_UNKNOWN_MUSIC_1.get(),
                    new Item.Properties().group(SoulMagica.BASICS).maxStackSize(1).rarity(Rarity.EPIC)));

    static void register() {}
}

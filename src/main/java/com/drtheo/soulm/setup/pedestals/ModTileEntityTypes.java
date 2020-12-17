package com.drtheo.soulm.setup.pedestals;

import com.drtheo.soulm.SoulMagica;

import com.drtheo.soulm.setup.BlockInit;
import com.drtheo.soulm.setup.pedestals.itempedestal.ItemPedestalTileEntity;
import com.drtheo.soulm.setup.pedestals.pentagrams.PentagramAltarTileEntity;
import com.drtheo.soulm.setup.pedestals.soulcharger.SoulChargerTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModTileEntityTypes {

    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister.create(
            ForgeRegistries.TILE_ENTITIES, SoulMagica.MOD_ID);


    public static final RegistryObject<TileEntityType<SoulChargerTileEntity>> SOUL_CHARGER = TILE_ENTITY_TYPES
            .register("soul_charger", () -> TileEntityType.Builder
                    .create(SoulChargerTileEntity::new, BlockInit.SOUL_CHARGER.get()).build(null));
    public static final RegistryObject<TileEntityType<ItemPedestalTileEntity>> ITEM_PEDESTAL = TILE_ENTITY_TYPES
            .register("item_pedestal", () -> TileEntityType.Builder
                    .create(ItemPedestalTileEntity::new, BlockInit.ITEM_PEDESTAL.get()).build(null));
    public static final RegistryObject<TileEntityType<PentagramAltarTileEntity>> PENTAGRAM_ALTAR = TILE_ENTITY_TYPES
            .register("pentagram_altar", () -> TileEntityType.Builder
                    .create(PentagramAltarTileEntity::new, BlockInit.PENTAGRAM_ALTAR.get()).build(null));
}
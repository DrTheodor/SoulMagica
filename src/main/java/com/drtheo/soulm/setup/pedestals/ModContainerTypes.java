package com.drtheo.soulm.setup.pedestals;

import com.drtheo.soulm.SoulMagica;

import com.drtheo.soulm.setup.pedestals.itempedestal.ItemPedestalContainer;
import com.drtheo.soulm.setup.pedestals.pentagrams.PentagramAltarContainer;
import com.drtheo.soulm.setup.pedestals.soulcharger.SoulChargerContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModContainerTypes {

    public static final DeferredRegister<ContainerType<?>> CONTAINER_TYPES = DeferredRegister.create(
            ForgeRegistries.CONTAINERS, SoulMagica.MOD_ID);


    public static final RegistryObject<ContainerType<SoulChargerContainer>> SOUL_CHARGER = CONTAINER_TYPES
            .register("soul_charger", () -> IForgeContainerType.create(SoulChargerContainer::new));
    public static final RegistryObject<ContainerType<ItemPedestalContainer>> ITEM_PEDESTAL = CONTAINER_TYPES
            .register("item_pedestal", () -> IForgeContainerType.create(ItemPedestalContainer::new));
    public static final RegistryObject<ContainerType<PentagramAltarContainer>> PENTAGRAM_ALTAR = CONTAINER_TYPES
            .register("pentagram_altar", () -> IForgeContainerType.create(PentagramAltarContainer::new));
}
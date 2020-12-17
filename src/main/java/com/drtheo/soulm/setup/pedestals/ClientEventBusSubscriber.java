package com.drtheo.soulm.setup.pedestals;

import com.drtheo.soulm.SoulMagica;
import com.drtheo.soulm.setup.Registration;
import com.drtheo.soulm.setup.gui.ItemPedestalScreen;
import com.drtheo.soulm.setup.gui.PentagramAltarScreen;
import com.drtheo.soulm.setup.gui.SoulChargerScreen;

import com.drtheo.soulm.setup.pedestals.itempedestal.ItemPedestalRenderer;
import com.drtheo.soulm.setup.pedestals.soulcharger.SoulChargerRenderer;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = SoulMagica.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventBusSubscriber {


    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        ScreenManager.registerFactory(ModContainerTypes.SOUL_CHARGER.get(),
                SoulChargerScreen::new);
        ScreenManager.registerFactory(ModContainerTypes.ITEM_PEDESTAL.get(),
                ItemPedestalScreen::new);
        ScreenManager.registerFactory(ModContainerTypes.PENTAGRAM_ALTAR.get(),
                PentagramAltarScreen::new);


        //RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.EXAMPLE_ENTITY.get(), ExampleEntityRender::new);

        ClientRegistry.bindTileEntityRenderer(ModTileEntityTypes.SOUL_CHARGER.get(), SoulChargerRenderer::new);
        ClientRegistry.bindTileEntityRenderer(ModTileEntityTypes.ITEM_PEDESTAL.get(), ItemPedestalRenderer::new);


    }

}
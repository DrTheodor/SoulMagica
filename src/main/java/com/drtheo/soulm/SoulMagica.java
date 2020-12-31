package com.drtheo.soulm;

import com.drtheo.soulm.setup.*;
import com.drtheo.soulm.setup.pedestals.ModContainerTypes;
import com.drtheo.soulm.setup.pedestals.ModTileEntityTypes;
import com.drtheo.soulm.structures.NetherStructures;
import com.drtheo.soulm.structures.STStructures;
import com.drtheo.soulm.world.EventsHandler;
import com.drtheo.soulm.world.gen.OreGen;
import com.drtheo.soulm.structures.STConfiguredStructures;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.Dimension;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.FlatChunkGenerator;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(SoulMagica.MOD_ID)
public class SoulMagica
{
    public static final String MOD_ID = "soulm";
    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();

    public SoulMagica() {

        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the enqueueIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        Registration.register();
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();


        modEventBus.addListener(this::setup);
        BlockInit.BLOCKS.register(modEventBus);
        SoundInit.SOUNDS.register(modEventBus);
        ModTileEntityTypes.TILE_ENTITY_TYPES.register(modEventBus);
        ModContainerTypes.CONTAINER_TYPES.register(modEventBus);


        IEventBus forgeBus = MinecraftForge.EVENT_BUS;

        forgeBus.addListener(EventPriority.NORMAL, this::addDimensionalSpacing);
        modEventBus.addGenericListener(Structure.class, this::onRegisterStructures);
        // The comments for BiomeLoadingEvent and StructureSpawnListGatherEvent says to do HIGH for additions.
        forgeBus.addListener(EventPriority.HIGH, this::biomeModification);


        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    /**
     * Will go into the world's chunkgenerator and manually add our structure spacing.
     * If the spacing is not added, the structure doesn't spawn.
     *
     * Use this for dimension blacklists for your structure.
     * (Don't forget to attempt to remove your structure too from
     *  the map if you are blacklisting that dimension! It might have
     *  your structure in it already.)
     *
     * Basically use this to make absolutely sure the chunkgenerator
     * can or cannot spawn your structure.
     */




    private void setup(final FMLCommonSetupEvent event)
    {
        RenderTypeLookup.setRenderLayer(ModBlocks.CRIMSON_LADDER.get(), RenderType.getCutout());
        OreGen.registerOres();
        // some preinit code
        //LOGGER.info("HELLO FROM PREINIT");
        //LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event)
    {
        KeyBinds.register();
    }


    private void doClientStuff(final FMLClientSetupEvent event) {
        // do something that can only be done on the client

        LOGGER.info("Got game settings {}", event.getMinecraftSupplier().get().gameSettings);
    }

    public static final ItemGroup BASICS = new ItemGroup("sm_basics") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModBlocks.SILVER_BLOCK.get());
        }
    };

    public static final ItemGroup INSTRUMENTS = new ItemGroup("sm_instruments") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(Registration.NECROMANTS_WAND_1.get());
        }
    };

    public static final ItemGroup MISC = new ItemGroup("sm_misc") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModItems.MAGIC_CLOTH.get());
        }
    };

    private void enqueueIMC(final InterModEnqueueEvent event)
    {
        // some example code to dispatch IMC to another mod
        //InterModComms.sendTo("soulm", "helloworld", () -> { LOGGER.info("Hello world from the MDK"); return "Hello world";});
    }

    private void processIMC(final InterModProcessEvent event)
    {
        // some example code to receive and process InterModComms from other mods
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m->m.getMessageSupplier().get()).
                collect(Collectors.toList()));
    }
    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        // do something when the server starts
        //LOGGER.info("HELLO from server starting");
    }


    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
            // register a new block here
            //LOGGER.info("HELLO from Register Block");
        }
    }



    public void onRegisterStructures(final RegistryEvent.Register<Structure<?>> event) {
        // Registers the structures.
        // If you don't do this, bad things might happen... very bad things... Spooky...
        STStructures.registerStructures(event);
        NetherStructures.registerStructures(event);
        STConfiguredStructures.registerConfiguredStructures();

        LOGGER.log(Level.INFO, "structures registered.");
    }

    public void biomeModification(final BiomeLoadingEvent event) {
        // Add our structure to all biomes including other modded biomes
        //
        // You can filter to certain biomes based on stuff like temperature, scale, precipitation, mod id

        event.getGeneration().getStructures().add(() -> STConfiguredStructures.CONFIGURED_DARK_TREE);
        event.getGeneration().getStructures().add(() -> STConfiguredStructures.CONFIGURED_LIGHT_TREE);
        event.getGeneration().getStructures().add(() -> STConfiguredStructures.CONFIGURED_NETHER_VILLAGE);
    }


    public void addDimensionalSpacing(final WorldEvent.Load event) {
        if(event.getWorld() instanceof ServerWorld){
            ServerWorld serverWorld = (ServerWorld)event.getWorld();
            Map<Structure<?>, StructureSeparationSettings> tempMap = new HashMap<>(serverWorld.getChunkProvider().generator.func_235957_b_().func_236195_a_());
            // Prevent spawning our structure in Vanilla's superflat world as
            // people seem to want their superflat worlds free of modded structures.
            // Also that vanilla superflat is really tricky and buggy to work with in my experience.
            if(serverWorld.getChunkProvider().getChunkGenerator() instanceof FlatChunkGenerator &&
                    serverWorld.getDimensionKey().equals(World.OVERWORLD)){
                return;
            }
            //tempMap.put(NetherStructures.NETHER_VILLAGE, DimensionStructuresSettings.field_236191_b_.get(NetherStructures.NETHER_VILLAGE));

            Map<Structure<?>, StructureSeparationSettings> tempMapN = new HashMap<>(serverWorld.getChunkProvider().generator.func_235957_b_().func_236195_a_());
            tempMap.put(STStructures.DARK_TREE, DimensionStructuresSettings.field_236191_b_.get(STStructures.DARK_TREE));
            tempMap.put(STStructures.LIGHT_TREE, DimensionStructuresSettings.field_236191_b_.get(STStructures.LIGHT_TREE));
            for(Biome biome : ForgeRegistries.BIOMES) {
                if(biome.getRegistryName() == Biomes.CRIMSON_FOREST.getRegistryName()) {
                    tempMap.put(NetherStructures.NETHER_VILLAGE, DimensionStructuresSettings.field_236191_b_.get(NetherStructures.NETHER_VILLAGE));
                }
            }
            serverWorld.getChunkProvider().generator.func_235957_b_().field_236193_d_ = tempMap;
        }
    }


    public static <T extends IForgeRegistryEntry<T>> T register(IForgeRegistry<T> registry, T entry, String registryKey) {
        entry.setRegistryName(new ResourceLocation(SoulMagica.MOD_ID, registryKey));
        registry.register(entry);
        return entry;
    }
}

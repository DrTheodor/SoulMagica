package com.drtheo.soulm.structures;

import com.drtheo.soulm.SoulMagica;
import com.drtheo.soulm.structures.STStructures;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeRegistry;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.FlatGenerationSettings;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistry;

public class STConfiguredStructures {
    // Static instance of our structure so we can reference it and add it to biomes easily.
    public static StructureFeature<?, ?> CONFIGURED_DARK_TREE = STStructures.DARK_TREE.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG);
    public static StructureFeature<?, ?> CONFIGURED_LIGHT_TREE = STStructures.LIGHT_TREE.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG);
    public static StructureFeature<?, ?> CONFIGURED_NETHER_VILLAGE = NetherStructures.NETHER_VILLAGE.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG);


    /*
     * Registers the configured structure which is what gets added to the biomes.
     * Noticed we are not using a forge registry because there is none for configured structures
     */
    public static void registerConfiguredStructures() {
        Registry<StructureFeature<?, ?>> registry = WorldGenRegistries.CONFIGURED_STRUCTURE_FEATURE;
        Registry.register(registry, new ResourceLocation(SoulMagica.MOD_ID, "configured_dark_tree"), CONFIGURED_DARK_TREE);
        Registry.register(registry, new ResourceLocation(SoulMagica.MOD_ID, "configured_dark_tree"), CONFIGURED_LIGHT_TREE);
        Registry.register(registry, new ResourceLocation(SoulMagica.MOD_ID, "configured_nether_village"), CONFIGURED_NETHER_VILLAGE);
        // Ok so, this part may be hard to grasp but basically, just add your structure to this to
        // prevent any sort of crash or issue with other mod's custom ChunkGenerators. If they use
        // FlatGenerationSettings.STRUCTURES in it and you don't add your structure to it, the game
        // could crash later when you attempt to add the StructureSeparationSettings to the dimension.

        // (It would also crash with superflat worldtype if you omit the below line
        //  and attempt to add the structure's StructureSeparationSettings to the world)
        //
        // Note: If you want your structure to spawn in superflat, remove the FlatChunkGenerator check
        // in StructureTutorialMain.addDimensionalSpacing and then create a superflat world, exit it,
        // and re-enter it and your structures will be spawning. I could not figure out why it needs
        // the restart but honestly, superflat is really buggy and shouldn't be you main focus in my opinion.
        FlatGenerationSettings.STRUCTURES.put(STStructures.DARK_TREE, CONFIGURED_DARK_TREE);
        FlatGenerationSettings.STRUCTURES.put(STStructures.LIGHT_TREE, CONFIGURED_LIGHT_TREE);
        FlatGenerationSettings.STRUCTURES.put(NetherStructures.NETHER_VILLAGE, CONFIGURED_NETHER_VILLAGE);
    }
}
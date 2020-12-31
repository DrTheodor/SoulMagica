package com.drtheo.soulm.structures;

import com.drtheo.soulm.SoulMagica;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraftforge.event.RegistryEvent.Register;

public class STStructures {
    // Static instance of our structure so we can reference it
    // and use it to make configured structures in STConfiguredStructures
    public static Structure<NoFeatureConfig> DARK_TREE = new DarkTreeStructure(NoFeatureConfig.field_236558_a_);
    public static IStructurePieceType DTP = DarkTreePieces.Piece::new;

    public static Structure<NoFeatureConfig> LIGHT_TREE = new LightTreeStructure(NoFeatureConfig.field_236558_a_);
    public static IStructurePieceType LTP = LightTreePieces.Piece::new;

    //public static Structure<NoFeatureConfig> NETHER_VILLAGE = new NetherVillageStructure(NoFeatureConfig.field_236558_a_);
    //public static IStructurePieceType NVP = NetherVillagePieces.Piece::new;

    /*
     * Registers the structure itself and sets what its path is. In this case, the
     * structure will have the resourcelocation of structure_tutorial:run_down_house.
     *
     * This is also where the rarity of your structure is set. See the
     * comments in below in new StructureSeparationSettings for details.
     *
     * It is always a good idea to register your Structures so that other mods can
     * use them too directly from the Forge Registry. It great for mod compatibility.
     */
    public static void registerStructures(Register<Structure<?>> event) {

        // Registers the structure (our helper method attaches the modid to the front of the Structure's ResourceLocation)
        SoulMagica.register(event.getRegistry(), DARK_TREE, "dark_tree");
        SoulMagica.register(event.getRegistry(), LIGHT_TREE, "light_tree");
        //SoulMagica.register(event.getRegistry(), NETHER_VILLAGE, "nether_village");
        System.out.println("Starting registering...");

        /*
         * IMPORTANT: Once you have set the name for your structure below and distributed your mod,
         * it should NEVER be changed or else it can cause worlds to become corrupted if they generated
         * any chunks with your mod with the old structure name. See MC-194811 in Mojang's bug tracker for details.
         *
         * Forge has an issue report here: https://github.com/MinecraftForge/MinecraftForge/issues/7363
         * Keep watch on that to know when it is safe to remove or change structure's registry names
         */
        registerStructure(
                DARK_TREE, /* The instance of the structure */
                new StructureSeparationSettings(15 /* maximum distance apart in chunks between spawn attempts */,
                        10 /* minimum distance apart in chunks between spawn attempts */,
                        51008 /* this modifies the seed of the structure so no two structures always spawn over each-other. Make this large and unique. */),
                true);
        registerStructure(
                LIGHT_TREE, /* The instance of the structure */
                new StructureSeparationSettings(15 /* maximum distance apart in chunks between spawn attempts */,
                        10 /* minimum distance apart in chunks between spawn attempts */,
                        210408 /* this modifies the seed of the structure so no two structures always spawn over each-other. Make this large and unique. */),
                true);



        STStructures.registerAllPieces();
        System.out.println("Registered all pieces");
    }

    /*
     * Adds the provided structure to the registry, and adds the separation settings.
     * The rarity of the structure is determined based on the values passed into
     * this method in the structureSeparationSettings argument. Called by registerFeatures.
     */
    public static <F extends Structure<?>> void registerStructure(
            F structure,
            StructureSeparationSettings structureSeparationSettings,
            boolean transformSurroundingLand)
    {
        /*
         * We need to add our structures into the map in Structure alongside vanilla
         * structures or else it will cause errors. Called by registerStructure.
         *
         * (If you are using deferred registries, do not put this line inside the deferred method.
         *  Instead, call it anywhere else before you start the configuredstructure registering)
         */
        Structure.NAME_STRUCTURE_BIMAP.put(structure.getRegistryName().toString(), structure);

        /*
         * Will add land at the base of the structure like it does for Villages and Outposts.
         * Doesn't work well on structure that have pieces stacked vertically.
         */
        if(transformSurroundingLand){
            Structure.field_236384_t_ =
                    ImmutableList.<Structure<?>>builder()
                            .addAll(Structure.field_236384_t_)
                            .add(structure)
                            .build();
        }

        /*
         * Adds the structure's spacing into several places so that the structure's spacing remains
         * correct in any dimension or worldtype instead of not spawning.
         *
         * However, it seems it doesn't always work for code made dimensions as they read from
         * this list beforehand. Use the WorldEvent.Load event in StructureTutorialMain to add
         * the structure spacing from this list into that dimension.
         */
        DimensionStructuresSettings.field_236191_b_ =
                ImmutableMap.<Structure<?>, StructureSeparationSettings>builder()
                        .putAll(DimensionStructuresSettings.field_236191_b_)
                        .put(structure, structureSeparationSettings)
                        .build();
    }

    /*
     * If you have multiple structures it is helpful to break out the registering of the pieces.
     * If you change the name you register the pieces with and load a world from before the name
     * was changed it will spam errors to the console, so pick a name you like before distributing
     * your mod and don't change it. Called by registerFeatures.
     */
    public static void registerAllPieces() {
        registerStructurePiece(DTP, new ResourceLocation(SoulMagica.MOD_ID, "dtp"));
        registerStructurePiece(LTP, new ResourceLocation(SoulMagica.MOD_ID, "ltp"));
        //registerStructurePiece(NVP, new ResourceLocation(SoulMagica.MOD_ID, "nvp"));
    }

    /*
     * Registers the structures pieces themselves. If you don't do this part, Forge will complain to
     * you in the Console. Called by registerPieces.
     */
    static void registerStructurePiece(IStructurePieceType structurePiece, ResourceLocation rl) {
        Registry.register(Registry.STRUCTURE_PIECE, rl, structurePiece);
    }
}
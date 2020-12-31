package com.drtheo.soulm.setup;

import com.drtheo.soulm.setup.pedestals.itempedestal.ItemPedestalTileEntity;
import com.drtheo.soulm.setup.pedestals.soulcharger.SoulChargerTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.client.util.InputMappings;
import net.minecraft.command.arguments.NBTTagArgument;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ParticleType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWWindowFocusCallback;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

public class HealerWandItem extends Item {
    public static final Logger LOGGER = LogManager.getLogger();

    public HealerWandItem(Properties properties) {
        super(properties);
    }

    private static DamageSource ds = new DamageSource("Vampirism");

    public void enableFly(PlayerEntity player) {
        player.abilities.allowFlying=true;
        player.abilities.isFlying = true;
        Registration.flyOn = true;
    }



    public void disableFly(PlayerEntity player) {
        player.abilities.allowFlying = false;
        player.abilities.isFlying = false;
        player.abilities.disableDamage = false;
        Registration.flyOn = false;
    }


    @SubscribeEvent
    public void cancelPlayerFallDamage(LivingFallEvent event)
    {
        if (event.getEntity() instanceof PlayerEntity)
        {
            PlayerEntity player = (PlayerEntity) event.getEntity();

            if (player.getHeldItem(Hand.MAIN_HAND).getItem() == Registration.NECROMANTS_WAND.get() || player.getHeldItem(Hand.OFF_HAND).getItem() == Registration.NECROMANTS_WAND_1.get())
            {
                event.getEntity().fallDistance = 0;
            }
        }
    }

    private RayTraceResult rayTrace(World world, PlayerEntity player)
    {
        float rotationPitch = player.rotationPitch;
        float rotationYaw = player.rotationYaw;
        Vector3d eyePosition = player.getEyePosition(1.0f);
        //Normalize the look vector
        float f2 = MathHelper.cos(-rotationYaw * ((float)Math.PI / 180f) - (float)Math.PI);
        float f3 = MathHelper.sin(-rotationYaw * ((float)Math.PI / 180f) - (float)Math.PI);
        float f4 = MathHelper.cos(-rotationPitch * ((float)Math.PI / 180f));
        float f5 = MathHelper.sin(-rotationPitch * ((float)Math.PI / 180f));
        float f6 = f3 * f4;
        float f7 = f2 * f4;
        double rayTraceDistance = 32;
        //Take the start position and add the look vector multiplied by the rayTraceDistance
        Vector3d endPosition = eyePosition.add((double)f6 * rayTraceDistance, (double)f5 * rayTraceDistance, (double)f7 * rayTraceDistance);
        return world.rayTraceBlocks(new RayTraceContext(eyePosition, endPosition, RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.NONE, player));
    }

    @Override
    public ActionResultType itemInteractionForEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand) {
        if (!playerIn.world.isRemote) {
            if(target.isEntityUndead()) {
                target.attackEntityFrom(ds.setDamageBypassesArmor().setDamageIsAbsolute().setMagicDamage(), 6);
                return ActionResultType.SUCCESS;
            } else {
                target.heal(6);
                playerIn.world.addParticle(ParticleTypes.HAPPY_VILLAGER, target.getPosX(), target.getPosY() + 2, target.getPosZ(), 1, 1, 1);
                return ActionResultType.SUCCESS;
            }
        }
        return super.itemInteractionForEntity(stack, playerIn, target, hand);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if(playerIn.isSneaking()) {
            playerIn.heal(6);
            playerIn.world.addParticle(ParticleTypes.HAPPY_VILLAGER, playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ(), 10, 10, 10);
            return ActionResult.resultSuccess(playerIn.getHeldItem(handIn));
        }

        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Nonnull
    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        PlayerEntity player = context.getPlayer(); // получение игрока
        BlockPos pos = context.getPos(); // получить позицию блока
        Hand handIn = context.getHand();
        if(player.getHeldItem(handIn).getItem() == Registration.HEALER_WAND.get() && !Registration.flyOn) {
            enableFly(player);
            return ActionResultType.SUCCESS;
        }


        //context.getFace(); // получить сторону блока
        //context.getHitVec(); // получение вектора взаимодействия
        World world = context.getWorld(); // получение мира
        BlockPos normalPos = new BlockPos(pos.getX(), pos.getY(), pos.getZ());


        if (world.getDimensionKey() == World.THE_NETHER) {
            //System.out.println(world.getBiome(normalPos).equals(ForgeRegistries.BIOMES.getValue(Biomes.WARPED_FOREST.getRegistryName())));
            //System.out.println(world.getBiome(normalPos).equals(Biomes.WARPED_FOREST.getRegistryName()));
            System.out.println(world.getBiome(normalPos));
            //System.out.println(ForgeRegistries.BIOMES.getValue(Biomes.WARPED_FOREST.getRegistryName()));
            if (world.getBiome(normalPos).getRegistryName().equals(new ResourceLocation("warped_forest"))) {
                //world.getBiome(normalPos).equals(ForgeRegistries.BIOMES.getValue(Biomes.WARPED_FOREST.getRegistryName()))
                System.out.println("You in the warped forest");
                System.out.println(player.getEntityWorld().getBiome(normalPos));
                if (world.getBlockState(pos).getBlock() == BlockInit.SOUL_CHARGER.get()) {
                    int pointOneX = (pos.getX() + 2);
                    int pointOneY = (pos.getY());
                    int pointOneZ = (pos.getZ());
                    BlockPos pointOne = new BlockPos(pointOneX, pointOneY, pointOneZ);

                    int pointTwoX = (pos.getX() - 2);
                    int pointTwoY = (pos.getY());
                    int pointTwoZ = (pos.getZ());
                    BlockPos pointTwo = new BlockPos(pointTwoX, pointTwoY, pointTwoZ);

                    int pointThreeX = (pos.getX());
                    int pointThreeY = (pos.getY());
                    int pointThreeZ = (pos.getZ() + 2);
                    BlockPos pointThree = new BlockPos(pointThreeX, pointThreeY, pointThreeZ);

                    int pointFourX = (pos.getX());
                    int pointFourY = (pos.getY());
                    int pointFourZ = (pos.getZ() - 2);
                    BlockPos pointFour = new BlockPos(pointFourX, pointFourY, pointFourZ);

                    TileEntity te1 = world.getTileEntity(pointOne);
                    TileEntity te2 = world.getTileEntity(pointTwo);
                    TileEntity te3 = world.getTileEntity(pointThree);
                    TileEntity te4 = world.getTileEntity(pointFour);
                    TileEntity MainAltar = world.getTileEntity(normalPos);
                    if (te1 instanceof ItemPedestalTileEntity) {

                        //System.out.println(((ItemPedestalTileEntity) te1).getItems());
                        ((ItemPedestalTileEntity) te1).getItems().clear();

                    }
                    if (te2 instanceof ItemPedestalTileEntity) {

                        //System.out.println(((ItemPedestalTileEntity) te2).getItems());
                        ((ItemPedestalTileEntity) te2).getItems().clear();

                    }
                    if (te3 instanceof ItemPedestalTileEntity) {

                        //System.out.println(((ItemPedestalTileEntity) te3).getItems());
                        ((ItemPedestalTileEntity) te3).getItems().clear();

                    }
                    if (te4 instanceof ItemPedestalTileEntity) {

                        //System.out.println(((ItemPedestalTileEntity) te4).getItems());
                        ((ItemPedestalTileEntity) te4).getItems().clear();
                        ((SoulChargerTileEntity) MainAltar).getItems().clear();

                    }
                    // else {
                    //System.out.println("i must again fix this shit");
                    //System.out.println(pointOne);
                    //System.out.println(pointTwo);
                    //System.out.println(pointThree);
                    //System.out.println(pointFour);
                    //System.out.println("Normal: " + pointOne);
                    //System.out.println("Normal: " + pointTwo);
                    //System.out.println("Normal: " + pointThree);
                    //System.out.println("Normal: " + pointFour);
                    //System.out.println("Original X: " + pos.getX());
                    //System.out.println("Original Y: " + pos.getY());
                    //System.out.println("Original Z: " + pos.getZ());
                    //((ItemPedestalTileEntity) te1).getItems().clear();
                    //((ItemPedestalTileEntity) te2).getItems().clear();
                    //((ItemPedestalTileEntity) te3).getItems().clear();
                    //((ItemPedestalTileEntity) te4).getItems().clear();

                    //}
                    return ActionResultType.SUCCESS;
                } else if (world.getBlockState(pos).getBlock() == BlockInit.PENTAGRAM_ALTAR.get()) {
                    return ActionResultType.SUCCESS;
                }
            }
        }
        return ActionResultType.FAIL;
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
    {
        tooltip.add(new StringTextComponent("Magic wand that can heal anyone and hurt undead..."));
    }
}

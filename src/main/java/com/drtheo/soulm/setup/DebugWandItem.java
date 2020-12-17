package com.drtheo.soulm.setup;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.ITextProperties;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class DebugWandItem extends Item {

    public DebugWandItem(Properties properties) {
        super(properties);
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



    private int tickCounter = 0;


    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent evt)
    {
        RayTraceResult result = this.rayTrace(evt.player.world, evt.player);
        if (!evt.player.world.isRemote) {
            if (result instanceof EntityRayTraceResult) {
                if (tickCounter % 20 == 0 && tickCounter < 201) {
                    evt.player.attackEntityFrom(DamageSource.causeIndirectMagicDamage(evt.player, ((EntityRayTraceResult) result).getEntity()), 2);
                }
            }
        }

        tickCounter++;
    }


    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
    {
        RayTraceResult result = this.rayTrace(worldIn, playerIn);
        if (!worldIn.isRemote) {
            if (result instanceof EntityRayTraceResult) {
                if (tickCounter % 20 == 0 && tickCounter < 201) {
                    playerIn.attackEntityFrom(DamageSource.causeIndirectMagicDamage(playerIn, ((EntityRayTraceResult) result).getEntity()), 2);
                    return ActionResult.resultSuccess(new ItemStack(Registration.DEBUG_WAND.get()));
                }
            }
        }
        tickCounter++;
        return ActionResult.resultFail(new ItemStack(Registration.DEBUG_WAND.get()));
    }


    //@SuppressWarnings({ "unchecked", "rawtypes" })
    //@Override
    //public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
      //  if (stack.hasTag() && stack.getTag().hasUniqueId("Uses"))
        //{
          //  tooltip.add(new StringTextComponent(Integer.toString(stack.getTag().getInt("Uses"))));
        //}
    //}
}

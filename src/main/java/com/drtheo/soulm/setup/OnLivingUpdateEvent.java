package com.drtheo.soulm.setup;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;


public class OnLivingUpdateEvent {
    @SubscribeEvent
    public void RegisterLivingEvent(LivingEvent.LivingUpdateEvent event) {

        if (event.getEntity() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getEntity();
            ItemStack heldItem = player.getHeldItem(Hand.MAIN_HAND);
            if (heldItem != null && heldItem.getItem() == Registration.NECROMANTS_WAND.get()) {
                player.abilities.allowFlying = true;
            } else {
                if (player.abilities.isFlying == true) {
                    player.abilities.isFlying = player.abilities.isCreativeMode ? true : false;
                    player.abilities.allowFlying = player.abilities.isCreativeMode ? true : false;
                }
            }
        }
    }
}
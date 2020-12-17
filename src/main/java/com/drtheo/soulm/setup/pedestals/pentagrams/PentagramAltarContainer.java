package com.drtheo.soulm.setup.pedestals.pentagrams;


import java.util.Objects;


import com.drtheo.soulm.setup.BlockInit;
import com.drtheo.soulm.setup.pedestals.ModContainerTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;

public class PentagramAltarContainer extends Container {

    public final PentagramAltarTileEntity tileEntity;
    private final IWorldPosCallable canInteractWithCallable;

    public PentagramAltarContainer(final int windowId, final PlayerInventory playerInventory,
                                   final PentagramAltarTileEntity tileEntity) {
        super(ModContainerTypes.PENTAGRAM_ALTAR.get(), windowId);
        this.tileEntity = tileEntity;
        this.canInteractWithCallable = IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos());

        // Main Inventory
        int startX = 8;
        int startY = 18;
        int slotSizePlus2 = 18;
        //выход
        this.addSlot(new Slot(tileEntity, 1, startX + 50 + 66, startY + 27));
        //papers
        this.addSlot(new Slot(tileEntity, 2, startX + 21, startY - 5));
        //squid sac
        this.addSlot(new Slot(tileEntity, 3, startX + 67, startY - 5));
        //feathers
        this.addSlot(new Slot(tileEntity, 4, startX + 50 + 67, startY - 4));
        //crafting slots
        this.addSlot(new Slot(tileEntity, 5, startX + 19, startY + 12 + 7));
        this.addSlot(new Slot(tileEntity, 6, startX + 19 + 18, startY + 12 + 7));
        this.addSlot(new Slot(tileEntity, 7, startX + 19 + 18 + 18, startY + 12 + 7));
        this.addSlot(new Slot(tileEntity, 8, startX + 19 + 18 + 18, startY + 12 + 7 + 18));
        this.addSlot(new Slot(tileEntity, 9, startX + 19 + 18, startY + 12 + 7 + 18));
        this.addSlot(new Slot(tileEntity, 10, startX + 19, startY + 12 + 7 + 18));


        // Main Player Inventory
        int startPlayerInvY = startY * 5 - 6;
        for (int row = 0; row < 3; ++row) {
            for (int column = 0; column < 9; ++column) {
                this.addSlot(new Slot(playerInventory, 9 + (row * 9) + column, startX + (column * slotSizePlus2),
                        startPlayerInvY + (row * slotSizePlus2)));
            }
        }

        // Hotbar
        int hotbarY = startPlayerInvY + (startPlayerInvY / 2) + 16;
        for (int column = 0; column < 9; ++column) {
            this.addSlot(new Slot(playerInventory, column, startX + (column * slotSizePlus2), hotbarY));
        }
    }

    private static PentagramAltarTileEntity getTileEntity(final PlayerInventory playerInventory,
                                                          final PacketBuffer data) {
        Objects.requireNonNull(playerInventory, "playerInventory cannot be null");
        Objects.requireNonNull(data, "data cannot be null");
        final TileEntity tileAtPos = playerInventory.player.world.getTileEntity(data.readBlockPos());
        if (tileAtPos instanceof PentagramAltarTileEntity) {
            return (PentagramAltarTileEntity) tileAtPos;
        }
        throw new IllegalStateException("Tile entity is not correct! " + tileAtPos);
    }

    public PentagramAltarContainer(final int windowId, final PlayerInventory playerInventory, final PacketBuffer data) {
        this(windowId, playerInventory, getTileEntity(playerInventory, data));
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(canInteractWithCallable, playerIn, BlockInit.PENTAGRAM_ALTAR.get());
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if (index < 36) {
                if (!this.mergeItemStack(itemstack1, 36, this.inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(itemstack1, 0, 36, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
        }
        return itemstack;
    }
}
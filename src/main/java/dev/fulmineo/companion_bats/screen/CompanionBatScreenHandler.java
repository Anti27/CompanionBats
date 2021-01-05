package dev.fulmineo.companion_bats.screen;

import dev.fulmineo.companion_bats.CompanionBats;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.Hand;

public class CompanionBatScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    public Hand hand;

    public CompanionBatScreenHandler(int syncId, PlayerInventory playerInventory, PacketByteBuf buf){
        this(syncId, playerInventory, new SimpleInventory(2));
        this.hand = buf.readEnumConstant(Hand.class);
    }

    public CompanionBatScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(CompanionBats.BAT_SCREEN_HANDLER, syncId);
        this.inventory = inventory;
        inventory.onOpen(playerInventory.player);
        this.addSlot(new Slot(inventory, 0, 8, 18) {
            public boolean canInsert(ItemStack stack) {
                return stack.isOf(Items.BUNDLE) && !this.hasStack();
            }

            @Environment(EnvType.CLIENT)
            public boolean doDrawHoveringEffect() {
                return true;
            }
        });
        this.addSlot(new Slot(inventory, 1, 8, 36) {
            // TODO: Check for equipment items
            public boolean canInsert(ItemStack stack) {
                return true; //entity.isHorseArmor(stack);
            }

            @Environment(EnvType.CLIENT)
            public boolean doDrawHoveringEffect() {
                return true; // entity.hasArmorSlot();
            }

            public int getMaxItemCount() {
                return 1;
            }
        });

        int o;
        int n;
        
        for(o = 0; o < 3; ++o) {
            for(n = 0; n < 9; ++n) {
            this.addSlot(new Slot(playerInventory, n + o * 9 + 9, 8 + n * 18, 102 + o * 18 + -18));
            }
        }

        for(o = 0; o < 9; ++o) {
            this.addSlot(new Slot(playerInventory, o, 8 + o * 18, 142));
        }
    }

    public boolean canUse(PlayerEntity player) {
        return true;
    }

    public ItemStack transferSlot(PlayerEntity player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = (Slot)this.slots.get(index);
        if (slot != null && slot.hasStack()) {
            ItemStack itemStack2 = slot.getStack();
            itemStack = itemStack2.copy();
            if (this.getSlot(0).canInsert(itemStack2) && !this.getSlot(0).hasStack()) {
                if (!this.insertItem(itemStack2, 0, 1, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (this.getSlot(1).canInsert(itemStack2) && !this.getSlot(1).hasStack()) {
                if (!this.insertItem(itemStack2, 1, 2, false)) {
                    return ItemStack.EMPTY;
                }
            } 

            if (itemStack2.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return itemStack;
    }

    public void close(PlayerEntity player) {
        super.close(player);
        this.inventory.onClose(player);
    }
}

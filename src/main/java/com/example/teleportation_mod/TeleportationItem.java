package com.example.teleportation_mod;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import org.apache.logging.log4j.LogManager;

public class TeleportationItem extends Item {

    public TeleportationItem(Properties properties) {
        super(properties);
    }

    public void SaveTeleportationBlockPosition(ItemStack itemStack, BlockPos pos, PlayerEntity player) {
        CompoundNBT nbt = itemStack.getOrCreateTag();
        nbt.putIntArray("teleportation_mod_pos", new int[] { pos.getX(), pos.getY(), pos.getZ() });
        itemStack.setTag(nbt);

        DoDamage(itemStack, player);
    }

    public void DoDamage(ItemStack itemStack, PlayerEntity player) {
        itemStack.damageItem(1, player, playerEntity -> {
            LogManager.getLogger().info("DESTROYED");
        });
    }

    public Boolean hasBlockPosition(ItemStack itemStack) {
        CompoundNBT nbt = itemStack.getOrCreateTag();
        return nbt.getIntArray("teleportation_mod_pos").length != 0;
    }

    public int[] getBlockPosition(ItemStack itemStack) {
        CompoundNBT nbt = itemStack.getOrCreateTag();
        return nbt.getIntArray("teleportation_mod_pos");
    }
}

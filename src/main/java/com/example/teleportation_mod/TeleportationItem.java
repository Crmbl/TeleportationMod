package com.example.teleportation_mod;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TeleportationItem extends Item {

    public TeleportationItem(Properties properties) {
        super(properties);
    }

    @OnlyIn(Dist.CLIENT)
    public void SaveTeleportationBlockPosition(ItemStack itemStack, BlockPos pos, PlayerEntity player) {
        CompoundNBT nbt = itemStack.getOrCreateTag();
        nbt.putIntArray("teleportation_mod_pos", new int[] { pos.getX(), pos.getY(), pos.getZ() });
        itemStack.setTag(nbt);

        itemStack.damageItem(1, player, playerEntity -> {});
    }

    public int[] getBlockPosition(ItemStack itemStack) {
        CompoundNBT nbt = itemStack.getOrCreateTag();
        return nbt.getIntArray("teleportation_mod_pos");
    }
}

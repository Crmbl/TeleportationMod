package com.example.teleportation_mod;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SlabBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class TeleportationBlock extends SlabBlock {

    public TeleportationBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return TeleportationModTileEntityTypes.TELEPORTATION_BLOCK.get().create();
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {

        ItemStack currentItemStack = player.getHeldItem(handIn);
        Item currentItem = currentItemStack.getItem();
        TeleportationBlockTileEntity tile = ((TeleportationBlockTileEntity) worldIn.getTileEntity(pos));
        if (tile == null)
            return ActionResultType.SUCCESS;

        if (currentItem == TeleportationModItems.TELEPORTATION_ITEM.get()) {
            TeleportationItem teleportationItem = ((TeleportationItem) currentItem);

            if (!teleportationItem.hasBlockPosition(currentItemStack)) {
                teleportationItem.SaveTeleportationBlockPosition(currentItemStack, pos, player);
                return ActionResultType.SUCCESS;
            }

            int[] otherBlockPos = teleportationItem.getBlockPosition(currentItemStack);
            if (otherBlockPos[0] != pos.getX() || otherBlockPos[1] != pos.getY() || otherBlockPos[2] != pos.getZ()) {
                tile.setLinkedBlockPos(teleportationItem.getBlockPosition(currentItemStack));
                tile.markDirty();
                worldIn.notifyBlockUpdate(pos, state, state, 2);

                teleportationItem.SaveTeleportationBlockPosition(currentItemStack, pos, player);
                return ActionResultType.SUCCESS;
            }
        }

        int[] linkedBlockPos = tile.getLinkedBlockPos();
        if (linkedBlockPos != null && linkedBlockPos.length != 0) {
            if (isPlayerOnTop(player, pos))
                player.setPositionAndUpdate(linkedBlockPos[0] + 0.5, linkedBlockPos[1] + 0.5, linkedBlockPos[2] + 0.5);
        }

        return ActionResultType.SUCCESS;
    }

    private Boolean isPlayerOnTop(PlayerEntity player, BlockPos pos) {
        return player.getPosX() > pos.getX() && player.getPosX() < pos.getX() + 1
                && player.getPosY() == pos.getY() + 0.5
                && player.getPosZ() > pos.getZ() && player.getPosZ() < pos.getZ() + 1;
    }
}
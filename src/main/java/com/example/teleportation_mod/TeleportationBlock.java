package com.example.teleportation_mod;

import net.minecraft.block.BlockState;
import net.minecraft.block.SlabBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public class TeleportationBlock extends SlabBlock {

    public TeleportationBlock(Properties properties) {
        super(properties);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void animateTick(BlockState state, World world, BlockPos pos, Random random) {
        super.animateTick(state, world, pos, random);

        TeleportationBlockTileEntity tile = ((TeleportationBlockTileEntity) world.getTileEntity(pos));
        if (tile == null) return;

        if (tile.getLinkedBlockPos() != null && tile.getLinkedBlockPos().length != 0) {
            int x = pos.getX();
            int y = pos.getY();
            int z = pos.getZ();
            for (int i = 0; i < 6; ++i) {
                double d0 = ((float) x + 0.5) + (random.nextFloat() - 0.5) * 1D;
                double d1 = (((float) y + 0.7) + (random.nextFloat() - 0.5) * 1D) + 0.5;
                double d2 = ((float) z + 0.5) + (random.nextFloat() - 0.5) * 1D;
                world.addParticle(ParticleTypes.PORTAL, d0, d1, d2, 0, 0, 0);
            }
        }
    }

    @Override
    public boolean isNormalCube(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return false;
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
        return true;
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

            int[] teleportationItemBlockPos = teleportationItem.getBlockPosition(currentItemStack);
            if (teleportationItemBlockPos == null || teleportationItemBlockPos.length == 0) {
                teleportationItem.SaveTeleportationBlockPosition(currentItemStack, pos, player);
                return ActionResultType.SUCCESS;
            }

            if (teleportationItemBlockPos[0] != pos.getX() || teleportationItemBlockPos[1] != pos.getY() || teleportationItemBlockPos[2] != pos.getZ()) {
                tile.setLinkedBlockPos(teleportationItem.getBlockPosition(currentItemStack));
                playActivationSound(player);

                tile.markDirty();
                worldIn.notifyBlockUpdate(pos, state, state, 2);

                teleportationItem.SaveTeleportationBlockPosition(currentItemStack, pos, player);
                return ActionResultType.SUCCESS;
            }
        }

        MovePlayer(tile.getLinkedBlockPos(), player, pos);
        return ActionResultType.SUCCESS;
    }

    private void MovePlayer(int[] linkedBlockPos, PlayerEntity player, BlockPos actualPos) {
        if (linkedBlockPos != null && linkedBlockPos.length != 0) {
            if (isPlayerOnTop(player, actualPos)){
                playUsingSound(player);
                player.setPositionAndUpdate(linkedBlockPos[0] + 0.5, linkedBlockPos[1] + 0.5, linkedBlockPos[2] + 0.5);
                //TODO fix bug son
            }
        }
    }

    private Boolean isPlayerOnTop(PlayerEntity player, BlockPos pos) {
        return player.getPosX() > pos.getX() && player.getPosX() < pos.getX() + 1
                && (player.getPosY() == pos.getY() + 0.5 || player.getPosY() == pos.getY() + 1)
                && player.getPosZ() > pos.getZ() && player.getPosZ() < pos.getZ() + 1;
    }

    private void playActivationSound(PlayerEntity playerEntity) {
        ResourceLocation location = new ResourceLocation("minecraft", "block.ender_chest.open");
        SoundEvent event = new SoundEvent(location);
        playerEntity.playSound(event, 1.5F, 0.9F);
    }

    private void playUsingSound(PlayerEntity playerEntity) {
        ResourceLocation location = new ResourceLocation("minecraft", "entity.enderman.teleport");
        SoundEvent event = new SoundEvent(location);
        playerEntity.playSound(event, 1.5F, 0.9F);
    }
}
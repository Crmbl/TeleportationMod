package com.example.teleportation_mod;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;

import javax.annotation.Nullable;

public class TeleportationBlockTileEntity extends TileEntity {

    private int[] linkedBlockPos;
    public int[] getLinkedBlockPos() { return linkedBlockPos; }
    public void setLinkedBlockPos(int[] value) { linkedBlockPos = value; }

    public TeleportationBlockTileEntity() {
        super(TeleportationModTileEntityTypes.TELEPORTATION_BLOCK.get());
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        if (linkedBlockPos != null)
            compound.putIntArray("teleportation_mod_pos_linked", linkedBlockPos);

        return super.write(compound);
    }

    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        linkedBlockPos = compound.getIntArray("teleportation_mod_pos_linked");
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        CompoundNBT nbtTag = new CompoundNBT();
        if (linkedBlockPos != null)
            nbtTag.putIntArray("teleportation_mod_pos_linked", linkedBlockPos);

        return new SUpdateTileEntityPacket(getPos(), -1, nbtTag);
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        CompoundNBT tag = pkt.getNbtCompound();
        linkedBlockPos = tag.getIntArray("teleportation_mod_pos_linked");
    }
}

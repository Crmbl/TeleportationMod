package com.example.teleportation_mod;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TeleportationModTileEntityTypes {

    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = new DeferredRegister<>(ForgeRegistries.TILE_ENTITIES, TeleportationMod.MOD_ID);
    public static final RegistryObject<TileEntityType<?>> TELEPORTATION_BLOCK = TILE_ENTITIES.register("teleportation_block", () ->
            TileEntityType.Builder.create(TeleportationBlockTileEntity::new, TeleportationModBlocks.TELEPORTATION_BLOCK.get()).build(null));
}

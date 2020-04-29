package com.example.teleportation_mod;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TeleportationModBlocks {

    public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, TeleportationMod.MOD_ID);
    public static final RegistryObject<Block> TELEPORTATION_BLOCK = BLOCKS.register("teleportation_block", () ->
        new TeleportationBlock(Block.Properties.create(Material.ROCK)
                .hardnessAndResistance(4.0F, 10.0F)
                .harvestLevel(2)
                .harvestTool(ToolType.PICKAXE)
                .sound(SoundType.STONE))
    );
}

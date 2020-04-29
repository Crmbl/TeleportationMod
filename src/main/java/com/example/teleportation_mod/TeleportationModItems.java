package com.example.teleportation_mod;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TeleportationModItems {

    public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, TeleportationMod.MOD_ID);
    public static final RegistryObject<Item> TELEPORTATION_ITEM = ITEMS.register("teleportation_item", () ->
        new TeleportationItem(new Item.Properties().maxStackSize(1)
                                .setNoRepair()
                                .maxDamage(3)
                                .group(ItemGroup.TRANSPORTATION)
        )
    );

    public static final RegistryObject<Item> TELEPORTATION_BLOCK = ITEMS.register("teleportation_block", () ->
        new BlockItem(TeleportationModBlocks.TELEPORTATION_BLOCK.get(), new Item.Properties()
                .maxStackSize(1)
                .group(ItemGroup.TRANSPORTATION)
        )
    );
}

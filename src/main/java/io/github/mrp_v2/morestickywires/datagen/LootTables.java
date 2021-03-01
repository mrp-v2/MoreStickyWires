package io.github.mrp_v2.morestickywires.datagen;

import io.github.mrp_v2.morestickywires.util.ObjectHolder;
import mrp_v2.mrplibrary.datagen.BlockLootTables;
import net.minecraft.block.Block;
import net.minecraftforge.fml.RegistryObject;

import java.util.ArrayList;
import java.util.List;

public class LootTables extends BlockLootTables
{
    public LootTables()
    {
        List<RegistryObject<? extends Block>> blockObjects = new ArrayList<>();
        blockObjects.addAll(ObjectHolder.STICKY_WIRE_BLOCKS.values());
        blockObjects.addAll(ObjectHolder.STICKY_INFINIWIRE_BLOCKS.values());
        for (RegistryObject<? extends Block> blockObj : blockObjects)
        {
            dropSelfLootTable(blockObj.get());
        }
    }

    protected void dropSelfLootTable(Block block)
    {
        this.addLootTable(block, this::registerDropSelfLootTable);
    }
}

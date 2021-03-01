package io.github.mrp_v2.morestickywires.util;

import io.github.mrp_v2.morestickywires.MoreStickyWires;
import io.github.mrp_v2.morestickywires.block.StickyAdjustedRedstoneWireBlock;
import io.github.mrp_v2.morestickywires.block.StickyInfiniwireBlock;
import mrp_v2.morewires.item.AdjustedRedstoneItem;
import mrp_v2.morewires.item.InfiniwireItem;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.Map;

public class ObjectHolder
{
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, MoreStickyWires.ID);
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, MoreStickyWires.ID);
    public static final Map<String, RegistryObject<StickyAdjustedRedstoneWireBlock>> STICKY_WIRE_BLOCKS;
    public static final Map<String, RegistryObject<AdjustedRedstoneItem>> STICKY_WIRE_BLOCK_ITEMS;
    public static final Map<String, RegistryObject<StickyInfiniwireBlock>> STICKY_INFINIWIRE_BLOCKS;
    public static final Map<String, RegistryObject<InfiniwireItem>> STICKY_INFINIWIRE_BLOCK_ITEMS;
    public static final Tags.IOptionalNamedTag<Item> STICKY_WIRES_TAG =
            ItemTags.createOptional(new ResourceLocation(MoreStickyWires.ID, "sticky_wires"));
    public static final Tags.IOptionalNamedTag<Item> STICKY_INFINIWIRES_TAG =
            ItemTags.createOptional(new ResourceLocation(MoreStickyWires.ID, "sticky_infiniwires"));

    static
    {
        Map<String, Pair<Float, ITag<Item>>> colors = mrp_v2.morewires.util.ObjectHolder.COLORS;
        STICKY_WIRE_BLOCKS = new HashMap<>(colors.size());
        STICKY_WIRE_BLOCK_ITEMS = new HashMap<>(colors.size());
        STICKY_INFINIWIRE_BLOCKS = new HashMap<>(colors.size());
        STICKY_INFINIWIRE_BLOCK_ITEMS = new HashMap<>(colors.size());
        for (String color : colors.keySet())
        {
            String stickyWireId = "sticky_" + color + "_wire";
            STICKY_WIRE_BLOCKS.put(color, BLOCKS.register(stickyWireId,
                    () -> new StickyAdjustedRedstoneWireBlock(colors.get(color).getLeft())));
            STICKY_WIRE_BLOCK_ITEMS.put(color, ITEMS.register(stickyWireId,
                    () -> STICKY_WIRE_BLOCKS.get(color).get().createBlockItem(colors.get(color).getRight())));
            String stickyInfiniwireId = "sticky_" + color + "_infiniwire";
            STICKY_INFINIWIRE_BLOCKS.put(color,
                    BLOCKS.register(stickyInfiniwireId, () -> new StickyInfiniwireBlock(colors.get(color).getLeft())));
            STICKY_INFINIWIRE_BLOCK_ITEMS.put(color, ITEMS.register(stickyInfiniwireId,
                    () -> STICKY_INFINIWIRE_BLOCKS.get(color).get().createBlockItem(colors.get(color).getRight())));
        }
    }

    public static void registerListeners(IEventBus bus)
    {
        BLOCKS.register(bus);
        ITEMS.register(bus);
    }
}

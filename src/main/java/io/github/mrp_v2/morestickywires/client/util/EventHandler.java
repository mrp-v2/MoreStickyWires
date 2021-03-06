package io.github.mrp_v2.morestickywires.client.util;

import io.github.mrp_v2.morestickywires.MoreStickyWires;
import io.github.mrp_v2.morestickywires.util.ObjectHolder;
import mrp_v2.morewires.block.AdjustedRedstoneWireBlock;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = MoreStickyWires.ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EventHandler
{
    @SubscribeEvent public static void clientSetup(final FMLClientSetupEvent event)
    {
        List<RegistryObject<? extends Block>> blockObjects = new ArrayList<>();
        blockObjects.addAll(ObjectHolder.STICKY_INFINIWIRE_BLOCKS.values());
        blockObjects.addAll(ObjectHolder.STICKY_WIRE_BLOCKS.values());
        for (RegistryObject<? extends Block> blockObject : blockObjects)
        {
            RenderTypeLookup.setRenderLayer(blockObject.get(), EventHandler::matchesStickyWireRenderTypes);
        }
    }

    private static boolean matchesStickyWireRenderTypes(RenderType renderType)
    {
        return renderType == RenderType.getCutout() || renderType == RenderType.getTranslucent();
    }

    @SubscribeEvent public static void registerBlockColors(final ColorHandlerEvent.Block event)
    {
        List<RegistryObject<? extends Block>> blockObjects = new ArrayList<>();
        blockObjects.addAll(ObjectHolder.STICKY_INFINIWIRE_BLOCKS.values());
        blockObjects.addAll(ObjectHolder.STICKY_WIRE_BLOCKS.values());
        List<Block> blocks = new ArrayList<>();
        for (RegistryObject<? extends Block> blockObject : blockObjects)
        {
            blocks.add(blockObject.get());
        }
        IBlockColor colorer =
                (blockState, iBlockDisplayReader, blockPos, tint) -> AdjustedRedstoneWireBlock.getColor(blockState);
        event.getBlockColors().register(colorer, blocks.toArray(new Block[0]));
    }
}

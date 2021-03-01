package io.github.mrp_v2.morestickywires.datagen;

import io.github.mrp_v2.morestickywires.block.StickyAdjustedRedstoneWireBlock;
import io.github.mrp_v2.morestickywires.block.StickyInfiniwireBlock;
import io.github.mrp_v2.morestickywires.util.ObjectHolder;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.fml.RegistryObject;

public class EN_USTranslationGenerator extends mrp_v2.morewires.datagen.EN_USTranslationGenerator
{
    public EN_USTranslationGenerator(DataGenerator gen, String modid)
    {
        super(gen, modid);
    }

    @Override protected void addTranslations()
    {
        for (RegistryObject<StickyAdjustedRedstoneWireBlock> block : ObjectHolder.STICKY_WIRE_BLOCKS.values())
        {
            addWireTranslation(block.get());
        }
        for (RegistryObject<StickyInfiniwireBlock> block : ObjectHolder.STICKY_INFINIWIRE_BLOCKS.values())
        {
            addWireTranslation(block.get());
        }
    }
}

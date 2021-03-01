package io.github.mrp_v2.morestickywires.datagen;

import io.github.mrp_v2.morestickywires.util.ObjectHolder;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.TagsProvider;
import net.minecraft.item.Item;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

public class ItemTagGenerator extends mrp_v2.morewires.datagen.ItemTagGenerator
{
    public ItemTagGenerator(DataGenerator dataGenerator, BlockTagsProvider blockTagProvider, String modId,
            @Nullable ExistingFileHelper existingFileHelper)
    {
        super(dataGenerator, blockTagProvider, modId, existingFileHelper);
    }

    @Override protected void registerTags()
    {
        TagsProvider.Builder<Item> stickyWiresTagBuilder = this.getOrCreateBuilder(ObjectHolder.STICKY_WIRES_TAG);
        TagsProvider.Builder<Item> stickyInfiniwiresTagBuilder =
                this.getOrCreateBuilder(ObjectHolder.STICKY_INFINIWIRES_TAG);
        for (String color : mrp_v2.morewires.util.ObjectHolder.COLORS.keySet())
        {
            stickyWiresTagBuilder.add(ObjectHolder.STICKY_WIRE_BLOCK_ITEMS.get(color).get());
            stickyInfiniwiresTagBuilder.add(ObjectHolder.STICKY_INFINIWIRE_BLOCK_ITEMS.get(color).get());
        }
    }
}

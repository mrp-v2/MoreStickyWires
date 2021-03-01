package io.github.mrp_v2.morestickywires.datagen;

import io.github.mrp_v2.morestickywires.MoreStickyWires;
import io.github.mrp_v2.morestickywires.util.ObjectHolder;
import mrp_v2.morewires.item.AdjustedRedstoneItem;
import mrp_v2.morewires.item.InfiniwireItem;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;

public class ItemModelGenerator extends mrp_v2.morewires.datagen.ItemModelGenerator
{
    public ItemModelGenerator(DataGenerator generator, String modId, ExistingFileHelper existingFileHelper)
    {
        super(generator, modId, existingFileHelper);
    }

    @Override protected void registerModels()
    {
        for (RegistryObject<AdjustedRedstoneItem> itemObject : ObjectHolder.STICKY_WIRE_BLOCK_ITEMS.values())
        {
            registerItemModel(itemObject.get());
        }
        for (RegistryObject<InfiniwireItem> itemObject : ObjectHolder.STICKY_INFINIWIRE_BLOCK_ITEMS.values())
        {
            registerInfiniwireItemModel(itemObject.get());
        }
    }

    private void registerInfiniwireItemModel(Item item)
    {
        String path = item.getRegistryName().getPath().replace("infini", "");
        this.registerItemModel(item, new ResourceLocation(MoreStickyWires.ID, "item/" + path));
    }
}

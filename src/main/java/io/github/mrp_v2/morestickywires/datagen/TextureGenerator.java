package io.github.mrp_v2.morestickywires.datagen;

import mrp_v2.morewires.MoreWires;
import mrp_v2.mrplibrary.datagen.providers.TextureProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;

public class TextureGenerator extends TextureProvider
{
    public TextureGenerator(DataGenerator generator, ExistingFileHelper existingFileHelper, String modId)
    {
        super(generator, existingFileHelper, modId);
    }

    @Override protected void addTextures(FinishedTextureConsumer finishedTextureConsumer)
    {
        // Item textures
        final Texture slimeBall = getTexture(new ResourceLocation("item/slime_ball")), stickyRedWire =
                getTexture(new ResourceLocation("item/redstone")), stickyBlueWire =
                getTexture(new ResourceLocation(MoreWires.ID, "item/blue_wire")), stickyGreenWire =
                getTexture(new ResourceLocation(MoreWires.ID, "item/green_wire")), stickyOrangeWire =
                getTexture(new ResourceLocation(MoreWires.ID, "item/orange_wire")), stickyPinkWire =
                getTexture(new ResourceLocation(MoreWires.ID, "item/pink_wire")), stickyYellowWire =
                getTexture(new ResourceLocation(MoreWires.ID, "item/yellow_wire"));
        for (Texture stickyWire : new Texture[]{stickyRedWire, stickyBlueWire, stickyGreenWire, stickyOrangeWire,
                stickyPinkWire, stickyYellowWire})
        {
            int[] rgbArray = new int[256];
            slimeBall.getTexture().getRGB(2, 2, 12, 12, rgbArray, 17, 16);
            int[] adding = stickyWire.getTexture().getRGB(2, 3, 12, 11, new int[256], 67, 16);
            mergePixelsAdditively(rgbArray, adding, 16, 16);
            stickyWire.getTexture().setRGB(0, 0, 16, 16, rgbArray, 0, 16);
        }
        finish(stickyRedWire, modLoc("item/sticky_red_wire"), finishedTextureConsumer);
        finish(stickyBlueWire, modLoc("item/sticky_blue_wire"), finishedTextureConsumer);
        finish(stickyGreenWire, modLoc("item/sticky_green_wire"), finishedTextureConsumer);
        finish(stickyOrangeWire, modLoc("item/sticky_orange_wire"), finishedTextureConsumer);
        finish(stickyPinkWire, modLoc("item/sticky_pink_wire"), finishedTextureConsumer);
        finish(stickyYellowWire, modLoc("item/sticky_yellow_wire"), finishedTextureConsumer);
    }

    public static void mergePixelsAdditively(final int[] base, final int[] added, int width, int height)
    {
        if (base.length != added.length)
        {
            throw new IllegalArgumentException("Arguments 'base' and 'added' must have the same length.");
        }
        if (width * height != base.length)
        {
            throw new IllegalArgumentException("Arguments 'base' and 'added' must have the same length.");
        }
        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                int pAdded = added[y * width + x];
                if ((pAdded & 0xFF000000) != 0)
                {
                    base[y * width + x] = pAdded;
                }
            }
        }
    }
}

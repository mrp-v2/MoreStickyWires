package io.github.mrp_v2.morestickywires.datagen;

import io.github.mrp_v2.morestickywires.util.ObjectHolder;
import mrp_v2.morewires.item.AdjustedRedstoneItem;
import mrp_v2.morewires.item.InfiniwireItem;
import mrp_v2.mrplibrary.datagen.providers.RecipeProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ITag;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

public class RecipeGenerator extends RecipeProvider
{
    protected RecipeGenerator(DataGenerator dataGeneratorIn, String modId)
    {
        super(dataGeneratorIn, modId);
    }

    @Override protected void registerRecipes(Consumer<IFinishedRecipe> consumer)
    {
        for (String color : mrp_v2.morewires.util.ObjectHolder.COLORS.keySet())
        {
            AdjustedRedstoneItem stickyWireItem = ObjectHolder.STICKY_WIRE_BLOCK_ITEMS.get(color).get();
            makeDyedStickyWireRecipe(consumer, stickyWireItem,
                    mrp_v2.morewires.util.ObjectHolder.WIRE_BLOCK_ITEMS.get(color).get());
            makeDyeingStickyWireRecipe(consumer, stickyWireItem, stickyWireItem.getDyeTag());
            InfiniwireItem stickyInfiniwireItem = ObjectHolder.STICKY_INFINIWIRE_BLOCK_ITEMS.get(color).get();
            makeDyedStickyInfiniwireRecipes(consumer, stickyInfiniwireItem,
                    mrp_v2.morewires.util.ObjectHolder.INFINIWIRE_BLOCK_ITEMS.get(color).get(), stickyWireItem);
            makeDyeingStickyInfiniwireRecipe(consumer, stickyInfiniwireItem, stickyInfiniwireItem.getDyeTag());
        }
    }

    private void makeDyeingStickyWireRecipe(Consumer<IFinishedRecipe> iFinishedRecipeConsumer, IItemProvider result,
            ITag<Item> dyeTag)
    {
        ResourceLocation resultLoc = result.asItem().getRegistryName();
        ShapelessRecipeBuilder.shapelessRecipe(result, 8)
                .addIngredient(Ingredient.fromTag(ObjectHolder.STICKY_WIRES_TAG), 8).addIngredient(dyeTag)
                .addCriterion("has_sticky_wire", hasItem(ObjectHolder.STICKY_WIRES_TAG)).setGroup(resultLoc.getPath())
                .build(iFinishedRecipeConsumer,
                        modLoc(mrp_v2.morewires.datagen.RecipeGenerator.DYEING_ID + "/" + resultLoc.getPath()));
    }

    private void makeDyedStickyWireRecipe(Consumer<IFinishedRecipe> iFinishedRecipeConsumer, IItemProvider result,
            IItemProvider ingredient)
    {
        ShapelessRecipeBuilder.shapelessRecipe(result, 8).addIngredient(ingredient, 8)
                .addIngredient(Tags.Items.SLIMEBALLS).addCriterion("has_base_wire", hasItem(ingredient))
                .addCriterion("has_slimeballs", hasItem(Tags.Items.SLIMEBALLS))
                .setGroup(result.asItem().getRegistryName().getPath()).build(iFinishedRecipeConsumer);
    }

    private void makeDyedStickyInfiniwireRecipes(Consumer<IFinishedRecipe> iFinishedRecipeConsumer,
            IItemProvider result, IItemProvider wireEquivalent, IItemProvider stickyWireEquivalent)
    {
        ResourceLocation resultLoc = result.asItem().getRegistryName();
        ShapelessRecipeBuilder.shapelessRecipe(result, 8).addIngredient(wireEquivalent, 8)
                .addIngredient(Tags.Items.SLIMEBALLS).addCriterion("has_base_wire", hasItem(wireEquivalent))
                .addCriterion("has_slimeballs", hasItem(Tags.Items.SLIMEBALLS)).setGroup(resultLoc.getPath())
                .build(iFinishedRecipeConsumer);
        ShapelessRecipeBuilder.shapelessRecipe(result, 8).addIngredient(stickyWireEquivalent, 8)
                .addIngredient(Tags.Items.INGOTS_IRON)
                .addCriterion("has_base_sticky_wire", hasItem(stickyWireEquivalent)).setGroup(resultLoc.getPath())
                .build(iFinishedRecipeConsumer, modLoc("from_wires/" + resultLoc.getPath()));
    }

    private void makeDyeingStickyInfiniwireRecipe(Consumer<IFinishedRecipe> iFinishedRecipeConsumer,
            IItemProvider result, ITag<Item> dyeTag)
    {
        ResourceLocation resultLoc = result.asItem().getRegistryName();
        ShapelessRecipeBuilder.shapelessRecipe(result, 8)
                .addIngredient(Ingredient.fromTag(ObjectHolder.STICKY_INFINIWIRES_TAG), 8).addIngredient(dyeTag)
                .addCriterion("has_sticky_infiniwire", RecipeProvider.hasItem(ObjectHolder.STICKY_INFINIWIRES_TAG))
                .setGroup(resultLoc.getPath()).build(iFinishedRecipeConsumer,
                modLoc(mrp_v2.morewires.datagen.RecipeGenerator.DYEING_ID + "/" + resultLoc.getPath()));
    }
}

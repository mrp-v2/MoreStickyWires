package io.github.mrp_v2.morestickywires.datagen;

import io.github.mrp_v2.morestickywires.block.StickyAdjustedRedstoneWireBlock;
import io.github.mrp_v2.morestickywires.block.StickyInfiniwireBlock;
import io.github.mrp_v2.morestickywires.util.ObjectHolder;
import mrp_v2.morewires.MoreWires;
import mrp_v2.morewires.block.AdjustedRedstoneWireBlock;
import net.minecraft.block.RedstoneWireBlock;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.data.DataGenerator;
import net.minecraft.state.properties.RedstoneSide;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.ModelBuilder;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.loaders.MultiLayerModelBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;

import java.util.function.Function;

public class BlockStateGenerator extends mrp_v2.morewires.datagen.BlockStateGenerator
{
    public static final ResourceLocation NONE = new ResourceLocation("none", "none");
    public static final float SLIME_HEIGHT = 0.125f, WIRE_HEIGHT = 0.25f;
    protected ModelFile slimeCenter;
    protected BlockModelBuilder slimeNorth, slimeEast, slimeSouth, slimeWest, slimeUp;
    protected ModelFile wireNorthSouthCenter, wireEastWestCenter, wireNorth, wireEast, wireSouth, wireWest;

    public BlockStateGenerator(DataGenerator gen, String modid, ExistingFileHelper exFileHelper)
    {
        super(gen, modid, exFileHelper);
    }

    @Override protected void registerStatesAndModels()
    {
        // Slime part models
        ResourceLocation slimeTexture = new ResourceLocation("block/slime_block");
        Function<ModelBuilder<BlockModelBuilder>.ElementBuilder, ModelBuilder.ElementBuilder> facesFunction =
                (elementBuilder) -> elementBuilder.face(Direction.UP).texture("#slime").end().face(Direction.DOWN)
                        .texture("#slime").end();
        BlockModelBuilder centerBuilder = models().getBuilder("block/slime_center");
        facesFunction.apply(centerBuilder.ao(false).texture("slime", slimeTexture).element().from(4, SLIME_HEIGHT, 4)
                .to(12, SLIME_HEIGHT, 12).shade(false)).end();
        slimeCenter = models().getBuilder("block/slime_center_multilayer").customLoader(MultiLayerModelBuilder::begin)
                .submodel(RenderType.getTranslucent(), centerBuilder).end();
        slimeNorth = new BlockModelBuilder(NONE, models().existingFileHelper);
        facesFunction.apply(slimeNorth.ao(false).texture("slime", slimeTexture).element().from(4, SLIME_HEIGHT, 0)
                .to(12, SLIME_HEIGHT, 4).shade(false)).end();
        slimeEast = new BlockModelBuilder(NONE, models().existingFileHelper);
        facesFunction.apply(slimeEast.ao(false).texture("slime", slimeTexture).element().from(12, SLIME_HEIGHT, 4)
                .to(16, SLIME_HEIGHT, 12).shade(false)).end();
        slimeSouth = new BlockModelBuilder(NONE, models().existingFileHelper);
        facesFunction.apply(slimeSouth.ao(false).texture("slime", slimeTexture).element().from(4, SLIME_HEIGHT, 12)
                .to(12, SLIME_HEIGHT, 16).shade(false)).end();
        slimeWest = new BlockModelBuilder(NONE, models().existingFileHelper);
        facesFunction.apply(slimeWest.ao(false).texture("slime", slimeTexture).element().from(0, SLIME_HEIGHT, 4)
                .to(4, SLIME_HEIGHT, 12).shade(false)).end();
        slimeUp = new BlockModelBuilder(NONE, models().existingFileHelper);
        slimeUp.ao(false).texture("slime", slimeTexture).element().from(4, 0, SLIME_HEIGHT).to(12, 16, SLIME_HEIGHT)
                .face(Direction.SOUTH).texture("#slime").end().face(Direction.NORTH).texture("#slime").end().end();
        registerWireStates();
        registerInfiniwireStates();
    }

    @Override protected void registerWireStates()
    {
        setupWireModels();
        for (RegistryObject<StickyAdjustedRedstoneWireBlock> blockObject : ObjectHolder.STICKY_WIRE_BLOCKS.values())
        {
            registerWireBasedStates(blockObject.get());
        }
    }

    @Override protected void setupWireModels()
    {
        super.setupWireModels();
        ResourceLocation line0 = mcLoc("block/redstone_dust_line0"), line1 =
                modLoc("block/redstone_dust_line1_rotated");
        BlockModelBuilder northSouthCenterBuilder =
                new BlockModelBuilder(NONE, models().existingFileHelper).ao(false).texture("line", line0).element()
                        .from(7, WIRE_HEIGHT, 5).to(10, WIRE_HEIGHT, 11).shade(false).face(Direction.UP)
                        .texture("#line").tintindex(0).end().face(Direction.DOWN).texture("#line").tintindex(0).end()
                        .end();
        wireNorthSouthCenter =
                models().getBuilder("block/redstone_dust_north_south").customLoader(MultiLayerModelBuilder::begin)
                        .submodel(RenderType.getCutout(), northSouthCenterBuilder).end();
        BlockModelBuilder eastWestCenterBuilder =
                new BlockModelBuilder(NONE, models().existingFileHelper).ao(false).texture("line", line1).element()
                        .from(5, WIRE_HEIGHT, 6).to(11, WIRE_HEIGHT, 10).shade(false).face(Direction.UP)
                        .texture("#line").tintindex(0).end().face(Direction.DOWN).texture("#line").tintindex(0).end()
                        .end();
        wireEastWestCenter =
                models().getBuilder("block/redstone_dust_east_west").customLoader(MultiLayerModelBuilder::begin)
                        .submodel(RenderType.getCutout(), eastWestCenterBuilder).end();
        BlockModelBuilder northBuilder =
                new BlockModelBuilder(NONE, models().existingFileHelper).ao(false).texture("line", line0).element()
                        .from(7, WIRE_HEIGHT, 0).to(11, WIRE_HEIGHT, 5).shade(false).face(Direction.UP).texture("#line")
                        .tintindex(0).end().face(Direction.DOWN).texture("#line").tintindex(0).end().end();
        wireNorth = models().getBuilder("block/redstone_dust_north").customLoader(MultiLayerModelBuilder::begin)
                .submodel(RenderType.getCutout(), northBuilder).submodel(RenderType.getTranslucent(), slimeNorth).end();
        BlockModelBuilder eastBuilder =
                new BlockModelBuilder(NONE, models().existingFileHelper).ao(false).texture("line", line1).element()
                        .from(11, WIRE_HEIGHT, 6).to(16, WIRE_HEIGHT, 10).shade(false).face(Direction.UP)
                        .texture("#line").tintindex(0).end().face(Direction.DOWN).texture("#line").tintindex(0).end()
                        .end();
        wireEast = models().getBuilder("block/redstone_dust_east").customLoader(MultiLayerModelBuilder::begin)
                .submodel(RenderType.getCutout(), eastBuilder).submodel(RenderType.getTranslucent(), slimeEast).end();
        BlockModelBuilder southBuilder =
                new BlockModelBuilder(NONE, models().existingFileHelper).ao(false).texture("line", line0).element()
                        .from(6, WIRE_HEIGHT, 11).to(9, WIRE_HEIGHT, 16).shade(false).face(Direction.UP)
                        .texture("#line").tintindex(0).end().face(Direction.DOWN).texture("#line").tintindex(0).end()
                        .end();
        wireSouth = models().getBuilder("block/redstone_dust_south").customLoader(MultiLayerModelBuilder::begin)
                .submodel(RenderType.getCutout(), southBuilder).submodel(RenderType.getTranslucent(), slimeSouth).end();
        BlockModelBuilder westBuilder =
                new BlockModelBuilder(NONE, models().existingFileHelper).ao(false).texture("line", line1).element()
                        .from(0, WIRE_HEIGHT, 6).to(5, WIRE_HEIGHT, 9).shade(false).face(Direction.UP).texture("#line")
                        .tintindex(0).end().face(Direction.DOWN).texture("#line").tintindex(0).end().end();
        wireWest = models().getBuilder("block/redstone_dust_west").customLoader(MultiLayerModelBuilder::begin)
                .submodel(RenderType.getCutout(), westBuilder).submodel(RenderType.getTranslucent(), slimeWest).end();
        dotModel = models().getBuilder("block/redstone_dust_dot").customLoader(MultiLayerModelBuilder::begin)
                .submodel(RenderType.getCutout(), new BlockModelBuilder(NONE, models().existingFileHelper)
                        .parent(models().getExistingFile(mcLoc("block/redstone_dust_dot")))).end();
        upModel = models().getBuilder("block/sticky_redstone_dust_up").customLoader(MultiLayerModelBuilder::begin)
                .submodel(RenderType.getCutout(), new BlockModelBuilder(NONE, models().existingFileHelper)
                        .parent(models().getExistingFile(mcLoc("block/redstone_dust_up"))))
                .submodel(RenderType.getTranslucent(), slimeUp).end();
    }

    @Override protected void registerWireBasedStates(AdjustedRedstoneWireBlock wireBasedBlock)
    {
        this.getMultipartBuilder(wireBasedBlock)
                // always center slime
                .part().modelFile(slimeCenter).addModel().end()
                // no connections | multi-axis connections
                .part().modelFile(dotModel).addModel().useOr().nestedGroup()
                .condition(RedstoneWireBlock.NORTH, RedstoneSide.NONE)
                .condition(RedstoneWireBlock.SOUTH, RedstoneSide.NONE)
                .condition(RedstoneWireBlock.EAST, RedstoneSide.NONE)
                .condition(RedstoneWireBlock.WEST, RedstoneSide.NONE).end().nestedGroup().nestedGroup().useOr()
                .condition(RedstoneWireBlock.NORTH, RedstoneSide.SIDE, RedstoneSide.UP)
                .condition(RedstoneWireBlock.SOUTH, RedstoneSide.SIDE, RedstoneSide.UP).endNestedGroup().nestedGroup()
                .useOr().condition(RedstoneWireBlock.EAST, RedstoneSide.SIDE, RedstoneSide.UP)
                .condition(RedstoneWireBlock.WEST, RedstoneSide.SIDE, RedstoneSide.UP).endNestedGroup().end().end()
                // north
                .part().modelFile(wireNorth).addModel()
                .condition(RedstoneWireBlock.NORTH, RedstoneSide.SIDE, RedstoneSide.UP).end()
                // east
                .part().modelFile(wireEast).addModel()
                .condition(RedstoneWireBlock.EAST, RedstoneSide.SIDE, RedstoneSide.UP).end()
                // south
                .part().modelFile(wireSouth).addModel()
                .condition(RedstoneWireBlock.SOUTH, RedstoneSide.SIDE, RedstoneSide.UP).end()
                // west
                .part().modelFile(wireWest).addModel()
                .condition(RedstoneWireBlock.WEST, RedstoneSide.SIDE, RedstoneSide.UP).end()
                // north and south
                .part().modelFile(wireNorthSouthCenter).addModel()
                .condition(RedstoneWireBlock.NORTH, RedstoneSide.SIDE, RedstoneSide.UP)
                .condition(RedstoneWireBlock.SOUTH, RedstoneSide.SIDE, RedstoneSide.UP)
                .condition(RedstoneWireBlock.EAST, RedstoneSide.NONE)
                .condition(RedstoneWireBlock.WEST, RedstoneSide.NONE).end()
                // east and west
                .part().modelFile(wireEastWestCenter).addModel()
                .condition(RedstoneWireBlock.EAST, RedstoneSide.SIDE, RedstoneSide.UP)
                .condition(RedstoneWireBlock.WEST, RedstoneSide.SIDE, RedstoneSide.UP)
                .condition(RedstoneWireBlock.NORTH, RedstoneSide.NONE)
                .condition(RedstoneWireBlock.SOUTH, RedstoneSide.NONE).end()
                // north up
                .part().modelFile(upModel).addModel().condition(RedstoneWireBlock.NORTH, RedstoneSide.UP).end()
                // east up
                .part().modelFile(upModel).rotationY(90).addModel().condition(RedstoneWireBlock.EAST, RedstoneSide.UP)
                .end()
                // south up
                .part().modelFile(upModel).rotationY(180).addModel().condition(RedstoneWireBlock.SOUTH, RedstoneSide.UP)
                .end()
                // west up
                .part().modelFile(upModel).rotationY(270).addModel().condition(RedstoneWireBlock.WEST, RedstoneSide.UP)
                .end();
    }

    @Override protected void registerInfiniwireStates()
    {
        setupInfiniwireModels();
        for (RegistryObject<StickyInfiniwireBlock> blockObject : ObjectHolder.STICKY_INFINIWIRE_BLOCKS.values())
        {
            registerWireBasedStates(blockObject.get());
        }
    }

    @Override protected void setupInfiniwireModels()
    {
        super.setupInfiniwireModels();
        ResourceLocation line0 = new ResourceLocation(MoreWires.ID, "block/infiniwire_line0"), line1 =
                modLoc("block/infiniwire_line1_rotated");
        BlockModelBuilder northSouthCenterBuilder =
                new BlockModelBuilder(NONE, models().existingFileHelper).ao(false).texture("line", line0).element()
                        .from(6, WIRE_HEIGHT, 5).to(10, WIRE_HEIGHT, 11).shade(false).face(Direction.UP)
                        .texture("#line").tintindex(0).end().face(Direction.DOWN).texture("#line").tintindex(0).end()
                        .end();
        wireNorthSouthCenter =
                models().getBuilder("block/infiniwire_north_south").customLoader(MultiLayerModelBuilder::begin)
                        .submodel(RenderType.getCutout(), northSouthCenterBuilder).end();
        BlockModelBuilder eastWestCenterBuilder =
                new BlockModelBuilder(NONE, models().existingFileHelper).ao(false).texture("line", line1).element()
                        .from(5, WIRE_HEIGHT, 6).to(11, WIRE_HEIGHT, 10).shade(false).face(Direction.UP)
                        .texture("#line").tintindex(0).end().face(Direction.DOWN).texture("#line").tintindex(0).end()
                        .end();
        wireEastWestCenter =
                models().getBuilder("block/infiniwire_east_west").customLoader(MultiLayerModelBuilder::begin)
                        .submodel(RenderType.getCutout(), eastWestCenterBuilder).end();
        BlockModelBuilder northBuilder =
                new BlockModelBuilder(NONE, models().existingFileHelper).ao(false).texture("line", line0).element()
                        .from(7, WIRE_HEIGHT, 0).to(11, WIRE_HEIGHT, 5).shade(false).face(Direction.UP).texture("#line")
                        .tintindex(0).end().face(Direction.DOWN).texture("#line").tintindex(0).end().end();
        wireNorth = models().getBuilder("block/infiniwire_north").customLoader(MultiLayerModelBuilder::begin)
                .submodel(RenderType.getCutout(), northBuilder).submodel(RenderType.getTranslucent(), slimeNorth).end();
        BlockModelBuilder eastBuilder =
                new BlockModelBuilder(NONE, models().existingFileHelper).ao(false).texture("line", line1).element()
                        .from(11, WIRE_HEIGHT, 6).to(16, WIRE_HEIGHT, 10).shade(false).face(Direction.UP)
                        .texture("#line").tintindex(0).end().face(Direction.DOWN).texture("#line").tintindex(0).end()
                        .end();
        wireEast = models().getBuilder("block/infiniwire_east").customLoader(MultiLayerModelBuilder::begin)
                .submodel(RenderType.getCutout(), eastBuilder).submodel(RenderType.getTranslucent(), slimeEast).end();
        BlockModelBuilder southBuilder =
                new BlockModelBuilder(NONE, models().existingFileHelper).ao(false).texture("line", line0).element()
                        .from(6, WIRE_HEIGHT, 11).to(9, WIRE_HEIGHT, 16).shade(false).face(Direction.UP)
                        .texture("#line").tintindex(0).end().face(Direction.DOWN).texture("#line").tintindex(0).end()
                        .end();
        wireSouth = models().getBuilder("block/infiniwire_south").customLoader(MultiLayerModelBuilder::begin)
                .submodel(RenderType.getCutout(), southBuilder).submodel(RenderType.getTranslucent(), slimeSouth).end();
        BlockModelBuilder westBuilder =
                new BlockModelBuilder(NONE, models().existingFileHelper).ao(false).texture("line", line1).element()
                        .from(0, WIRE_HEIGHT, 6).to(5, WIRE_HEIGHT, 10).shade(false).face(Direction.UP).texture("#line")
                        .tintindex(0).end().face(Direction.DOWN).texture("#line").tintindex(0).end().end();
        wireWest = models().getBuilder("block/infiniwire_west").customLoader(MultiLayerModelBuilder::begin)
                .submodel(RenderType.getCutout(), westBuilder).submodel(RenderType.getTranslucent(), slimeWest).end();
        dotModel = models().getBuilder("block/infiniwire_dot").customLoader(MultiLayerModelBuilder::begin)
                .submodel(RenderType.getCutout(), new BlockModelBuilder(NONE, models().existingFileHelper)
                        .parent(models().getExistingFile(new ResourceLocation(MoreWires.ID, "block/infiniwire_dot"))))
                .end();
        upModel = models().getBuilder("block/sticky_infiniwire_up").customLoader(MultiLayerModelBuilder::begin)
                .submodel(RenderType.getCutout(), new BlockModelBuilder(NONE, models().existingFileHelper)
                        .parent(models().getExistingFile(new ResourceLocation(MoreWires.ID, "block/infiniwire_up"))))
                .submodel(RenderType.getTranslucent(), slimeUp).end();
    }
}

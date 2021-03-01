package io.github.mrp_v2.morestickywires;

import io.github.mrp_v2.morestickywires.util.ObjectHolder;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(MoreStickyWires.ID) public class MoreStickyWires
{
    public static final String ID = "more" + "sticky" + "wires";

    public MoreStickyWires()
    {
        ObjectHolder.registerListeners(FMLJavaModLoadingContext.get().getModEventBus());
    }
}

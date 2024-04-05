package com.bainhero.ahjinmod;

import com.bainhero.ahjinmod.network.AhjinPacketHandler;
import com.bainhero.ahjinmod.network.EntityRegistry;
import com.bainhero.ahjinmod.network.ItemRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(AhjinMod.MODID)
public class AhjinMod
{
    public static final String MODID = "ahjinmod";

    public AhjinMod(){
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        
        ItemRegistry.register(modEventBus);
        EntityRegistry.register(modEventBus);
        
        modEventBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);

    }

    private void commonSetup(FMLCommonSetupEvent event){
    	AhjinPacketHandler.INSTANCE.init();
    }

}

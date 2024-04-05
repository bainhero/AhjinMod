package com.bainhero.ahjinmod.item;

import com.bainhero.ahjinmod.network.ItemRegistry;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class AhjinModCreativeModeTab 
{
	public static final CreativeModeTab AHJIN_TAB = new CreativeModeTab("ahjintab") {
		
		@Override
		public ItemStack makeIcon() {
			// TODO Auto-generated method stub
			return new ItemStack(ItemRegistry.MAGIC_CORE_E.get());
		}
	};
}

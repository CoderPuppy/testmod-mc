package cpup.mc.testMod

import cpup.mc.lib.CPupCommonProxy
import net.minecraftforge.common.MinecraftForge
import cpw.mods.fml.common.FMLCommonHandler

class CommonProxy extends CPupCommonProxy[TTestMod] {
	def mod = TestMod

	def registerRenderers {}
	def registerEvents {
		MinecraftForge.EVENT_BUS.register(new Events)
		FMLCommonHandler.instance.bus.register(new Events)
	}
}
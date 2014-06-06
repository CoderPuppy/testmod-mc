package cpup.mc.testMod.client

import cpup.mc.testMod.CommonProxy
import cpup.mc.testMod.content.personaltech
import net.minecraftforge.client.MinecraftForgeClient
import cpw.mods.fml.common.FMLCommonHandler
import net.minecraftforge.common.MinecraftForge

class ClientProxy extends CommonProxy {
	override def registerRenderers {
		MinecraftForgeClient.registerItemRenderer(mod.content.items("swordBow"), new SwordBowRenderer)
		MinecraftForgeClient.registerItemRenderer(mod.content.items("dagger"), new DaggerRenderer)
		println("register renderers")
		val personaltechClientEvents = new personaltech.ClientEvents()
		FMLCommonHandler.instance().bus().register(personaltechClientEvents)
		MinecraftForge.EVENT_BUS.register(personaltechClientEvents)
	}
}
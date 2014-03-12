package cpup.mc.testMod

import cpw.mods.fml.common.{SidedProxy, Mod}
import cpw.mods.fml.common.Mod.EventHandler
import cpw.mods.fml.common.event.{FMLPostInitializationEvent, FMLInitializationEvent, FMLPreInitializationEvent}
import cpup.mc.lib.CPupMod
import cpup.mc.testMod.content.Content

trait TTestMod extends CPupMod[TRef] {
	@SidedProxy(clientSide = "cpup.mc.testMod.client.ClientProxy", serverSide = "cpup.mc.testMod.CommonProxy")
	var proxy: CommonProxy = null
	def ref = Ref
	def content = Content

	@EventHandler
	override def preInit(e: FMLPreInitializationEvent) {
		super.preInit(e)
		proxy.registerRenderers
		logger.info("FMLPreInitializationEvent")
	}

	@EventHandler
	override def init(e: FMLInitializationEvent) {
		super.init(e)
		logger.info("FMLInitializationEvent")
	}

	@EventHandler
	override def postInit(e: FMLPostInitializationEvent) {
		super.postInit(e)
		logger.info("FMLPostInitializationEvent")
	}
}
@Mod(modid = Ref.modID, modLanguage = "scala", dependencies = "required-after:cpup-mc")
object TestMod extends TTestMod
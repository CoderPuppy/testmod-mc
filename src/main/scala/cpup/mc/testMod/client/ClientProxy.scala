package cpup.mc.testMod.client

import cpup.mc.testMod.CommonProxy
import net.minecraftforge.client.MinecraftForgeClient

class ClientProxy extends CommonProxy {
	override def registerRenderers {
		MinecraftForgeClient.registerItemRenderer(mod.content.items("swordBow"), new SwordBowRenderer)
	}
}
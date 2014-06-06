package cpup.mc.testMod.content.personaltech

import net.minecraftforge.client.event.RenderGameOverlayEvent
import net.minecraft.client.settings.KeyBinding
import org.lwjgl.input.{Mouse, Keyboard}
import cpw.mods.fml.client.registry.ClientRegistry
import net.minecraft.client.renderer.Tessellator
import cpw.mods.fml.common.gameevent.TickEvent
import net.minecraft.client.Minecraft
import cpw.mods.fml.common.eventhandler.SubscribeEvent

class ClientEvents {
	val selectPowerKeyBind = new KeyBinding("key.testmod:selectAirPower", Keyboard.KEY_V, "key.categories.testmod")
	ClientRegistry.registerKeyBinding(selectPowerKeyBind)

	@SubscribeEvent
	def renderOverlay(e: RenderGameOverlayEvent) {
		println(selectPowerKeyBind.isPressed, selectPowerKeyBind.getIsKeyPressed)
		if(selectPowerKeyBind.isPressed) {
			val tess = Tessellator.instance
			tess.setColorRGBA(255, 255, 255, 255)
			tess.startDrawingQuads
			val x = 0
			val y = 0
			tess.addVertex(x, y, 0)
			tess.addVertex(x, x + 50, 0)
			tess.addVertex(x + 50, y + 50, 0)
			tess.addVertex(x + 50, y, 0)
			tess.draw
		}
	}

	@SubscribeEvent
	def renderTick(e: TickEvent.RenderTickEvent) {
		val mc = Minecraft.getMinecraft
		if(e.phase == TickEvent.Phase.START) {
			if(selectPowerKeyBind.isPressed) {
				Mouse.getDX
				Mouse.getDY
				mc.mouseHelper.deltaX = 0
				mc.mouseHelper.deltaY = 0
//				mc.renderViewEntity
			}
		}
	}
}
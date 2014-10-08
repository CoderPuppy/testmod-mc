package cpup.mc.testMod.client

import net.minecraftforge.client.IItemRenderer
import net.minecraft.item.ItemStack
import net.minecraftforge.client.IItemRenderer.{ItemRendererHelper, ItemRenderType}
import cpup.mc.testMod.content.{ItemDagger, ItemSwordBow}
import net.minecraft.util.IIcon
import org.lwjgl.opengl.{GL12, GL11}
import net.minecraft.client.renderer.texture.{TextureManager, TextureUtil}
import net.minecraft.client.renderer.{ItemRenderer, OpenGlHelper, Tessellator}
import net.minecraft.client.Minecraft
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Items

class DaggerRenderer extends IItemRenderer {
	@Override
	def handleRenderType(stack: ItemStack, renderType: ItemRenderType) = if(stack.getItem.isInstanceOf[ItemDagger]) {
		renderType == ItemRenderType.EQUIPPED || renderType == ItemRenderType.EQUIPPED_FIRST_PERSON
	} else { false }

	@Override
	def shouldUseRenderHelper(renderType: ItemRenderType, stack: ItemStack, helper: ItemRendererHelper) = false

	@Override
	def renderItem(renderType: ItemRenderType, stack: ItemStack, data: Object*) {
		val texManager = Minecraft.getMinecraft.getTextureManager
		val item = stack.getItem.asInstanceOf[ItemDagger]

		GL11.glPushMatrix

		val icon = item.getIconIndex(stack)
		if(icon == null) {
			GL11.glPopMatrix
			return
		}

		texManager.bindTexture(texManager.getResourceLocation(stack.getItemSpriteNumber))
//		TextureUtil.func_147950_a(false, false)

		val tessellator: Tessellator = Tessellator.instance

		val minU: Float = icon.getMinU
		val maxU: Float = icon.getMaxU
		val minV: Float = icon.getMinV
		val maxV: Float = icon.getMaxV

		GL11.glEnable(GL12.GL_RESCALE_NORMAL)

		if(renderType == ItemRenderType.EQUIPPED) {
			GL11.glRotated(5, 1, 0, 0)
			GL11.glTranslated(0.6, -0.5, 0)
		}

		ItemRenderer.renderItemIn2D(tessellator, maxU, minV, minU, maxV, icon.getIconWidth, icon.getIconHeight, 0.0625F)

		GL11.glDisable(GL12.GL_RESCALE_NORMAL)
		texManager.bindTexture(texManager.getResourceLocation(stack.getItemSpriteNumber))
		TextureUtil.func_147945_b

		GL11.glPopMatrix
	}
}
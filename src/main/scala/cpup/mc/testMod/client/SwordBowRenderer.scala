package cpup.mc.testMod.client

import net.minecraftforge.client.IItemRenderer
import net.minecraft.item.ItemStack
import net.minecraftforge.client.IItemRenderer.{ItemRendererHelper, ItemRenderType}
import cpup.mc.testMod.content.ItemSwordBow
import net.minecraft.util.IIcon
import org.lwjgl.opengl.{GL12, GL11}
import net.minecraft.client.renderer.texture.{TextureManager, TextureUtil}
import net.minecraft.client.renderer.{ItemRenderer, OpenGlHelper, Tessellator}
import net.minecraft.client.Minecraft
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Items

class SwordBowRenderer extends IItemRenderer {
	@Override
	def handleRenderType(stack: ItemStack, renderType: ItemRenderType) = if(stack.getItem.isInstanceOf[ItemSwordBow]) {
		if(stack.getItem.asInstanceOf[ItemSwordBow].isBow(stack)) {
			renderType == ItemRenderType.EQUIPPED || renderType == ItemRenderType.EQUIPPED_FIRST_PERSON
		} else { false }
	} else { false }

	@Override
	def shouldUseRenderHelper(renderType: ItemRenderType, stack: ItemStack, helper: ItemRendererHelper) = false

	@Override
	def renderItem(renderType: ItemRenderType, stack: ItemStack, data: Object*) {
		val texManager = Minecraft.getMinecraft.getTextureManager
		val livingEntity = data(1).asInstanceOf[EntityLivingBase]

		var dur = 0
		if(livingEntity.isInstanceOf[EntityPlayer]) {
			val me = livingEntity.asInstanceOf[EntityPlayer]

			if(me.getItemInUse == stack) {
				dur = me.getItemInUseDuration
			}
		}

		GL11.glPushMatrix
		val icon = if(dur > 0) {
			Items.bow.getItemIconForUseDuration(if(dur >= 13) {
				2
			} else if(dur > 7) {
				1
			} else {
				0
			})
		} else {
			Items.bow.getIconIndex(stack)
		}
		if(icon == null) {
			GL11.glPopMatrix
			return
		}

		texManager.bindTexture(texManager.getResourceLocation(stack.getItemSpriteNumber))
		TextureUtil.func_147950_a(false, false)

		val tessellator: Tessellator = Tessellator.instance

		val minU: Float = icon.getMinU
		val maxU: Float = icon.getMaxU
		val minV: Float = icon.getMinV
		val maxV: Float = icon.getMaxV

		GL11.glEnable(GL12.GL_RESCALE_NORMAL)

		if(dur > 0) {
			GL11.glRotated(90, 1, 0, 0)
			GL11.glRotated(35, 0, 1, 0)
			GL11.glRotated(45, 0, 0, -1)
			GL11.glTranslated(0, 0, 0.1)
			//GL11.glTranslated(0, 0, 0)

			if(renderType == ItemRenderType.EQUIPPED_FIRST_PERSON) {
				GL11.glRotated(10, -1, 0, 0)
				GL11.glTranslated(0.3, 0, 0)
				GL11.glTranslated(0, -0.3, 0)
				GL11.glTranslated(0, 0, 0.15)
				GL11.glRotated(7, 0, 0, -1)
			}
		}

		ItemRenderer.renderItemIn2D(tessellator, maxU, minV, minU, maxV, icon.getIconWidth, icon.getIconHeight, 0.0625F)

		GL11.glDisable(GL12.GL_RESCALE_NORMAL)
		texManager.bindTexture(texManager.getResourceLocation(stack.getItemSpriteNumber))
		TextureUtil.func_147945_b

		GL11.glPopMatrix
	}
}
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

		GL11.glPushMatrix
		val icon = livingEntity.getItemIcon(stack, 0)
		if(icon == null) {
			GL11.glPopMatrix
			return
		}

		texManager.bindTexture(texManager.getResourceLocation(stack.getItemSpriteNumber))
		TextureUtil.func_147950_a(false, false)

		val tessellator: Tessellator = Tessellator.instance

		val f: Float = icon.getMinU
		val f1: Float = icon.getMaxU
		val f2: Float = icon.getMinV
		val f3: Float = icon.getMaxV
		val f4: Float = 0.0F
		val f5: Float = 0.3F

		GL11.glEnable(GL12.GL_RESCALE_NORMAL)
//		GL11.glTranslatef(-f4, -f5, 0.0F)
//		val f6: Float = 1.5F
//		GL11.glScalef(f6, f6, f6)
//		GL11.glRotatef(50.0F, 0.0F, 1.0F, 0.0F)
//		GL11.glRotatef(335.0F, 0.0F, 0.0F, 1.0F)
//		GL11.glTranslatef(-0.9375F, -0.0625F, 0.0F)

		ItemRenderer.renderItemIn2D(tessellator, f1, f2, f, f3, icon.getIconWidth, icon.getIconHeight, 0.0625F)

		GL11.glDisable(GL12.GL_RESCALE_NORMAL)
		texManager.bindTexture(texManager.getResourceLocation(stack.getItemSpriteNumber))
		TextureUtil.func_147945_b

		GL11.glPopMatrix
	}
}
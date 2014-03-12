package cpup.mc.testMod.content

import net.minecraft.item.{ItemStack, Item}
import net.minecraftforge.common.ISpecialArmor
import net.minecraft.entity.{Entity, EntityLivingBase}
import net.minecraft.util.DamageSource
import net.minecraft.entity.player.EntityPlayer
import net.minecraftforge.common.ISpecialArmor.ArmorProperties

class ItemCloak extends Item with TItemBase with ISpecialArmor {
	override def isValidArmor(stack: ItemStack, armorType: Int, entity: Entity) = armorType == 1

	def damageArmor(entity: EntityLivingBase, stack: ItemStack, source: DamageSource, dmg: Int, slot: Int) {}

	def getArmorDisplay(player: EntityPlayer, stack: ItemStack, slot: Int) = 4

	def getProperties(entity: EntityLivingBase, stack: ItemStack, source: DamageSource, dmg: Double, slot: Int) = new ArmorProperties(0, 0.2, Int.MaxValue)

	override def getArmorTexture(stack: ItemStack, entity: Entity, slot: Int, layer: String) = mod.ref.modID + ":textures/armors/" + iconString + "." + layer + ".png"
}
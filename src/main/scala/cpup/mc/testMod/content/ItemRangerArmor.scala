package cpup.mc.testMod.content

import net.minecraft.item.{ItemStack, Item}
import net.minecraftforge.common.ISpecialArmor
import net.minecraft.entity.{Entity, EntityLivingBase}
import net.minecraft.util.DamageSource
import net.minecraft.entity.player.EntityPlayer
import net.minecraftforge.common.ISpecialArmor.ArmorProperties
import net.minecraft.item.ItemArmor.ArmorMaterial

class ItemRangerArmor(val armorType: Int) extends Item with TItemBase with ISpecialArmor {
	override def isValidArmor(stack: ItemStack, armorType: Int, entity: Entity) = armorType == this.armorType

	def damageArmor(entity: EntityLivingBase, stack: ItemStack, source: DamageSource, dmg: Int, slot: Int) {}
	def getArmorDisplay(player: EntityPlayer, stack: ItemStack, slot: Int) = ArmorMaterial.CHAIN.getDamageReductionAmount(armorType)
	def getProperties(entity: EntityLivingBase, stack: ItemStack, source: DamageSource, dmg: Double, slot: Int) = {
		new ArmorProperties(0, ArmorMaterial.CHAIN.getDamageReductionAmount(armorType), Int.MaxValue)
	}

	override def getArmorTexture(stack: ItemStack, entity: Entity, slot: Int, layer: String) = {
		mod.ref.modID + ":textures/armors/ranger." + name + "." + layer + ".png"
	}
}
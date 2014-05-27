package cpup.mc.testMod.content

import net.minecraft.item.{ItemArmor, ItemStack, Item}
import net.minecraftforge.common.ISpecialArmor
import net.minecraft.entity.{Entity, EntityLivingBase}
import net.minecraft.util.DamageSource
import net.minecraft.entity.player.EntityPlayer
import net.minecraftforge.common.ISpecialArmor.ArmorProperties
import net.minecraft.item.ItemArmor.ArmorMaterial
import net.minecraft.potion.{Potion, PotionEffect}

class ItemRangerArmor(armorType: Int) extends ItemArmor(ArmorMaterial.CHAIN, 0, armorType) with TItemBase with ISpecialArmor {
	override def isValidArmor(stack: ItemStack, armorType: Int, entity: Entity) = armorType == this.armorType

	// overall this is 50% reduction
	// so 2 hearts from an iron sword
	val reductionAmounts = List(
		0.05, // cowl helps a bit, but it's light cloth
		0.25, // cloak has hardened leather
		0.20, // leggings are good
		0.00  // boots don't help except for falling
	)

	def damageArmor(entity: EntityLivingBase, stack: ItemStack, source: DamageSource, dmg: Int, slot: Int) {
		if(armorType != 3 || source = DamageSource.fall) {
			// give the wearer slowness and weakness (stunning)
			// 40 ticks is 2 seconds
			entity.addPotionEffect(new PotionEffect(Potion.moveSlowdown.getId, 40, 3))
			entity.addPotionEffect(new PotionEffect(Potion.weakness.getId, 40, 3))
		}
	}
	def getArmorDisplay(player: EntityPlayer, stack: ItemStack, slot: Int) = 4
	def getProperties(entity: EntityLivingBase, stack: ItemStack, source: DamageSource, dmg: Double, slot: Int) = {
		if(armorType == 3 && source == DamageSource.fall) {
			new ArmorProperties(0, 2, Int.MaxValue)
		} else {
			// this is supposed to be hardened leather armor (with padded boots)
			// so it should be able to handle an iron sword and mostly break
			// 6 is how much damage an iron sword does + 1 for the base punch
			new ArmorProperties(0, reductionAmounts(armorType), 7)
		}
	}

	override def getItemEnchantability = 30

	override def getArmorTexture(stack: ItemStack, entity: Entity, slot: Int, layer: String) = {
		mod.logger.debug("ranger armor: {} - {}", slot, layer)
		s"${mod.ref.modID}:textures/armors/ranger.${if(slot == 2) 2 else 1}.${if(layer == null) "" else s".$layer"}.png"
	}
}
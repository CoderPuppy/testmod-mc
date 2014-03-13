package cpup.mc.testMod.content

import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.init.Items
import net.minecraft.item.{ItemSword, Item, EnumAction, ItemStack}
import net.minecraft.world.World
import net.minecraft.entity.player.EntityPlayer
import net.minecraftforge.event.entity.player.{ArrowLooseEvent, ArrowNockEvent}
import net.minecraftforge.common.MinecraftForge
import net.minecraft.enchantment.{Enchantment, EnchantmentHelper}
import net.minecraft.entity.projectile.EntityArrow
import com.google.common.collect.{HashMultimap, Multimap}
import net.minecraft.entity.{Entity, EntityLivingBase, SharedMonsterAttributes}
import net.minecraft.entity.ai.attributes.AttributeModifier
import cpup.mc.lib.util.ItemUtil
import cpup.lib.Util
import cpw.mods.fml.relauncher.{SideOnly, Side}
import net.minecraft.util.IIcon

class ItemSwordBow extends ItemSword(Item.ToolMaterial.IRON) with TItemBase {
	setMaxDamage(600)

	//begin Item\\
	override def isDamaged(stack: ItemStack) = getDisplayDamage(stack) > 0

	override def getItemEnchantability = 22

	override def getDisplayDamage(stack: ItemStack) = {
		Util.checkNull(ItemUtil.compound(stack).getInteger("damage"), (dmg: Int) => dmg, 0)
	}

	protected def damageItem(stack: ItemStack, me: EntityLivingBase) {
		if(me.isInstanceOf[EntityPlayer] && me.asInstanceOf[EntityPlayer].capabilities.isCreativeMode) {
			return
		}

		if(me.getRNG.nextInt(4) > EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack)) {
			val compound = ItemUtil.compound(stack)
			val damage = Util.checkNull(compound.getInteger("damage"), (dmg: Int) => dmg, 0)
			compound.setInteger("damage", damage + 1)

			if(damage >= getMaxDamage(stack)) {
				me.renderBrokenItemStack(stack)
				stack.stackSize -= 1

				if(me.isInstanceOf[EntityPlayer]) {
					val player = me.asInstanceOf[EntityPlayer]

					if(stack.stackSize == 0) {
						player.destroyCurrentEquippedItem
					}
				}

				if(stack.stackSize < 0) {
					stack.stackSize = 0
				}

				stack.setItemDamage(0)
			}
		}
	}
//	override def getDisplayDamage(stack: ItemStack) = getDamage(stack)
	//end Item\\

	//begin Switching\\
	def isBow(stack: ItemStack) = stack.getItemDamage == 1
	def setIsBow(stack: ItemStack, isBow: Boolean) {
		stack.setItemDamage(if(isBow) { 1 } else { 0 })
	}

	override def registerIcons(register: IIconRegister) {}
	override def getIconFromDamage(dmg: Int) = dmg match {
		case 1 => Items.bow.getIconFromDamage(0)
		case _ => Items.golden_sword.getIconFromDamage(0)
	}

	override def getUnlocalizedName(stack: ItemStack) = super.getUnlocalizedName(stack) + (if(isBow(stack)) {
		".bow"
	} else {
		".sword"
	})

	override def getItemUseAction(stack: ItemStack) = if(isBow(stack)) {
		EnumAction.bow
	} else {
		EnumAction.block
	}

	override def getMaxItemUseDuration(stack: ItemStack) = Items.bow.getMaxItemUseDuration(stack)

	override def onItemRightClick(stack: ItemStack, world: World, player: EntityPlayer): ItemStack = {
		val newStack = stack.copy

//		println("sneaking", player.isSneaking)
//		println("isBow", isBow(stack))

		if(player.isSneaking) {
			setIsBow(newStack, !isBow(stack))
//			println("isBow", isBow(newStack))
		} else {
			if(isBow(stack)) {
//				println("onItemRightClick", player)
				val event = new ArrowNockEvent(player, stack)

				MinecraftForge.EVENT_BUS.post(event)

				if(event.isCanceled) {
					return event.result
				}

				if(player.capabilities.isCreativeMode || player.inventory.hasItem(Items.arrow)) {
					player.setItemInUse(newStack, getMaxItemUseDuration(stack))
				}
			} else {
				player.setItemInUse(newStack, getMaxItemUseDuration(stack))
			}
		}

		newStack
	}
	//end Switching\\

	//begin Sword\\
	override def hitEntity(stack: ItemStack, hitEntity: EntityLivingBase, me: EntityLivingBase) = {
		damageItem(stack, me)

		true
	}

	override def getItemAttributeModifiers = {
		val attributes = HashMultimap.create().asInstanceOf[Multimap[String, AttributeModifier]]
		attributes.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName, new AttributeModifier(Item.field_111210_e, "Weapon modifier", 12.0, 0))
		attributes
	}
	//end Sword\\

	//begin Bow\\
	def drawSpeed = 3.0

	override def onEaten(stack: ItemStack, world: World, player: EntityPlayer) = stack
	override def onPlayerStoppedUsing(stack: ItemStack, world: World, player: EntityPlayer, oppDur: Int) {
		if(isBow(stack)) {
			val dur = this.getMaxItemUseDuration(stack) - oppDur

			val event = new ArrowLooseEvent(player, stack, dur)
			MinecraftForge.EVENT_BUS.post(event)
			if(event.isCanceled) {
				return
			}
//			println(dur, event.charge, oppDur, getMaxItemUseDuration(stack))
			//		val charge = event.charge.asInstanceOf[Double] * 2 + 2
			val charge = event.charge.asInstanceOf[Double] * drawSpeed

			val flag = player.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, stack) > 0
			if(flag || player.inventory.hasItem(Items.arrow)) {
				var f: Float = charge.asInstanceOf[Float] / 20.0F
				f = (f * f + f * 2.0F) / 3.0F
				if(f.asInstanceOf[Double] < 0.1D) {
					return
				}
				if(f > 1.0F) {
					f = 1.0F
				}
				val arrow = new EntityArrow(world, player, f * 2.0F)

				arrow.setDamage(charge / 12)

				if(f == 1.0F) {
					arrow.setIsCritical(true)
				}
				var power = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, stack)

				if(charge >= 7) {
					power += 2
				}

				if(power > 0) {
					arrow.setDamage(arrow.getDamage + power.asInstanceOf[Double] * 0.5D + 0.5D)
				}

				var punch = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, stack)

				if(charge >= 7) {
					punch += 2
				}

				if(punch > 0) {
					arrow.setKnockbackStrength(punch)
				}

				if(EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, stack) > 0) {
					arrow.setFire(100)
				}

				damageItem(stack, player)

				world.playSoundAtEntity(player, "random.bow", 1.0F, 1.0F / (Item.itemRand.nextFloat * 0.4F + 1.2F) + f * 0.5F)
				if(flag) {
					arrow.canBePickedUp = 2
				}
				else {
					player.inventory.consumeInventoryItem(Items.arrow)
				}
				if(!world.isRemote) {
					world.spawnEntityInWorld(arrow)
				}
			}
		}
	}

	@SideOnly(Side.CLIENT)
	def getItemIconForUseDuration(par1: Int) = Items.bow.getItemIconForUseDuration(par1)
	//end Bow\\
}

package cpup.mc.testMod.content

import cpup.mc.lib.content.CPupContent
import cpup.mc.testMod.{TestMod, TTestMod}
import cpw.mods.fml.common.event.FMLPreInitializationEvent
import net.minecraft.creativetab.CreativeTabs
import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.item.ItemStack
import net.minecraft.init.Items

object Content extends CPupContent[TTestMod] {
	def mod = TestMod

	override def preInit(e: FMLPreInitializationEvent) {
		registerItem(new ItemDagger().setName("dagger").setCreativeTab(CreativeTabs.tabCombat).asInstanceOf[TItemBase])

		registerItem(new ItemSwordBow().setName("swordBow").setCreativeTab(CreativeTabs.tabCombat).asInstanceOf[TItemBase])
		GameRegistry.addShapelessRecipe(new ItemStack(items("swordBow")), Items.golden_sword, Items.bow)
	}
}
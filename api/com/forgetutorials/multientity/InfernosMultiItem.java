package com.forgetutorials.multientity;

import java.util.List;

import com.forgetutorials.lib.registry.ForgeTutorialsRegistry;
import com.forgetutorials.lib.registry.InfernosRegisteryProxyEntity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class InfernosMultiItem extends ItemBlock {

	public InfernosMultiItem(int par1) {
		super(par1);
		setHasSubtypes(true);
		setMaxDamage(0);
		this.maxStackSize = 64;
	}

	@Override
	public String getUnlocalizedName(ItemStack itemStack) {
		return super.getUnlocalizedName() + ".multi." + ((InfernosMultiItem) (itemStack.getItem())).getUnlocalizedEntityName(itemStack);

	}

	@Override
	public String getItemDisplayName(ItemStack itemStack) {
		return itemStack.getTagCompound().toString();
	}

	private String getUnlocalizedEntityName(ItemStack itemStack) {
		return "" + itemStack.getItemDamage();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamage(int par1) {
		return Block.wood.getIcon(0, par1);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIconIndex(ItemStack itemStack) {
		return ((InfernosMultiItem) (itemStack.getItem())).getMultiIcon(itemStack);
	}

	private Icon getMultiIcon(ItemStack itemStack) {
		return Block.blocksList[itemStack.stackSize].getIcon(0, 0);
	}

	public String getProxyEntity(ItemStack itemStack) {
		return itemStack.getTagCompound() != null ? (itemStack.getTagCompound().getString("MES")) : null;
	}

	@Override
	public int getDamage(ItemStack itemStack) {
		InfernosMultiEntityType type = InfernosRegisteryProxyEntity.INSTANCE.getType(((InfernosMultiItem) (itemStack.getItem())).getProxyEntity(itemStack));
		return type != null ? type.ordinal() : -1;
	}

	@Override
	public int getMetadata(int damageValue) {
		return damageValue;
	}

	@Override
	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8,
			float par9, float par10) {
		int i1 = par3World.getBlockId(par4, par5, par6);

		if ((i1 == Block.snow.blockID) && ((par3World.getBlockMetadata(par4, par5, par6) & 7) < 1)) {
			par7 = 1;
		} else if ((i1 != Block.vine.blockID) && (i1 != Block.tallGrass.blockID) && (i1 != Block.deadBush.blockID)
				&& ((Block.blocksList[i1] == null) || !Block.blocksList[i1].isBlockReplaceable(par3World, par4, par5, par6))) {
			if (par7 == 0) {
				--par5;
			}

			if (par7 == 1) {
				++par5;
			}

			if (par7 == 2) {
				--par6;
			}

			if (par7 == 3) {
				++par6;
			}

			if (par7 == 4) {
				--par4;
			}

			if (par7 == 5) {
				++par4;
			}
		}

		if (par1ItemStack.stackSize == 0) {
			return false;
		} else if (!par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack)) {
			return false;
		} else if ((par5 == 255) && Block.blocksList[getBlockID()].blockMaterial.isSolid()) {
			return false;
		} else if (par3World.canPlaceEntityOnSide(getBlockID(), par4, par5, par6, false, par7, par2EntityPlayer, par1ItemStack)) {
			Block block = Block.blocksList[getBlockID()];
			int j1 = getMetadata(par1ItemStack.getItemDamage());
			int k1 = Block.blocksList[getBlockID()].onBlockPlaced(par3World, par4, par5, par6, par7, par8, par9, par10, j1);

			if (placeBlockAt(par1ItemStack, par2EntityPlayer, par3World, par4, par5, par6, par7, par8, par9, par10, k1)) {
				par3World.playSoundEffect(par4 + 0.5F, par5 + 0.5F, par6 + 0.5F, block.stepSound.getPlaceSound(), (block.stepSound.getVolume() + 1.0F) / 2.0F,
						block.stepSound.getPitch() * 0.8F);
				--par1ItemStack.stackSize;
				InfernosMultiEntity entity = (InfernosMultiEntity) par3World.getBlockTileEntity(par4, par5, par6);
				entity.onBlockPlaced(par7,par8,par9,par10,k1);
			}

			return true;
		} else {
			return false;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean isFull3D() {
		return true;
	}
	
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@SideOnly(Side.CLIENT)
	public void getSubItems(int id, CreativeTabs creativeTab, List list) {
		ForgeTutorialsRegistry.INSTANCE.getSubItems(id, creativeTab, list);
	}
	
	@Override
	public CreativeTabs[] getCreativeTabs() {
		return ForgeTutorialsRegistry.INSTANCE.getCreativeTabs();
	}
}

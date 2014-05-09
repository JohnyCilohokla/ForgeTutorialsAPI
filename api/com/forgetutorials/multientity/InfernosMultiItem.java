package com.forgetutorials.multientity;

import com.forgetutorials.lib.registry.InfernosRegisteryProxyEntity;
import com.forgetutorials.multientity.base.InfernosProxyEntityBase;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

abstract public class InfernosMultiItem extends ItemBlock {

	public InfernosMultiItem(Block block) {
		super(block);
		setHasSubtypes(true);
		setMaxDamage(0);
		this.maxStackSize = 64;
	}

	@Override
	public String getUnlocalizedName(ItemStack itemStack) {
		return super.getUnlocalizedName() + ".multi." + ((InfernosMultiItem) (itemStack.getItem())).getUnlocalizedEntityName(itemStack);

	}
	@Override
    public String getItemStackDisplayName(ItemStack itemStack)
    {
		if (((itemStack == null) || !itemStack.hasTagCompound())){
			return "MES(Internal/Error)";
		}
		InfernosProxyEntityBase entity = InfernosRegisteryProxyEntity.INSTANCE.getStaticMultiEntity(((InfernosMultiItem) (itemStack.getItem()))
				.getProxyEntity(itemStack));
		return ((entity != null)) ? (entity.getItemStackDisplayName(itemStack)) : "MES(Internal/Error)";
    }
	/*@Override
	public String getItemDisplayName(ItemStack itemStack) {
		return ((itemStack != null) && itemStack.hasTagCompound()) ? (itemStack.getUnlocalizedName() + " " + itemStack.getTagCompound().toString())
				: "MES(Internal)";
	}*/

	private String getUnlocalizedEntityName(ItemStack itemStack) {
		return "" + getProxyEntity(itemStack);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int par1) {
		return Blocks.wool.getIcon(0, par1);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconIndex(ItemStack itemStack) {
		return ((InfernosMultiItem) (itemStack.getItem())).getMultiIcon(itemStack);
	}

	private IIcon getMultiIcon(ItemStack itemStack) {
		return Blocks.beacon.getIcon(0, 0);
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
        Block block = par3World.getBlock(par4, par5, par6);

        if (block == Blocks.snow_layer && (par3World.getBlockMetadata(par4, par5, par6) & 7) < 1)
        {
            par7 = 1;
        }
        else if (block != Blocks.vine && block != Blocks.tallgrass && block != Blocks.deadbush && !block.isReplaceable(par3World, par4, par5, par6))
        {
            if (par7 == 0)
            {
                --par5;
            }

            if (par7 == 1)
            {
                ++par5;
            }

            if (par7 == 2)
            {
                --par6;
            }

            if (par7 == 3)
            {
                ++par6;
            }

            if (par7 == 4)
            {
                --par4;
            }

            if (par7 == 5)
            {
                ++par4;
            }
        }

		if (par1ItemStack.stackSize == 0) {
			return false;
		} else if (!par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack)) {
			return false;
		} else if ((par5 == 255) && block.getMaterial().isSolid()) {
			return false;
		} else if (par3World.canPlaceEntityOnSide(block, par4, par5, par6, false, par7, par2EntityPlayer, par1ItemStack)) {

			InfernosMultiEntityStatic entity = placeBlock(par1ItemStack, par2EntityPlayer, par3World, par4, par5, par6, par7, par8, par9, par10);
			if (entity != null){
				entity.getProxyEntity().readFromNBT(par1ItemStack.getTagCompound());
			}

			return true;
		} else {
			return false;
		}
	}

	abstract public InfernosMultiEntityStatic placeBlock(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7,
			float par8, float par9, float par10);

	@Override
	@SideOnly(Side.CLIENT)
	public boolean isFull3D() {
		return true;
	}
}

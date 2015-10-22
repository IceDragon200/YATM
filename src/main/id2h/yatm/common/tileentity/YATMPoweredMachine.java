package id2h.yatm.common.tileentity;

import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.EntityPlayer;

public abstract class YATMPoweredMachine extends YATMPoweredTile implements ISidedInventory
{
	public int getSizeInventory()
	{
		return 0;
	}

	public ItemStack getStackInSlot(int index)
	{
		return null;
	}

	public ItemStack decrStackSize(int index, int amount)
	{
		return null;
	}

	public ItemStack getStackInSlotOnClosing(int index)
	{
		return null;
	}

	public void setInventorySlotContents(int index, ItemStack stack)
	{

	}

	public boolean hasCustomInventoryName()
	{
		return false;
	}

	public String getInventoryName()
	{
		return "null";
	}

	public boolean isInventoryNameLocalized()
	{
		return false;
	}

	public int getInventoryStackLimit()
	{
		return 64;
	}

	public void onInventoryChanged()
	{

	}

	public boolean isUseableByPlayer(EntityPlayer player)
	{
		return true;
	}

	public void openInventory() {}

	public void closeInventory() {}

	public boolean isItemValidForSlot(int index, ItemStack stack)
	{
		return true;
	}

	public int[] getAccessibleSlotsFromSide(int side)
	{
		return new int[] {0};
	}

	public boolean canInsertItem(int index, ItemStack stack, int side)
	{
		return false;
	}

	public boolean canExtractItem(int index, ItemStack stack, int side)
	{
		return false;
	}

	public int powerRequiredToWork()
	{
		return 0;
	}

	public boolean canWork()
	{
		return false;
	}

	public boolean doWork()
	{
		return true;
	}

	public void updateMachine()
	{
		if (canWork())
		{
			final int powerReq = powerRequiredToWork();
			if (energyStorage.getEnergyStored() >= powerReq)
			{
				if (doWork())
				{
					energyStorage.extractEnergy(powerReq, false);
				}
			}
		}
	}

	@Override
	public void updateEntity()
	{
		super.updateEntity();
		updateMachine();
	}
}

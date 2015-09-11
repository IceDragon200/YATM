package id2h.yatm.tileentity

import java.util.ArrayList;
import java.util.List;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;

import appeng.api.AEApi;
import appeng.api.features.IGrinderEntry;
import appeng.api.util.WorldCoord;
import appeng.tile.AEBaseInvTile;
import appeng.tile.inventory.AppEngInternalInventory;
import appeng.tile.inventory.InvOperation;
import appeng.util.InventoryAdaptor;
import appeng.util.Platform;
import appeng.util.inv.WrapperInventoryRange;

class TileEntityAutoGrinder extends AEBaseInvTile {

  val inputs = Array(0, 1, 2)
  val sides = Array(0, 1, 2, 3, 4, 5)
  val inv = new AppEngInternalInventory(this, 7)
  var points: Int = _

  override def setOrientation(inForward: ForgeDirection, inUp: ForgeDirection) {
    super.setOrientation(inForward, inUp)
    this.getBlockType.onNeighborBlockChange(this.worldObj, this.xCoord, this.yCoord, this.zCoord, Platform.AIR)
  }

  override def getInternalInventory(): IInventory = this.inv

  override def onChangeInventory(inv: IInventory, slot: Int, mc: InvOperation, removed: ItemStack, added: ItemStack) {
  }

  override def canInsertItem(slotIndex: Int, insertingItem: ItemStack, side: Int): Boolean = {
    if (AEApi.instance().registries().grinder().getRecipeForInput(insertingItem) == null) {
      return false
    }
    slotIndex >= 0 && slotIndex <= 2
  }

  override def canExtractItem(slotIndex: Int, extractedItem: ItemStack, side: Int): Boolean = slotIndex >= 3 && slotIndex <= 5

  override def getAccessibleSlotsBySide(side: ForgeDirection): Array[Int] = this.sides

  def canTurn(): Boolean = {
    if (Platform.isClient) {
      return false
    }
    if (null == this.getStackInSlot(6)) {
      val src = new WrapperInventoryRange(this, this.inputs, true)
      for (x <- 0 until src.getSizeInventory) {
        var item = src.getStackInSlot(x)
        if (item == null) {
          //continue
        }
        val r = AEApi.instance().registries().grinder().getRecipeForInput(item)
        if (r != null) {
          if (item.stackSize >= r.getInput.stackSize) {
            item.stackSize -= r.getInput.stackSize
            val ais = item.copy()
            ais.stackSize = r.getInput.stackSize
            if (item.stackSize <= 0) {
              item = null
            }
            src.setInventorySlotContents(x, item)
            this.setInventorySlotContents(6, ais)
            return true
          }
        }
      }
      return false
    }
    true
  }

  def applyTurn() {
    if (Platform.isClient) {
      return
    }
    this.points += 1
    val processing = this.getStackInSlot(6)
    val r = AEApi.instance().registries().grinder().getRecipeForInput(processing)
    if (r != null) {
      if (r.getEnergyCost > this.points) {
        return
      }
      this.points = 0
      val sia = InventoryAdaptor.getAdaptor(new WrapperInventoryRange(this, 3, 3, true), ForgeDirection.EAST)
      this.addItem(sia, r.getOutput)
      var chance = (Platform.getRandomInt % 2000) / 2000.0f
      if (chance <= r.getOptionalChance) {
        this.addItem(sia, r.getOptionalOutput)
      }
      chance = (Platform.getRandomInt % 2000) / 2000.0f
      if (chance <= r.getSecondOptionalChance) {
        this.addItem(sia, r.getSecondOptionalOutput)
      }
      this.setInventorySlotContents(6, null)
    }
  }

  private def addItem(sia: InventoryAdaptor, output: ItemStack) {
    if (output == null) {
      return
    }
    val notAdded = sia.addItems(output)
    if (notAdded != null) {
      val wc = new WorldCoord(this.xCoord, this.yCoord, this.zCoord)
      wc.add(this.getForward, 1)
      val out = new ArrayList[ItemStack]()
      out.add(notAdded)
      Platform.spawnDrops(this.worldObj, wc.x, wc.y, wc.z, out)
    }
  }
}

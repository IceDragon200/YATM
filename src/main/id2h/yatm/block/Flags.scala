package id2h.yatm.block
/*
 * Constant class for Block flags
 */
object Flags {
	// Cause the block to update
	val BLOCK_UPDATE: Int = 1
	// Send change to clients
	val SEND_TO_CLIENT: Int = 2
	// Stop the block from re-rendering
	val SUPRESS_RENDER: Int = 4

	val UPDATE_CLIENT: Int = BLOCK_UPDATE | SEND_TO_CLIENT
	val ALL: Int = UPDATE_CLIENT | SUPRESS_RENDER
}

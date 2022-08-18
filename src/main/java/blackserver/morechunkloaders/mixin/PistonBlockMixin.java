package blackserver.morechunkloaders.mixin;

import blackserver.morechunkloaders.ExtraTickets;
import net.minecraft.block.BlockState;
import net.minecraft.block.FacingBlock;
import net.minecraft.block.PistonBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PistonBlock.class)
public class PistonBlockMixin
{
    @Inject(at = @At("TAIL"), method = "onSyncedBlockEvent")
    private void addTicket(BlockState state, World world, BlockPos pos, int type, int data, CallbackInfoReturnable<Boolean> cir)
    {
        if (world instanceof ServerWorld)
        {
            Direction direction = state.get(FacingBlock.FACING);
            int x = pos.getX() + direction.getOffsetX();
            int z = pos.getZ() + direction.getOffsetZ();
            ChunkPos chunkPos = new ChunkPos((int) Math.floor(x / 16), (int) Math.floor(z / 16));
            ((ServerWorld)world).getChunkManager().addTicket(ExtraTickets.PISTON, chunkPos, 1, chunkPos);
        }
    }
}
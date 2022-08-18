package blackserver.morechunkloaders.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import blackserver.morechunkloaders.ExtraTickets;

import static net.minecraft.block.HopperBlock.ENABLED;
import static net.minecraft.block.HopperBlock.FACING;

@Mixin(HopperBlockEntity.class)
public abstract class HopperBlockEntityMixin extends BlockEntity
{
    public HopperBlockEntityMixin(BlockEntityType<?> type)
    {
        super(type);
    }

    @Inject(at = @At("HEAD"), method = "tick")
    private void addTicket(CallbackInfo ci)
    {
        BlockState state = this.world.getBlockState(pos);
        if(state.get(ENABLED) && world instanceof ServerWorld)
        {
            Direction dir = state.get(FACING);
            int x = pos.getX() + dir.getOffsetX();
            int z = pos.getZ() + dir.getOffsetZ();
            ChunkPos selfPos = new ChunkPos((int) Math.floor(pos.getX() / 16), (int) Math.floor(pos.getZ() / 16));
            ChunkPos facingPos = new ChunkPos((int) Math.floor(x / 16), (int) Math.floor(z / 16));
            if (!selfPos.equals(facingPos))
                ((ServerWorld)world).getChunkManager().addTicket(ExtraTickets.HOPPER, facingPos, 2,facingPos);
        }
    }
}
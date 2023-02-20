package morechunkloaders.mixin;

import morechunkloaders.ExtraTickets;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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
            int y = pos.getY() + dir.getOffsetY();
            int z = pos.getZ() + dir.getOffsetZ();
            BlockPos facingBlockPos = new BlockPos(x, y, z);
            BlockState facingBlock = this.world.getBlockState(facingBlockPos);
            ChunkPos selfPos = new ChunkPos(pos.getX() >> 4, pos.getZ() >> 4);
            ChunkPos facingPos = new ChunkPos(x >> 4, z >> 4);
            if (!selfPos.equals(facingPos) && facingBlock.isAir())
                ((ServerWorld)world).getChunkManager().addTicket(ExtraTickets.HOPPER, facingPos, 2,facingPos);
        }
    }
}
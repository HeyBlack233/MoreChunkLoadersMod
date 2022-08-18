package blackserver.morechunkloaders;

import net.minecraft.server.world.ChunkTicketType;
import net.minecraft.util.math.ChunkPos;

import java.util.Comparator;

public class ExtraTickets
{
    public static final ChunkTicketType<ChunkPos> PISTON = ChunkTicketType.create("piston", Comparator.comparingLong(ChunkPos::toLong), 4);
    public static final ChunkTicketType<ChunkPos> HOPPER = ChunkTicketType.create("hopper", Comparator.comparingLong(ChunkPos::toLong), 1);
}
package dmr.DragonMounts.types.habitats;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;

public record FluidHabitat(float multiplier, TagKey<Fluid> fluidType) implements Habitat
{
	public static final Codec<FluidHabitat> CODEC = RecordCodecBuilder.create(instance -> instance.group(Habitat.withMultiplier(0.5f, FluidHabitat::multiplier), TagKey.codec(Registries.FLUID).fieldOf("fluid_tag").forGetter(FluidHabitat::fluidType)).apply(instance, FluidHabitat::new));
	
	@Override
	public int getHabitatPoints(Level level, BlockPos pos)
	{
		return (int)(BlockPos.betweenClosedStream(pos.offset(1, 1, 1), pos.offset(-1, -1, -1)).filter(p -> level.getFluidState(p).is(fluidType)).count() * multiplier);
	}
	
	@Override
	public String type()
	{
		return Habitat.IN_FLUID;
	}
}

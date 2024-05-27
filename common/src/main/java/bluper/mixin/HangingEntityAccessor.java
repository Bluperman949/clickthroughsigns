package bluper.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.decoration.HangingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(HangingEntity.class)
public interface HangingEntityAccessor {
  @Accessor
  Direction getDirection();

  @Accessor
  BlockPos getPos();
}

package bluper.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ChestBlockEntity.class)
public interface ChestBlockEntityInvoker {
  @Invoker("playSound")
  static void playSound(Level level, BlockPos pos, BlockState state, SoundEvent sound) {
    throw new AssertionError();
  }
}

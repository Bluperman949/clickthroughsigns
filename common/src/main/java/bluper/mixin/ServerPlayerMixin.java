package bluper.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.OptionalInt;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin {
  @Shadow
  public abstract OptionalInt openMenu(@Nullable MenuProvider menu);

  private boolean invokeIsCrouching() {
    // in its own method because Idea thinks this is always false
    return ((ServerPlayer) (Object) this).isCrouching();
  }

  @Inject(at = @At("HEAD"), method = "openTextEdit", cancellable = true)
  private void openTextEdit(SignBlockEntity signEntity, boolean isFrontText, CallbackInfo ci) {
    if (invokeIsCrouching()) return;
    BlockState signBlockState = signEntity.getBlockState();
    if (signBlockState.getBlock() instanceof WallSignBlock) {
      Level level = signEntity.getLevel();
      BlockPos behindPos = signEntity.getBlockPos()
          .offset(signBlockState.getValue(BlockStateProperties.HORIZONTAL_FACING).getOpposite().getNormal());
      if (level.getBlockEntity(behindPos) instanceof MenuProvider menuProvider) {
        this.openMenu(menuProvider);
        ci.cancel();
      }
    }
  }
}

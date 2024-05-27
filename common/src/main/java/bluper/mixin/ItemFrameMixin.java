package bluper.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemFrame.class)
public abstract class ItemFrameMixin {

  @Shadow
  public abstract ItemStack getItem();

  @Inject(at = @At("HEAD"), method = "interact", cancellable = true)
  public void interact(Player player, InteractionHand hand, CallbackInfoReturnable ci) {
    HangingEntityAccessor accessor = (HangingEntityAccessor) this;
    BlockPos behindPos = accessor.getPos().offset(accessor.getDirection().getOpposite().getNormal());
    BlockEntity behindBlockEntity = player.level().getBlockEntity(behindPos);
    if (behindBlockEntity == null || this.getItem().isEmpty() || player.isCrouching()) return;
    if (behindBlockEntity instanceof MenuProvider menuProvider) {
      player.openMenu(menuProvider);
      ci.setReturnValue(InteractionResult.SUCCESS);
    }
  }
}

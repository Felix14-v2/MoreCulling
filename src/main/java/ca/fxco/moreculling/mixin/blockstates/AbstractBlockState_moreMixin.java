package ca.fxco.moreculling.mixin.blockstates;

import ca.fxco.moreculling.api.block.MoreBlockCulling;
import ca.fxco.moreculling.patches.MoreStateCulling;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Optional;

@Mixin(AbstractBlock.AbstractBlockState.class)
public abstract class AbstractBlockState_moreMixin implements MoreStateCulling {
    @Shadow
    public abstract Block getBlock();

    @Shadow
    protected abstract BlockState asBlockState();

    @Override
    public boolean usesCustomShouldDrawFace() {
        return ((MoreBlockCulling)this.getBlock()).usesCustomShouldDrawFace(this.asBlockState());
    }

    @Override
    public Optional<Boolean> customShouldDrawFace(BlockView view, BlockState sideState, BlockPos thisPos, BlockPos sidePos, Direction side) {
        return ((MoreBlockCulling)this.getBlock()).customShouldDrawFace(view, this.asBlockState(), sideState, thisPos, sidePos, side);
    }
}

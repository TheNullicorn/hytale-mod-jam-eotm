package me.nullicorn.jam.eotm;

import com.hypixel.hytale.component.ArchetypeChunk;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.RemoveReason;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.component.dependency.Dependency;
import com.hypixel.hytale.component.dependency.Order;
import com.hypixel.hytale.component.dependency.SystemDependency;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
import com.hypixel.hytale.math.vector.Vector3d;
import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Set;

public class CogFollowSystem extends EntityTickingSystem<EntityStore> {
    private static final double COG_FOLLOW_SPEED = 2.0;

    @Nonnull
    @Override
    public Set<Dependency<EntityStore>> getDependencies() {
        return Collections.singleton(new SystemDependency<>(Order.AFTER, CognadoTravelSystem.class));
    }

    @Nullable
    @Override
    public Query<EntityStore> getQuery() {
        return Cog.getComponentType();
    }

    @Override
    public void tick(
        final float dt,
        final int index,
        @Nonnull final ArchetypeChunk<EntityStore> archetypeChunk,
        @Nonnull final Store<EntityStore> store,
        @Nonnull final CommandBuffer<EntityStore> commandBuffer
    ) {
        final Cog cog = archetypeChunk.getComponent(index, Cog.getComponentType());
        final TransformComponent cogTransform = archetypeChunk.getComponent(index, TransformComponent.getComponentType());
        assert cog != null;
        assert cogTransform != null;

        if (!cog.cognado.isValid()) {
            commandBuffer.removeEntity(archetypeChunk.getReferenceTo(index), RemoveReason.REMOVE);
            return;
        }

        final TransformComponent cognadoTransform = commandBuffer.getComponent(cog.cognado, TransformComponent.getComponentType());
        assert cognadoTransform != null;

        cogTransform.setPosition(
            Vector3d.lerp(
                cogTransform.getPosition(),                             // Current position
                cognadoTransform.getPosition().clone().add(cog.offset), // Desired position
                dt * cog.lerpFactor * COG_FOLLOW_SPEED
            )
        );
        cog.pitchTime += dt;
        cogTransform.getRotation().setPitch((float) (Math.sin(cog.pitchTime) * (24.0 / 180) * Math.PI));
    }
}

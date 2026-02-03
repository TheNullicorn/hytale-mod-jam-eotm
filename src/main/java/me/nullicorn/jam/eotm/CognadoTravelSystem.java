package me.nullicorn.jam.eotm;

import com.hypixel.hytale.component.ArchetypeChunk;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.component.dependency.Dependency;
import com.hypixel.hytale.component.dependency.Order;
import com.hypixel.hytale.component.dependency.SystemDependency;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Set;

public class CognadoTravelSystem extends EntityTickingSystem<EntityStore> {
    private static final double MOVE_SPEED = 0.15;

    private static final double DIRECTION_DELTA_MIN = (25.0 / 180.0) * Math.PI;
    private static final double DIRECTION_DELTA_MAX = (155.0 / 180.0) * Math.PI;

    private static final double DIRECTION_TIMER_MIN = 2.0f;
    private static final double DIRECTION_TIMER_MAX = 5.0f;

    @Nonnull
    @Override
    public Set<Dependency<EntityStore>> getDependencies() {
        return Collections.singleton(new SystemDependency<>(Order.AFTER, CognadoRefSystem.class));
    }

    @Nullable
    @Override
    public Query<EntityStore> getQuery() {
        return Cognado.getComponentType();
    }

    @Override
    public void tick(
        final float dt,
        final int index,
        @Nonnull final ArchetypeChunk<EntityStore> archetypeChunk,
        @Nonnull final Store<EntityStore> store,
        @Nonnull final CommandBuffer<EntityStore> commandBuffer
    ) {
        final Cognado cognado = archetypeChunk.getComponent(index, Cognado.getComponentType());
        final TransformComponent transform = archetypeChunk.getComponent(index, TransformComponent.getComponentType());
        assert cognado != null;
        assert transform != null;

        if (cognado.directionResetTimer >= 0) {
            cognado.directionResetTimer -= dt;
        } else {
            final float delta = (float) (DIRECTION_DELTA_MIN + (Math.random() * (DIRECTION_DELTA_MAX - DIRECTION_DELTA_MIN)));
            final float resetTimer = (float) (DIRECTION_TIMER_MIN + (Math.random() * (DIRECTION_TIMER_MAX - DIRECTION_TIMER_MIN)));
            cognado.direction = cognado.direction + delta * (Math.random() < 0.5 ? -1 : 1);
            cognado.directionResetTimer = resetTimer;
        }

        transform.setPosition(
            transform.getPosition().clone().add(
                Math.cos(cognado.direction) * MOVE_SPEED,
                0.0,
                Math.sin(cognado.direction) * MOVE_SPEED
            )
        );
    }
}

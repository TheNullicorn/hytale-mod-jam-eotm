package me.nullicorn.jam.eotm;

import com.hypixel.hytale.component.*;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.component.system.RefSystem;
import com.hypixel.hytale.math.vector.Vector3d;
import com.hypixel.hytale.math.vector.Vector3f;
import com.hypixel.hytale.server.core.asset.type.model.config.Model;
import com.hypixel.hytale.server.core.asset.type.model.config.ModelAsset;
import com.hypixel.hytale.server.core.modules.entity.component.EntityScaleComponent;
import com.hypixel.hytale.server.core.modules.entity.component.ModelComponent;
import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
import com.hypixel.hytale.server.core.modules.entity.tracker.NetworkId;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CognadoRefSystem extends RefSystem<EntityStore> {
    private static final int COGS_PER_COGNADO = 18;
    private static final float COG_SCALE_MULTIPLIER = 3.5f;

    @Nullable
    @Override
    public Query<EntityStore> getQuery() {
        return CognadoPlugin.get().getCognadoComponentType();
    }

    @Override
    public void onEntityAdded(
        @Nonnull final Ref<EntityStore> ref,
        @Nonnull final AddReason reason,
        @Nonnull final Store<EntityStore> store,
        @Nonnull final CommandBuffer<EntityStore> commandBuffer
    ) {
        final TransformComponent transform = commandBuffer.ensureAndGetComponent(ref, TransformComponent.getComponentType());
        final ModelAsset cogModelAsset = ModelAsset.getAssetMap().getAsset("Eotm_Cog");
        assert cogModelAsset != null;

        final Model cogModel = Model.createUnitScaleModel(cogModelAsset);

        double y = 0.5;
        double yInc = 0;

        for (int i = 0; i < COGS_PER_COGNADO; i++) {
            final Vector3d cogOffset = new Vector3d(0, y, 0);
            yInc += 0.35;
            y += 1.5 + yInc;

            final Holder<EntityStore> cog = store.getRegistry().newHolder();
            cog.addComponent(
                Cog.getComponentType(),
                new Cog(ref, 1.0 / (i + 1), cogOffset, i * 0.3f)
            );
            cog.addComponent(
                NetworkId.getComponentType(),
                new NetworkId(store.getExternalData().takeNextNetworkId())
            );
            cog.addComponent(
                TransformComponent.getComponentType(),
                new TransformComponent(transform.getPosition().clone().add(cogOffset), new Vector3f())
            );
            cog.addComponent(
                ModelComponent.getComponentType(),
                new ModelComponent(cogModel)
            );
            cog.addComponent(
                EntityScaleComponent.getComponentType(),
                new EntityScaleComponent((i * COG_SCALE_MULTIPLIER) + 3)
            );
            commandBuffer.addEntity(cog, reason);
        }
    }

    @Override
    public void onEntityRemove(
        @Nonnull final Ref<EntityStore> ref,
        @Nonnull final RemoveReason reason,
        @Nonnull final Store<EntityStore> store,
        @Nonnull final CommandBuffer<EntityStore> commandBuffer
    ) {
    }
}

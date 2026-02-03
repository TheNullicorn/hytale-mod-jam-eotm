package me.nullicorn.jam.eotm;

import com.hypixel.hytale.component.Component;
import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.math.vector.Vector3d;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

import javax.annotation.Nullable;

public class Cog implements Component<EntityStore> {
    public Ref<EntityStore> cognado;
    public double lerpFactor;
    public Vector3d offset;
    public float rotationOffset;
    public float pitchTime;

    public static ComponentType<EntityStore, Cog> getComponentType() {
        return CognadoPlugin.get().getCogComponentType();
    }

    public Cog() {
    }

    public Cog(
        final Ref<EntityStore> cognado,
        final double lerpFactor,
        final Vector3d offset,
        final float rotationOffset
    ) {
        this.cognado = cognado;
        this.lerpFactor = lerpFactor;
        this.offset = offset.clone();
        this.rotationOffset = rotationOffset;
        this.pitchTime = this.rotationOffset;
    }

    @Nullable
    @Override
    public Component<EntityStore> clone() {
        final Cog clone = new Cog();
        clone.cognado = this.cognado;
        clone.lerpFactor = this.lerpFactor;
        clone.offset = this.offset.clone();
        return clone;
    }
}

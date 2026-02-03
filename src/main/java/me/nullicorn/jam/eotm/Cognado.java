package me.nullicorn.jam.eotm;

import com.hypixel.hytale.component.Component;
import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

import javax.annotation.Nullable;

public class Cognado implements Component<EntityStore> {
    public float direction;
    public float directionResetTimer;

    public static ComponentType<EntityStore, Cognado> getComponentType() {
        return CognadoPlugin.get().getCognadoComponentType();
    }

    public Cognado() {
        this.direction = (float) (Math.random() * Math.TAU);
    }

    @Nullable
    @Override
    public Component<EntityStore> clone() {
        return new Cognado();
    }
}

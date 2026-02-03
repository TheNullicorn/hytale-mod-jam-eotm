package me.nullicorn.jam.eotm;

import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

import javax.annotation.Nonnull;

public final class CognadoPlugin extends JavaPlugin {
    private static CognadoPlugin instance;

    private ComponentType<EntityStore, Cognado> cognadoComponentType;
    private ComponentType<EntityStore, Cog> cogComponentType;

    public static CognadoPlugin get() {
        return instance;
    }

    public CognadoPlugin(@Nonnull final JavaPluginInit init) {
        super(init);
    }

    @Override
    protected void setup() {
        instance = this;

        this.cognadoComponentType = this.getEntityStoreRegistry().registerComponent(Cognado.class, Cognado::new);
        this.cogComponentType = this.getEntityStoreRegistry().registerComponent(Cog.class, Cog::new);

        this.getEntityStoreRegistry().registerSystem(new CognadoRefSystem());
        this.getEntityStoreRegistry().registerSystem(new CognadoTravelSystem());
        this.getEntityStoreRegistry().registerSystem(new CogFollowSystem());

        this.getCommandRegistry().registerCommand(new CognadoCommand());
    }

    public ComponentType<EntityStore, Cognado> getCognadoComponentType() {
        return this.cognadoComponentType;
    }

    public ComponentType<EntityStore, Cog> getCogComponentType() {
        return this.cogComponentType;
    }
}

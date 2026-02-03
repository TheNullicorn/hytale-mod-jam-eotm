package me.nullicorn.jam.eotm;

import com.hypixel.hytale.component.AddReason;
import com.hypixel.hytale.component.Holder;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.math.vector.Vector3f;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractWorldCommand;
import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
import com.hypixel.hytale.server.core.modules.entity.tracker.NetworkId;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

import javax.annotation.Nonnull;

public class CognadoCommand extends AbstractWorldCommand {
    public CognadoCommand() {
        super("cognado", "Creates a cognado at the sender's position");
    }

    @Override
    protected void execute(
        @Nonnull final CommandContext context,
        @Nonnull final World world,
        @Nonnull final Store<EntityStore> store
    ) {
        final Ref<EntityStore> player = context.senderAsPlayerRef();
        if (player == null) {
            context.sendMessage(Message.raw("Must be executed by a player"));
            return;
        }

        final TransformComponent playerTransform = store.getComponent(player, TransformComponent.getComponentType());
        if (playerTransform == null) {
            context.sendMessage(Message.raw("Player must have a TransformComponent"));
            return;
        }

        final Holder<EntityStore> cognado = store.getRegistry().newHolder();
        cognado.addComponent(
            Cognado.getComponentType(),
            new Cognado()
        );
        cognado.addComponent(
            TransformComponent.getComponentType(),
            new TransformComponent(playerTransform.getPosition(), new Vector3f())
        );
        cognado.addComponent(
            NetworkId.getComponentType(),
            new NetworkId(store.getExternalData().takeNextNetworkId())
        );

        store.addEntity(cognado, AddReason.SPAWN);
    }
}

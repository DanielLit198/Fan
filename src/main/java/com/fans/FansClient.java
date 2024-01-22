package com.fans;

import com.fans.client.S2C;
import com.fans.init.RendererInit;
import com.fans.init.SoundInit;
import net.fabricmc.api.ClientModInitializer;

public class FansClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        RendererInit.init();
        S2C.init();
        SoundInit.init();
    }
}

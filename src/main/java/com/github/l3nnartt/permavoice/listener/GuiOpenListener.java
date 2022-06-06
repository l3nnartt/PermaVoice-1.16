package com.github.l3nnartt.permavoice.listener;

import com.github.l3nnartt.permavoice.PermaVoice;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.events.client.gui.screen.ScreenOpenEvent;

public class GuiOpenListener {

  @Subscribe
  public void onGuiOpenEvent(ScreenOpenEvent event) {
    if (PermaVoice.getInstance().isInit()) {
      PermaVoice.getInstance().getPermaVoiceTickListener().setFieldTest(false);
    }
  }
}

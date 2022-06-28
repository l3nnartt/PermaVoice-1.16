package com.github.l3nnartt.permavoice.listener;

import com.github.l3nnartt.permavoice.PermaVoice;
import java.lang.reflect.Field;
import net.labymod.addon.AddonLoader;
import net.labymod.addons.voicechat.VoiceChat;
import net.labymod.api.LabyModAddon;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.events.client.TickEvent;
import net.labymod.main.LabyMod;
import net.labymod.utils.Keyboard;
import net.minecraft.client.Minecraft;

public class PermaVoiceTickListener {

  private Field fieldPress;
  private Field fieldTest;
  private boolean togglePressed;
  private boolean currentStatus;

  @Subscribe
  public void onTick(TickEvent event) {
    if (!PermaVoice.getInstance().isInit()) {
      System.out.println("Init");
      for (LabyModAddon addon : AddonLoader.getAddons()) {
        if (addon == null || addon.about == null || addon.about.name == null) {
          continue;
        }
        if (addon.about.name.equals("VoiceChat") && addon instanceof VoiceChat) {
          System.out.println("Found VoiceChat");
          PermaVoice.getInstance().setVoiceChat((VoiceChat) addon);
          PermaVoice.getInstance().setFound(true);
          try {
            fieldPress =
                PermaVoice.getInstance()
                    .getVoiceChat()
                    .getClass()
                    .getDeclaredField("pushToTalkPressed");
            fieldPress.setAccessible(true);
            fieldTest =
                PermaVoice.getInstance()
                    .getVoiceChat()
                    .getClass()
                    .getDeclaredField("testingMicrophone");
            fieldTest.setAccessible(true);
          } catch (Exception ex) {
            ex.printStackTrace();
          }
        }
      }
      PermaVoice.getInstance().setInit(true);
    }
    if (!PermaVoice.getInstance().isFound()) {
      return;
    }
    PermaVoice.getInstance().getVoiceChat().tick(event);
    if (PermaVoice.getInstance().getVoiceChat().getKeyPushToTalk() == -1
        || !PermaVoice.getInstance().isEnabled()) {
      return;
    }
    if (PermaVoice.getInstance().isActive()
        && !PermaVoice.getInstance().getVoiceChat().isPushToTalkPressed()) {
      setPressed(true);
    }

    if (PermaVoice.getInstance().getKey() == -1) {
      return;
    }
    if (Keyboard.isKeyDown(PermaVoice.getInstance().getKey())) {
      if (Minecraft.getInstance().currentScreen == null) {
        if (!this.togglePressed) {
          this.togglePressed = true;
          PermaVoice.getInstance().setActive(!PermaVoice.getInstance().getActive());
          setCurrentStatus(!isCurrentStatus());
          if (PermaVoice.getInstance().isChatMessages()) {
            LabyMod.getInstance()
                .getLabyModAPI()
                .displayMessageInChat(
                    "§ePermaVoice §8» §e" + (PermaVoice.getInstance().getActive() ? "§aON"
                        : "§cOFF"));
          }
        }
      }
    } else {
      this.togglePressed = false;
    }
  }

  public void setPressed(boolean mode) {
    try {
      System.out.println("enabled first " + fieldPress.getBoolean(PermaVoice.getInstance().getVoiceChat()));
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    }
    System.out.println("setPressed: " + mode);
    try {
      PermaVoice.getInstance().setActive(mode);
      fieldPress.set(PermaVoice.getInstance().getVoiceChat(), mode);
      System.out.println("press simulated");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public boolean isCurrentStatus() {
    return currentStatus;
  }

  public void setCurrentStatus(boolean currentStatus) {
    this.currentStatus = currentStatus;
  }

  public void setFieldTest(boolean mode) {
    try {
      fieldTest.set(PermaVoice.getInstance().getVoiceChat(), mode);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
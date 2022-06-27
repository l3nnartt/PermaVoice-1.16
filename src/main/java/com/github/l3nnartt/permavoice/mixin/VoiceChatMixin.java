package com.github.l3nnartt.permavoice.mixin;

import com.github.l3nnartt.permavoice.PermaVoice;
import net.labymod.addons.voicechat.VoiceChat;
import net.labymod.api.event.events.client.TickEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(VoiceChat.class)
public class VoiceChatMixin {

  @Inject(method = "tick", at = @At("TAIL"))
  public void onTick(TickEvent event, CallbackInfo ci) {
    PermaVoice.getInstance().getPermaVoiceTickListener().onTick();
  }

}
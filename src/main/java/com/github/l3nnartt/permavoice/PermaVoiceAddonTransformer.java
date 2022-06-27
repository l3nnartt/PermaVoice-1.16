package com.github.l3nnartt.permavoice;

import net.labymod.addon.AddonTransformer;
import net.labymod.api.TransformerType;

public class PermaVoiceAddonTransformer extends AddonTransformer {

  @Override
  public void registerTransformers() {
    this.registerTransformer(TransformerType.ALL, "permavoice.mixin.json");
  }
}
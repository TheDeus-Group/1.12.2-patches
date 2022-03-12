package me.allink.patches.mixin.text;

import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.IllegalFormatException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Mixin(TranslatableText.class)
public abstract class TranslatableTextMixin {
    @Shadow @Final public static Pattern ARG_FORMAT;

    @Shadow private List<Text> translations;

    @Shadow @Final private Object[] args;

    @Shadow protected abstract Text getArg(int index);

    @Inject(method = "setTranslation(Ljava/lang/String;)V", at = @At("HEAD"), cancellable = true)
    public void setTranslation(String translation, CallbackInfo ci) {
        ci.cancel();
        boolean bl = false;
        Matcher matcher = ARG_FORMAT.matcher(translation);
        int i = 0;
        int j = 0;

        try {
            int l;
            for(; matcher.find(j); j = l) {
                int k = matcher.start();
                l = matcher.end();
                if (k > j) {
                    TranslatableText thiz = ((TranslatableText) (Object) this);
                    LiteralText literalText = new LiteralText(String.format(translation.substring(j, k)));
                    literalText.getStyle().setParent(thiz.getStyle());
                    this.translations.add(literalText);
                }

                String string = matcher.group(2);
                String string2 = translation.substring(k, l);
                if ("%".equals(string) && "%%".equals(string2)) {
                    TranslatableText thiz = ((TranslatableText) (Object) this);
                    LiteralText literalText2 = new LiteralText("%");
                    literalText2.getStyle().setParent(thiz.getStyle());
                    this.translations.add(literalText2);
                } else {
                    if (!"s".equals(string)) {
                        return;
                    }

                    String string3 = matcher.group(1);
                    int m = string3 != null ? Integer.parseInt(string3) - 1 : i++;
                    if (m < this.args.length) {
                        this.translations.add(this.getArg(m));
                    }
                }
            }

            if (j < translation.length()) {
                TranslatableText thiz = ((TranslatableText) (Object) this);
                LiteralText literalText3 = new LiteralText(String.format(translation.substring(j)));
                literalText3.getStyle().setParent(thiz.getStyle());
                this.translations.add(literalText3);
            }

        } catch (IllegalFormatException ignored) {
        }
    }
}
/**
 * /give Player415 oak_sign{BlockEntityTag:{Text1:'{"translate":"translation.test.invalid"}'}}
 */
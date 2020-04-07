package TheGuardianChan.patches;

import TheGuardianChan.TheGuardianChan;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;
import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;
import guardian.characters.GuardianCharacter;


import java.lang.reflect.Field;


@SuppressWarnings("unused")
public class CharacterSelectScreenPatches
{
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(TheGuardianChan.makeID("ReSkin"));
    public static final String[] TEXT = uiStrings.TEXT;
    private static int TalentCount = 1;

    public static Hitbox TalentRight;
    public static Hitbox TalentLeft;
    private static float Talent_RIGHT_W = FontHelper.getSmartWidth(FontHelper.cardTitleFont, TEXT[1], 9999.0F, 0.0F);
    private static float Talent_LEFT_W = FontHelper.getSmartWidth(FontHelper.cardTitleFont, TEXT[1], 9999.0F, 0.0F);

    private static float X_fixed = 30.0f *Settings.scale;

    public static Field charInfoField;

    private static Texture GuardianOriginal = new Texture(TheGuardianChan.assetPath("/img/GuardianMod/portrait.png"));
    private static Texture GuardianChan = new Texture(TheGuardianChan.assetPath("/img/GuardianMod/portrait_waifu.png"));


    @SpirePatch(clz = CharacterSelectScreen.class, method = "initialize")
    public static class CharacterSelectScreenPatch_Initialize
    {
        @SpirePostfixPatch
        public static void Initialize(CharacterSelectScreen __instance)
        {
            // Called when you first open the screen, create hitbox for each button
            TalentRight = new Hitbox(Talent_RIGHT_W - 10.0F * Settings.scale + X_fixed, 50.0F * Settings.scale);
            TalentLeft = new Hitbox(Talent_RIGHT_W - 10.0F * Settings.scale + X_fixed, 50.0F * Settings.scale);
            TalentRight.move(Settings.WIDTH / 2.0F - Talent_RIGHT_W / 2.0F - 550.0F * Settings.scale + 16.0f*Settings.scale + X_fixed, 800.0F * Settings.scale);
            TalentLeft.move(Settings.WIDTH / 2.0F - Talent_LEFT_W / 2.0F - 800.0F * Settings.scale + 16.0f*Settings.scale + X_fixed, 800.0F * Settings.scale);


        }
    }

    @SpirePatch(clz = CharacterSelectScreen.class, method = "render")
    public static class CharacterSelectScreenPatch_Render
    {
        @SpirePostfixPatch
        public static void Postfix(CharacterSelectScreen __instance, SpriteBatch sb)
        {
            // Render your buttons/images by passing SpriteBatch
            if (!(TalentCount == 1 ||TalentCount == 2 ))
            {TalentCount = 1;}

            for (CharacterOption o : __instance.options) {
                if (o.name.equals(CardCrawlGame.languagePack.getUIString(TheGuardianChan.makeID("Name")).TEXT[0]) && o.selected) {

                    TalentRight.render(sb);
                    TalentLeft.render(sb);

                    if (TalentRight.hovered || Settings.isControllerMode) {sb.setColor(Color.WHITE);} else {sb.setColor(Color.LIGHT_GRAY);}
                    sb.draw(ImageMaster.CF_RIGHT_ARROW, Settings.WIDTH / 2.0F - Talent_RIGHT_W / 2.0F - 550.0F * Settings.scale + X_fixed, 800.0F * Settings.scale - 16.0F, 16.0F, 16.0F, 32.0F, 32.0F, Settings.scale*2.0f, Settings.scale*2.0f, 0.0F, 0, 0, 48, 48, false, false);
                    if (TalentLeft.hovered || Settings.isControllerMode) {sb.setColor(Color.WHITE);} else {sb.setColor(Color.LIGHT_GRAY);}
                    sb.draw(ImageMaster.CF_LEFT_ARROW, Settings.WIDTH / 2.0F - Talent_LEFT_W / 2.0F - 800.0F * Settings.scale + X_fixed, 800.0F * Settings.scale - 16.0F, 16.0F, 16.0F, 32.0F, 32.0F, Settings.scale*2.0f, Settings.scale*2.0f, 0.0F, 0, 0, 48, 48, false, false);

                    FontHelper.cardTitleFont.getData().setScale(1.0F);
                    FontHelper.bannerFont.getData().setScale(0.8F);
                    FontHelper.renderFontCentered(sb, FontHelper.bannerFont, TEXT[0], Settings.WIDTH / 2.0F - 680.0F * Settings.scale, 850.0F * Settings.scale , Settings.GOLD_COLOR);

                    FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, TEXT[TalentCount], Settings.WIDTH / 2.0F - 680.0F * Settings.scale, 800.0F * Settings.scale , Settings.GOLD_COLOR);


            }
        }
    }

    }


    @SpirePatch(clz = CharacterSelectScreen.class, method = "update")
    public static class CharacterSelectScreenPatch_Update
    {
        @SpirePostfixPatch
        public static void Postfix(CharacterSelectScreen __instance)
        {
            // Update your buttons position, check if the player clicked them, and do something if they did
            for (CharacterOption o : __instance.options) {
                if (o.name.equals(CardCrawlGame.languagePack.getUIString(TheGuardianChan.makeID("Name")).TEXT[0]) && o.selected) {


                    if (InputHelper.justClickedLeft && TalentLeft.hovered) {
                        TalentLeft.clickStarted = true;
                        CardCrawlGame.sound.play("UI_CLICK_1");
                    }
                    if (InputHelper.justClickedLeft && TalentRight.hovered) {
                        TalentRight.clickStarted = true;
                        CardCrawlGame.sound.play("UI_CLICK_1");
                    }

                    if (TalentLeft.justHovered || TalentRight.justHovered) {
                        CardCrawlGame.sound.playV("UI_HOVER", 0.75f);
                    }
//==================================
                    if (!(TalentCount == 1 ||TalentCount == 2 ))
                    {TalentCount = 1;}

                    if(TheGuardianChan.GuardianOriginalAnimation ){
                        if(TalentCount != 1){
                            TalentCount = 1;
                            __instance.bgCharImg = updateBgImg();
                        }
                    }else {
                        if(TalentCount != 2){
                            TalentCount = 2;
                            __instance.bgCharImg = updateBgImg();
                        }
                    }
                    if(TalentCount == 1 && TheGuardianChan.GuardianOriginalAnimation){
                        if(__instance.bgCharImg != GuardianOriginal ){
                            __instance.bgCharImg = GuardianOriginal;
                        }
                    }
                    if(TalentCount == 2 && (!TheGuardianChan.GuardianOriginalAnimation)){
                        if(__instance.bgCharImg != GuardianChan ){
                            __instance.bgCharImg = GuardianChan;
                        }
                    }




                        if(TalentRight.clicked || CInputActionSet.pageRightViewExhaust.isJustPressed()){
                            TalentRight.clicked = false;
                            if (TalentCount < 2) {
                                TalentCount += 1;
                            } else {
                                TalentCount = 1;
                            }
                            __instance.bgCharImg = updateBgImg();
                        }

                        if(TalentLeft.clicked || CInputActionSet.pageRightViewExhaust.isJustPressed()){
                            TalentLeft.clicked = false;
                            if (TalentCount > 1) {
                                TalentCount -= 1;
                            } else {
                                TalentCount = 2;
                            }
                            __instance.bgCharImg = updateBgImg();
                        }


                    TalentLeft.update();
                    TalentRight.update();
                }}
        }
    }


    public static Texture updateBgImg(){
        switch (TalentCount ){
            case 1:
                TheGuardianChan.GuardianOriginalAnimation = true;
                TheGuardianChan.saveSettings();
                return GuardianOriginal;
            case 2:
                TheGuardianChan.GuardianOriginalAnimation = false;
                TheGuardianChan.saveSettings();
                return GuardianChan;
            default:
                return GuardianOriginal;
        }
    }
}
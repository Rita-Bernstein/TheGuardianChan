package TheGuardianChan.patches;

import TheGuardianChan.TheGuardianChan;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.esotericsoftware.spine.*;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
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

import static TheGuardianChan.TheGuardianChan.disablePortraitAnimation;
import static com.megacrit.cardcrawl.core.AbstractCreature.sr;


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

    private static Texture GuardianOriginal =  ImageMaster.loadImage(TheGuardianChan.assetPath("/img/GuardianMod/portrait.png"));
    private static Texture GuardianChan =  ImageMaster.loadImage(TheGuardianChan.assetPath("/img/GuardianMod/portrait_waifu.png"));
    private static Texture GuardianChan2 =  ImageMaster.loadImage(TheGuardianChan.assetPath("/img/GuardianMod/portrait_waifu2.png"));

    private static Texture SlimeOriginal =  ImageMaster.loadImage(TheGuardianChan.assetPath("/img/Slimebound/portrait.png"));
    private static Texture SlaifuTexture =  ImageMaster.loadImage(TheGuardianChan.assetPath("/img/Slimebound/portrait_waifu.png"));

    private static Texture  portraitFix =  ImageMaster.loadImage(TheGuardianChan.assetPath("/img/GuardianMod/portrait_waifu_fix.png"));

    private static TextureAtlas portraitAtlas = null ;
    private static Skeleton portraitSkeleton;
    private static AnimationState portraitState ;
    private static AnimationStateData portraitStateData;
    private static SkeletonData portraitData;

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
//载入动态立绘
    private static void loadPortraitAnimation() {
        portraitAtlas = new TextureAtlas(Gdx.files.internal("TheGuardianChan/img/GuardianMod/animation/GuardianChan_portrait.atlas"));
        SkeletonJson json = new SkeletonJson(portraitAtlas);
        json.setScale(Settings.scale / 1.0F);
        portraitData = json.readSkeletonData(Gdx.files.internal("TheGuardianChan/img/GuardianMod/animation/GuardianChan_portrait.json"));


        portraitSkeleton = new Skeleton(portraitData);
        portraitSkeleton.setColor(Color.WHITE);
        portraitStateData = new AnimationStateData(portraitData);
        portraitState = new AnimationState(portraitStateData);
        portraitStateData.setDefaultMix(0.2F);

        portraitState.setTimeScale(0.5f);
        portraitState.setAnimation(1, "fade_in", false);
        portraitState.addAnimation(0, "idle", true,0.0f);
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
                for(int i = 0; i <= 1; i++){


                if (o.name.equals(CardCrawlGame.languagePack.getUIString(TheGuardianChan.makeID("Name")).TEXT[i]) && o.selected) {

                    TalentRight.render(sb);
                    TalentLeft.render(sb);

                    if (TalentRight.hovered || Settings.isControllerMode) {sb.setColor(Color.WHITE);} else {sb.setColor(Color.LIGHT_GRAY);}
                    sb.draw(ImageMaster.CF_RIGHT_ARROW, Settings.WIDTH / 2.0F - Talent_RIGHT_W / 2.0F - 550.0F * Settings.scale + X_fixed, 800.0F * Settings.scale - 16.0F, 16.0F, 16.0F, 32.0F, 32.0F, Settings.scale*2.0f, Settings.scale*2.0f, 0.0F, 0, 0, 48, 48, false, false);
                    if (TalentLeft.hovered || Settings.isControllerMode) {sb.setColor(Color.WHITE);} else {sb.setColor(Color.LIGHT_GRAY);}
                    sb.draw(ImageMaster.CF_LEFT_ARROW, Settings.WIDTH / 2.0F - Talent_LEFT_W / 2.0F - 800.0F * Settings.scale + X_fixed, 800.0F * Settings.scale - 16.0F, 16.0F, 16.0F, 32.0F, 32.0F, Settings.scale*2.0f, Settings.scale*2.0f, 0.0F, 0, 0, 48, 48, false, false);

                    FontHelper.cardTitleFont.getData().setScale(1.0F);
                    FontHelper.bannerFont.getData().setScale(0.8F);
                    FontHelper.renderFontCentered(sb, FontHelper.bannerFont, TEXT[0], Settings.WIDTH / 2.0F - 680.0F * Settings.scale, 850.0F * Settings.scale , Settings.GOLD_COLOR);

                    FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, TEXT[TalentCount+ 2*i], Settings.WIDTH / 2.0F - 680.0F * Settings.scale, 800.0F * Settings.scale , Settings.GOLD_COLOR);

                }
            }
        }
    }

    }

    //                动态立绘
    @SpirePatch(clz = CharacterSelectScreen.class, method = "render")
    public static class CharacterSelectScreenPatch_portraitSkeleton
    {
        @SpireInsertPatch(rloc = 45)
        public static void Insert(CharacterSelectScreen __instance, SpriteBatch sb)
        {
            // Render your buttons/images by passing SpriteBatch
            if (!(TalentCount == 1 ||TalentCount == 2 ))
            {TalentCount = 1;}

            for (CharacterOption o : __instance.options) {
                for(int i = 0; i <= 1; i++){


                    if (o.name.equals(CardCrawlGame.languagePack.getUIString(TheGuardianChan.makeID("Name")).TEXT[0]) && o.selected && TalentCount == 2 && !disablePortraitAnimation) {

                        portraitState.update(Gdx.graphics.getDeltaTime());
                        portraitState.apply(portraitSkeleton);
                        portraitSkeleton.updateWorldTransform();
                        portraitSkeleton.setPosition(1092.0f * Settings.scale,Settings.HEIGHT- 1032.0f * Settings.scale);
                        portraitSkeleton.setColor(Color.WHITE);
                        portraitSkeleton.setFlip(false,false);

                    sb.end();
                    CardCrawlGame.psb.begin();
                    sr.draw(CardCrawlGame.psb, portraitSkeleton);
                    CardCrawlGame.psb.end();
                    sb.begin();


                        sb.draw(portraitFix, Settings.WIDTH / 2.0F - 960.0F, Settings.HEIGHT / 2.0F - 600.0F, 960.0F, 600.0F, 1920.0F, 1200.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 1920, 1200, false, false);


                    }
                }
            }
        }

    }

//    立绘动画重置

    @SpirePatch(clz = CharacterOption.class, method = "updateHitbox")

    public static class CharacterOptionPatch_reloadAnimation
    {
        @SpireInsertPatch(rloc = 56)
        public static void Insert(CharacterOption __instance)
        {
            if(__instance.name.equals(CardCrawlGame.languagePack.getUIString(TheGuardianChan.makeID("Name")).TEXT[0]) && !disablePortraitAnimation){
                loadPortraitAnimation();
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
                for(int i = 0; i <= 1; i++){
                if (o.name.equals(CardCrawlGame.languagePack.getUIString(TheGuardianChan.makeID("Name")).TEXT[i]) && o.selected) {


                    if (InputHelper.justClickedLeft && TalentLeft.hovered) {
                        TalentLeft.clickStarted = true;
                        CardCrawlGame.sound.play("UI_CLICK_1");
                        if(TalentCount == 1 && !disablePortraitAnimation){
                            loadPortraitAnimation();
                        }
                    }
                    if (InputHelper.justClickedLeft && TalentRight.hovered) {
                        TalentRight.clickStarted = true;
                        CardCrawlGame.sound.play("UI_CLICK_1");
                        if(TalentCount == 1 && !disablePortraitAnimation ){
                            loadPortraitAnimation();
                        }
                    }

                    if (TalentLeft.justHovered || TalentRight.justHovered) {
                        CardCrawlGame.sound.playV("UI_HOVER", 0.75f);
                    }
//==================================
                    if (!(TalentCount == 1 ||TalentCount == 2 ))
                    {TalentCount = 1;}




                    switch (i){
                        case 0 :
                            if(TheGuardianChan.GuardianOriginalAnimation ){
                                if(TalentCount != 1){
                                    TalentCount = 1;
                                    __instance.bgCharImg = updateBgImg(i);
                                }
                            }else {
                                if(TalentCount != 2){
                                    TalentCount = 2;
                                    __instance.bgCharImg = updateBgImg(i);
                                }
                            }
                            if(TalentCount == 1 && TheGuardianChan.GuardianOriginalAnimation){
                                if(__instance.bgCharImg != GuardianOriginal ){
                                    __instance.bgCharImg = GuardianOriginal;
                                }
                            }
                            if(TalentCount == 2 && (!TheGuardianChan.GuardianOriginalAnimation)){
                                if(disablePortraitAnimation){
                                    if(__instance.bgCharImg != GuardianChan ){
                                        __instance.bgCharImg = GuardianChan;
                                    }
                                }else {
                                    if(__instance.bgCharImg != GuardianChan2 ){
                                        __instance.bgCharImg = GuardianChan2;
                                    }
                                }

                            }
                            break;

                        case 1:
                            if(TheGuardianChan.SlimeOriginalAnimation ){
                                if(TalentCount != 1){
                                    TalentCount = 1;
                                    __instance.bgCharImg = updateBgImg(i);
                                }
                            }else {
                                if(TalentCount != 2){
                                    TalentCount = 2;
                                    __instance.bgCharImg = updateBgImg(i);
                                }
                            }
                            if(TalentCount == 1 && TheGuardianChan.SlimeOriginalAnimation){
                                if(__instance.bgCharImg != SlimeOriginal ){
                                    __instance.bgCharImg = SlimeOriginal;
                                }
                            }
                            if(TalentCount == 2 && (!TheGuardianChan.SlimeOriginalAnimation)){
                                if(__instance.bgCharImg != SlaifuTexture ){
                                    __instance.bgCharImg = SlaifuTexture;
                                }
                            }
                            break;
                        default:
                            break;
                    }

//==================================




                        if(TalentRight.clicked || CInputActionSet.pageRightViewExhaust.isJustPressed()){
                            TalentRight.clicked = false;
                            if (TalentCount < 2) {
                                TalentCount += 1;
                            } else {
                                TalentCount = 1;
                            }
                            __instance.bgCharImg = updateBgImg(i);
                        }

                        if(TalentLeft.clicked || CInputActionSet.pageRightViewExhaust.isJustPressed()){
                            TalentLeft.clicked = false;
                            if (TalentCount > 1) {
                                TalentCount -= 1;
                            } else {
                                TalentCount = 2;
                            }
                            __instance.bgCharImg = updateBgImg(i);
                        }


                    TalentLeft.update();
                    TalentRight.update();
                }
            }
            }
        }
    }


    public static Texture updateBgImg(int selectedCharCount){
        if(selectedCharCount == 0){
            switch (TalentCount ){
                case 1:
                    TheGuardianChan.GuardianOriginalAnimation = true;
                    TheGuardianChan.saveSettings();
                    return GuardianOriginal;
                case 2:
                    TheGuardianChan.GuardianOriginalAnimation = false;
                    TheGuardianChan.saveSettings();
                    if(disablePortraitAnimation){
                        return GuardianChan;
                    }else {
                        return GuardianChan2;
                    }

                default:
                    return GuardianOriginal;
            }
        }
        else if (selectedCharCount == 1){
            switch (TalentCount ){
                case 1:
                    TheGuardianChan.SlimeOriginalAnimation = true;
                    TheGuardianChan.saveSettings();
                    return SlimeOriginal;
                case 2:
                    TheGuardianChan.SlimeOriginalAnimation = false;
                    TheGuardianChan.saveSettings();
                    return SlaifuTexture;
                default:
                    return SlimeOriginal;
            }
        }else {
            return null;
        }

        }

}
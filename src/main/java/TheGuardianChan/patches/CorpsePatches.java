package TheGuardianChan.patches;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.rooms.CampfireUI;
import guardian.patches.GuardianEnum;

import static TheGuardianChan.TheGuardianChan.*;

public class CorpsePatches
{
    private static Texture guardianPatchShoulderImg = ImageMaster.loadImage("TheGuardianChan/img/GuardianMod/shoulder.png");
    private static Texture guardianPatchShoulderImg2 = ImageMaster.loadImage("TheGuardianChan/img/GuardianMod/shoulder2.png");
    private static Texture guardianOriginalShoulderImg = ImageMaster.loadImage("GuardianImages/char/shoulder.png");
    private static Texture guardianOriginalShoulderImg2 = ImageMaster.loadImage("GuardianImages/char/shoulder2.png");

    private static Texture slimePatchShoulderImg = ImageMaster.loadImage("TheGuardianChan/img/Slimebound/shoulder.png");
    private static Texture slimePatchShoulderImg2 = ImageMaster.loadImage("TheGuardianChan/img/Slimebound/shoulder2.png");
    private static Texture slimeOriginalShoulderImg = ImageMaster.loadImage("SlimeboundImages/char/shoulder.png");
    private static Texture slimeOriginalShoulderImg2 = ImageMaster.loadImage("SlimeboundImages/char/shoulder2.png");

    @SpirePatch(clz = AbstractPlayer.class,method = "playDeathAnimation")
    public static class CorpseImgPatch{
        @SpireInsertPatch(rloc = 1)
        public  static SpireReturn<Void> Insert(AbstractPlayer _instance){

            if(AbstractDungeon.player.getClass().getName().contains("GuardianCharacter")){
                if(!GuardianOriginalAnimation){
                    _instance.img = ImageMaster.loadImage("TheGuardianChan/img/GuardianMod/corpse.png");
                }else {
                    _instance.img = ImageMaster.loadImage("GuardianImages/char/corpse.png");
                }
            }
            if(AbstractDungeon.player.getClass().getName().contains("SlimeboundCharacter")){
                if(!SlimeOriginalAnimation){
                    _instance.img = ImageMaster.loadImage("TheGuardianChan/img/Slimebound/corpse.png");
                }else {
                    _instance.img = ImageMaster.loadImage("SlimeboundImages/char/corpse.png");
                }
            }

            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = AbstractPlayer.class,method = "renderShoulderImg")
    public static class ShoulderImgPatch{
        @SpirePrefixPatch
        public  static SpireReturn<Void> Prefix(AbstractPlayer _instance, SpriteBatch sb){
            if(AbstractDungeon.player.getClass().getName().contains("GuardianCharacter")){
                if(!GuardianOriginalAnimation){
                    if (CampfireUI.hidden) {
                        sb.draw(guardianPatchShoulderImg2, 0.0F, 0.0F, (float) Settings.WIDTH, 1136.0F * Settings.scale);
                    } else {
                        sb.draw(guardianPatchShoulderImg, _instance.animX, 0.0F, (float)Settings.WIDTH, 1136.0F * Settings.scale);
                    }
                }else {
                    if (CampfireUI.hidden) {
                        sb.draw(guardianOriginalShoulderImg2, 0.0F, 0.0F, (float) Settings.WIDTH, 1136.0F * Settings.scale);
                    } else {
                        sb.draw(guardianOriginalShoulderImg, _instance.animX, 0.0F, (float)Settings.WIDTH, 1136.0F * Settings.scale);
                    }
                }

                return SpireReturn.Return(null);
            }

            if(AbstractDungeon.player.getClass().getName().contains("SlimeboundCharacter")){
                if(!SlimeOriginalAnimation){
                    if (CampfireUI.hidden) {
                        sb.draw(slimePatchShoulderImg2, 0.0F, 0.0F, (float) Settings.WIDTH, 1136.0F * Settings.scale);
                    } else {
                        sb.draw(slimePatchShoulderImg, _instance.animX, 0.0F, (float)Settings.WIDTH, 1136.0F * Settings.scale);
                    }
                }else {
                    if (CampfireUI.hidden) {
                        sb.draw(slimeOriginalShoulderImg2, 0.0F, 0.0F, (float) Settings.WIDTH, 1136.0F * Settings.scale);
                    } else {
                        sb.draw(slimeOriginalShoulderImg, _instance.animX, 0.0F, (float)Settings.WIDTH, 1136.0F * Settings.scale);
                    }
                }

                return SpireReturn.Return(null);
            }

            return SpireReturn.Continue();
        }
    }
}

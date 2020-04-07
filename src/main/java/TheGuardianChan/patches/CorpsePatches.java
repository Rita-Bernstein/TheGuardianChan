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
    private static Texture patchShoulderImg = ImageMaster.loadImage("TheGuardianChan/img/GuardianMod/shoulder.png");
    private static Texture patchShoulderImg2 = ImageMaster.loadImage("TheGuardianChan/img/GuardianMod/shoulder2.png");
    private static Texture originalShoulderImg = ImageMaster.loadImage("GuardianImages/char/shoulder.png");
    private static Texture originalShoulderImg2 = ImageMaster.loadImage("GuardianImages/char/shoulder2.png");

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
                        sb.draw(patchShoulderImg2, 0.0F, 0.0F, (float) Settings.WIDTH, 1136.0F * Settings.scale);
                    } else {
                        sb.draw(patchShoulderImg, _instance.animX, 0.0F, (float)Settings.WIDTH, 1136.0F * Settings.scale);
                    }
                }else {
                    if (CampfireUI.hidden) {
                        sb.draw(originalShoulderImg2, 0.0F, 0.0F, (float) Settings.WIDTH, 1136.0F * Settings.scale);
                    } else {
                        sb.draw(originalShoulderImg, _instance.animX, 0.0F, (float)Settings.WIDTH, 1136.0F * Settings.scale);
                    }
                }

                return SpireReturn.Return(null);
            }

            return SpireReturn.Continue();
        }
    }
}

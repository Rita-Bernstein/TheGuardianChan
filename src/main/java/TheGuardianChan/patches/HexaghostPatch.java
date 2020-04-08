package TheGuardianChan.patches;

import TheGuardianChan.TheGuardianChan;
import basemod.ReflectionHacks;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.*;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.exordium.Hexaghost;
import com.megacrit.cardcrawl.monsters.exordium.HexaghostOrb;
import javassist.*;
import javassist.expr.ConstructorCall;
import javassist.expr.ExprEditor;
import com.badlogic.gdx.graphics.Color;
import org.lwjgl.Sys;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static com.megacrit.cardcrawl.core.AbstractCreature.sr;


@SpirePatch(
        clz = Hexaghost.class,
        method = SpirePatch.CONSTRUCTOR
)
public class HexaghostPatch {
//    protected static TextureAtlas ghostSurroundAtlas = null;
//    protected static Skeleton[] ghostSurroundSkeleton = new Skeleton[6];
//    public static AnimationState[] ghostSurroundState = new AnimationState[6];
//    protected static AnimationStateData[] ghostSurroundStateData = new AnimationStateData[6];
//    protected static SkeletonData[] ghostSurroundData = new SkeletonData[6];
      public static AbstractMonster hx = null;
			public static ExprEditor Instrument(){
				return new ExprEditor() {
					@Override
					public void edit(ConstructorCall e) throws CannotCompileException
					{
						if (e.getFileName().equals("Hexaghost.java") && e.getMethodName().equals("super")) {
							e.replace("{$8 = null; $proceed($$);}");
						}
					}
				};
			}
//--------------------------去掉原版贴图

    @SpirePatch(
            clz = Hexaghost.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class PatchConstructor {
        @SpireInsertPatch(rloc = 1)
        public static SpireReturn<Void> Insert(Hexaghost hexaghost) {
            try {
                Method method = AbstractCreature.class.getDeclaredMethod("loadAnimation", String.class, String.class, float.class);
                method.setAccessible(true);
                float scale;
                if(AbstractDungeon.getCurrRoom().getClass().getName().contains("FixedMonsterRoom"))
                {
                    scale = 2.0f;
                }else {
                    scale = 1.0f;
                }

                method.invoke(hexaghost, "TheGuardianChan/monsters/TheHexaghostKo/self/Hexaghost_self.atlas", "TheGuardianChan/monsters/TheHexaghostKo/self/Hexaghost_self.json", scale);

                loadGhostAnimation(hexaghost);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
            hexaghost.dialogY = 100.0F * Settings.scale;
            AnimationState.TrackEntry e = hexaghost.state.setAnimation(0, "disappear", true);
            e.setTime(e.getEndTime() * MathUtils.random());
            return SpireReturn.Continue();
        }
    }
//------------------------加入本体动画


    @SpirePatch(
            clz = Hexaghost.class,
            method = "changeState"
    )public static class PatchChangeState {
        @SpireInsertPatch(rloc = 8)
        public static SpireReturn<Void> Insert(Hexaghost hexaghost, String stateName) {
                try{
                    if(TheGuardianChan.hexaghostMask){
                        hexaghost.state.setAnimation(0, "idle2_fade_in", false);
                        hexaghost.state.addAnimation(0, "idle2", true, 0.2F);
                    }else {
                        hexaghost.state.setAnimation(0, "idle2_mask_fade_in", false);
                        hexaghost.state.addAnimation(0, "idle2_mask", true, 0.2F);
                    }
                }
                catch (Exception e) {
            e.printStackTrace();
        }
            return SpireReturn.Continue();
        }
		}
//-----------------------本体激活显现

    @SpirePatch(
            clz = Hexaghost.class,
            method = "render"
    )public static class PatchRender {
        @SpireInsertPatch(rloc = 1)
        public static SpireReturn<Void> Insert(Hexaghost hexaghost, SpriteBatch sb) {
            try{

                for (int i = 0; i < 6; i++) {
                    PatchHexaghostField.ghostSurroundState.get(hexaghost)[i].update(Gdx.graphics.getDeltaTime());
                    PatchHexaghostField.ghostSurroundState.get(hexaghost)[i].apply(PatchHexaghostField.ghostSurroundSkeleton.get(hexaghost)[i]);
                    PatchHexaghostField.ghostSurroundSkeleton.get(hexaghost)[i].updateWorldTransform();
                    PatchHexaghostField.ghostSurroundSkeleton.get(hexaghost)[i].setPosition(hexaghost.drawX + hexaghost.animX, hexaghost.drawY + hexaghost.animY);
                    PatchHexaghostField.ghostSurroundSkeleton.get(hexaghost)[i].setColor(hexaghost.tint.color);
                    PatchHexaghostField.ghostSurroundSkeleton.get(hexaghost)[i].setFlip(hexaghost.flipHorizontal, hexaghost.flipVertical);
                }
                sb.end();
                CardCrawlGame.psb.begin();
                for (int i = 0; i < 6; i++) {sr.draw(CardCrawlGame.psb, PatchHexaghostField.ghostSurroundSkeleton.get(hexaghost)[i]);}
                CardCrawlGame.psb.end();
                sb.begin();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return SpireReturn.Continue();
        }
    }
//----------------------鬼魂渲染

    @SpirePatch(
            clz = Hexaghost.class,
            method=SpirePatch.CLASS
    )public static class PatchHexaghostField {

        public static SpireField<TextureAtlas> ghostSurroundAtlas = new SpireField<>(() -> null);
        public static SpireField<Skeleton[]> ghostSurroundSkeleton = new SpireField<>(() -> new Skeleton[6]);
        public static SpireField<AnimationState[]> ghostSurroundState = new SpireField<>(() -> new AnimationState[6]);
        public static SpireField<AnimationStateData[]> ghostSurroundStateData = new SpireField<>(() -> new AnimationStateData[6]);
        public static SpireField<SkeletonData[]> ghostSurroundData = new SpireField<>(() -> new SkeletonData[6]);
    }
//----------------------本体类插入变量

    @SpirePatch(
            clz = HexaghostOrb.class,
            method=SpirePatch.CLASS
    )public static class PatchHexaghostOrbField {
        public static SpireField<Boolean> ghostIgnite = new SpireField<>(() -> false);
        public static SpireField<Boolean> ghostDeactivate = new SpireField<>(() -> false);
        public static SpireField<Integer> ghostIndex = new SpireField<>(() -> 0);
    }
//----------------------鬼火类插入变量


    /*
    @SpirePatch(
            clz = HexaghostOrb.class,
            method=SpirePatch.CONSTRUCTOR
    )
    public static class PatchHexaghostOrbConstructor {
        @SpirePostfixPatch
        public static void Postfix(HexaghostOrb hexaghostOrb,float x, float y, int index){
            PatchHexaghostOrbField.ghostIndex.set(hexaghostOrb,index);
        }
    }


    @SpirePatch(
            clz = HexaghostOrb.class,
            method = "activate"
    )public static class PatchHexaghostOrbActivate {
        @SpirePostfixPatch
        public static void Postfix(HexaghostOrb hexaghostOrb,float oX, float oY) {
            try{
                PatchHexaghostOrbField.ghostIgnite.set(hexaghostOrb,true);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @SpirePatch(
            clz = HexaghostOrb.class,
            method = "deactivate"
    )public static class PatchHexaghostOrbDeactivate {
        @SpirePostfixPatch
        public static void Postfix(HexaghostOrb hexaghostOrb) {
            try{
                PatchHexaghostOrbField.ghostDeactivate.set(hexaghostOrb,true);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
*/
//-----------------鬼火类的 鬼火数计数，激活，熄灭 修改

/*
    @SpirePatch(
            clz = HexaghostOrb.class,
            method = "update"
    )public static class PatchHexaghostOrbUpdateActivate {
        @SpireInsertPatch(rloc = 17)
        public static SpireReturn<Void> Insert(HexaghostOrb hexaghostOrb,float oX, float oY) {
            try{
                if(PatchHexaghostOrbField.ghostIgnite.get(hexaghostOrb)){
                    int i = PatchHexaghostOrbField.ghostIndex.get(hexaghostOrb);
                    System.out.println(i);
                    PatchHexaghostField.ghostSurroundState.get(hx)[i].setAnimation(0, "fade_in", false);
                    PatchHexaghostField.ghostSurroundState.get(hx)[i].addAnimation(1, "idle", true,5.0F);
                    PatchHexaghostOrbField.ghostIgnite.set(hexaghostOrb,false);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return SpireReturn.Continue();
        }
    }
*/
//--------------------------------鬼火的update

    @SpirePatch(
            clz = Hexaghost.class,
            method = "changeState"
    )public static class PatchHexaghostActivate {
        @SpireInsertPatch(rloc = 8)
        public static SpireReturn<Void> Insert(Hexaghost hexaghost,String stateName) {
            try{
                if(!TheGuardianChan.hexaghostSurroundDisplay){
                    for (int i = 0; i < 6; i++) {
                        System.out.println(i);
                        PatchHexaghostField.ghostSurroundState.get(hexaghost)[i].setAnimation(0, "disappear", false);
                        PatchHexaghostField.ghostSurroundState.get(hexaghost)[i].addAnimation(0, "fade_in", false, i*0.3F+0.2f);
                        PatchHexaghostField.ghostSurroundState.get(hexaghost)[i].addAnimation(0, "idle", true, 0.5F);
                    }
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return SpireReturn.Continue();
        }
    }
//    本体changestate 鬼魂同时激活


    @SpirePatch(
            clz = Hexaghost.class,
            method = "changeState"
    )public static class PatchHexaghostActivateOrb {
        @SpireInsertPatch(rloc = 20)
        public static SpireReturn<Void> Insert(Hexaghost hexaghost,String stateName) {
            try{

                Field threshold = Hexaghost.class.getDeclaredField("orbActiveCount");
                threshold.setAccessible(true);
                    int i = (int) threshold.get(hexaghost);
                if(!TheGuardianChan.hexaghostSurroundDisplay){
                    PatchHexaghostField.ghostSurroundState.get(hexaghost)[i].setAnimation(0, "fade_in", false);
                    PatchHexaghostField.ghostSurroundState.get(hexaghost)[i].addAnimation(0, "idle", true, 5.0F);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return SpireReturn.Continue();
        }
    }
//    本体changestate 鬼魂依次激活  ----2

    @SpirePatch(
            clz = Hexaghost.class,
            method = "changeState"
    )public static class PatchHexaghostOrbFadeOut {
        @SpireInsertPatch(rloc = 33)
        public static SpireReturn<Void> Insert(Hexaghost hexaghost,String stateName) {
            try{
                if(!TheGuardianChan.hexaghostSurroundDisplay){
                for (int i = 0; i < 6; i++) {
                    System.out.println(i);
                    PatchHexaghostField.ghostSurroundState.get(hexaghost)[i].setAnimation(0, "fade_out", false);
                    PatchHexaghostField.ghostSurroundState.get(hexaghost)[i].addAnimation(0, "disappear", true, 5.0F);
                }
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return SpireReturn.Continue();
        }
    }
//    本体changestate 鬼魂同时熄灭


    private static void loadGhostAnimation(AbstractMonster m) {
        TextureAtlas T = new TextureAtlas(Gdx.files.internal("TheGuardianChan/monsters/TheHexaghostKo/surround/Hexaghost_surround.atlas"));
        PatchHexaghostField.ghostSurroundAtlas.set(m,T);

        SkeletonJson json = new SkeletonJson(PatchHexaghostField.ghostSurroundAtlas.get(m));
        float scale;
        if(AbstractDungeon.getCurrRoom().getClass().getName().contains("paleoftheancients.rooms.FixedMonsterRoom"))
        {
            scale = 2.0f;
        }else {
            scale = 1.0f;
        }
        json.setScale(Settings.scale / scale);
//------------------------------------------------
        PatchHexaghostField.ghostSurroundData.get(m)[0] = json.readSkeletonData(Gdx.files.internal("TheGuardianChan/monsters/TheHexaghostKo/surround/Hexaghost_surround_7.json"));
        PatchHexaghostField.ghostSurroundData.get(m)[1] = json.readSkeletonData(Gdx.files.internal("TheGuardianChan/monsters/TheHexaghostKo/surround/Hexaghost_surround_9.json"));
        PatchHexaghostField.ghostSurroundData.get(m)[2] = json.readSkeletonData(Gdx.files.internal("TheGuardianChan/monsters/TheHexaghostKo/surround/Hexaghost_surround_6.json"));
        PatchHexaghostField.ghostSurroundData.get(m)[3] = json.readSkeletonData(Gdx.files.internal("TheGuardianChan/monsters/TheHexaghostKo/surround/Hexaghost_surround_3.json"));
        PatchHexaghostField.ghostSurroundData.get(m)[4] = json.readSkeletonData(Gdx.files.internal("TheGuardianChan/monsters/TheHexaghostKo/surround/Hexaghost_surround_1.json"));
        PatchHexaghostField.ghostSurroundData.get(m)[5] = json.readSkeletonData(Gdx.files.internal("TheGuardianChan/monsters/TheHexaghostKo/surround/Hexaghost_surround_4.json"));


        for (int i = 0; i < 6; i++){
            PatchHexaghostField.ghostSurroundSkeleton.get(m)[i] = new Skeleton(PatchHexaghostField.ghostSurroundData.get(m)[i]);
            PatchHexaghostField.ghostSurroundSkeleton.get(m)[i].setColor(Color.WHITE);
            PatchHexaghostField.ghostSurroundStateData.get(m)[i] = new AnimationStateData(PatchHexaghostField.ghostSurroundData.get(m)[i]);
            PatchHexaghostField.ghostSurroundState.get(m)[i] = new AnimationState(PatchHexaghostField.ghostSurroundStateData.get(m)[i]);
            PatchHexaghostField.ghostSurroundStateData.get(m)[i].setDefaultMix(0.2F);
            PatchHexaghostField.ghostSurroundState.get(m)[i].setAnimation(0, "disappear", true);
        }
    }
//    鬼魂-本体快捷载入函数
}



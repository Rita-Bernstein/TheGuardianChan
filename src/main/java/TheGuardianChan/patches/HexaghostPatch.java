package TheGuardianChan.patches;

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
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.exordium.Hexaghost;
import com.megacrit.cardcrawl.monsters.exordium.HexaghostOrb;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.expr.ConstructorCall;
import javassist.expr.ExprEditor;
import com.badlogic.gdx.graphics.Color;

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

			public static ExprEditor Instrument(){
				return new ExprEditor() {
					@Override
					public void edit(ConstructorCall e) throws CannotCompileException
					{
                        System.out.println("========================================================");
                        System.out.println(Hexaghost.class.getName());
                        System.out.println(e.getClassName());
                        System.out.println(e.getMethodName());
                        System.out.println(e.getFileName());
                        System.out.println(e.getSignature());
                        System.out.println("========================================================");
						if (e.getFileName().equals("Hexaghost.java") && e.getMethodName().equals("super")) {
							e.replace("{$8 = null; $proceed($$);}");
						}
					}
				};
			}


    @SpirePatch(
            clz = Hexaghost.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class PatchConstructor {
        @SpireInsertPatch(rloc = 1)
        public static SpireReturn<Void> Insert(Hexaghost Hexaghost) {
            try {
                Method method = AbstractCreature.class.getDeclaredMethod("loadAnimation", String.class, String.class, float.class);
                method.setAccessible(true);
                method.invoke(Hexaghost, "TheGuardianChan/monsters/TheHexaghostKo/self/Hexaghost_self.atlas", "TheGuardianChan/monsters/TheHexaghostKo/self/Hexaghost_self.json", 1.0F);

                loadGhostAnimation(Hexaghost);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
            Hexaghost.dialogY = 100.0F * Settings.scale;
            AnimationState.TrackEntry e = Hexaghost.state.setAnimation(0, "disappear", true);
            e.setTime(e.getEndTime() * MathUtils.random());
            return SpireReturn.Continue();
        }
    }



    @SpirePatch(
            clz = Hexaghost.class,
            method = "changeState"
    )public static class PatchChangeState {
        @SpireInsertPatch(rloc = 8)
        public static SpireReturn<Void> Insert(Hexaghost Hexaghost, String stateName) {
                try{
                    Hexaghost.state.setAnimation(0, "idle2_mask_fade_in", false);
                    Hexaghost.state.addAnimation(1, "idle2_mask", true, 5.0F);
                }
                catch (Exception e) {
            e.printStackTrace();
        }
            return SpireReturn.Continue();
        }
		}

    @SpirePatch(
            clz = Hexaghost.class,
            method = "render"
    )public static class PatchRender {
        @SpireInsertPatch(rloc = 1)
        public static SpireReturn<Void> Insert(Hexaghost Hexaghost, SpriteBatch sb) {
            try{
                for (int i = 0; i < 6; i++) {
                    PatchHexaghostField.ghostSurroundState.get(Hexaghost)[i].update(Gdx.graphics.getDeltaTime());
                    PatchHexaghostField.ghostSurroundState.get(Hexaghost)[i].apply(PatchHexaghostField.ghostSurroundSkeleton.get(Hexaghost)[i]);
                    PatchHexaghostField.ghostSurroundSkeleton.get(Hexaghost)[i].updateWorldTransform();
                    PatchHexaghostField.ghostSurroundSkeleton.get(Hexaghost)[i].setPosition(Hexaghost.drawX + Hexaghost.animX, Hexaghost.drawY + Hexaghost.animY);
                    PatchHexaghostField.ghostSurroundSkeleton.get(Hexaghost)[i].setColor(Hexaghost.tint.color);
                    PatchHexaghostField.ghostSurroundSkeleton.get(Hexaghost)[i].setFlip(Hexaghost.flipHorizontal, Hexaghost.flipVertical);
                }
                sb.end();
                CardCrawlGame.psb.begin();
                for (int i = 0; i < 6; i++) {sr.draw(CardCrawlGame.psb, PatchHexaghostField.ghostSurroundSkeleton.get(Hexaghost)[i]);}
                CardCrawlGame.psb.end();
                sb.begin();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return SpireReturn.Continue();
        }
    }

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


    @SpirePatch(
            clz = HexaghostOrb.class,
            method=SpirePatch.CLASS
    )public static class PatchHexaghostOrbField {
        public static SpireField<Boolean> ghostIgnite = new SpireField<>(() -> false);
        public static SpireField<Boolean> ghostDeactivate = new SpireField<>(() -> false);
        public static SpireField<Integer> ghostIndex = new SpireField<>(() -> 0);
    }

    @SpirePatch(
            clz = HexaghostOrb.class,
            method=SpirePatch.CONSTRUCTOR
    )
    public static class PatchHexaghostOrbConstructor {
        @SpirePostfixPatch
        public static void Postfix(HexaghostOrb HexaghostOrb,float x, float y, int index){
            PatchHexaghostOrbField.ghostIndex.set(HexaghostOrb,index);
        }
    }

    @SpirePatch(
            clz = HexaghostOrb.class,
            method = "activate"
    )public static class PatchHexaghostOrbActivate {
        @SpirePostfixPatch
        public static void Postfix(HexaghostOrb HexaghostOrb,float oX, float oY) {
            try{
                PatchHexaghostOrbField.ghostIgnite.set(HexaghostOrb,true);
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
        public static void Postfix(HexaghostOrb HexaghostOrb) {
            try{
                PatchHexaghostOrbField.ghostDeactivate.set(HexaghostOrb,true);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @SpirePatch(
            clz = HexaghostOrb.class,
            method = "update"
    )public static class PatchHexaghostOrbUpdateActivate {
        @SpireInsertPatch(rloc = 17)
        public static SpireReturn<Void> Insert(HexaghostOrb HexaghostOrb,float oX, float oY) {
            try{
                if(PatchHexaghostOrbField.ghostIgnite.get(HexaghostOrb)){
                    int i = PatchHexaghostOrbField.ghostIndex.get(HexaghostOrb);
                    System.out.println(i);
                    PatchHexaghostField.ghostSurroundState.get(HexaghostOrb)[i].setAnimation(0, "fade_in", false);
                    PatchHexaghostField.ghostSurroundState.get(HexaghostOrb)[i].addAnimation(1, "idle", true,5.0F);
                    PatchHexaghostOrbField.ghostIgnite.set(HexaghostOrb,false);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz = HexaghostOrb.class,
            method = "update"
    )public static class PatchHexaghostOrbUpdateDeactivate {
        @SpireInsertPatch(rloc = 26)
        public static SpireReturn<Void> Insert(HexaghostOrb HexaghostOrb,float oX, float oY) {
            try{
                if(PatchHexaghostOrbField.ghostDeactivate.get(HexaghostOrb)){
                    int i = PatchHexaghostOrbField.ghostIndex.get(HexaghostOrb);
                    System.out.println(i);
                    PatchHexaghostField.ghostSurroundState.get(HexaghostOrb)[i].setAnimation(0, "fade_out", false);
                    PatchHexaghostField.ghostSurroundState.get(HexaghostOrb)[i].addAnimation(1, "disappear", true,5.0F);
                    PatchHexaghostOrbField.ghostDeactivate.set(HexaghostOrb,false);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return SpireReturn.Continue();
        }
    }



    private static void loadGhostAnimation(AbstractMonster m) {
        TextureAtlas T = new TextureAtlas(Gdx.files.internal("TheGuardianChan/monsters/TheHexaghostKo/surround/Hexaghost_surround.atlas"));
        PatchHexaghostField.ghostSurroundAtlas.set(m,T);
System.out.println("+++++++++++++++++++++++++++这里loadGhostAnimation");
        SkeletonJson json = new SkeletonJson(PatchHexaghostField.ghostSurroundAtlas.get(m));
        json.setScale(Settings.scale / 1.0F);
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
}



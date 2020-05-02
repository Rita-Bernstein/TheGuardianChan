package TheGuardianChan.patches;

import TheGuardianChan.TheGuardianChan;
import com.esotericsoftware.spine.Skeleton;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.exordium.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


@SuppressWarnings("unused")
public class SlimeBossPatch {


    @SpirePatch(
            clz = SlimeBoss.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class BossPatch {
        @SpireInsertPatch(rloc = 26)
        public static SpireReturn<Void> Insert(SlimeBoss slimeBoss) {
            if(!TheGuardianChan.displaySkin_SlimeBoss) {
                try {
                    Method method = AbstractCreature.class.getDeclaredMethod("loadAnimation", String.class, String.class, float.class);
                    method.setAccessible(true);
                    method.invoke(slimeBoss, "TheGuardianChan/monsters/TheSlimeBossWaifu/TheSlimeBossWaifu.atlas", "TheGuardianChan/monsters/TheSlimeBossWaifu/TheSlimeBossWaifu.json", 1.0F);


                    Field field = AbstractCreature.class.getDeclaredField("skeleton");
                    field.setAccessible(true);
                    Skeleton skeleton = (Skeleton) field.get(slimeBoss);
                    SlimeAttachPoints.attachRelic(skeleton);

                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | NoSuchFieldException e) {
                    e.printStackTrace();
                }
            }
            return SpireReturn.Continue();
        }
    }






    //==================================酸液
    @SpirePatch(
            clz = AcidSlime_L.class,
            method = SpirePatch.CONSTRUCTOR,
            paramtypez = {float.class , float.class , int.class , int.class}
    )
    public static class AcidSlime_LPatch {
        @SpireInsertPatch(rloc = 21)
        public static SpireReturn<Void> Insert(AcidSlime_L acidSlime_L) {
            if(!TheGuardianChan.displaySkin_SlimeAcid_L) {
                try {
                    Method method = AbstractCreature.class.getDeclaredMethod("loadAnimation", String.class, String.class, float.class);
                    method.setAccessible(true);
                    method.invoke(acidSlime_L, "TheGuardianChan/monsters/TheSlimeBossWaifu/Slime_acid_L.atlas", "TheGuardianChan/monsters/TheSlimeBossWaifu/Slime_acid_L.json", 1.0F);

                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz = AcidSlime_M.class,
            method = SpirePatch.CONSTRUCTOR,
            paramtypez = {float.class , float.class , int.class , int.class}
    )
    public static class AcidSlime_MPatch {
        @SpireInsertPatch(rloc = 16)
        public static SpireReturn<Void> Insert(AcidSlime_M acidSlime_M) {
            if(!TheGuardianChan.displaySkin_SlimeAcid_M) {
                try {
                    Method method = AbstractCreature.class.getDeclaredMethod("loadAnimation", String.class, String.class, float.class);
                    method.setAccessible(true);
                    method.invoke(acidSlime_M, "TheGuardianChan/monsters/TheSlimeBossWaifu/Slime_acid_M.atlas", "TheGuardianChan/monsters/TheSlimeBossWaifu/Slime_acid_M.json", 1.0F);


                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
            return SpireReturn.Continue();
        }
    }


    @SpirePatch(
            clz = AcidSlime_S.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class AcidSlime_SPatch {
        @SpireInsertPatch(rloc = 19)
        public static SpireReturn<Void> Insert(AcidSlime_S acidSlime_S) {
            if(!TheGuardianChan.displaySkin_SlimeAcid_S) {
                try {
                    Method method = AbstractCreature.class.getDeclaredMethod("loadAnimation", String.class, String.class, float.class);
                    method.setAccessible(true);
                    method.invoke(acidSlime_S, "TheGuardianChan/monsters/TheSlimeBossWaifu/Slime_acid_S.atlas", "TheGuardianChan/monsters/TheSlimeBossWaifu/Slime_acid_S.json", 1.0F);


                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
            return SpireReturn.Continue();
        }
    }




    //==================================尖刺
    @SpirePatch(
            clz = SpikeSlime_L.class,
            method = SpirePatch.CONSTRUCTOR,
            paramtypez = {float.class , float.class , int.class , int.class}
    )
    public static class SpikeSlime_LPatch {
        @SpireInsertPatch(rloc = 19)
        public static SpireReturn<Void> Insert(SpikeSlime_L spikeSlime_L) {
            if(!TheGuardianChan.displaySkin_SlimeSpike_L) {
                try {
                    Method method = AbstractCreature.class.getDeclaredMethod("loadAnimation", String.class, String.class, float.class);
                    method.setAccessible(true);
                    method.invoke(spikeSlime_L, "TheGuardianChan/monsters/TheSlimeBossWaifu/Slime_spike_L.atlas", "TheGuardianChan/monsters/TheSlimeBossWaifu/Slime_spike_L.json", 1.0F);


                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
            return SpireReturn.Continue();
        }
    }


    @SpirePatch(
            clz = SpikeSlime_M.class,
            method = SpirePatch.CONSTRUCTOR,
            paramtypez = {float.class , float.class , int.class , int.class}
    )
    public static class SpikeSlime_MPatch {
        @SpireInsertPatch(rloc = 14)
        public static SpireReturn<Void> Insert(SpikeSlime_M spikeSlime_M) {
            if(!TheGuardianChan.displaySkin_SlimeSpike_M) {
                try {
                    Method method = AbstractCreature.class.getDeclaredMethod("loadAnimation", String.class, String.class, float.class);
                    method.setAccessible(true);
                    method.invoke(spikeSlime_M, "TheGuardianChan/monsters/TheSlimeBossWaifu/Slime_spike_M.atlas", "TheGuardianChan/monsters/TheSlimeBossWaifu/Slime_spike_M.json", 1.0F);


                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
            return SpireReturn.Continue();
        }
    }



    @SpirePatch(
            clz = SpikeSlime_S.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class SpikeSlime_SPatch {
        @SpireInsertPatch(rloc = 20)
        public static SpireReturn<Void> Insert(SpikeSlime_S spikeSlime_S) {
            if(!TheGuardianChan.displaySkin_SlimeSpike_S) {
                try {
                    Method method = AbstractCreature.class.getDeclaredMethod("loadAnimation", String.class, String.class, float.class);
                    method.setAccessible(true);
                    method.invoke(spikeSlime_S, "TheGuardianChan/monsters/TheSlimeBossWaifu/Slime_spike_S.atlas", "TheGuardianChan/monsters/TheSlimeBossWaifu/Slime_spike_S.json", 1.0F);


                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
            return SpireReturn.Continue();
        }
    }

}

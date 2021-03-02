package TheGuardianChan.patches;

import TheGuardianChan.TheGuardianChan;
import com.esotericsoftware.spine.Skeleton;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.city.Champ;
import com.megacrit.cardcrawl.monsters.exordium.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


@SuppressWarnings("unused")
public class ChampPatch {


    @SpirePatch(
            clz = Champ.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class BossPatch {
        @SpireInsertPatch(rloc = 6)
        public static SpireReturn<Void> Insert(Champ champ) {
            if(!TheGuardianChan.displaySkin_Champ) {
                try {
                    Method method = AbstractCreature.class.getDeclaredMethod("loadAnimation", String.class, String.class, float.class);
                    method.setAccessible(true);
                    method.invoke(champ, "TheGuardianChan/monsters/TheSisterChamp/TheChamp.atlas", "TheGuardianChan/monsters/TheSisterChamp/TheChamp.json", 2.0F);


                    Field field = AbstractCreature.class.getDeclaredField("skeleton");
                    field.setAccessible(true);
                    Skeleton skeleton = (Skeleton) field.get(champ);
                    SlimeAttachPoints.attachRelic(skeleton);

                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | NoSuchFieldException e) {
                    e.printStackTrace();
                }
            }
            return SpireReturn.Continue();
        }
    }
}

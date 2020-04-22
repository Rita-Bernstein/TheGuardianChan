package TheGuardianChan.patches;


import TheGuardianChan.powers.SayaPower;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.city.Snecko;



@SuppressWarnings("unused")
public class SneckoPatch {


    @SpirePatch(
            clz = Snecko.class,
            method = "takeTurn"
    )
    public static class PatchTakeTurn {
        @SpireInsertPatch(rloc = 8)
        public static SpireReturn<Void> Insert(Snecko snecko) {
            try {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, snecko, new SayaPower(AbstractDungeon.player,true)));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return SpireReturn.Continue();
        }
    }

}

package TheGuardianChan.patches;

import com.esotericsoftware.spine.AnimationStateData;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ChangeStateAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.LoseBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.exordium.TheGuardian;
import com.megacrit.cardcrawl.powers.ModeShiftPower;
import TheGuardianChan.TheGuardianChan;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@SuppressWarnings("unused")
public class GuardianPatch {
    @SpirePatch(
            clz = TheGuardian.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class PatchConstructor {
        @SpireInsertPatch(rloc = 29)
        public static SpireReturn<Void> Insert(TheGuardian guardian) {
            try {
                Method method2 = AbstractMonster.class.getDeclaredMethod("updateHitbox", float.class, float.class, float.class, float.class);
                method2.setAccessible(true);
                method2.invoke(guardian, 0.0F, 95.0F, 450.0F, 430.0F);
                Method method = AbstractCreature.class.getDeclaredMethod("loadAnimation", String.class, String.class, float.class);
                method.setAccessible(true);
                method.invoke(guardian, "TheGuardianChan/monsters/TheGuardianChan/Guardian.atlas", "TheGuardianChan/monsters/TheGuardianChan/Guardian2.json", 2.0F);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
            guardian.dialogY = 100.0F * Settings.scale;
            guardian.state.setAnimation(0, "idle", true);
            return SpireReturn.Return(null);
        }
    }

    @SpirePatch(
            clz = TheGuardian.class,
            method = "changeState"
    )
    public static class PatchChangeState {
        public static SpireReturn<Void> Prefix(TheGuardian guardian, String stateName) {
            if (stateName.equals("Defensive Mode")) {
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(guardian, guardian, "Mode Shift"));
                CardCrawlGame.sound.play("GUARDIAN_ROLL_UP");
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(guardian, guardian, 20));
                try {
                    Field stateDataField = AbstractCreature.class.getDeclaredField("stateData");
                    stateDataField.setAccessible(true);
                    AnimationStateData stateData = (AnimationStateData) stateDataField.get(guardian);
                    Field threshold = TheGuardian.class.getDeclaredField("dmgThreshold");
                    threshold.setAccessible(true);
                    threshold.set(guardian, (int)threshold.get(guardian) + 10);
                    Field openField = TheGuardian.class.getDeclaredField("isOpen");
                    openField.setAccessible(true);
                    openField.set(guardian, false);

                    Method methodLoad = AbstractCreature.class.getDeclaredMethod("loadAnimation", String.class, String.class, float.class);
                    methodLoad.setAccessible(true);
                    methodLoad.invoke(guardian, "TheGuardianChan/monsters/TheGuardianChan/Guardian.atlas", "TheGuardianChan/monsters/TheGuardianChan/Guardian2.json", 2.0F);

                    guardian.state.setTimeScale(2.0F);
                    guardian.state.setAnimation(0, "transition", false);
                    guardian.state.addAnimation(0, "defensive", true, 0.0F);
                    guardian.setMove(TheGuardian.MOVES[0], (byte) 1, AbstractMonster.Intent.BUFF);
                    guardian.createIntent();
                    Method method = AbstractMonster.class.getDeclaredMethod("updateHitbox", float.class, float.class, float.class, float.class);
                    method.setAccessible(true);
                    method.invoke(guardian, 0.0F, 95.0F, 440.0F, 250.0F);
                    guardian.healthBarUpdatedEvent();
                    return SpireReturn.Return(null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (stateName.equals("Offensive Mode")) {
                try {
                    Field stateDataField = AbstractCreature.class.getDeclaredField("stateData");
                    stateDataField.setAccessible(true);
                    AnimationStateData stateData = (AnimationStateData) stateDataField.get(guardian);
                    Field threshold = TheGuardian.class.getDeclaredField("dmgThreshold");
                    threshold.setAccessible(true);
                    int dmgThreshold = (int) threshold.get(guardian);
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(guardian, guardian, new ModeShiftPower(guardian, dmgThreshold)));
                    AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(guardian, "Reset Threshold"));
                    if (guardian.currentBlock != 0) {
                        AbstractDungeon.actionManager.addToBottom(new LoseBlockAction(guardian, guardian, guardian.currentBlock));
                    }
                    stateData.setMix("defensive", "offensive", 0.1F);
                    guardian.state.setTimeScale(1.0F);
                    guardian.state.setAnimation(0, "offensive", false);
                    guardian.state.addAnimation(0, "idle", true, 0.0F);
                    Field openField = TheGuardian.class.getDeclaredField("isOpen");
                    openField.setAccessible(true);
                    openField.set(guardian, true);
                    Field closeUpTriggeredField = TheGuardian.class.getDeclaredField("closeUpTriggered");
                    closeUpTriggeredField.setAccessible(true);
                    closeUpTriggeredField.set(guardian, false);
                    Method method = AbstractMonster.class.getDeclaredMethod("updateHitbox", float.class, float.class, float.class, float.class);
                    method.setAccessible(true);
                    method.invoke(guardian, 0.0F, 95.0F, 450.0F, 430.0F);
                    guardian.healthBarUpdatedEvent();
                    return SpireReturn.Return(null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return SpireReturn.Continue();
        }
    }


    @SpirePatch(clz = TheGuardian.class, method = "useChargeUp")
    public static class useChargeUpPatch
    {
        @SpirePrefixPatch
        public static SpireReturn<Void> Prefix(TheGuardian guardian)
        {
            try {
                Field stateDataField = AbstractCreature.class.getDeclaredField("stateData");
                stateDataField.setAccessible(true);
                AnimationStateData stateData = (AnimationStateData) stateDataField.get(guardian);
                guardian.state.setAnimation(1, "turn_red", false);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return SpireReturn.Continue();
        }
    }
}

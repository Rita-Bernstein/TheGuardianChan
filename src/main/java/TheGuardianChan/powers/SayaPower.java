package TheGuardianChan.powers;

import TheGuardianChan.TheGuardianChan;
import TheGuardianChan.actions.SayaAction;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.InvisiblePower;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.city.Snecko;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SayaPower extends AbstractPower implements InvisiblePower {
  public static final String POWER_ID = TheGuardianChan.makeID("SayaPower");
  private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(TheGuardianChan.makeID("SayaPower"));
  public static final String NAME = powerStrings.NAME;
  public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

  private AbstractPlayer p;
  public static byte SneckoWaifu_playerLastTurn = 0;
  private static boolean justApply = true;

  public SayaPower(AbstractCreature owner,boolean justApplied) {
    this.name = NAME;
    this.ID = POWER_ID;
    this.owner = owner;
    this.justApply = justApplied;
    this.type = PowerType.BUFF;
    this.isTurnBased = false;
    updateDescription();
    this.loadRegion("confusion");

  }

  @Override
  public void atStartOfTurnPostDraw() {
    if(justApply){
      SneckoWaifu_playerLastTurn = 0;
      justApply = false;
    }

    if(this.owner.hasPower("Confusion")){
      addToBot(new SayaAction(this.owner));
    }
  }

  @Override
  public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
    super.onApplyPower(power, target, source);
  }

  public void updateDescription() { this.description = DESCRIPTIONS[0]; }

}


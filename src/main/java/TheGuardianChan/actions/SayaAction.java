 package TheGuardianChan.actions;
 import TheGuardianChan.powers.SayaPower;
 import com.megacrit.cardcrawl.actions.AbstractGameAction;
 import com.megacrit.cardcrawl.actions.animations.VFXAction;
 import com.megacrit.cardcrawl.cards.AbstractCard;
 import com.megacrit.cardcrawl.core.AbstractCreature;
 import com.megacrit.cardcrawl.core.Settings;
 import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

 import com.megacrit.cardcrawl.monsters.AbstractMonster;
 import com.megacrit.cardcrawl.monsters.city.Snecko;
 import com.megacrit.cardcrawl.vfx.combat.SmokeBombEffect;


 import java.lang.reflect.InvocationTargetException;
 import java.lang.reflect.Method;

 
 public class SayaAction extends AbstractGameAction {
   private AbstractCreature sk;
     private byte SneckoWaifu = 0;
     private boolean change = true;


   public SayaAction(AbstractCreature owners) {
       this.sk = owners;


     this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
     this.duration = Settings.ACTION_DUR_FAST;

   }

   
   public void update() {
       for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters)
       {
           if(mo instanceof Snecko){
               sk = mo;
               if (AbstractDungeon.player.hand.size() > 0) {
                   int cost = 0;
                   for (AbstractCard card : AbstractDungeon.player.hand.group) {
                       cost += card.cost;

                   }

                   change = true;

                   System.out.println("Cost:" + cost);
                   System.out.println("上回合动画:" + SayaPower.SneckoWaifu_playerLastTurn);


                   if ((float) cost / (float) AbstractDungeon.player.hand.size() >= 1.0f) {
                       SneckoWaifu = 1;

                       if ((float) cost / (float) AbstractDungeon.player.hand.size() >= 2.0f) {
                           SneckoWaifu = 2;
                       }
                   } else {
                       SneckoWaifu = 0;
                   }

                   if(SneckoWaifu == SayaPower.SneckoWaifu_playerLastTurn ){
                       change = false;
                   }
                   SayaPower.SneckoWaifu_playerLastTurn = SneckoWaifu;


                   System.out.println("SneckoWaifu:" + SneckoWaifu);
                   System.out.println("change:" + change);

                   if(change){
                       addToBot(new VFXAction(new SmokeBombEffect(sk.hb.cX, sk.hb.cY)));
                       addToBot(new SayaReloadAnimationAction(sk,SneckoWaifu));
                   change = false;
                   }
               }
           }
       }



       this.isDone = true;
   }
 }



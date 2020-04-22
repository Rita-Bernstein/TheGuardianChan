 package TheGuardianChan.actions;
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

 
 public class SayaReloadAnimationAction extends AbstractGameAction {
   private AbstractCreature sk;
     private byte SneckoWaifu = 0;
     private boolean change = true;


   public SayaReloadAnimationAction(AbstractCreature owners,byte animationNum) {
       this.sk = owners;
       this.SneckoWaifu = animationNum;

     this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
     this.duration = Settings.ACTION_DUR_LONG;

   }

   
   public void update() {
       if(this.duration == Settings.ACTION_DUR_LONG){
           try {
               Method method = AbstractCreature.class.getDeclaredMethod("loadAnimation", String.class, String.class, float.class);
               method.setAccessible(true);
               switch (SneckoWaifu){
                   case 1:
                       method.invoke(sk, "TheGuardianChan/monsters/TheSneckoWaifu/Snecko_waifu.atlas", "TheGuardianChan/monsters/TheSneckoWaifu/Snecko_waifu.json", 1.0F);
                       break;
                   case 2:
                       method.invoke(sk, "TheGuardianChan/monsters/TheSneckoWaifu/Snecko_waifu2.atlas", "TheGuardianChan/monsters/TheSneckoWaifu/Snecko_waifu2.json", 1.0F);
                       break;
                   default:
                       method.invoke(sk, "images/monsters/theCity/reptile/skeleton.atlas", "images/monsters/theCity/reptile/skeleton.json", 1.0F);
                       break;
               }
               sk.state.setAnimation(0, "Idle", true);
           } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
               e.printStackTrace();
           }
       }
       tickDuration();
   }
 }



package slimebound.vfx;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.esotericsoftware.spine.Skeleton;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import slimebound.characters.SlimeboundCharacter;

import static TheGuardianChan.TheGuardianChan.SlimeOriginalAnimation;


public class TinyHatParticle extends com.megacrit.cardcrawl.vfx.AbstractGameEffect {
    private static final float DURATION = 0.75F;
    private static final float START_SCALE = 1.2F * com.megacrit.cardcrawl.core.Settings.scale;
    private float scale = .75F;
    private static int W;
    private static int H;
    private Texture img;
    public SlimeboundCharacter p;
    private static int xOffset = 45;
    private static int yOffset = 85;
    private static float duplicatedScale = 1.0f;

    public TinyHatParticle(AbstractPlayer p) {
        this.duration = 0.05F;
//        重新初始化
        if(SlimeOriginalAnimation){
            this.img = ImageMaster.loadImage("SlimeboundImages/relics/tinybowlerhatinverted.png");
            W = img.getWidth();
            H = img.getHeight();
            xOffset = 20;
            yOffset = 0;
            this.scale = .75f;
        }
        else {
            this.img = ImageMaster.loadImage("TheGuardianChan/monsters/TheSlimeBossWaifu/char/hat.png");
            W = img.getWidth();
            H = img.getHeight();
            xOffset = -17;
            yOffset = -2;
            this.scale = 0.7f;
        }
        this.p = (SlimeboundCharacter)p;
        this.renderBehind = false;



    }

    public void update() {


    }
    public void dispose() {
        this.isDone = true;
        this.img.dispose();
    }

    public void render(SpriteBatch sb, float x, float y) {
    }


    public void render(SpriteBatch sb) {


        sb.setColor(new Color(1F, 1F, 1F, 2F));

        if(AbstractDungeon.player.hasPower("Slimebound:DuplicatedFormPower" ) && !SlimeOriginalAnimation ){duplicatedScale = 1.5f;}else {duplicatedScale = 1.0f;}

        if(SlimeboundCharacter.showHat){
             sb.draw(this.img, this.p.hatX + p.animX + this.p.drawX - W / 2.0F * Settings.scale  + ((xOffset / p.renderscale) * Settings.scale/ duplicatedScale), this.p.hatY + this.p.animY + this.p.drawY - H / 2.0F * Settings.scale + ((yOffset/p.renderscale) * Settings.scale/ duplicatedScale), W / 2.0F , H / 2.0F, W, H, Settings.scale * scale / duplicatedScale, Settings.scale * scale / duplicatedScale, this.p.animationAngle, 0, 0, W, H, false, false);
        }


    }
}



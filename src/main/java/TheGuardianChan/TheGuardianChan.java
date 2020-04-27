package TheGuardianChan;

import TheGuardianChan.helpers.AssetLoader;
import basemod.BaseMod;
import basemod.ModLabeledToggleButton;
import basemod.ModPanel;
import basemod.interfaces.EditStringsSubscriber;
import basemod.interfaces.ISubscriber;
import basemod.interfaces.PostInitializeSubscriber;
import basemod.interfaces.StartGameSubscriber;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.util.Properties;


@SpireInitializer
public class TheGuardianChan
        implements EditStringsSubscriber,
        PostInitializeSubscriber,
        StartGameSubscriber
{

    public static String MOD_ID = "TheGuardianChan";
    public static String makeID(String id) {
        return MOD_ID + ":" + id;
    }
    public static String assetPath(String path) {
        return MOD_ID + "/" + path;
    }

    public static final String MODNAME = "TheGuardianChan";
    public static final String AUTHOR = "Rita";
    public static final String DESCRIPTION = "";

    public static boolean GuardianOriginalAnimation = true;
    public static boolean SlimeOriginalAnimation = true;
    public static boolean hexaghostMask = false;
    public static boolean hexaghostSurroundDisplay = false;
    public static boolean disablePortraitAnimation = false;

    public static Properties TheGuardianChanDefaults = new Properties();

    public static boolean foundmod_GuardianMod = false;
    public static final Logger logger = LogManager.getLogger(TheGuardianChan.class.getSimpleName());


    public static AssetLoader assets = new AssetLoader();

    @SuppressWarnings("unused")
    public static void initialize() {   new TheGuardianChan();

        foundmod_GuardianMod = Loader.isModLoaded("Guardian");
        if(foundmod_GuardianMod){logger.info("==========================守护者mod存在=============================");}

    }

    public TheGuardianChan() {
        BaseMod.subscribe(this);
    }


    public static String getLanguageString() {
        String language;
        switch (Settings.language) {
            case ZHS:
                language = "zhs";
                break;
            case ZHT:
                language = "zht";
                break;
            case KOR:
                language = "kor";
                break;
            case JPN:
                language = "jpn";
                break;
            default:
                language = "eng";
        }
        return language;
    }

    @Override
    public void receiveEditStrings() {
        String language;
        language = getLanguageString();
        String powerStrings = Gdx.files.internal("TheGuardianChan/localization/" + language + "/PowerStrings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(PowerStrings.class, powerStrings);
        String uiStrings = Gdx.files.internal("TheGuardianChan/localization/" + language + "/UIStrings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(UIStrings.class, uiStrings);
    }

    @Override
    public void receivePostInitialize() {
        loadSettings();
        Texture badgeTexture = new Texture(assetPath("/img/badge.png"));
        ModPanel settingsPanel = new ModPanel();
        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);

        ModLabeledToggleButton hexaghostMaskSwitch = new ModLabeledToggleButton(CardCrawlGame.languagePack.getUIString(makeID("modSettings")).TEXT[0],400.0f, 720.0f, Settings.CREAM_COLOR, FontHelper.charDescFont,hexaghostMask, settingsPanel,
                (label) -> {}, (button) -> {hexaghostMask = button.enabled;saveSettings();});
        ModLabeledToggleButton hexaghostSurroundDisplaySwitch = new ModLabeledToggleButton(CardCrawlGame.languagePack.getUIString(makeID("modSettings")).TEXT[1],400.0f, 660.0f, Settings.CREAM_COLOR, FontHelper.charDescFont,hexaghostSurroundDisplay, settingsPanel,
                (label) -> {}, (button) -> {hexaghostSurroundDisplay = button.enabled;saveSettings();});
        ModLabeledToggleButton disablePortraitAnimationSwitch = new ModLabeledToggleButton(CardCrawlGame.languagePack.getUIString(makeID("modSettings")).TEXT[2],400.0f, 600.0f, Settings.CREAM_COLOR, FontHelper.charDescFont,disablePortraitAnimation, settingsPanel,
                (label) -> {}, (button) -> {disablePortraitAnimation = button.enabled;saveSettings();});

        settingsPanel.addUIElement(hexaghostMaskSwitch);
        settingsPanel.addUIElement(hexaghostSurroundDisplaySwitch);
        settingsPanel.addUIElement(disablePortraitAnimationSwitch);

    }

    public static void saveSettings() {
        try {
            SpireConfig config = new SpireConfig("TheGuardianChanFix", "settings",TheGuardianChanDefaults);
            config.setBool("GuardianOriginalAnimation", GuardianOriginalAnimation);
            config.setBool("SlimeOriginalAnimation", SlimeOriginalAnimation);
            config.setBool("hexaghostMask", hexaghostMask);
            config.setBool("hexaghostSurroundDisplay", hexaghostSurroundDisplay);
            config.setBool("disablePortraitAnimation", disablePortraitAnimation);
            config.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadSettings() {
        try {
            SpireConfig config = new SpireConfig("TheGuardianChanFix", "settings",TheGuardianChanDefaults);
            config.load();
            GuardianOriginalAnimation = config.getBool("GuardianOriginalAnimation");
            SlimeOriginalAnimation = config.getBool("SlimeOriginalAnimation");
            hexaghostMask = config.getBool("hexaghostMask");
            hexaghostSurroundDisplay = config.getBool("hexaghostSurroundDisplay");
            disablePortraitAnimation = config.getBool("disablePortraitAnimation");
        } catch (Exception e) {
            e.printStackTrace();
            clearSettings();
        }
    }

    public static void clearSettings() {
        saveSettings();
    }

    @Override
    public void receiveStartGame() {

    }
}

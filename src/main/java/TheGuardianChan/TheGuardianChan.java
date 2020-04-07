package TheGuardianChan;

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

    public static Properties TheGuardianChanDefaults = new Properties();

    public static boolean foundmod_GuardianMod = false;
    public static final Logger logger = LogManager.getLogger(TheGuardianChan.class.getSimpleName());

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
            case KOR:
                language = "kor";
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


        String uiStrings = Gdx.files.internal("TheGuardianChan/localization/" + language + "/GuardianChan-UIStrings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(UIStrings.class, uiStrings);
    }

    @Override
    public void receivePostInitialize() {
        loadSettings();
    }

    public static void saveSettings() {
        try {
            SpireConfig config = new SpireConfig("TheGuardianChanFix", "settings",TheGuardianChanDefaults);
            config.setBool("GuardianOriginalAnimation", GuardianOriginalAnimation);
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

package TheGuardianChan.patches;

import TheGuardianChan.TheGuardianChan;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.spine.Skeleton;
import com.esotericsoftware.spine.Skin;
import com.esotericsoftware.spine.Slot;
import com.esotericsoftware.spine.SlotData;
import com.esotericsoftware.spine.attachments.RegionAttachment;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.BurningBlood;
import com.megacrit.cardcrawl.relics.SmilingMask;

import java.util.HashMap;
import java.util.Map;

public class SlimeAttachPoints
{
    private static Map<Integer, AttachPoint> map;
    static
    {
        map = new HashMap<>();

        map.put(1,
                new AttachPoint(
                        BurningBlood.ID,
                        "emptyRelic", 1,
                        "images/relics/cantUseRelic.png",
                        1.0f, 1.0f,
                        0, 0,
                        0
                )
        ); 

        map.put(2,
                new AttachPoint(
                        BurningBlood.ID,
                        "emptyRelic", 1,
                        "images/relics/compass.png",
                        1.0f, 1.0f,
                        0, 0,
                        0
                )
        );
        map.put(3,
                new AttachPoint(
                        BurningBlood.ID,
                        "emptyRelic", 1,
                        "images/relics/crownOfThorns.png",
                        1.0f, 1.0f,
                        0, 0,
                        0
                )
        );
        map.put(4,
                new AttachPoint(
                        BurningBlood.ID,
                        "emptyRelic", 1,
                        "images/relics/cursedBlood.png",
                        1.0f, 1.0f,
                        0, 0,
                        0
                )
        );
        map.put(5,
                new AttachPoint(
                        BurningBlood.ID,
                        "emptyRelic", 1,
                        "images/relics/dodecahedron.png",
                        1.0f, 1.0f,
                        0, 0,
                        0
                )
        );
        map.put(6,
                new AttachPoint(
                        BurningBlood.ID,
                        "emptyRelic", 1,
                        "images/relics/equilibrium.png",
                        1.0f, 1.0f,
                        0, 0,
                        0
                )
        );
            map.put(7,
                new AttachPoint(
                        BurningBlood.ID,
                        "emptyRelic", 1,
                        "images/relics/goldTooth.png",
                        1.0f, 1.0f,
                        0, 0,
                        0
                )
        );
        map.put(8,
                new AttachPoint(
                        BurningBlood.ID,
                        "emptyRelic", 1,
                        "images/relics/goldTooth.png",
                        1.0f, 1.0f,
                        0, 0,
                        0
                )
        );
        map.put(9,
                new AttachPoint(
                        BurningBlood.ID,
                        "emptyRelic", 1,
                        "images/relics/gyoza.png",
                        1.0f, 1.0f,
                        0, 0,
                        0
                )
        );
        map.put(10,
                new AttachPoint(
                        BurningBlood.ID,
                        "emptyRelic", 1,
                        "images/relics/headband.png",
                        1.0f, 1.0f,
                        0, 0,
                        0
                )
        );
        map.put(11,
                new AttachPoint(
                        BurningBlood.ID,
                        "emptyRelic", 1,
                        "images/relics/hotPotato.png",
                        1.0f, 1.0f,
                        0, 0,
                        0
                )
        );
        map.put(12,
                new AttachPoint(
                        BurningBlood.ID,
                        "emptyRelic", 1,
                        "images/relics/keyRing.png",
                        1.0f, 1.0f,
                        0, 0,
                        0
                )
        );

        map.put(13,
                new AttachPoint(
                        BurningBlood.ID,
                        "emptyRelic", 1,
                        "images/relics/kindling.png",
                        1.0f, 1.0f,
                        0, 0,
                        0
                )
        );
        map.put(14,
                new AttachPoint(
                        BurningBlood.ID,
                        "emptyRelic", 1,
                        "images/relics/lives.png",
                        1.0f, 1.0f,
                        0, 0,
                        0
                )
        );
        map.put(15,
                new AttachPoint(
                        BurningBlood.ID,
                        "emptyRelic", 1,
                        "images/relics/monocle.png",
                        1.0f, 1.0f,
                        0, 0,
                        0
                )
        );
        map.put(16,
                new AttachPoint(
                        BurningBlood.ID,
                        "emptyRelic", 1,
                        "images/relics/owlDoll.png",
                        1.0f, 1.0f,
                        0, 0,
                        0
                )
        );
        map.put(17,
                new AttachPoint(
                        BurningBlood.ID,
                        "emptyRelic", 1,
                        "images/relics/redPill.png",
                        1.0f, 1.0f,
                        0, 0,
                        0
                )
        );
        map.put(18,
                new AttachPoint(
                        BurningBlood.ID,
                        "emptyRelic", 1,
                        "images/relics/runicSphere.png",
                        1.0f, 1.0f,
                        0, 0,
                        0
                )
        );
        map.put(19,
                new AttachPoint(
                        BurningBlood.ID,
                        "emptyRelic", 1,
                        "images/relics/rupee.png",
                        1.0f, 1.0f,
                        0, 0,
                        0
                )
        );
        map.put(20,
                new AttachPoint(
                        BurningBlood.ID,
                        "emptyRelic", 1,
                        "images/relics/spearHead.png",
                        1.0f, 1.0f,
                        0, 0,
                        0
                )
        );
        map.put(21,
                new AttachPoint(
                        BurningBlood.ID,
                        "emptyRelic", 1,
                        "images/relics/vCore.png",
                        1.0f, 1.0f,
                        0, 0,
                        0
                )
        );
        map.put(22,
                new AttachPoint(
                        BurningBlood.ID,
                        "emptyRelic", 1,
                        "images/relics/test7.png",
                        1.0f, 1.0f,
                        0, 0,
                        0
                )
        );
    }

    public static void attachRelic(Skeleton skeleton)
    {

        int num = AbstractDungeon.monsterRng.random(0,22);
        AttachPoint attachPoint = map.get(num);
        if (attachPoint == null) {
            return;
        }
//        hash表是否能找到对应

        String attachName = attachPoint.attachName;
        int slotIndex = skeleton.findSlotIndex(attachName);
        if (slotIndex < 0) {
            return;
        }
//        检测已有slot是否存在


        if (attachPoint.attachIndex != null) {
            if (skeleton.findSlotIndex(attachName + attachPoint.attachIndex) < 0) {

                // 新建一份用于 attachment的slot
                Slot origSlot = skeleton.findSlot(attachName);
                //获取原slot
                Slot slotClone = new Slot(new SlotData(origSlot.getData().getIndex(), attachName + attachPoint.attachIndex, origSlot.getBone().getData()), origSlot.getBone());
                //新建slot
                slotClone.getData().setBlendMode(origSlot.getData().getBlendMode());
                skeleton.getSlots().insert(slotIndex, slotClone);
//                获取原slot的各项

                // 将新增的slot加到正确的绘制顺序Add new slot to draw order
                Array<Slot> drawOrder = skeleton.getDrawOrder();
//                获取当前绘制顺序

                int insertIndex = drawOrder.indexOf(origSlot, true);
//                获取原slot的绘制顺序
                boolean found = false;

                for (int i = 0; i < drawOrder.size; ++i) {
                    Slot slot = drawOrder.get(i);


                    if (slot.getData().getName().startsWith(attachName)) {
                        found = true;
                        String tmp = slot.getData().getName().substring(attachName.length());
                        if (tmp.isEmpty()) {
                            tmp = "0";
                        }
                        int curIndex;
                        try {
                            curIndex = Integer.parseInt(tmp);
                        } catch (NumberFormatException ignore) {
                            continue;
                        }
                        insertIndex = i;
                        if (curIndex > attachPoint.attachIndex) {
                            break;
                        }
                    }

                    else if (found) {
                        insertIndex = i;
                        break;
                    }
                }
                drawOrder.insert(insertIndex, slotClone);
                skeleton.setDrawOrder(drawOrder);
            }
            attachName = attachName + attachPoint.attachIndex;
        }




        Texture tex = TheGuardianChan.assets.loadImage(attachPoint.imgPath);
        tex.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        TextureRegion region = new TextureRegion(tex);
        RegionAttachment attachment = new RegionAttachment(attachPoint.ID);
        attachment.setRegion(region);
        attachment.setWidth(tex.getWidth());
        attachment.setHeight(tex.getHeight());
        attachment.setX(attachPoint.x * Settings.scale);
        attachment.setY(attachPoint.y * Settings.scale);
        attachment.setScaleX(attachPoint.scaleX * Settings.scale);
        attachment.setScaleY(attachPoint.scaleY * Settings.scale);
        attachment.setRotation(attachPoint.angle);
        attachment.updateOffset();

        Skin skin = skeleton.getData().getDefaultSkin();
        skin.addAttachment(slotIndex, attachment.getName(), attachment);

        skeleton.setAttachment(attachName, attachment.getName());
    }

    public static class AttachPoint
    {
        final String ID;
        final String attachName;
        final Integer attachIndex;
        final String imgPath;
        final float scaleX;
        final float scaleY;
        final float x;
        final float y;
        final float angle;

        public AttachPoint(
                String id, String attachName, String img,
                float scaleX, float scaleY,
                float x, float y,
                float angle
        )
        {
            this(id, attachName, null, img, scaleX, scaleY, x, y, angle);
        }

        public AttachPoint(
                String id, String attachName, Integer attachIndex, String img,
                float scaleX, float scaleY,
                float x, float y,
                float angle
        )
        {
            ID = id;
            this.attachName = attachName;
            this.attachIndex = attachIndex;
            imgPath = img;
            this.scaleX = scaleX;
            this.scaleY = scaleY;
            this.x = x;
            this.y = y;
            this.angle = angle;
        }
    }
}

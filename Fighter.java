package scripts;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import org.tribot.api.ChooseOption;
import org.tribot.api.DynamicClicking;
import org.tribot.api.GameTab;
import org.tribot.api.General;
import org.tribot.api.ScreenModels;
import org.tribot.api.Text;
import org.tribot.api.Textures;
import org.tribot.api.Timing;
import org.tribot.api.types.ScreenModel;
import org.tribot.api.types.TextChar;
import org.tribot.api.types.Texture;
import org.tribot.api.types.generic.Condition;
import org.tribot.api.types.generic.CustomRet_0P;
import org.tribot.script.EnumScript;
import org.tribot.script.interfaces.Painting;

public class Fighter extends EnumScript<Fighter.STATE> implements
        Painting {

    enum STATE {
        CHECK, COMBAT, ATTACK
    }

    final long SPIDER_ID = 320820472L;

    final int HP_ID = 33739;


    
    public String getTarget() {
        if (!GameTab.open(GameTab.TABS.COMBAT))
            return null;

        TextChar[] text = Text.findCharsInArea(593, 266, 150, 20, true);
        for (TextChar[] line : Text.splitByHorizontalLines(text)) {
            String str = Text.lineToString(line, 4);

            if (str.contains("No Target"))
                return null;

            if (str.length() > 0 && !str.equals("-"))
                return str;
        }

        return null;
    }

    @Override
    public void onPaint(Graphics g) {
        g.setColor(Color.BLACK);

        g.fillRect(0, 0, 795, 50);

        g.setColor(Color.GREEN);

        g.drawString("Target: " + getTarget(), 5, 12);
    }

    @Override
    public STATE getInitialState() {
        return STATE.CHECK;
    }

    @Override
    public STATE handleState(STATE t) {
        switch (t) {
        case CHECK:
            if (getTarget() == null)
                return STATE.ATTACK;
            else
                return STATE.COMBAT;

            // break;
        case ATTACK:
            if (getTarget() != null)
                return STATE.COMBAT;

            final Texture[] tex = Textures.find(this.HP_ID);

            if (!DynamicClicking.clickScreenModel(
                    new CustomRet_0P<ScreenModel>() {

                        @Override
                        public ScreenModel ret() {
                            if (getTarget() != null)
                                return null;

                            ScreenModel[] m = ScreenModels
                                    .findNearest(SPIDER_ID);
                            if (m.length < 1)
                                return null;

                            for (ScreenModel m1 : m) {
                                boolean under_attack = false;

                                for (Texture t : tex)
                                    if (General.distanceTo(new Point((int) t.x
                                            + (t.width / 2), (int) t.y
                                            + t.height), m1.base_point) < 50
                                            && t.y < m1.base_point.y) {
                                        under_attack = true;

                                        break;
                                    }

                                if (!under_attack)
                                    return m1;
                            }

                            return null;
                        }
                    }, 3))
                return STATE.ATTACK;

            if (!Timing.waitMenuOpen(3000))
                return STATE.ATTACK;

            if (!Timing.waitChooseOption("Attack Giant Spider",
                    General.random(100, 150))) {
                ChooseOption.close();

                return STATE.ATTACK;
            }

            if (Timing.waitCondition(new Condition() {

                @Override
                public boolean active() {
                    return getTarget() != null;
                }
            }, 6000))
                return STATE.COMBAT;

            break;
        case COMBAT:

            // HANDLE COMBAT HERE

            break;
        }

        return STATE.CHECK;
    }

}
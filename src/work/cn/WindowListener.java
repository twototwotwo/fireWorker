package work.cn;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * @author ËÄšð
 * @date
 */
public class WindowListener implements KeyListener {
    Window JW;
    public void setWindow(Window JW) {
        this.JW = JW;

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!JW.change) {
            int code = e.getKeyCode();
            JW.pressCounter++;
            if (code == 39) {
                if (JW.hero.a > 4 || JW.hero.a < 2) {
                    JW.hero.a = 2;
                }
                JW.hero.RL = true;
                JW.hero.xspeed = 20;

            }
            if (code == KeyEvent.VK_LEFT) {
                if (JW.hero.a > 4 || JW.hero.a < 2) {
                    JW.hero.a = 2;
                }
                JW.hero.RL = false;
                JW.hero.xspeed = -20;
            }

            if (code == 88) {
                JW.hero.a = 5;
                if (JW.heroSword.attack[0] == 0) {
                    JW.heroSword.attack[0] = 5;
                    JW.heroSword.attackCommand[0] = 1;
                } else if (JW.heroSword.attack[1] == 0) {
                    JW.heroSword.attack[1] = 5;
                    if (JW.heroSword.attackCommand[0] != 3) {
                        JW.heroSword.attackCommand[1] = JW.heroSword.attackCommand[0] + 1;
                    } else {
                        JW.heroSword.attackCommand[1] = 1;
                    }
                }


                JW.hero.attack();

            }
            if (code == KeyEvent.VK_UP) {
                if (!JW.hero.down || JW.hero.flyBySword) {
                    JW.hero.yspeed = 25;
                }
            }
            if (code == KeyEvent.VK_DOWN) {
                if (JW.hero.flyBySword) {
                    JW.hero.yspeed2 = 25;
                }
            }

            if (code == KeyEvent.VK_S) {
                if (JW.heroSword.attack[0] < 30) {
                    JW.heroSword.attack[0] = 400;
                    if (JW.hero.RL) {
                        JW.heroSword.attackCommand[0] = 3;
                        JW.heroSword.xTurn = 60;
                    } else {
                        JW.heroSword.attackCommand[0] = -3;
                        JW.heroSword.xTurn = -60;
                    }

                } else {
                    if (JW.heroSword.xTurn > 0) {
                        JW.heroSword.attackCommand[0] = -3;
                    } else {
                        JW.heroSword.attackCommand[0] = 3;
                    }
                }


            }

            if (code == KeyEvent.VK_Z) {
                if (JW.hero.flyBySword) {
                    JW.hero.flyBySword = false;
                } else if (JW.hero.ability > 100) {
                    JW.hero.flyBySword = true;
                }
            }

            if (code == KeyEvent.VK_A) {
                double theta;
                double theta2;
                for (int i = 0; i < 5; i++) {
                    theta2 = -(i - 2) * Math.PI / 4;
                    theta = -i * Math.PI / 4;
                    FlySword f = new FlySword(JW, (int) (JW.hero.x + 20 + 70 * Math.cos(theta)), (int) (JW.hero.y + 70 + 60 * Math.sin(theta)), theta2);
                    JW.manSword.add(f);
                    f.start();
                }

                if (code == KeyEvent.VK_C) {
                    JW.hero.x = -800;
                }
            }

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        JW.pressCounter = 0;
        JW.hero.interval = 60;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    	 /*
    	 int code=e.getKeyCode();
    	 // ?????
		 if(code==39) {
			 JW.hero.a=2;
			 JW.hero.xspeed=16;
		 }
		 // ?????
		 else if(code==KeyEvent.VK_LEFT) {
			 JW.hero.a=2;
			JW.hero.xspeed=-16;
		 }
		 */
    }
}

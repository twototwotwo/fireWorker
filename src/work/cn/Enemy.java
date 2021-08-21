package work.cn;

import java.util.Random;

public class Enemy extends Thread {
    int e = 0;
    int e1 = 0;
    int time = 0;
    int x;
    int y;
    int xspeed = -1;
    int yspeed = 10;
    int blood = 0;
    boolean right = true;
    boolean left = true;
    boolean up = true;
    boolean down = true;
    boolean RL = true;
    boolean attack = false;
    boolean bleeding = false;
    Object lock;
    Window JW;

    public Enemy(Window JW) {
        Random random = new Random();
        x = random.nextInt(550) + 20;
        y = random.nextInt(85) + 7;
        this.JW = JW;
        this.lock = JW.lock;

        this.start();
    }

    public void run() {

        while (true) {

            enemyIsFloor();
            // 检查上一帧的攻击，判断英雄是否受到伤害。
            checkAttack();
            enemySBAI();

            try {
                Thread.sleep(100);
            } catch (Exception e) {
                //  TODO: handle exception
            }
            if (blood >= 18) {
                break;
            } else {
                bleeding = false;
            }
        }


    }


    /**
     * 贴地方法。让敌人在地板上
     */
    public void enemyIsFloor() {
        int x = this.x / 50;
        int xleft = x - 1;
        if (xleft < 0) {
            xleft = 0;
        }
        // 在数组中的位置，x为横轴（600以内），y为数组第几层，方便找出降落阶梯
        int y = this.y + 7;
        if (JW.mp.map[y][x] != 1 && JW.mp.map[y][xleft] != 1 && JW.mp.map[y][x] != 2) {
            // System.out.println(y);
            y += 1;
        }
        this.y = y - 7;
    }


    public void enemySBAI() {
        // 判断移动是否可行,攻击不移动
        if (!attack()) {
            int x = (this.x + this.xspeed) / 50;
            int y = this.y + 7;
            if (x < 0) {
                x = 20;
                y = 10;
            }
            int xleft = x - 1;
            if (xleft < 0) xleft = 0;
            if (JW.mp.map[y][x] == 0 && JW.mp.map[y][xleft] == 0) {
                RL = !RL;
                this.xspeed = -this.xspeed;
                // System.out.println(this.xspeed);
            } else {
                if (this.x < 30 || this.x > 570) {

                    // 敌人越过屏幕，需要改变反向
                    if (this.x < 30) {
                        this.RL = false;
                    }
                    if (this.x > 570) {
                        this.RL = true;
                    }
                }
                // 移动方向改变速度的方向
                if (this.RL) {
                    this.xspeed = -Math.abs(this.xspeed);

                } else {
                    this.xspeed = Math.abs(this.xspeed);
                }
                this.x += this.xspeed;
            }
        } else {
            if (e1 < 7) {
                e1 = 7;
            }
        }
        // 选图片。攻击移动，不影响选图片
        if (e1 == 6) {
            if (time == 1) {
                time = 0;
                e1 = 1;
            } else {
                time++;
            }
        } else if (e1 < 6) {
            if (time == 1) {
                e1++;
                time = 0;
            } else {
                time++;
            }
        } else {
            if (e1 == 9) {
                e1 = 1;
            } else {
                e1++;
            }
        }
        if (RL) {
            e = e1;
        } else {
            e = e1 + 100;
        }
    }

    public boolean attack() {
        int a = JW.hero.x;
        // 获得人物此时的位置
        int b = JW.hero.y / 15 + JW.mp.upLimited;
        int x, y;
        y = this.y;
        if (a < this.x) {
            x = Math.abs(this.x - 90);
        } else {
            x = this.x + 20;
        }
        if ((y - b) * (y - b) < 4 && (a - x) * (a - x) < 900) {
            RL = this.x - a > 0;
            // 变换方向后，速度方向也要改变，否则会倒着走
            if (RL && xspeed > 0) {
                xspeed = -xspeed;
            }
            if (!RL && xspeed < 0) {
                xspeed = -xspeed;
            }
            attack = true;
            return true;
        } else {
            attack = false;
            return false;
        }

    }

    public void checkAttack() {
        if (e1 > 6) {
            // 确定攻击区域,左右宽度，上下长度
            int x, y, x1, y1;
            // 方向
            if (RL) {
                x = Math.abs(this.x - 150);
            } else {
                x = this.x + 20;
            }
            x1 = x + 60;
            y = this.y - JW.mp.upLimited;
            y1 = y + 7;

            int yUp = JW.hero.y / 15;
            int yDown = yUp + 6;
            int xLeft = JW.hero.x;
            int xRight = JW.hero.x + 60;
            if (xRight >= x && xLeft <= x1) {
                if (yDown > y && yUp < y1) {
                    // 向左向右，攻击到时，给hero一个击退
                    if (RL) {
                        JW.hero.xspeed = -20;
                    } else {
                        JW.hero.xspeed = 20;
                    }
                    JW.hero.yspeed = 10;
                }
            }
        }
    }
}

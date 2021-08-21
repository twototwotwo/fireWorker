package work.cn;

public class Hero extends Thread {
    /**
     * 英雄图片编号
     */
    int a = 1;
    /**
     * x,y表示在窗口的初始化位置
     */
    int x = 300;
    int y = 250;
    int xspeed = 0;
    int yspeed = 0;
    int yspeed2 = 1;
    int interval = 60;
    int ability = 200;
    /**
     * 御剑飞行的标志
     */
    boolean flyBySword = false;
    /**
     * 方向
     */
    boolean RL = true;
    boolean up = true;
    boolean down = true;
    boolean left = true;
    boolean right = true;
    /**
     * 标识hero此时是停止（没有接收到按键处理）或运动（收到按键处理）
     */
    boolean stop = true;
    Window JW;
    int theta = 9;
    Object lock;

    public Hero(Window JW) {
        this.JW = JW;
        lock = JW.lock;
        this.start();
    }


    /**
     * 把hero当做线程
     */
    @Override
    public void run() {
        while (true) {
            if (flyBySword) {
                if (ability > 0) {
                    ability -= 2;
                } else {
                    flyBySword = false;
                }
            } else {
                if (ability < 200) {
                    ability += 1;
                }
            }

            try {
                Thread.sleep(interval);
            } catch (Exception e) {
                //  TODO: handle exception
            }

            if (flyBySword) {
                fly();
                // 御剑保持动作不变
                a = 1;
            } else {
                selectPicture();
                this.heroBehaviour(this);
            }
            // boss血条空了，游戏结束
            if (JW.boss.blood >= 18) {
                break;
            }
        }
    }


    public void jump() {
        if (up && yspeed != 0) {
            y -= yspeed;
            if (yspeed > 7) {
                yspeed -= 5;
            } else {
                yspeed -= 2;
            }
            if (yspeed < 0) {
                yspeed = 0;
            }
        } else if (down && yspeed2 != 0) {
            y += yspeed2;
            yspeed2 += 5;
        } else {
            yspeed2 = 0;
            // 调节人物与窗口的位置关系
            adjustWindow();
        }
    }


    public void adjustWindow() {
        if (y < 150) {
            if (JW.mp.upLimited > 4) {
                JW.mp.downLimited--;
                JW.mp.upLimited--;
                JW.mp.middle--;
                y += 15;
            } else {
                if (this.x > 250) {
                    JW.change = true;
                }
            }
        } else if (y > 250) {
            int n = (y - 250) / 15 + 1;
            if (JW.mp.downLimited < 99) {
                JW.mp.downLimited += n;
                JW.mp.upLimited += n;
                JW.mp.middle += n;
                y -= 15 * n;
            }
        }
    }

    public void attack() {
        // 攻击框
        int x1;
        int x2;
        int y1;
        int y2;
        // 敌人体型框
        int x3;
        int x4;
        int y3;
        int y4;

        if (RL) {
            x1 = x + 25;
            y1 = y;
            x2 = x1 + 50;
        } else {
            x2 = x + 30;
            x1 = x2 - 50;
            y1 = y;
        }
        y2 = y1 + 40;

        for (Enemy e : JW.firer) {
            x3 = e.x - 60;
            y3 = (e.y - JW.mp.upLimited) * 15 + 50;
            x4 = x3 + 90;
            y4 = y3 + 50;
            if (y4 < y1 || y3 > y2) {
            } else if (x4 < x1 || x3 > x2) {
            } else {
                e.blood -= 1;
                if (RL) {
                    e.x += 30;
                } else {
                    e.x -= 30;
                }


            }
        }
    }

    /**
     * 人物的行为，移动进行，以及四个方向的障碍判断
     * @param hero
     */
    public void heroBehaviour(Hero hero) {
        int x = hero.x + 13;
        int y = hero.y + 10;
        int x1 = x + 30;
        int y1 = y + 80;
        int xLeft = x / 50 - 1;
        if (xLeft < 0) {
            xLeft = 0;
        }
        if (JW.mp.map[y / 15 + JW.mp.middle][x / 50] != 0 || JW.mp.map[y / 15 + JW.mp.middle][x1 / 50] != 0 || JW.mp.map[y / 15 + JW.mp.middle][xLeft] != 0) {
            hero.up = false;
            // 检测到顶部有障碍物，向上速度为零
            hero.yspeed = 0;
        } else {
            hero.up = true;
        }

        if (JW.mp.map[y1 / 15 + JW.mp.middle][x / 50] != 0 || JW.mp.map[y1 / 15 + JW.mp.middle][x1 / 50] != 0 || JW.mp.map[y1 / 15 + JW.mp.middle][xLeft] != 0) {
            hero.down = false;
            hero.yspeed2 = 0;
        } else {
            hero.down = true;
            hero.yspeed2 = 10;
        }

        hero.left = x >= 0;
        hero.right = x1 <= 575;


        if (hero.right && hero.xspeed > 0) {
            hero.x += hero.xspeed;
        } else if (hero.left == true && hero.xspeed < 0) {
            // 左右移动的距离
            hero.x += hero.xspeed;
        }
        // 移动速度减半
        hero.xspeed = hero.xspeed / 2;
        // 跳跃或坠落
        hero.jump();
    }

    public void selectPicture() {
        switch (a) {
            case 1:
                a = 1;
                break;
            case 2:
            case 3:
                a++;
                break;
            case 4:
                a = 1;
                break;
            case 5:
                a = 6;
                break;
            case 6:
                a = 1;
                break;
            case 7:
                a = 1;
                break;
            default:
                break;
        }
    }

    /**
     * 御剑
     */
    public void fly() {
        this.y -= this.yspeed;
        this.y += this.yspeed2;
        this.yspeed /= 2;
        this.yspeed2 /= 2;
        this.x += this.xspeed;
        this.xspeed /= 2;
    }
}



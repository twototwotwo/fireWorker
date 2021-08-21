package work.cn;

public class Sword extends Thread {
    Window JW;

    /**
     * 间隔
     */
    int interval = 70;
    /**
     * x,y坐标由英雄xy坐标决定
     */
    int x = 0;
    int y = 0;
    int theta = 9;
    int xTurn = 0;
    /**
     * 武器旋转点相对于x，y偏移量。
     */
    int yTurn = 0;
    int xRotate = 0;
    /**
     *  武器绕中心点旋转的坐标偏移量，等于宽度和长度各自的一半
     */
    int yRotate = 0;
    /**
     *  攻击指令数组
     */
    int[] attack = new int[3];
    /**
     *  攻击指令类型数组
     */
    int[] attackCommand = new int[3];
    /**
     *  攻击方向
     */
    boolean RL = true;

    public Sword(Window JW) {
        this.JW = JW;
        x = JW.hero.x - 30;
        y = JW.hero.y;
        this.start();
    }

    @Override
    public void run() {
        do {
            if (JW.hero.RL) {
                x = JW.hero.x;
            } else {
                x = JW.hero.x + 70;
            }
            y = JW.hero.y + 10;
            attack();// 攻击


            try {
                sleep(interval);
            } catch (Exception e) {
                //  TODO: handle exception
            }
            // boss血条空了，游戏结束
        } while (JW.boss.blood < 18);

    }

    public void attack() {

        if (attack[0] != 0) {
            // 剑的攻击方向默认与hero一样
            RL = JW.hero.RL;
            // 攻击攻击指令对sword的旋转角度，旋转点等作出对应的修改。
            if (attackCommand[0] == 1) {
                // 攻击类型为1时，执行攻击1
                switch (attack[0]) {
                    case 5:
                        theta = 18;
                        break;
                    case 4:
                        theta = 24;
                        xTurn = 30;
                        yTurn = 20;
                        break;
                    case 3:
                        theta = 30;
                        xTurn = 60;
                        yTurn = 20;
                        break;
                    case 2:
                        theta = 0;
                        xTurn = 80;
                        yTurn = 30;
                        break;
                    case 1:
                        theta = 0;
                        xTurn = 50;
                        yTurn = 30;
                        break;
                    default:
                        break;
                }
                // 根据英雄方向改变偏移量的方向
                if (!JW.hero.RL) {
                    theta = 18 - theta;
                    xTurn = -xTurn;
                }
            } else if (attackCommand[0] == 2) {
                // 攻击类型为2时，执行攻击2
                switch (attack[0]) {
                    case 5:
                        theta = 0;
                        xTurn = 30;
                        yTurn = 30;
                        break;
                    case 4:
                        theta = 0;
                        xTurn = 50;
                        yTurn = 20;
                        break;
                    case 3:
                        theta = 0;
                        xTurn = 70;
                        yTurn = 30;
                        break;
                    case 2:
                        theta = 0;
                        xTurn = 90;
                        yTurn = 20;
                        break;
                    case 1:
                        theta = 0;
                        xTurn = 110;
                        yTurn = 30;
                        break;
                    default:
                        break;
                }
                // 根据英雄方向改变偏移量的方向
                if (!JW.hero.RL) {
                    theta = 18 - theta;
                    xTurn = -xTurn;
                }
            } else {
                swordSkill();
            }
            attack[0] -= 1;
            if (attack[0] == 0) {
                // 一个攻击指令执行完毕,攻击指令向数组的前一位迁移
                attack[0] = attack[1];
                attack[1] = attack[2];
                attack[2] = 0;
                attackCommand[0] = attackCommand[1];
                attackCommand[1] = attackCommand[2];
                attackCommand[2] = 0;
            }
            // 根据人物朝向确定攻击方向，改变xturn。yturn，theta的方向
            attackCheck();
        } else {
            theta = 9;
            xTurn = 0;
            yTurn = 0;
            // 攻击指令数组为0，恢复原位。
        }
    }

    public void attackCheck() {
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
        if (RL || Math.abs(attackCommand[0]) == 3) {
            x1 = x + xTurn + 30;
            y1 = y + yTurn;
            x2 = x1 + 10;
            y2 = y1 + 10;

        } else {
            x2 = x + xTurn;
            x1 = x2 - 40;
            y1 = y + yTurn;
            y2 = y1 + 10;
        }

        for (Enemy e : JW.firer) {
            x3 = e.x - 60;
            y3 = (e.y - JW.mp.upLimited) * 15 + 50;

            x4 = x3 + 90;
            y4 = y3 + 50;
            if (y4 < y1 || y3 > y2) {

            } else if (x4 < x1 || x3 > x2) {


            } else {
                if (!e.bleeding) {
                    // 流血状态，无敌不受伤害
                    e.blood += 1;
                    if (RL) {
                        if (e.x <= 520) {
                            e.x += 30;
                        }
                    } else {
                        if (e.x >= 80) {
                            e.x -= 30;
                        }
                    }
                    e.RL = JW.hero.x < e.x;
                    e.bleeding = true;
                }
            }
        }
        x3 = JW.boss.x - 30;
        x4 = x3 + 60;
        y3 = JW.boss.y - 30;
        y4 = y3 + 60;
        if (y4 >= y1 && y3 <= y2) {
            if (x4 >= x1 && x3 <= x2) {
                JW.boss.blood += 1;
                if (RL) {
                    if (JW.boss.x < 560) {
                        JW.boss.x += 40;
                    }
                } else {
                    if (JW.boss.x > 40) {
                        JW.boss.x -= 40;
                    }
                }
                if (JW.boss.y > 30) {
                    JW.boss.y -= 30;
                }
            }
        }
    }

    public void swordSkill() {
        xRotate = 40;
        yRotate = 5;
        theta += 11;
        yTurn = 30;
        interval = 10;
        if (xTurn + x < -20) {
            attackCommand[0] = 3;
        } else if (x + xTurn > 620) {
            attackCommand[0] = -3;
        }
        if (attackCommand[0] > 0) {
            xTurn += 3;
            // 剑往右，攻击方向为右
            RL = true;
        } else {
            xTurn -= 3;
            // 剑往左，攻击往左
            RL = false;
        }
        if (attack[0] == 1 || Math.abs(xTurn + xRotate) <= 1) {
            xRotate = 0;
            yRotate = 0;
            theta += 0;
            interval = 70;
            attack[0] = 1;
        }
    }
}

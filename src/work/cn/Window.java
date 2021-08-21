package work.cn;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Window extends JFrame {

    BOSS boss;
    boolean changeScene = false;
    int pressCounter = 0;
    ArrayList<Firebullet> bossBullet = new ArrayList<Firebullet>();

    Hero hero;

    Sword heroSword;

    Hero otherhero;

    boolean change = false;

    JButton entranceButton = new JButton("開始遊戲");

    boolean yNot = false;

    Object lock = new Object();

    CreatMap mp = new CreatMap();

    ArrayList<Enemy> firer = new ArrayList<Enemy>();
    ArrayList<FlySword> manSword = new ArrayList<FlySword>();


    public Window() {
        this.setTitle("火柴人大战");
        this.setDefaultCloseOperation(3);
        JPanel JP = new JPanel();
        JP.setLayout(null);
        JP.setBackground(Color.white);
        ImageIcon imag1 = new ImageIcon("src/image/bg5.gif");
        JLabel back = new JLabel(imag1);
        back.setBounds(140, 170, 310, 170);
        JP.add(back);
        ImageIcon imag3 = new ImageIcon("src/image/fireWorker.jpg");
        JLabel gName = new JLabel(imag3);
        gName.setBounds(170, 10, 233, 66);
        JP.add(gName);
        entranceButton.setFont(new Font(Font.MONOSPACED, Font.BOLD, 24));
        entranceButton.setForeground(Color.red);
        entranceButton.setBounds(200, 100, 160, 26);
        entranceButton.setBackground(Color.white);
        entranceButton.setContentAreaFilled(true);
        entranceButton.setBorderPainted(false);
        JP.add(entranceButton);
        this.setBounds(380, 200, 600, 360);
        this.setContentPane(JP);
        WindowListener listener = new WindowListener();
        listener.setWindow(this);
        this.addKeyListener(listener);
        this.setFocusable(true);
    }

    public void setGame() {
        hero = new Hero(this);
        Enemy f;
        for (int i = 0; i < 10; i++) {
            f = new Enemy(this);
            firer.add(f);
        }
        boss = new BOSS(this);
        heroSword = new Sword(this);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        if (yNot == true) {
            BufferedImage image = (BufferedImage) this.createImage(this.getSize().width, this.getSize().height);
            Graphics2D f = (Graphics2D) image.getGraphics();
            if (!changeScene) {
                Image background = new ImageIcon("src/image2/BGblack.png").getImage();
                f.drawImage(background, 0, -1072 + (99 - mp.downLimited) * 15, 600, 1422, null);
            } else {
                Image background = new ImageIcon("src/image2/BG.png").getImage();
                f.drawImage(background, 0, 0, 600, 360, null);
            }
            Image bg = new ImageIcon("src/image2/阶梯.png").getImage();
            for (int i = mp.downLimited; i >= mp.upLimited; i--) {
                for (int j = 0; j < 50; j++) {
                    if (mp.map[i][j] == 1) {
                        f.drawImage(bg, (j) * 50, (i - mp.upLimited) * 15, 100, 15, null);
                    }
                }
            }

            for (int i = 0; i < firer.size(); i++) {
                if (firer.get(i).blood < 18) {
                    Image enemyImage = new ImageIcon("src/image3/" + firer.get(i).e + ".png").getImage();
                    f.drawImage(enemyImage, firer.get(i).x - 90, (firer.get(i).y - mp.upLimited) * 15, 150, 150, null);
                    Image blood = new ImageIcon("src/blood/enemyblood" + firer.get(i).blood + ".png").getImage();
                    f.drawImage(blood, firer.get(i).x - 50, (firer.get(i).y - mp.upLimited) * 15 + 20, 80, 30, null);
                } else {
                    firer.remove(i);
                }
            }

            for (int i = 0; i < manSword.size(); i++) {
                if (!manSword.get(i).death) {
                    f.rotate(manSword.get(i).theta, manSword.get(i).x, manSword.get(i).y);
                    Image sword = new ImageIcon("src/image2/剑圣3.png").getImage();
                    f.drawImage(sword, manSword.get(i).x - 5, manSword.get(i).y - 40, 10, 80, null);
                    f.rotate(-manSword.get(i).theta, manSword.get(i).x, manSword.get(i).y);
                } else {
					manSword.remove(manSword.get(i));
				}
            }


            int a = 0;
            if (hero.RL == false) {
				a = 100;
			} else {
				a = 0;
			}
            Image heroPicture = new ImageIcon("src/image2/剑圣" + (hero.a + a) + ".png").getImage();
            f.drawImage(heroPicture, hero.x, hero.y, 60, 90, null);
            int b = 0;
            if (hero.RL) {
                b = 0;
            } else {
				b = 30;
			}
            Image ability = new ImageIcon("src/blood/ability.png").getImage();
            f.drawImage(ability, hero.x + b - 10, hero.y, hero.ability / 4, 5, null);
            // ????????
            f.rotate(heroSword.theta * Math.PI / 18, heroSword.x + heroSword.xTurn + heroSword.xRotate, heroSword.y + heroSword.yTurn + heroSword.yRotate);
            Image sword = new ImageIcon("src/image2/剑圣1.png").getImage();
            f.drawImage(sword, heroSword.x + heroSword.xTurn, heroSword.y + heroSword.yTurn, 80, 10, null);
            f.rotate(-heroSword.theta * Math.PI / 18, heroSword.x + heroSword.xTurn + heroSword.xRotate, heroSword.y + heroSword.yTurn + heroSword.yRotate);

            if (hero.flyBySword) {
                b = 0;
                if (hero.RL) {
                    sword = new ImageIcon("src/image2/剑圣1.png").getImage();
                } else {
                    b = 30;
                    sword = new ImageIcon("src/image2/剑圣2.png").getImage();
                }


                f.drawImage(sword, hero.x - 20 + b, hero.y + 80, 80, 10, null);
            }

            if (change) {
                if (boss.width >= 160) {
                    for (int i = 0; i < 50; i++) {
                        for (int j = 4; j < 99; j++) {
                            mp.map[j][i] = 0;
                        }
                    }
                    change = false;
                    changeScene = true;
                    mp.downLimited = 99;
                    mp.upLimited = 76;
                    mp.middle = 76;

                }
            }
            f.rotate(boss.theta, boss.x, boss.y);
            Image BossImage = new ImageIcon("src/BOSS/" + boss.number + ".png").getImage();
            f.drawImage(BossImage, boss.x - boss.width / 2, boss.y - boss.height / 2, boss.width, boss.height, null);
            f.rotate(-boss.theta, boss.x, boss.y);
            Image blood = new ImageIcon("src/blood/enemyblood" + boss.blood + ".png").getImage();
            f.drawImage(blood, -10, 20, boss.height * 3, 30, null);
            for (int i = 0; i < bossBullet.size(); i++) {
                Firebullet e = bossBullet.get(i);
                if (e.number > 5) {
                    bossBullet.remove(e);
                } else {
                    f.rotate(e.theta, e.x, e.y);
                    BossImage = new ImageIcon("src/burnt/burnt" + e.number + ".png").getImage();
                    f.drawImage(BossImage, e.x - e.width / 2, e.y - e.height / 2, e.width, e.height, null);
                    f.rotate(-e.theta, e.x, e.y);
                }
            }

            if (boss.blood >= 18) {
                BossImage = new ImageIcon("src/ending/gameover.png").getImage();
                f.drawImage(BossImage, 0, 30, 600, 380, null);
            }
            g.drawImage(image, 0, 0, null);
        }


    }
}
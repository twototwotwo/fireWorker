package work.cn;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class CreatMap {
    int[][] map = new int[100][50];
    int upLimited = 76;
    int downLimited = 99;
    int middle = 76;

    public CreatMap() {
        File file = new File("src/file/map.txt");
        try {
            FileReader in = new FileReader(file);
            BufferedReader reader = new BufferedReader(in);
            String s;
            for (int i = 0; i < 100; i++) {
                s = reader.readLine();
                String[] values = s.split("，");
                for (int j = 0; j < 49; j++) {
                    map[i][j] = Integer.parseInt(values[j]);
                }
            }
            reader.close();

        } catch (IOException e) {
            System.out.println("File read Error " + e);
        }
    }
}

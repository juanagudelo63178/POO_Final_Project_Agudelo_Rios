package data;

import java.time.LocalDateTime;

public class DataManager {

    private int saveCount;

    public DataManager() {
        saveCount = 0;
    }

    public void saveData() {
        saveCount++;

        System.out.println(
            "Data saved successfully at: "
            + LocalDateTime.now()
        );

        System.out.println(
            "Total saves: " + saveCount
        );
    }
}
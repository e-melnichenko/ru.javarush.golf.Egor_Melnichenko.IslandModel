package island;

import java.util.Iterator;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Island {
    public Location[][] area;
    public static final int WIDTH = 3;
    public static final int HEIGHT = 3;
    public Island() {
        area = new Location[WIDTH][HEIGHT];
    }

    public void print() {
        for (int i = 0; i < area.length; i++) {
            for (int j = 0; j < area[i].length; j++) {
                System.out.print(i + ":" + j + " "+ area[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("----------------------------");
    }

    public void generate() {
        for (int i = 0; i < area.length; i++) {
            for (int j = 0; j < area[i].length; j++) {
                area[j][i] = new Location(i, j);
            }
        }
    }

    public void start() {
        while (true) {
            try {
                moveAnimals();
                print();
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
//        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);
//        Runnable task = new Runnable() {
//            @Override
//            public void run() {
//                moveAnimals();
//                print();
//            }
//        };
//
//        scheduledExecutorService.scheduleWithFixedDelay(task, 0, 2, TimeUnit.SECONDS);
    }

    private void moveAnimals() {
//         todo iterator
        for (int i = 0; i < area.length; i++) {
            for (int j = 0; j < area[i].length; j++) {
//                 todo getter
                final int x = i;
                final int y = j;
//                 todo iterator
                Iterator<Herbivour> iterator = area[i][j].herbivourList.iterator();
                while(iterator.hasNext()) {
                    Herbivour herbivour = iterator.next();
                    herbivour.move(new int[]{x, y}, this);
                    iterator.remove();
                }
//                area[i][j].herbivourList.forEach(herbivour -> herbivour.move(new int[]{x, y}, this));
            }
        }
    }
}

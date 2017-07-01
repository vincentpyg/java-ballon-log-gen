package vincentg.driver;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author vincentg
 */
public class Main {

    @Parameter(names = {"-n"}, required = true)
    private int numberOfLogs = 0;


    public static void main(String... args) {
        Main main = new Main();
        JCommander.newBuilder().addObject(main).args(args).build();
        main.run();
    }


    private void run(){

        int lastAUX = 1;
        int lastAUY = 2;

        int lastUSX = 1;
        int lastUSY = 2;

        int lastFRX = 1;
        int lastFRY = 2;

        int lastOtherX = 1;
        int lastOtherY = 2;

        // 2014-12-31T13:44|10,5|243|AU
        LocalDateTime localDateTime = LocalDateTime.now();

        ThreadLocalRandom rand = ThreadLocalRandom.current();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("tmp/input.txt"))) {
            for (int x = 0; x < numberOfLogs; x++) {
                String record;
                int temperature = rand.nextInt(-36,36); //celsius
                switch (rand.nextInt(1, 6)) {
                    case 1:
                        record = (++lastAUX)+","+(++lastAUY)+"|"+temperature+"|AU";
                        break;
                    case 2:
                        record = (++lastUSX)+","+(++lastUSY)+"|"+(temperature*1.8+32)+"|US";
                        break;
                    case 3:
                        record = (++lastFRX)+","+(++lastFRY)+"|"+(temperature+273.15)+"|FR";
                        break;
                    case 4:
                        record = (++lastOtherX)+","+(++lastOtherY)+"|"+(temperature+ 273.15)+"|OTHER";
                        break;
                    default:
                        record = "INVALID|"+(temperature+ 273.15)+"|"+(++lastOtherX)+","+(++lastOtherY);
                        break;
                }
                localDateTime = localDateTime.plusMinutes(1);
                record = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'hh:mm"))+"|"+record;
                bw.write(record+System.lineSeparator());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

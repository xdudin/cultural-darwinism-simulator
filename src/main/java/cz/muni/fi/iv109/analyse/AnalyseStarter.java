package cz.muni.fi.iv109.analyse;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.muni.fi.iv109.setup.Disposition;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@SuppressWarnings("SameParameterValue")
public class AnalyseStarter {

    public static void main(String[] args) throws Exception {

        int numberOfCores = Runtime.getRuntime().availableProcessors();
        try(ExecutorService executor = Executors.newFixedThreadPool(numberOfCores)) {

            int granularity = 5;

            List<Future<ResultEntry>> futures = new ArrayList<>(granularity * granularity);
            submitTasks(granularity).forEach(task -> futures.add(executor.submit(task)));

            long l = System.currentTimeMillis();

            ResultEntry[][] results = new ResultEntry[granularity][granularity];
            for (Future<ResultEntry> future : futures) {
                ResultEntry resultEntry = future.get();

                int x = resultEntry.resultGrid_x();
                int y = resultEntry.resultGrid_y();

                results[x][y] = resultEntry;
            }

            System.out.println("total: " + (System.currentTimeMillis() - l) / 1000 + "s");

            export(results, Disposition.RANDOM, granularity);
        }
    }

    /**
     * @param granularity number of cells in result grid
     */
    private static List<SimulationTask> submitTasks(int granularity) {
        List<SimulationTask> tasks = new ArrayList<>();
        float delta = (4f - 1f) / (granularity - 1f);

        for (int x = 0; x < granularity; x++) {
            for (int y = 0; y < granularity; y++) {
                tasks.add(new SimulationTask(
                        1 + x * delta,
                        1 + y * delta,
                        x,
                        y
                ));
            }
        }

        return tasks;
    }

    private static void export(ResultEntry[][] results, Disposition disposition, int granularity) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        Files.createDirectories(Path.of("result"));
        String filename = String.format("%s_%dx%d", disposition.toString().toLowerCase(), granularity, granularity);

        objectMapper.writeValue(Path.of("result", filename + ".json").toFile(), results);
    }
}

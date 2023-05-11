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
import java.util.function.Function;

@SuppressWarnings("SameParameterValue")
public class AnalyseStarter {

    private static final int GRANULARITY = 2;
    private static final Disposition DISPOSITION = Disposition.RANDOM;

    public static void main(String[] args) throws Exception {

        int numberOfCores = Runtime.getRuntime().availableProcessors();
        try(ExecutorService executor = Executors.newFixedThreadPool(numberOfCores)) {

            List<Future<ResultEntry[]>> futures = new ArrayList<>(GRANULARITY * GRANULARITY);
            submitTasks(GRANULARITY).forEach(task -> futures.add(executor.submit(task)));

            long start = System.currentTimeMillis();

            List<ResultEntry[]> results = new ArrayList<>(GRANULARITY * GRANULARITY);
            for (Future<ResultEntry[]> future : futures) {
                results.add(future.get());
            }

            long totalMilliseconds = System.currentTimeMillis() - start;
            long millisecondPerSimulation = totalMilliseconds / (GRANULARITY * GRANULARITY * SimulationTask.NUMBER_OF_ROUNDS);
            System.out.println("total " + totalMilliseconds / 1000 + " sec");
            System.out.println(millisecondPerSimulation + " ms per simulation");


            float[][] averageCultureResults = extractValue(results, GRANULARITY, ResultEntry::averageCulture);
            export(averageCultureResults, "ac");

            float[][] absoluteAverageCultureResults = extractValue(
                    results,
                    GRANULARITY,
                    resultEntry -> Math.abs(resultEntry.averageCulture())
            );
            export(absoluteAverageCultureResults, "aac");

            export(results, "full");
        }
    }

    private static float[][] extractValue(
            List<ResultEntry[]> resultsList,
            int granularity,
            Function<ResultEntry, Float> valueExtractor
    ) {
        float[][] results = new float[granularity][granularity];
        for (ResultEntry[] resultEntries : resultsList) {

            int x = resultEntries[0].assimilationFactor_x();
            int y = resultEntries[0].fertilityFactor_y();

            float averageCulturePerRound = 0;
            for (ResultEntry resultEntry : resultEntries) {
                averageCulturePerRound += valueExtractor.apply(resultEntry);
            }
            averageCulturePerRound /= resultEntries.length;

            results[x][y] = averageCulturePerRound;
        }

        return results;
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
                        DISPOSITION,
                        1 + x * delta,
                        1 + y * delta,
                        x,
                        y
                ));
            }
        }

        return tasks;
    }

    /**
     * export format <br>
     * assimilation factor - x-axes <br>
     * fertility factor - y-axes <br>
     * <br>
     * &nbsp&nbsp a s s i m i l a t i o n ➔ <br>
     * f 1;1 &nbsp&nbsp&nbsp&nbsp&nbsp 1.2;1 &nbsp&nbsp&nbsp&nbsp&nbsp 1.4;1 &nbsp&nbsp ...<br>
     * e 1;1.2 &nbsp 1.2;1.2 &nbsp&nbsp 1.4;1.2 ...<br>
     * r 1;1.4 &nbsp&nbsp ...<br>
     * t ... <br>
     * i <br>
     * l <br>
     * i <br>
     * t <br>
     * y <br>
     * 🠗 <br>
     */
    private static void export(
            Object results,
            String fileNameSuffix
    ) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        Path analysePath = Path.of("analyse", "result", "result_60x60x10", "data_raw");
        Files.createDirectories(analysePath);
        String filename = String.format(
                "%s_%dx%d_%s", DISPOSITION.toString().toLowerCase(), GRANULARITY, GRANULARITY, fileNameSuffix
        );

        Path resultPath = analysePath.resolve(Path.of(filename + ".json"));
        objectMapper.writeValue(resultPath.toFile(), results);
    }
}

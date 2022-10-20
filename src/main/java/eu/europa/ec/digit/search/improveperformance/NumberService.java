package eu.europa.ec.digit.search.improveperformance;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class NumberService {

    private static final int SAMPLE_SIZE = 100_000;
    private Random random = new Random();

    public Integer findSmallestDuplicate(List<Integer> data) {

        List<Integer> duplicates = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {

            for (int j = i + 1; j < data.size(); j++) {

                if (data.get(i).equals(data.get(j))) {

                    log.info("found duplicate {}", data.get(j));
                    duplicates.add(data.get(j));
                }
            }
        }

        return duplicates.stream().sorted().findFirst().orElse(null);

    }

    public Integer findSmallestDuplicateImproved(List<Integer> data) {
        // use a HashSet so that adding and checking if an element is present
        // takes O(1) and then traverse the list of data to check the duplicates
        // using this HashSet; thus, the algorithm runs in O(n) time (but uses O(n) space)
        HashSet<Integer> hashData = new HashSet<>();
        int min = Integer.MAX_VALUE;
        int size = data.size();

        for (int i = 0; i < size; i++) {
            int item = data.get(i);
            if (!hashData.add(item)) {
                // a duplicate
                if (item < min) {
                    // found a new minimum
                    min = item;
                }
            }
        }

        return (min < Integer.MAX_VALUE) ? min : null;
    }

    // this method makes an important assumption: the items in the list
    // are in the range 0 .. SAMPLE_SIZE
    public Integer findSmallestDuplicateImproved2(List<Integer> data) {
        // keep an array of frequencies
        int freq[] = new int[SAMPLE_SIZE];

        int min = Integer.MAX_VALUE;

        int s = data.size();
        for (int i = 0; i < s; i++) {
            int d = data.get(i);
            // count the frequency of each item
            freq[d]++;

            if (freq[d] > 1) {
                // it's a duplicate
                if (d < min) min = d;
            }
        }

        return (min < Integer.MAX_VALUE) ? min : null;
    }

    public List<Integer> generateData() {

        List<Integer> data = IntStream.range(0, SAMPLE_SIZE).boxed().collect(toList());
        
        data.add(data.get(random.nextInt(data.size())));
        log.info("first duplicate number is: {}", data.get(data.size() - 1));
        data.add(data.get(random.nextInt(data.size())));
        log.info("second duplicate number is: {}", data.get(data.size() - 1));
        Collections.shuffle(data);

        return data;
    }
}

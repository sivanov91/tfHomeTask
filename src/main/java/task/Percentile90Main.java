package task;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import task.percentile.service.PhotoService;

import java.util.List;

import static java.util.stream.Collectors.toList;


public class Percentile90Main {

    public static void main(String ... args) {

        // Количество страниц.
        int pageCount = 3;
        // Количество элементов на странице.
        int size = 400;
        // Количество потоков
        int threadCount = 3;

        PhotoService photoService = new PhotoService();

        // Получаем данные о последних pageCount*size загруженных фотографиях в threadCount потоках.
        List<Integer> photoSList = Flux.range(0, pageCount)
                .parallel(threadCount)
                .runOn(Schedulers.parallel())
                .map(page -> photoService.getPhotos(page, size))
                .map(l -> l.stream().map(p -> p.getWidth() * p.getHeight()).collect(toList()))
                .sequential().collectList().block()
                .stream().flatMap(List::stream).sorted().collect(toList());

        // Вычисляем 90-й перцентиль площади
        // (Предпологается, что в сервис не может вернуть меньше pageCount * size элементов)
        // Если необходимо, персентиль можно также расчитывать в многопоточно режиме,
        // но учитывая затраты памяти/времяни на загрузку фотографий это не очень актуально.
        Integer percentile90 = photoSList.get(pageCount * size / 100 * 90);

        System.out.println(String.format("90-й перцентиль площади: %s", percentile90));

    }

}

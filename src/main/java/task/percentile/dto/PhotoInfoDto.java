package task.percentile.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Содержит информацию о метаданных одной фотографии.
 * (Т.к. в рамках задачи нам нужна только ширина и высота, остальные параметры игнорируем)
 */
@Getter
@Setter
public class PhotoInfoDto {

    /** Ширена фотограции. */
    private Integer width;

    /** Высота фотографии. */
    private Integer height;

}

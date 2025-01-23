package hexlet.code.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UrlModel {

    private Long id;
    private String name;
    private LocalDateTime createdAt;

    public UrlModel(String name) {
        this.name = name;
    }
}

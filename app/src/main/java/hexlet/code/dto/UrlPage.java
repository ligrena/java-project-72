package hexlet.code.dto;

import hexlet.code.model.UrlCheckModel;
import hexlet.code.model.UrlModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class UrlPage extends BasePage {

    private UrlModel url;
    private List<UrlCheckModel> urlCheck;
}

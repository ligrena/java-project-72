package hexlet.code.dto;

import hexlet.code.model.UrlModel;
import java.util.List;
import java.util.Map;

import hexlet.code.model.UrlCheckModel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UrlsPage extends BasePage {
    private List<UrlModel> urls;
    private Map<Long, UrlCheckModel> urlChecks;
}

@import hexlet.code.dto.UrlsPage
@import hexlet.code.dto.UrlPage
@import hexlet.code.service.DateService

@param UrlsPage page
@param UrlPage сheckUrl

@template.layouts.page(
page = page,
content = @`
    <main class="flex-grow-1">
        <section>
            <div class="container-lg mt-5">
                <h1>Сайты</h1>
                <table class="table table-bordered table-hover mt-3">
                    <thead>
                    <tr>
                        <th class="col-1">ID</th>
                        <th>Имя</th>
                        <th class="col-2">Последняя проверка</th>
                        <th class="col-1">Код ответа</th>
                    </tr>
                    </thead>
                    <tbody>
                    @for(var url : page.getUrls())
                        <tr>
                            <td>${url.getId()}</td>
                            <td><a href="/urls/${url.getId()}" target="_blank">${url.getName()}</a></td>
                            <td>
                                @if(page.getUrlChecks().containsKey(url.getId()))
                                    ${DateService.getFormattedCreatedAt(page.getUrlChecks().get(url.getId()).getCreatedAt())}
                                @endif
                            </td>
                            <td>
                                @if(page.getUrlChecks().containsKey(url.getId()))
                                    ${page.getUrlChecks().get(url.getId()).getStatusCode()}
                                @endif
                            </td>
                        </tr>
                    @endfor
                    </tbody>
                </table>
            </div>
        </section>
    </main>
`
)
## github-tester

### Описание проекта

Проект GitHub Tester - это автоматизированный набор тестов для проверки функциональности и надежности GitHub.
Он включает в себя API-тесты для взаимодействия с GitHub API и UI-тесты для проверки пользовательского интерфейса.

[<img src=".github/logo/java.png" alt="java_8" width="85" height="85"/>](https://www.oracle.com/ru/java/technologies/javase-jre8-downloads.html)
[<img src=".github/logo/gradle.png" alt="gradle" width="85" height="85"/>](https://gradle.org/)
[<img src=".github/logo/junit_5.png" alt="junit_5" width="85" height="85"/>](https://junit.org/junit5/)
[<img src=".github/logo/selenide.png" alt="selenide" width="85" height="85"/>](https://ru.selenide.org/)
[<img src=".github/logo/allure.png" alt="allure" width="85" height="85"/>](https://docs.qameta.io/allure/)
[<img src=".github/logo/jenkins.png" alt="jenkins" width="85" height="85"/>](https://www.jenkins.io/)
[<img src=".github/logo/selenoid.png" alt="selenoid" width="85" height="85"/>](https://aerokube.com/selenoid/latest/)

Проект представляет собой набор автоматизированных тестов, включающих API- и UI-тесты. Он разделен на следующие модули:

API-тесты:

1. **CreationRepositoryTest** - cоздания репозитория.
    - `testCreateRepository()`: cоздания репозитория.
    - `testCreateRepositoryWithExistingName`: cоздания репозитория с уже существующим именем.
2. **DownloadAndReadRepositoryTest** - cкачивания и чтения содержимого репозитория.
    - `testDownloadAndReadFileContentFromZipReadme`: cкачивания и чтения содержимого файла README из ZIP-архива.
    - `testDownloadAndReadNonexistentFileFromZip`: cкачивания и чтения содержимого несуществующего файла из ZIP-архива.
3. **ListOfUserRepositoriesTest** - получения списка репозиториев пользователя.
    - `testListOfUserRepositories`: список репозиториев пользователя.
4. **RenameRepositoryTest** - переименования репозитория.
    - `renameRepository`: успешное переименования репозитория.
5. **UploadFileRepositoryTest** - загрузки файла в репозиторий.
    - `UploadFileTest`: успешная загрузки файла в репозиторий.

UI-тесты:

1. **CheckProjectTest** - проверка комплектность данных проекта и наличие основных компонентов.
    - `testSearchInDownloadedFile`: поиск ключевых слов в скачанном файле.
    - `testForEmptyStrings`: проверка на избыточные пустые строки подряд.
2. **LoginTest** - авторизации.
    - `testSuccessfulLogin`: успешный вход в систему.
    - `testLoginWithInvalidEmail`: вход в систему с недопустимым email.
    - `testLoginWithInvalidPassword`: вход в систему с недопустимым паролем.
3. **ScreenShootTest** - скриншоты страниц.
    - `testMainPageHeader`: скриншот заголовка главной страницы.
    - `testLoginBody`: скриншот тела формы входа.
4. **UnauthenticatedTests** - включает в себя тесты для неаутентифицированных пользователей.
    - `testMainPageLoads`: загрузка главной страницы.
    - `testSearch`: поиск на главной странице.
    - `testAdvancedSearch`: расширенноый поиск.

## Настройка и запуск тестов

Вы можете запустить тесты локально или удаленно. Для конфигурации тестов вы можете использовать следующие системные свойства:

- `selenide.pageLoadStrategy` - стратегия загрузки страницы (по умолчанию "eager").
- `ownerName` - имя владельца репозитория (по умолчанию "SLomako").
- `repoUnderTest` - репозиторий для тестирования (по умолчанию "SLomako/github-tester").
- `searchRepo` - репозиторий для поиска (по умолчанию "SLomako/github-tester").
- `searchResult` - ожидаемый результат поиска (по умолчанию "github-tester").
- `advancedSearchLanguage` - язык для расширенного поиска (по умолчанию "Java").
- `advancedSearchFrom` - источник для расширенного поиска (по умолчанию "Slomako").
- `advancedSearchDate` - дата для расширенного поиска (по умолчанию "2023").

### Локальный запуск

Для локального запуска тестов используйте команду:

```bash
gradle clean test
```

### Удаленный запуск

Для удаленного запуска тестов используйте команду:

```bash
gradle clean test -DisRemote=true
```

### Запуск с Jenkins

Если вы используете Jenkins для запуска тестов, вы можете использовать параметризованную сборку с следующими параметрами:

- `BASE_URL` - окружение (например, "https://github.com").
- `BROWSER_AND_VERSION` - браузер и версия в Selenoid (например, "chrome,100.0").
- `BROWSER_SIZE` - разрешение экрана (например, "1920x1080").
- `REPO_UNDER_TEST` - выбор репозитория для проверки требований к проекту (например, "SLomako/github-tester").

### Файл test.properties

Для запуска тестов локально на вашем компьютере, вам необходимо добавить файл test.properties в папку ресурсов (test/src/resources/config/). В этом файле необходимо указать следующие свойства:

```bash 
login.gitHub=Ваш_логин_GitHub
password.gitHub=Ваш_пароль_GitHub
username.selenoid=Ваш_логин_Selenoid
password.selenoid=Ваш_пароль_Selenoid
api.token.github=Ваш_токен_GitHub
ui.browserWithVersion=Ваш_браузер_и_версия
ui.browserSize=Размер_экрана
ui.baseURL=Базовый_URL
ui.remoteURL=URL_удаленного_хоста
ui.isRemote=Запуск_на_удаленном_хосте
```

### Скриншоты 

<p align="center">  
    <img src=".github/sc/jenkins1.png" width="950" style="display:inline;"/> 
    <img src=".github/sc/jenkins2.png" width="310" style="display:inline;"/>  
</p>




[![Telegram](.github/logo/telegram1.svg)](https://t.me/lom14)
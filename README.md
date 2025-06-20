# GeoCompare API

Java Spring Boot-приложение, которое получает координаты заданного адреса из двух разных геосервисов — **Yandex Maps API** и **Dadata API** — и рассчитывает расстояние между ними. Проект предназначен для демонстрации работы с внешними API и обработки геоданных.

---

## Стек технологий

Java 17, Spring Boot 3.x, Maven, MySQL (в контейнере Docker), Docker & Docker Compose, REST API (JSON), Lombok, Yandex Maps API, Dadata API

---

## Установка и запуск

### 1. Клонируйте репозиторий

```bash
git clone https://github.com/vlad65253/calculate-coordinate-ya-and-dadata.git
cd calculate-coordinate-ya-and-dadata
```

### 2. Соберите проект

```bash
mvn clean package
```

### 3. Запустите проект через Docker Compose

```bash
docker-compose build
docker-compose up
```

### 4. Ключи API уже зашиты в application.properties. При необходимости замените на свои:

```
dadata.token=*************
dadata.secret=*************
yandex.apiKey=*************
```

### 5. Доступ к приложению
```
http://localhost:8080
```

## API

| URL                                  | HTTP - метод | Описание                                                                                                   |
|--------------------------------------|--------------|------------------------------------------------------------------------------------------------------------|
| /compare                             | POST         | Принимает текстовый адрес, возвращает координаты от Яндекс и Dadata, а также расстояние между ними.        |

# Selenium Cucumber Test Automation Project

Bu proje, Page Object Model (POM) tasarım deseni kullanılarak oluşturulmuş bir Selenium-Cucumber test otomasyon framework'üdür.

## Proje Yapısı

```
src
├── test
│   ├── java
│   │   ├── pages           # Page Object sınıfları
│   │   ├── stepdefinitions # Cucumber step tanımlamaları
│   │   ├── runners         # Test koşturucu sınıfları
│   │   └── utils          # Yardımcı sınıflar
│   └── resources
│       └── features       # Cucumber feature dosyaları
```

## Gereksinimler

- Java 11 veya üzeri
- Maven
- Chrome veya Firefox tarayıcı

## Kurulum

1. Projeyi klonlayın
2. Maven bağımlılıklarını yükleyin:
   ```
   mvn clean install
   ```

## Testleri Çalıştırma

Tüm testleri çalıştırmak için:
```
mvn test
```

Belirli bir feature dosyasını çalıştırmak için:
```
mvn test -Dcucumber.options="--tags @tag_name"
```

## Raporlama

Test raporları `target/cucumber-reports` dizininde oluşturulur.

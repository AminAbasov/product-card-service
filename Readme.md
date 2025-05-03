# Backend Developer Task

## 🎯 Məqsəd

Bu layihənin məqsədi müxtəlif texnologiyaları tətbiq edərək kiçik bir RESTful API tətbiqi yaratmaqdır. Tətbiq aşağıdakı texnologiyaları və anlayışları əhatə edir:

- **Docker** ilə mühitin konteynerləşdirilməsi
- **PostgreSQL** verilənlər bazası
- **Redis** ilə caching əməliyyatı
- **Cloudinary** ilə media fayl saxlanılması
- **Swagger** ilə API sənədləşməsi
- **CRUD** əməliyyatları olan REST API-lar

## 🛠️ Texnologiyalar

Bu layihə aşağıdakı texnologiyalarla hazırlanıb:

- **Java / Spring Boot**
- **PostgreSQL** (Docker konteynerində verilənlər bazası)
- **Redis** (Docker konteynerində Keşləmə)
- **Cloudinary** (Şəkil yükləmə üçün)
- **Swagger / OpenAPI** (API sənədləşməsi üçün)
- **Docker Compose** (Bütün mühitin ayağa qaldırılması üçün)

## 📁 Model Strukturları

### ✅ **Product**

- `productName`: Məhsulun adı (String)
- `price`: Məhsulun qiyməti (Double)
- `productImages`: Məhsul şəkilləri (List) - Cloudinary istifadə olunur.

### ✅ **Card**

- `cardNumber`: 16 rəqəmli string/number (Validation tələb olunur)
- `expireDate`: MM/YY formatında tarix (Validation tələb olunur)
- `cvv`: 3 rəqəmli string/number (Validation tələb olunur)

## 🌐 API Funksionallığı

### 🛒 **Product API**

1. **Create Product**  
   Yeni məhsul yaradılır və Cloudinary ilə şəkil yüklənir.

2. **Get All Products**  
   Bütün məhsulların siyahısı alınır.

3. **Get Product by ID**  
   ID ilə bir məhsulun detalları alınır.

4. **Update Product**  
   Var olan məhsul yenilənir.

5. **Delete Product**  
   Məhsul silinir.

### 💳 **Card API**

1. **Create Card**  
   Yeni kart yaradılır.

2. **Get All Cards**  
   Bütün kartlar alınır.

3. **Get Card by ID**  
   ID ilə bir kartın detalları alınır.

4. **Update Card**  
   Var olan kart yenilənir.

5. **Delete Card**  
   Kart silinir.

### 🧠 **Redis API**

1. **POST /cache/user**  
   JSON daxilində `email` və `adSoyad` alınır və Redis-də 10 dəqiqəlik yadda saxlanılır.  
   Məsələn:

   ```json
   {
     "email": "example@example.com",
     "adSoyad": "Ad Soyad"
   }
2. GET /cache/user/{email}
Email ilə axtarış edib Redis-dən adSoyad məlumatını qaytarır.

📦 Docker Tələbləri
docker-compose.yml Faylının Konfiqurasiyası
Bu layihə Docker Compose istifadə edərək 3 əsas servisi bir arada işlədirmək üçün qurulmuşdur:

app – Java Spring Boot tətbiqi

db – PostgreSQL verilənlər bazası (Card & Product cədvəlləri ilə)

redis – Redis (Keşləmə üçün)

Docker Mühitini Başlatmaq
Mühiti işə salmaq üçün aşağıdakı əmri istifadə edin:

bash
docker-compose up --build

📑 Swagger Dokumentasiyası
Bütün API-lar Swagger ilə sənədləşdirilmişdir. Swagger UI vasitəsilə API-ları test edə bilərsiniz. Swagger UI-yə aşağıdakı URL ilə daxil ola bilərsiniz:

plaintext
http://localhost:8080/swagger-ui.html

✅ Bonus Göstəricilər
Layihədə aşağıdakı xüsusiyyətlər tətbiq edilmişdir:

Request Validation: Card nömrəsi, CVV uzunluğu kimi məlumatlar doğrulanır.

Exception Handling: Xətalar düzgün şəkildə idarə olunur və istifadəçiyə uyğun mesajlar verilir.

Lombok: Kodun sadələşdirilməsi üçün Lombok istifadə edilmişdir.

DTO Layer: Məlumatların daşınması üçün DTO (Data Transfer Object) qatından istifadə edilmişdir.

Quraşdırma
1. Layihəni Klonlama
   Layihəni GitHub-dan klonlaya bilərsiniz:

bash
git clone https://github.com/amin-abc/backend-developer-task.git
2. Asılılıqları yükləyin
   Layihə üçün asılılıqları yükləmək üçün aşağıdakı əmri istifadə edə bilərsiniz:

bash
./gradlew build
3. Konfiqurasiya
   application.properties faylında aşağıdakı konfiqurasiyaları yoxlayın:

properties
spring.datasource.url=jdbc:postgresql://localhost:5432/your_db
spring.datasource.username=your_username
spring.datasource.password=your_password

spring.redis.host=localhost
spring.redis.port=6379
spring.redis.timeout=2000
4. Redis və PostgreSQL Quraşdırma
   Redis və PostgreSQL servisinin lokalda işlədiyinə əmin olun. Əgər Redis qurulmayıbsa, aşağıdakı əmri istifadə edərək onu quraşdıra bilərsiniz:

bash
sudo apt-get install redis-server
Sonra Redis serverini başladın:

bash
redis-server
PostgreSQL verilənlər bazasını lokalda qurun və lazımi cədvəlləri yaradın:

bash
psql -U postgres -d your_db < schema.sql

5. Proqramı İşə Salma
   Tətbiqi başlatmaq üçün aşağıdakı əmri istifadə edə bilərsiniz:

bash
./gradlew bootRun
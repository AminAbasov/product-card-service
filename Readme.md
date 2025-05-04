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


Bu layihə Spring Boot ilə yazılmış backend tətbiqidir və Cloudinary, Redis, PostgreSQL texnologiyalarından istifadə edir. Layihəni lokalda və ya Docker ilə işə sala bilərsiniz.

İlk öncə repozitoriyanı klonlayın:

git clone https://github.com/sizin-repo/backend-app.git
cd backend-app

Sonra asılılıqları yükləyin:

./gradlew build

Əgər lokal işlədəcəksinizsə, application.properties faylında aşağıdakı konfiqurasiyaların düzgünlüyünü yoxlayın:

spring.datasource.url=jdbc:postgresql://localhost:5432/your_db  
spring.datasource.username=your_username  
spring.datasource.password=your_password

spring.redis.host=localhost  
spring.redis.port=6379  
spring.redis.timeout=2000

cloudinary.cloud_name=your_cloud_name  
cloudinary.api_key=your_api_key  
cloudinary.api_secret=your_api_secret

Redis və PostgreSQL lokalda quraşdırılıbsa işlədə bilərsiniz. Əgər Redis qurulmayıbsa aşağıdakı əmr ilə quraşdırın:

sudo apt-get install redis-server  
redis-server

PostgreSQL üçün isə bazanı yaradın və struktur faylını tətbiq edin:

psql -U postgres -d your_db < schema.sql

Əgər bunlarla vaxt itirmək istəmirsinizsə, Docker Compose ilə hər şeyi avtomatik ayağa qaldıra bilərsiniz. Bunun üçün `.env.example` faylını əsas götürərək `.env` faylı yaradın:

cp .env.example .env

`.env.example` faylının tərkibi belədir:

CLOUDINARY_CLOUD_NAME=your_cloud_name  
CLOUDINARY_API_KEY=your_api_key  
CLOUDINARY_API_SECRET=your_api_secret

Sonra Docker ilə tətbiqi işə salın:

docker-compose up --build

Tətbiq http://localhost:9090 ünvanında işləyəcək.

Əgər lokalda işlətmək istəyirsinizsə, bu əmrdən istifadə edin:

./gradlew bootRun

Əgər sualınız olarsa, bizimlə əlaqə saxlaya bilərsiniz.

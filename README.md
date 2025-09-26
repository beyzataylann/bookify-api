#  Bookify API

**Bookify**, kullanıcıların kitapları yönetebileceği, ödünç alabileceği ve kullanıcı hesaplarını yönetebileceği bir **REST API servisidir**.  
Proje **Spring Boot** ile geliştirilmiştir.

---

##  Özellikler

-  **Kullanıcı Yönetimi**: Kayıt, giriş, kullanıcı bilgileri görüntüleme
-  **Kitap Yönetimi**: Ekleme, güncelleme, silme, listeleme
-  **Ödünç Alma İşlemleri**: Loan oluşturma, tarih kontrolü, iptal
-  **JWT Tabanlı Kimlik Doğrulama**
-  **Standart Response Mesajları** (başarılı & hata durumları)
-  **Postman ile test edilebilir**

---

##  Kurulum

1. Repository’yi klonlayın:
   ```bash
   git clone https://github.com/beyzataylann/bookify-api
   cd bookify
   ```

2. Gerekli bağımlılıkları yükleyin:
   ```bash
   mvn install
   ```

3. Uygulamayı başlatın:
   ```bash
   mvn spring-boot:run
   ```

 API artık `http://localhost:8080` üzerinde çalışır.

---

##  API Endpointleri

###  Kullanıcı İşlemleri
- **Register** → `POST /auth/register`
  ```json
  {
    "userEmail": "test@gmail.com",
    "password": "1234",
    "userName": "Admin",
    "userPhoneNumber": "5535550566",
    "role" : "ADMIN"
    
  }
  ```
- **Login** → `POST /auth/login`
  ```json
    {
    "userEmail": "admin@gmail.com",
    "password": "b123456"
    }
  ```
   Dönen JWT token, Postman’de `{token}` olarak kullanılabilir.

- **My Info** → `GET "/users/get-logged-in-profile-info`  
  Header: `Authorization: Bearer {{token}}`

- **Get All Users** → `GET /users/allUsers`
- **Get User by ID** → `GET /users/get-by-id/{userId}`
- **Delete User** → `DELETE /users/delete/{userId}`

---

###  Kitap İşlemleri
- **Get All Books** → `GET /books/allBooks`
- **Get Available Books** → `GET /books/all-available-books`
- **Get Book by ID** → `GET books//book-by-id/{bookId}`

- **Add Book** → `POST /books/add-book`
  ```json
  {
    "name": "Kitap1",
    "bookType": "Roman",
    "bookDescription": "Açıklama"
  }
  ```

- **Update Book** → `PUT /books/update/{bookId}`
  ```json
  {
    "name": "Yeni Kitap"
  }
  ```

- **Delete Book** → `DELETE /books/delete/{bookId}`

---

###  Ödünç Alma (Loans) İşlemleri
- **Save Loan** → `POST /loans/loans-book/{bookId}/{userId}`
  ```json
  {
    "loansDate": "2025-09-26",
    "returnDate": "2025-10-01"
  }
  ```

- **Get Loan by Confirmation Code** → `GET /loans/get-by-confirmation-code/{confirmationCode}`
- **Get All Loans** → `GET /loans/all-loans`
- **Cancel Loan** → `DELETE /loans/cancel/{loansId}`

---

##  Response Formatı

Tüm API cevapları standart JSON formatındadır:

```json
{
  "statusCode": 200,
  "message": "Başarılı",
  "data": {}
}
```

- ✅ `data` alanı başarılı işlemlerde doludur.
- ❌ Hatalarda `message` alanı açıklama içerir.

---

##  Postman Kullanımı

1. Postman uygulamasını açın.
2. Yeni bir koleksiyon oluşturun: **Bookify REST API**
3. Örnek endpointleri ekleyin:

```json
[
  {
    "name": "Register",
    "request": {
      "method": "POST",
      "header": [{ "key": "Content-Type", "value": "application/json" }],
      "body": {
        "mode": "raw",
        "raw": "{\n  \"userEmail\": \"test@gmail.com\",\n  \"password\": \"1234\",\n  \"userName\": \"Admin\",\n  \"userPhoneNumber\": \"5535550566\",\n  \"role\": \"ADMIN\"\n}"
      },
      "url": "http://localhost:8080/auth/register"
    }
  },
  {
    "name": "Login",
    "request": {
      "method": "POST",
      "header": [{ "key": "Content-Type", "value": "application/json" }],
      "body": {
        "mode": "raw",
        "raw": "{\n  \"userEmail\": \"admin@gmail.com\",\n  \"password\": \"b123456\"\n}"
      },
      "url": "http://localhost:8080/auth/login"
    }
  },
  {
    "name": "My Info",
    "request": {
      "method": "GET",
      "header": [{ "key": "Authorization", "value": "Bearer {{token}}" }],
      "url": "http://localhost:8080/users/get-logged-in-profile-info"
    }
  },
  {
    "name": "Get All Users",
    "request": {
      "method": "GET",
      "header": [{ "key": "Authorization", "value": "Bearer {{token}}" }],
      "url": "http://localhost:8080/users/allUsers"
    }
  },
  {
    "name": "Get User by ID",
    "request": {
      "method": "GET",
      "header": [{ "key": "Authorization", "value": "Bearer {{token}}" }],
      "url": "http://localhost:8080/users/get-by-id/1"
    }
  },
  {
    "name": "Delete User",
    "request": {
      "method": "DELETE",
      "header": [{ "key": "Authorization", "value": "Bearer {{token}}" }],
      "url": "http://localhost:8080/users/delete/1"
    }
  },
  {
    "name": "Get All Books",
    "request": {
      "method": "GET",
      "url": "http://localhost:8080/books/allBooks"
    }
  },
  {
    "name": "Get Available Books",
    "request": {
      "method": "GET",
      "url": "http://localhost:8080/books/all-available-books"
    }
  },
  {
    "name": "Get Book by ID",
    "request": {
      "method": "GET",
      "url": "http://localhost:8080/books/book-by-id/1"
    }
  },
  {
    "name": "Add Book",
    "request": {
      "method": "POST",
      "header": [{ "key": "Content-Type", "value": "application/json" }],
      "body": {
        "mode": "raw",
        "raw": "{\n  \"name\": \"Kitap1\",\n  \"bookType\": \"Roman\",\n  \"bookDescription\": \"Açıklama\"\n}"
      },
      "url": "http://localhost:8080/books/add-book"
    }
  },
  {
    "name": "Update Book",
    "request": {
      "method": "PUT",
      "header": [{ "key": "Content-Type", "value": "application/json" }],
      "body": {
        "mode": "raw",
        "raw": "{\n  \"name\": \"Yeni Kitap\"\n}"
      },
      "url": "http://localhost:8080/books/update/1"
    }
  },
  {
    "name": "Delete Book",
    "request": {
      "method": "DELETE",
      "url": "http://localhost:8080/books/delete/1"
    }
  },
  {
    "name": "Save Loan",
    "request": {
      "method": "POST",
      "header": [{ "key": "Content-Type", "value": "application/json" }],
      "body": {
        "mode": "raw",
        "raw": "{\n  \"loansDate\": \"2025-09-26\",\n  \"returnDate\": \"2025-10-01\"\n}"
      },
      "url": "http://localhost:8080/loans/loans-book/1/1"
    }
  },
  {
    "name": "Get Loan by Confirmation Code",
    "request": {
      "method": "GET",
      "url": "http://localhost:8080/loans/get-by-confirmation-code/ABC123"
    }
  },
  {
    "name": "Get All Loans",
    "request": {
      "method": "GET",
      "url": "http://localhost:8080/loans/all-loans"
    }
  },
  {
    "name": "Cancel Loan",
    "request": {
      "method": "DELETE",
      "url": "http://localhost:8080/loans/cancel/1"
    }
  }
]

```

4. `{token}` alanına login endpointinden aldığınız JWT tokenı ekleyin.

---

## 🛠 Kullanılan Teknolojiler

-  **Java 17**
-  **Spring Boot 3**
-  **Spring Security & JWT**
-  **Maven**
-  **MySQL / H2**
-  **Lombok**



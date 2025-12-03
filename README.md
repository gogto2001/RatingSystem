# ⭐ RatingSystem  
A modular Spring Boot application for managing users, sellers, comments, and game object ratings.  
Includes authentication, authorization, admin panel functionality, and seller analytics.


## 🚀 Features  
### 🔐 Authentication & Security
- Login / Register (User)
- Spring Security (JWT or Session — based on project setup)
- Role-based access (ADMIN / USER / SELLER)

### 📝 Comment & Rating System
- Users can create comments on sellers/game objects  
- Each comment includes rating value  
- Validation to prevent invalid ratings  
- Admin can moderate comments  

### 📊 Seller Analytics
- Average rating calculation  
- Total comments  
- Approved / pending comments  
- Top sellers endpoint (sorted by rating)

### 🧰 Admin Panel Capabilities
- Approve/Decline comments  
- Manage sellers  
- View statistics  
- Moderate content  

---

## 🧱 Project Architecture  
The application follows a clean layered structure:


 Database Schema

    USER {
        BIGINT id PK
        STRING first_name
        STRING last_name
        STRING email
        STRING password
        STRING role
        STRING seller_status
        INT rating_sum
        INT rating_count
        DOUBLE average_rating
    }

    COMMENT {
        BIGINT id PK
        STRING text
        INT rating
        BOOLEAN approved
        DATETIME created_at
        BIGINT author_id FK
        BIGINT target_seller_id FK
    }

    GAME_OBJECT {
        BIGINT id PK
        STRING title
        STRING description
        DATETIME created_at
        BIGINT seller_id FK
    }

    USER ||--o{ COMMENT : "writes"
    USER ||--o{ COMMENT : "receives"
    USER ||--o{ GAME_OBJECT : "owns"

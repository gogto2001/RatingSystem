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



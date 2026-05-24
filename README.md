# 📚 Canteen mobile app
Modern Android application for students to see an actual menu of an educational institution
---

## ✨ Features

- JWT Authentication with password
- Browse all available dishes and menu
- Making food requests for students
- Changing settings including change of language and app theme
- Logging out of account
- Checking request history
- Filtering dishes by category

---

## 🏗 Architecture

The project is built using **Clean Architecture** with clear separation of concerns:

- **Presentation** (Compose, ViewModel)
- **Domain** (UseCases, Models)
- **Data** (Repository, REST API, DataStore)

### Layers responsibility

- **UI (Compose)** – renders state and sends events  
- **ViewModel** – manages UI state and user actions  
- **UseCase** – contains business logic and validation  
- **Repository** – orchestrates data sources including REST API
- **DataStore** – user session, theme and language settings  

---


## 🛠 Tech Stack

**Language**
- Kotlin  

**UI**
- Jetpack Compose  
- Navigation Compose  

**Architecture**
- Clean Architecture  
- MVVM  
- Repository Pattern  

**Async**
- Kotlin Coroutines  
- Flow  

**Data**
- REST API 
- DataStore  

**DI**
- Hilt  
 

---

| Login | Main Screen | Details |
|------|----------|--------|
| ![](screenshots/login_mobile.jpg) | ![](screenshots/main_screen_mobile.jpg) | ![](screenshots/detail_screen_mobile.jpg) |

| Request | Settings | Request History |
|---------|---------|--------|
| ![](screenshots/making_request.jpg) | ![](screenshots/settings_mobile.jpg) | ![](screenshots/request_history_mobile.jpg) | |



---

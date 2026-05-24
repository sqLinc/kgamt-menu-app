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
| ![](https://github.com/sqLinc/kgamt-menu-app/blob/main/screenshots/login_mobile.png) | ![](https://github.com/sqLinc/kgamt-menu-app/blob/main/screenshots/main_screen_mobile.png) | ![](https://github.com/sqLinc/kgamt-menu-app/blob/main/screenshots/detail_screen_mobile.png) |

| Request | Settings | Request History |
|---------|---------|--------|
| ![](https://github.com/sqLinc/kgamt-menu-app/blob/main/screenshots/making_request.png) | ![](https://github.com/sqLinc/kgamt-menu-app/blob/main/screenshots/settings_mobile.png) | ![](https://github.com/sqLinc/kgamt-menu-app/blob/main/screenshots/request_history_mobile.png) | |



---

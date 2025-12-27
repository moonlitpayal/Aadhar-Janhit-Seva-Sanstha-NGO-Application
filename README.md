# Aadhar Janhit Seva Sanstha - Official App

> **Voluntarily architected and developed to drive digital transformation for social impact**

![Android](https://img.shields.io/badge/Android-Native-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Java](https://img.shields.io/badge/Java-Language-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Firebase](https://img.shields.io/badge/Firebase-Backend-039BE5?style=for-the-badge&logo=Firebase&logoColor=white)
![UPI](https://img.shields.io/badge/Payment-UPI_Intent-ff5722?style=for-the-badge&logo=google-pay&logoColor=white)

**This Official Android Application** serves as the digital face of the *Aadhar Janhit Seva Sanstha* (आधार जनहित सेवा संस्था). I developed this platform pro-bono to modernize the NGO's operations, streamline fundraising, and amplify their reach across India through technology.

---

## 📸 Application Showcase

### 📱 User Experience (Public Interface)
*Designed for accessibility, ensuring donors and volunteers can easily connect with the cause.*

| **1. Home Dashboard** | **2. Events & News** | **3. UPI Donation Screen** |
|:---:|:---:|:---:|
| <img src="PASTE_HOME_SCREEN_URL_HERE" width="250" alt="Home Screen"> | <img src="PASTE_EVENTS_SCREEN_URL_HERE" width="250" alt="News Feed"> | <img src="PASTE_DONATION_SCREEN_URL_HERE" width="250" alt="UPI Payment"> |
| *Dynamic Content Feed* | *Real-time Updates* | *Direct UPI Integration* |

### 🔐 Admin & Financial Control
*Secure backend controls allowing NGO staff to manage content and verify donations.*

| **4. Transaction Approval** | **5. Secure Admin Login** | **6. Content Upload** |
|:---:|:---:|:---:|
| <img src="PASTE_ADMIN_TRANSACTION_URL_HERE" width="250" alt="Transaction Verify"> | <img src="PASTE_LOGIN_SCREEN_URL_HERE" width="250" alt="Secure Login"> | <img src="PASTE_UPLOAD_SCREEN_URL_HERE" width="250" alt="Admin Upload"> |
| *Accept/Decline Manual Logic* | *Firebase Authentication* | *Dynamic Database Entry* |

---

## 🌟 Motivation: Stakeholder-Driven Engineering

**The Challenge:**
The NGO needed a digital donation channel but strict stakeholder requirements **prohibited the use of payment gateways (like Razorpay)** to avoid transaction fees and technical complexity. They needed a zero-cost, direct-to-bank solution.

**My Solution:**
I architected a **Custom "Verify-and-Approve" Financial Workflow**:
* **UPI Intent Integration:** Instead of a gateway, I used Android's native UPI Intent to trigger apps like GPay/PhonePe directly.
* **Manual Verification Loop:** Since there is no automatic server callback, I built a specific Admin feature where the NGO owner can view donation requests and manually **"Accept" or "Decline"** them after checking their bank statement.

---

## ✨ Technical Highlights

### 💸 1. Zero-Fee Donation Architecture (Custom Logic)
* **Problem:** Client refused external payment gateways.
* **Implementation:**
    1.  User enters amount -> App triggers **Android UPI Intent**.
    2.  Transaction details are sent to a "Pending" node in Firebase.
    3.  **Admin Panel:** The owner sees the "Pending" list and uses a custom slide-to-act button to **Accept** (move to History) or **Decline** (delete record) based on actual bank receipt.

### 🔥 2. Firebase Backend Architecture
* **Realtime Database:** Engineered the app to fetch News, Events, and Text data instantly. When the Admin posts an update, users see it immediately without needing to update the app.
* **Cloud Storage:** Implemented secure buckets for high-resolution images (Gallery/Banners), optimizing data usage for users with slower connections.

### 🛡️ 3. Secure Admin Module
* **Authentication:** Integrated Firebase Auth to restrict backend access. Only verified NGO administrators can access the "Financial Approval" and "Content Edit" screens.
* **Dynamic CMS:** Built a custom Content Management System (CMS) inside the app, allowing non-technical staff to upload photos and write news articles.

---

## 🛠️ Tech Stack

* **Language:** **Java** (Native Android Development)
* **UI/UX:** **XML** (Material Design Components)
* **Backend:** **Google Firebase**
    * Realtime Database (JSON NoSQL)
    * Firebase Storage (Media)
    * Firebase Authentication (Security)
* **Payments:** **Android UPI Intent API** (Deep Linking)

---

## 🚀 Installation

1.  **Clone the repository:**
    ```bash
    git clone [https://github.com/moonlitpayal/AadharJanhit-App.git](https://github.com/moonlitpayal/AadharJanhit-App.git)
    ```
2.  **Open in Android Studio.**
3.  **Sync Gradle.**
4.  **Run on Device/Emulator.**

---

## 👨‍💻 Social Responsibility
This project was not an academic assignment; it was a **voluntary contribution** to society. It represents my commitment to applying specialized technical skills toward tangible, high-impact social causes—demonstrating that technology is most powerful when it serves the public good.

*Developed with ❤️ by **Payal Dharma Mehta**.*

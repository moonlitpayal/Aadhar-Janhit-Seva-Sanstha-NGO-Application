
2. **Open in Android Studio**  
- `File` → `Open` → select the project folder.  
- Let Gradle sync and resolve dependencies.

3. **Create a Firebase project**  
- Go to [Firebase Console](https://console.firebase.google.com/).  
- Add a new Android app with your package name.  
- Download the `google-services.json` file and place it inside:  
  `app/` folder of the project.  

4. **Enable required Firebase services**  
- Firebase Realtime Database  
- (Optional) Firebase Authentication  
- (Optional) Firebase Storage  

5. **Run the app**  
- Connect an Android device / start an emulator.  
- Click **Run ▶** in Android Studio.

---

## ✅ Current Status

- First functional version for **Aadhar Janhit Seva Sanstha** completed as part of an Android Development internship project.  
- Core flows for campaigns, beneficiaries, and updates are implemented and wired to Firebase.[file:1]  
- Ongoing refinements based on NGO feedback and real usage.

---

## 🧪 Testing

- Manual end‑to‑end testing of:  
- Screen navigation and form validations.  
- Data creation, update, and retrieval from Firebase.  
- Basic error handling for no internet / invalid input.  
- Future scope: Add instrumented tests and UI tests with Espresso.

---

## 🛣 Future Enhancements

- Full **role‑based authentication** (admin, volunteer, donor).  
- In‑app donation links or payment gateway integration (as per NGO requirements).  
- Push notifications for new campaigns and events.  
- Analytics dashboard for tracking donations and impact metrics.  
- Localization for multiple Indian languages.

---

## 🤝 NGO & Social Impact

This project is built **pro bono** to support the mission of **Aadhar Janhit Seva Sanstha** and contribute to digital empowerment in the social sector.[file:1]  
If you are an NGO or student team interested in extending or reusing this idea, feel free to fork the repository and adapt it to your context.

---

## 📬 Contact

Created and maintained by **Payal Mehta** – Android & Flutter Application Developer.  
For collaboration, suggestions, or feature requests, please open an issue on GitHub or reach out via LinkedIn/Gmail linked on the profile.

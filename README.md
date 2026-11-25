<p align="center">
  📄 Read in Persian: <a href="README.fa.md"><b>فارسی</b></a>
</p>

<p align="center">
  <img src="./adobe%20xd/ic_launcher.png" width="400" title="DEFINEit Logo">
</p>

# DEFINEit (دیفاینیت) 🌐
### Translate text, look up words, and chat across languages on Android

**DEFINEit** is an Android app for Persian-speaking users that combines Google Translate, an offline English–Persian dictionary, and a chat-style conversation mode in one place.

---

## 📥 Download the App

Get the latest released version from Myket:

<p align="center">
  <a href="https://myket.ir/app/ir.DEFINEit"><b>⬇️ Download DEFINEit from Myket</b></a>
</p>

---

## 🆕 What's New in v2.0.0 — Smarter conversations

This release focuses on the conversation flow and a clear versioning scheme:

*   **Thinking state:** a reply now appears immediately with a `Thinking...` status while the request is running.
*   **Typewriter replies:** the translated text is typed out character by character, with the speed adjusted to the answer length.
*   **Retry on failure:** tap a failed reply to open a TelegramDialog popup and resend the same text and language pair.
*   **Versioning:** `versionCode` and `versionName` are now generated from `versionMajor`, `versionMinor`, and `versionPatch` in `app/build.gradle`.

---

## 🌟 Key Features

### 🌍 Translation
*   Translation powered by Google Translate, with a wide range of source and target languages.
*   Auto-translate after a short pause while typing; swap languages with one tap.
*   Copy the result, play it with Text-to-Speech, or dictate the source text with voice input.
*   A dedicated language picker and a device-volume check before audio playback.

### 📚 Persian Dictionary
*   Offline English ↔ Persian word lookup backed by a local Room database.
*   Live search in both languages after a short pause while typing.
*   Voice search, plus a popup menu to copy, pronounce, or share a word.

### 💬 Conversation
*   Two-way, chat-style translation for real conversations.
*   One send button and one mic button for the selected language pair.
*   **AI-style replies:** a `Thinking...` placeholder turns into the answer with a typewriter effect.
*   **Retry menu:** failed translations stay in the chat and can be retried by tapping the message.

---

## 🛠 Technologies Used

DEFINEit is built with these tools and libraries:

*   **Language:** Java 11
*   **Architecture:** Activities and Fragments with a Room-backed local database
*   **Database:** Room 2.8.4 (offline dictionary)
*   **Networking:** OkHttp 5.4.0 (Google Translate requests)
*   **UI:** AppCompat, Material Components, ConstraintLayout, chip-navigation-bar
*   **Async:** RxJava3 and RxAndroid (debounced search and translation)
*   **Voice:** Android `RecognizerIntent` and a Text-to-Speech wrapper (`TTsSingle`)
*   **Extras:** TelegramDialog popup menus, Logger, QShare

---

## 🚀 Installation & Run Guide

1. First, clone the project from GitHub:

```bash
git clone https://github.com/AmirBahadorAmiri/DEFINEit.git
cd DEFINEit
```

2. Open the project in a recent version of **Android Studio** with SDK 37 installed.
3. Let Gradle sync and download the dependencies.
4. Run the app on a device or emulator with **Android 8.0 (API 26)+**:

```bash
./gradlew assembleDebug
```

---

## 📸 Screenshots

These store images show the main screens of the app; the interface supports right-to-left Persian text.

| 📱 Home | 🌍 Translation | 📚 Dictionary | 💬 Conversation |
|:---:|:---:|:---:|:---:|
| <img width="220" height="352" alt="DEFINEit home screen" src="./adobe%20xd/store_pic/Web%201280%20%E2%80%93%201.png" /> | <img width="220" height="352" alt="Translation screen with source and target languages" src="./adobe%20xd/store_pic/Web%201280%20%E2%80%93%202.png" /> | <img width="220" height="352" alt="Persian dictionary search results" src="./adobe%20xd/store_pic/Web%201280%20%E2%80%93%203.png" /> | <img width="220" height="352" alt="Conversation chat with translated replies" src="./adobe%20xd/store_pic/Web%201280%20%E2%80%93%204.png" /> |

---

## 👨‍💻 Developer
Developed by **Amir Bahador Amiri**

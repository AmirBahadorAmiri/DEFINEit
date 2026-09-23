# DEFINEit

> 📖 [نسخه فارسی](./README.fa.md)

An Android translation app powered by **Google Translate**, with a built-in **Persian (Farsi) dictionary** and a two-way **Conversation** mode.

[![Android](https://img.shields.io/badge/Platform-Android-3DDC84?logo=android&logoColor=white)](https://developer.android.com/)
[![Java](https://img.shields.io/badge/Language-Java-orange?logo=java&logoColor=white)](https://www.java.com/)
[![Min SDK](https://img.shields.io/badge/minSdk-24-blue)](./app/build.gradle)
[![Version](https://img.shields.io/badge/version-2025.11.11-purple)](./app/build.gradle)
[![Package](https://img.shields.io/badge/package-ir.DEFINEit-lightgrey)](https://myket.ir/app/ir.DEFINEit)

## 📥 Download

Get the latest version from Myket:

**➡️ [Download DEFINEit on Myket](https://myket.ir/app/ir.DEFINEit)**

```text
https://myket.ir/app/ir.DEFINEit
```

## ✨ Features

### 🌍 Translator
- Translation powered by Google Translate
- Support for many source and target languages
- Auto-translate while typing (with debounce)
- Swap source / target languages with one tap
- Language picker screen (`ChangeLanguageActivity`)
- Translation history saved locally (`TranslateHistoryActivity`)
- Copy translated text to clipboard
- Text-to-Speech (TTS) playback of translations
- Volume-level check with Snackbar hint

### 📚 Persian Dictionary
- Built-in offline English ↔ Persian word database (Room)
- Fast live search (English and Persian)
- Detailed word view (`ShowWordActivity`)
- Dictionary search history (`DictionaryHistoryActivity`)
- Starred / favorite words (`StarWordsActivity`)
- Voice input for dictionary search

### 💬 Conversation
- Two-way chat-style translation for real conversations
- Separate send button for each side (each language)
- Voice input via Android Speech Recognizer
- Quick language swap
- Auto-scroll chat list

### ⚙️ Extras
- Material Design UI with bottom navigation (`chip-navigation-bar`)
- Persian font support (Calligraphy + ViewPump)
- Settings screen
- Splash screen
- RTL layout support
- Local database with Room + RxJava3
- Networking with OkHttp

## 🛠 Tech Stack

| Layer | Library / Tool |
|---|---|
| Language | Java 11 |
| Min / Target SDK | 24 / 36 |
| UI | AppCompat, Material, ConstraintLayout, chip-navigation-bar |
| Fonts | Calligraphy3, ViewPump |
| Database | Room 2.8.4 (RxJava3 support) |
| Async | RxJava3 + RxAndroid |
| Network | OkHttp 5.4.0 |
| Voice / Speech | Android `RecognizerIntent` |
| TTS | Android TextToSpeech wrapper (`TTsSingle`) |

## 📁 Project Structure

```text
app/src/main/java/ir/DEFINEit/
├── adapters/              # Conversation, Dictionary, History, Starred adapters
├── models/                # WordModel, TextModel, LangModel, ConversationModel
├── tools/
│   ├── translate_manager/ # Google Translate API calls
│   ├── mydb/              # Room database, WordDao, TextDao
│   ├── tts_manager/       # Text-to-Speech
│   ├── language_manager/  # From/To language prefs
│   ├── copy_helper/
│   ├── shared_helper/
│   └── ...
└── views/
    ├── activities/        # Main, Splash, Settings, ShowWord, StarWords, Histories
    └── fragments/         # TranslateFragment, DictionaryFragment, ConversationFragment
```

Main entry points:
- `views/fragments/TranslateFragment.java` — translator tab
- `views/fragments/DictionaryFragment.java` — offline dictionary tab
- `views/fragments/ConversationFragment.java` — conversation tab

## 🚀 Build & Run

1. Clone the repo:

   ```bash
   git clone <repo-url>
   cd DEFINEit
   ```

2. Open the project in **Android Studio** (recent version with SDK 36).

3. Let Gradle sync and download dependencies.

4. Run on an emulator or device with **Android 7.0 (API 24)+**:

   ```bash
   ./gradlew assembleDebug
   ```

> Internet permission is required for Google Translate. The dictionary itself works from the local database.

## 📋 Requirements

- Android 7.0+ (minSdk 24)
- Internet access (for Translate & Conversation)
- Google TTS / speech packages on device (for voice & pronunciation)

## 🤝 Contributing

Pull requests and suggestions are welcome. If you find a bug in translation, dictionary results, or conversation flow, please open an issue with steps to reproduce.

---

Made with ❤️ for Persian-speaking users — **DEFINEit (دیفاینیت)**

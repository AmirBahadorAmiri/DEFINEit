# دیفاینیت | DEFINEit

> 🌍 [English version](./README.md)

اپلیکیشن اندرویدی مترجم بر پایه **گوگل ترنسلیت**، به‌همراه **دیکشنری فارسی آفلاین** و حالت **مکالمه (Conversation)** دوطرفه.

[![Android](https://img.shields.io/badge/پلتفرم-اندروید-3DDC84?logo=android&logoColor=white)](https://developer.android.com/)
[![Java](https://img.shields.io/badge/زبان-جاوا-orange?logo=java&logoColor=white)](https://www.java.com/)
[![Min SDK](https://img.shields.io/badge/minSdk-24-blue)](./app/build.gradle)
[![Version](https://img.shields.io/badge/version-2025.11.11-purple)](./app/build.gradle)
[![Package](https://img.shields.io/badge/package-ir.DEFINEit-lightgrey)](https://myket.ir/app/ir.DEFINEit)

## 📥 دانلود

آخرین نسخه را از مایکت دانلود کنید:

**➡️ [دانلود دیفاینیت از مایکت](https://myket.ir/app/ir.DEFINEit)**

```text
https://myket.ir/app/ir.DEFINEit
```

## ✨ امکانات

### 🌍 مترجم
- ترجمه بر پایه گوگل ترنسلیت
- پشتیبانی از زبان‌های مختلف مبدأ و مقصد
- ترجمه خودکار هنگام تایپ
- جابه‌جایی زبان مبدأ و مقصد با یک لمس
- صفحه انتخاب زبان (`ChangeLanguageActivity`)
- ذخیره تاریخچه ترجمه‌ها (`TranslateHistoryActivity`)
- کپی متن ترجمه‌شده
- پخش صوتی ترجمه (Text-to-Speech)
- بررسی وضعیت صدای سیستم هنگام پخش

### 📚 دیکشنری فارسی
- دیتابیس آفلاین انگلیسی ↔ فارسی (Room)
- جست‌وجوی سریع فارسی و انگلیسی
- صفحه جزئیات هر واژه (`ShowWordActivity`)
- تاریخچه جست‌وجوی دیکشنری (`DictionaryHistoryActivity`)
- واژگان ستاره‌دار / موردعلاقه (`StarWordsActivity`)
- ورودی صوتی برای جست‌وجو

### 💬 گفت‌وگو (Conversation)
- ترجمه مکالمه‌ای دوطرفه به سبک چت
- دکمه ارسال جداگانه برای هر زبان
- ورودی صوتی با تشخیص گفتار اندروید
- جابه‌جایی سریع زبان‌ها
- اسکرول خودکار لیست گفت‌وگو

### ⚙️ امکانات جانبی
- رابط کاربری متریال با ناوبری پایینی
- فونت فارسی (Calligraphy + ViewPump)
- صفحه تنظیمات
- اسپلش اسکرین
- پشتیبانی از راست‌به‌چپ (RTL)
- دیتابیس محلی با Room و RxJava3
- ارتباط شبکه با OkHttp

## 🛠 تکنولوژی‌ها

| بخش | ابزار |
|---|---|
| زبان | Java 11 |
| Min / Target SDK | 24 / 36 |
| رابط کاربری | AppCompat, Material, ConstraintLayout, chip-navigation-bar |
| فونت | Calligraphy3, ViewPump |
| دیتابیس | Room 2.8.4 با پشتیبانی RxJava3 |
| برنامه‌نویسی ناهمگام | RxJava3 + RxAndroid |
| شبکه | OkHttp 5.4.0 |
| گفتار | `RecognizerIntent` اندروید |
| تبدیل متن به گفتار | TextToSpeech اندروید (`TTsSingle`) |

## 📁 ساختار پروژه

```text
app/src/main/java/ir/DEFINEit/
├── adapters/              # آداپترهای مکالمه، دیکشنری، تاریخچه و ستاره‌دارها
├── models/                # مدل‌های Word، Text، Lang و Conversation
├── tools/
│   ├── translate_manager/ # ارتباط با API گوگل ترنسلیت
│   ├── mydb/              # دیتابیس Room، WordDao و TextDao
│   ├── tts_manager/       # پخش صوتی متن
│   ├── language_manager/  # مدیریت زبان مبدأ و مقصد
│   ├── copy_helper/
│   ├── shared_helper/
│   └── ...
└── views/
    ├── activities/        # صفحات اصلی، اسپلش، تنظیمات، نمایش واژه و تاریخچه‌ها
    └── fragments/         # فرگمنت‌های مترجم، دیکشنری و مکالمه
```

فایل‌های اصلی:
- `views/fragments/TranslateFragment.java` — تب مترجم
- `views/fragments/DictionaryFragment.java` — تب دیکشنری آفلاین
- `views/fragments/ConversationFragment.java` — تب مکالمه

## 🚀 اجرا و بیلد

۱. کلون کردن پروژه:

   ```bash
   git clone <repo-url>
   cd DEFINEit
   ```

۲. باز کردن پروژه در **Android Studio** جدید (با SDK نسخه 36).

۳. صبر کنید تا Gradle سینک شود و وابستگی‌ها دانلود شوند.

۴. اجرا روی شبیه‌ساز یا گوشی با **اندروید ۷ به بالا (API 24)+**:

   ```bash
   ./gradlew assembleDebug
   ```

> برای بخش مترجم و مکالمه اینترنت لازم است. خود دیکشنری از دیتابیس محلی کار می‌کند.

## 📋 پیش‌نیازها

- اندروید ۷ به بالا (minSdk 24)
- اینترنت (برای مترجم و مکالمه)
- بسته‌های گفتار و TTS گوگل روی دستگاه (برای ورودی صوتی و تلفظ)

## 🤝 مشارکت

پول‌ریکوئست و پیشنهادها خوشحال‌مون می‌کنه. اگه باگی توی ترجمه، نتایج دیکشنری یا مکالمه دیدی، لطفاً یه Issue با مراحل بازتولید باز کن.

---

ساخته‌شده با ❤️ برای فارسی‌زبان‌ها — **دیفاینیت (DEFINEit)**

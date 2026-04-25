# StreamFlix TV - Modern Android IPTV Player

[![Build & Release APK](https://github.com/yourusername/streamflix-tv/actions/workflows/build.yml/badge.svg)](https://github.com/yourusername/streamflix-tv/actions/workflows/build.yml)
[![Platform](https://img.shields.io/badge/platform-Android-green.svg)](https://developer.android.com/)
[![API](https://img.shields.io/badge/API-21%2B-brightgreen.svg)](https://android-arsenal.com/api?level=21)
[![Kotlin](https://img.shields.io/badge/Kotlin-1.9.22-purple.svg)](https://kotlinlang.org/)
[![Jetpack Compose](https://img.shields.io/badge/Compose-1.6.0-blue.svg)](https://developer.android.com/jetpack/compose)

A **native, modern, and original** Android-styled IPTV player built with **Jetpack Compose**, **Material Design 3**, and automated with **GitHub Actions**.

## ✨ Features

### 🎨 Modern Android UI/UX
- **Material Design 3** theming with custom color palette
- **Dark mode** optimized for TV viewing
- **Smooth animations** and transitions
- **Responsive layouts** for phones, tablets, and Android TV
- **Landscape orientation** for immersive viewing experience

### 📺 IPTV Capabilities
- Parse **M3U/M3U8 playlists** from URLs or local files
- **Channel grouping** by category
- **EPG support** (Electronic Program Guide)
- **Logo display** for channels
- **Search functionality** to find channels quickly
- **Filter chips** for easy navigation between groups

### 🎬 Video Playback
- Built-in **ExoPlayer** (Media3) integration
- Support for **HLS**, **DASH**, and progressive streams
- Hardware acceleration enabled
- Buffer management and adaptive streaming
- Full-screen landscape playback

### ⚡ Performance
- **Coroutine-based** asynchronous operations
- Efficient channel parsing with coroutines
- Lazy loading for large playlists
- Image caching with **Coil**

### 🔧 GitHub Automation
- **Automated builds** on every push and pull request
- **Release automation** with version tags
- Generates both **APK** and **AAB** (Android App Bundle)
- Automatic release notes generation
- Artifact retention for debugging

## 🏗️ Tech Stack

| Technology | Version | Purpose |
|------------|---------|---------|
| Kotlin | 1.9.22 | Primary language |
| Jetpack Compose | 1.6.0 | Modern UI toolkit |
| Material 3 | Latest | Design system |
| Media3 (ExoPlayer) | 1.2.1 | Video playback |
| Coil | 2.5.0 | Image loading |
| DataStore | 1.0.0 | Preferences storage |
| Coroutines | Latest | Async operations |
| Gradle KTS | 8.2.2 | Build system |

## 🚀 Getting Started

### Prerequisites
- Android Studio Hedgehog (2023.1.1) or newer
- JDK 17
- Android SDK 34
- Minimum SDK 21 (Android 5.0 Lollipop)

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/streamflix-tv.git
   cd streamflix-tv
   ```

2. **Open in Android Studio**
   - Launch Android Studio
   - Select "Open an Existing Project"
   - Navigate to the cloned directory

3. **Sync Gradle**
   - Android Studio will automatically sync Gradle
   - Wait for dependencies to download

4. **Run the app**
   - Connect an Android device or start an emulator
   - Click the "Run" button or press `Shift + F10`

### Building Manually

```bash
# Debug build
./gradlew assembleDebug

# Release build
./gradlew assembleRelease

# Android App Bundle
./gradlew bundleRelease
```

## 🤖 GitHub Actions CI/CD

The project includes automated build and release workflows:

### Triggers
- Push to `main` or `master` branch
- Pull requests
- Tag pushes (format: `v*`)
- Manual workflow dispatch

### Outputs
- **Debug APK**: Uploaded as artifact (30 days retention)
- **Release APK**: Published to GitHub Releases
- **Release AAB**: Published to Google Play Console ready

### Creating a Release

```bash
# Tag your release
git tag v1.0.0
git push origin v1.0.0
```

This triggers the release workflow which:
1. Builds signed release APK
2. Creates Android App Bundle (AAB)
3. Creates a GitHub Release with assets
4. Generates release notes automatically

## 📂 Project Structure

```
app/
├── src/main/
│   ├── java/com/streamflix/iptv/
│   │   ├── MainActivity.kt          # Entry point
│   │   ├── data/
│   │   │   ├── Models.kt            # Data classes
│   │   │   └── M3uParser.kt         # M3U playlist parser
│   │   ├── player/
│   │   │   └── StreamPlayer.kt      # Player wrapper
│   │   └── ui/
│   │       ├── theme/
│   │       │   └── Theme.kt         # App theming
│   │       ├── screens/
│   │       │   ├── HomeScreen.kt    # Channel browser
│   │       │   └── PlayerActivity.kt# Video player
│   │       └── components/          # Reusable UI components
│   ├── res/
│   │   ├── values/
│   │   │   ├── strings.xml          # String resources
│   │   │   └── themes.xml           # Theme definitions
│   │   └── ...
│   └── AndroidManifest.xml
├── build.gradle.kts                  # Module build config
└── proguard-rules.pro               # ProGuard rules

.github/
└── workflows/
    └── build.yml                    # CI/CD pipeline

build.gradle.kts                     # Root build config
settings.gradle.kts                  # Project settings
```

## ⚙️ Configuration

### Custom Playlist URL

Edit `HomeScreen.kt` to change the default playlist:

```kotlin
val m3uUrl = "YOUR_PLAYLIST_URL_HERE"
```

### Supported Formats

- **M3U**: Standard playlist format
- **M3U8**: Extended M3U with UTF-8 encoding
- **HTTP/HTTPS**: Direct streaming URLs
- **HLS**: HTTP Live Streaming (.m3u8)
- **DASH**: Dynamic Adaptive Streaming over HTTP

## 🎨 Theming

Customize the app's appearance in `Theme.kt`:

```kotlin
val StreamFlixColors = darkColorScheme(
    primary = Color(0xFF00E5FF),      // Cyan accent
    secondary = Color(0xFFFF4081),    // Pink highlights
    background = Color(0xFF0A0E14),   // Deep blue-black
    surface = Color(0xFF151A23),      // Card color
)
```

## 🔐 Permissions

The app requires the following permissions:
- `INTERNET`: Stream video content
- `ACCESS_NETWORK_STATE`: Check network connectivity
- `WAKE_LOCK`: Keep device awake during playback

## 🛠️ Troubleshooting

### Common Issues

**1. Build fails with JDK error**
```bash
# Ensure JDK 17 is installed and configured
java -version
```

**2. ExoPlayer buffering issues**
- Check network connectivity
- Verify stream URL is accessible
- Some streams may require specific codecs

**3. M3U parsing errors**
- Ensure valid M3U format
- Check for malformed entries
- Verify URL accessibility

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

1. Fork the project
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 🙏 Acknowledgments

- [IPTV-org](https://github.com/iptv-org/iptv) for free IPTV channels
- [Jetpack Compose](https://developer.android.com/jetpack/compose) team
- [ExoPlayer/Media3](https://exoplayer.dev/) team
- Material Design 3 guidelines

## 📞 Support

If you have any questions or need help, please open an issue in the repository.

---

<div align="center">

**Built with ❤️ using Kotlin and Jetpack Compose**

[Report Bug](https://github.com/yourusername/streamflix-tv/issues) · [Request Feature](https://github.com/yourusername/streamflix-tv/issues)

</div>

# NyanAndroidArch

> 烧饼（ **自用** ）的 Android 应用基本框架库，享受 Data Binding 和 MVVM 模式的便利

[![](https://jitpack.io/v/moe.feng/NyanAndroidArch.svg)](https://jitpack.io/#moe.feng/NyanAndroidArch)

[English Version (Not ready yet)]()

## 介绍

本库是[烧饼@fython](https://github.com/fython)自己整理日常使用 Kotlin 开发 App 中常用的基本框架，主要方便使用 Data Binding + MVVM、
快速创建 RecyclerView 的 Adapter 等，在保持代码清晰简洁的情况下提高效率。

尽管对 Java 做了部分兼容，但仅在 Kotlin 语言下才能体现完整特性。

### 使用环境

- IntelliJ IDEA 2017.3+ / Android Studio 3+
- Gradle 4+
- Target SDK >=27
- Min SDK >=21（暂定，实际上可设定更低）

### 模块介绍

本库被拆分为多个模块：

- [android-common](./android-common) - Android 扩展
- [android-arch-base](./android-arch-base) - 基本框架（包含 Activity 基类）
- [android-arch-v4-fragment](./android-arch-base) - Support v4 Fragment 基类
- [android-arch-base-lite](./android-arch-base-lite) - Lite 版基本框架，主要区别在于不使用 AppCompat v7，减少项目体积
- [android-arch-listview](./android-arch-listview) - 列表框架（支持 RecyclerView 与 ListView）
- [support-core-ui-extensions](./support-core-ui-extensions) - Support v4 core-ui 扩展

（模块链接内将设有详细说明，目前尚未添加）

### 从 Gradle 导入

导入时，先在项目根目录下的 `build.gradle` 加入下列代码：
```gradle
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

再在应用 Module 的 `build.gradle` 中按需声明要依赖的模块：
```gradle
dependencies {
    implementation 'moe.feng:android-common:latest-version'
    implementation 'moe.feng:android-arch-base:latest-version'
    // implementation 'moe.feng:android-arch-base-lite:latest-version'
    implementation 'moe.feng:android-arch-listview:latest-version'
    implementation 'moe.feng:xxxxxx-xxx-xxx:latest-version'
}
```
（如果找不到库尝试将 `moe.feng` 替换为 `moe.feng.NyanAndroidArch`）

## 贡献

请阅读 [CONTRIBUTE.md](./CONTRIBUTE.md)。

## 联系方式

- Telegram: [@fython](https://t.me/fython)
- Email: fythonx#gmail.com

## 协议

[MIT License](./LICENSE)

```
MIT License

Copyright (c) 2017 Fung Go (fython)

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
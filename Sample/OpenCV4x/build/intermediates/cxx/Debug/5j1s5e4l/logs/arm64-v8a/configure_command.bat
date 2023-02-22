@echo off
"D:\\UNi\\prog\\skd\\cmake\\3.22.1\\bin\\cmake.exe" ^
  "-HE:\\uni\\3 ANO\\2sem\\ICM\\Sample\\OpenCV4x\\libcxx_helper" ^
  "-DCMAKE_SYSTEM_NAME=Android" ^
  "-DCMAKE_EXPORT_COMPILE_COMMANDS=ON" ^
  "-DCMAKE_SYSTEM_VERSION=21" ^
  "-DANDROID_PLATFORM=android-21" ^
  "-DANDROID_ABI=arm64-v8a" ^
  "-DCMAKE_ANDROID_ARCH_ABI=arm64-v8a" ^
  "-DANDROID_NDK=D:\\UNi\\prog\\skd\\ndk\\23.1.7779620" ^
  "-DCMAKE_ANDROID_NDK=D:\\UNi\\prog\\skd\\ndk\\23.1.7779620" ^
  "-DCMAKE_TOOLCHAIN_FILE=D:\\UNi\\prog\\skd\\ndk\\23.1.7779620\\build\\cmake\\android.toolchain.cmake" ^
  "-DCMAKE_MAKE_PROGRAM=D:\\UNi\\prog\\skd\\cmake\\3.22.1\\bin\\ninja.exe" ^
  "-DCMAKE_LIBRARY_OUTPUT_DIRECTORY=E:\\uni\\3 ANO\\2sem\\ICM\\Sample\\OpenCV4x\\build\\intermediates\\cxx\\Debug\\5j1s5e4l\\obj\\arm64-v8a" ^
  "-DCMAKE_RUNTIME_OUTPUT_DIRECTORY=E:\\uni\\3 ANO\\2sem\\ICM\\Sample\\OpenCV4x\\build\\intermediates\\cxx\\Debug\\5j1s5e4l\\obj\\arm64-v8a" ^
  "-DCMAKE_BUILD_TYPE=Debug" ^
  "-BE:\\uni\\3 ANO\\2sem\\ICM\\Sample\\OpenCV4x\\.cxx\\Debug\\5j1s5e4l\\arm64-v8a" ^
  -GNinja ^
  "-DANDROID_STL=c++_shared"

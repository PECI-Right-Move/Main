@echo off
"D:\\UNi\\prog\\skd\\cmake\\3.22.1\\bin\\cmake.exe" ^
  "-HE:\\uni\\3 ANO\\2sem\\ICM\\Sample\\OpenCV4x\\libcxx_helper" ^
  "-DCMAKE_SYSTEM_NAME=Android" ^
  "-DCMAKE_EXPORT_COMPILE_COMMANDS=ON" ^
  "-DCMAKE_SYSTEM_VERSION=21" ^
  "-DANDROID_PLATFORM=android-21" ^
  "-DANDROID_ABI=x86" ^
  "-DCMAKE_ANDROID_ARCH_ABI=x86" ^
  "-DANDROID_NDK=D:\\UNi\\prog\\skd\\ndk\\23.1.7779620" ^
  "-DCMAKE_ANDROID_NDK=D:\\UNi\\prog\\skd\\ndk\\23.1.7779620" ^
  "-DCMAKE_TOOLCHAIN_FILE=D:\\UNi\\prog\\skd\\ndk\\23.1.7779620\\build\\cmake\\android.toolchain.cmake" ^
  "-DCMAKE_MAKE_PROGRAM=D:\\UNi\\prog\\skd\\cmake\\3.22.1\\bin\\ninja.exe" ^
  "-DCMAKE_LIBRARY_OUTPUT_DIRECTORY=E:\\uni\\3 ANO\\2sem\\ICM\\Sample\\OpenCV4x\\build\\intermediates\\cxx\\RelWithDebInfo\\707415r3\\obj\\x86" ^
  "-DCMAKE_RUNTIME_OUTPUT_DIRECTORY=E:\\uni\\3 ANO\\2sem\\ICM\\Sample\\OpenCV4x\\build\\intermediates\\cxx\\RelWithDebInfo\\707415r3\\obj\\x86" ^
  "-DCMAKE_BUILD_TYPE=RelWithDebInfo" ^
  "-BE:\\uni\\3 ANO\\2sem\\ICM\\Sample\\OpenCV4x\\.cxx\\RelWithDebInfo\\707415r3\\x86" ^
  -GNinja ^
  "-DANDROID_STL=c++_shared"

@echo off
"D:\\UNi\\prog\\skd\\cmake\\3.22.1\\bin\\cmake.exe" ^
  "-HE:\\uni\\3 ANO\\2sem\\ICM\\Sample\\OpenCV4x\\libcxx_helper" ^
  "-DCMAKE_SYSTEM_NAME=Android" ^
  "-DCMAKE_EXPORT_COMPILE_COMMANDS=ON" ^
  "-DCMAKE_SYSTEM_VERSION=24" ^
  "-DANDROID_PLATFORM=android-24" ^
  "-DANDROID_ABI=x86_64" ^
  "-DCMAKE_ANDROID_ARCH_ABI=x86_64" ^
  "-DANDROID_NDK=D:\\UNi\\prog\\skd\\ndk\\23.1.7779620" ^
  "-DCMAKE_ANDROID_NDK=D:\\UNi\\prog\\skd\\ndk\\23.1.7779620" ^
  "-DCMAKE_TOOLCHAIN_FILE=D:\\UNi\\prog\\skd\\ndk\\23.1.7779620\\build\\cmake\\android.toolchain.cmake" ^
  "-DCMAKE_MAKE_PROGRAM=D:\\UNi\\prog\\skd\\cmake\\3.22.1\\bin\\ninja.exe" ^
  "-DCMAKE_LIBRARY_OUTPUT_DIRECTORY=E:\\uni\\3 ANO\\2sem\\ICM\\Sample\\OpenCV4x\\build\\intermediates\\cxx\\Debug\\1r435t2v\\obj\\x86_64" ^
  "-DCMAKE_RUNTIME_OUTPUT_DIRECTORY=E:\\uni\\3 ANO\\2sem\\ICM\\Sample\\OpenCV4x\\build\\intermediates\\cxx\\Debug\\1r435t2v\\obj\\x86_64" ^
  "-DCMAKE_BUILD_TYPE=Debug" ^
  "-BE:\\uni\\3 ANO\\2sem\\ICM\\Sample\\OpenCV4x\\.cxx\\Debug\\1r435t2v\\x86_64" ^
  -GNinja ^
  "-DANDROID_STL=c++_shared"

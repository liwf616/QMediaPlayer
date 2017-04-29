#!/bin/sh

set -e

ANDROID_NDK=/Users/liwenfeng/gsx/android_sdks/android-ndk-r13b

BUILD_ROOT=$(cd "$(dirname "$0")"; pwd)/
THIRD_PARTY=${BUILD_ROOT}/ijkplayer/extra

FDKAAC_VERSION=3.99.5
LAME_ROOT=${THIRD_PARTY}/lame-${FDKAAC_VERSION}/jni

cd $LAME_ROOT

$ANDROID_NDK/ndk-build V=1 NDK_PROJECT_PATH=. APP_BUILD_SCRIPT=./Android.mk NDK_APPLICATION_MK=./Application.mk clean
$ANDROID_NDK/ndk-build V=1 NDK_PROJECT_PATH=. APP_BUILD_SCRIPT=./Android.mk NDK_APPLICATION_MK=./Application.mk LOCAL_ARM_MODE=arm

HEADER_FILES=" \
    $LAME_ROOT/libmp3lame/lame.h"

mkdir -p  $BUILD_ROOT/ijkplayer/android/contrib/build/lame-armv5/output/include/lame
mkdir -p  $BUILD_ROOT/ijkplayer/android/contrib/build/lame-armv7a/output/include/lame
mkdir -p  $BUILD_ROOT/ijkplayer/android/contrib/build/lame-arm64/output/include/lame

mkdir -p  $BUILD_ROOT/ijkplayer/android/contrib/build/lame-armv5/output/lib
mkdir -p  $BUILD_ROOT/ijkplayer/android/contrib/build/lame-armv7a/output/lib
mkdir -p  $BUILD_ROOT/ijkplayer/android/contrib/build/lame-arm64/output/lib


cd $THIRD_PARTY/lame-3.99.5

cp -a $HEADER_FILES $BUILD_ROOT/ijkplayer/android/contrib/build/lame-armv5/output/include/lame
cp -a $HEADER_FILES $BUILD_ROOT/ijkplayer/android/contrib/build/lame-armv7a/output/include/lame
cp -a $HEADER_FILES $BUILD_ROOT/ijkplayer/android/contrib/build/lame-arm64/output/include/lame

cp -a $LAME_ROOT/obj/local/armeabi/libmp3lame.so $BUILD_ROOT/ijkplayer/android/contrib/build/lame-armv5/output/lib/libmp3lame.so
cp -a $LAME_ROOT/obj/local/armeabi-v7a/libmp3lame.so $BUILD_ROOT/ijkplayer/android/contrib/build/lame-armv7a/output/lib/libmp3lame.so
cp -a $LAME_ROOT/obj/local/arm64-v8a/libmp3lame.so $BUILD_ROOT/ijkplayer/android/contrib/build/lame-arm64/output/lib/libmp3lame.so

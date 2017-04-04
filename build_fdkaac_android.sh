#!/bin/sh

set -e

ANDROID_NDK=/Users/liwenfeng/gsx/android_sdks/android-ndk-r13b
# ANDROID_NDK=/Users/yuqilin/tools/adt-bundle-mac-x86_64-20140702/sdk/ndk-bundle

BUILD_ROOT=$(cd "$(dirname "$0")"; pwd)/build_out
THIRD_PARTY=${BUILD_ROOT}/../ijkplayer/extra

FDKAAC_VERSION=0.1.4
FDKAAC_ROOT=${THIRD_PARTY}/fdk-aac-${FDKAAC_VERSION}

cd $FDKAAC_ROOT

$ANDROID_NDK/ndk-build V=1 NDK_PROJECT_PATH=. APP_BUILD_SCRIPT=./Android.mk NDK_APPLICATION_MK=./Application.mk clean
$ANDROID_NDK/ndk-build V=1 NDK_PROJECT_PATH=. APP_BUILD_SCRIPT=./Android.mk NDK_APPLICATION_MK=./Application.mk LOCAL_ARM_MODE=arm

HEADER_FILES=" \
    $FDKAAC_ROOT/libAACdec/include/aacdecoder_lib.h \
    $FDKAAC_ROOT/libAACenc/include/aacenc_lib.h \
    $FDKAAC_ROOT/libSYS/include/FDK_audio.h \
    $FDKAAC_ROOT/libSYS/include/genericStds.h \
    $FDKAAC_ROOT/libSYS/include/machine_type.h"

mkdir -p  $BUILD_ROOT/../ijkplayer/android/contrib/build/fdkaac-armv5/output/include/fdk-aac
mkdir -p  $BUILD_ROOT/../ijkplayer/android/contrib/build/fdkaac-armv7a/output/include/fdk-aac
mkdir -p  $BUILD_ROOT/../ijkplayer/android/contrib/build/fdkaac-arm64/output/include/fdk-aac

mkdir -p  $BUILD_ROOT/../ijkplayer/android/contrib/build/fdkaac-armv5/output/lib
mkdir -p  $BUILD_ROOT/../ijkplayer/android/contrib/build/fdkaac-armv7a/output/lib
mkdir -p  $BUILD_ROOT/../ijkplayer/android/contrib/build/fdkaac-arm64/output/lib


cd $THIRD_PARTY/fdk-aac-0.1.4

cp -a $HEADER_FILES $BUILD_ROOT/../ijkplayer/android/contrib/build/fdkaac-armv5/output/include/fdk-aac
cp -a $HEADER_FILES $BUILD_ROOT/../ijkplayer/android/contrib/build/fdkaac-armv7a/output/include/fdk-aac
cp -a $HEADER_FILES $BUILD_ROOT/../ijkplayer/android/contrib/build/fdkaac-arm64/output/include/fdk-aac

cp -a $FDKAAC_ROOT/obj/local/armeabi/libfdkaac.a $BUILD_ROOT/../ijkplayer/android/contrib/build/fdkaac-armv5/output/lib
cp -a $FDKAAC_ROOT/obj/local/armeabi-v7a/libfdkaac.a $BUILD_ROOT/../ijkplayer/android/contrib/build/fdkaac-armv7a/output/lib
cp -a $FDKAAC_ROOT/obj/local/arm64-v8a/libfdkaac.a $BUILD_ROOT/../ijkplayer/android/contrib/build/fdkaac-arm64/output/lib

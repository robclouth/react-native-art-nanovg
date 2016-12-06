LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

ifeq ($(TARGET_ARCH_ABI), armeabi-v7a)
    LOCAL_ARM_MODE := arm
endif

LOCAL_MODULE := nanovg_jni
LOCAL_SRC_FILES := \
  ../../../../nanovg/src/nanovg.c\
  nanovg_jni.c\

LOCAL_C_INCLUDES := \
  $(LOCAL_PATH)//../../../../nanovg/src

LOCAL_CFLAGS += -Wall
LOCAL_LDLIBS := -llog -lGLESv2 -landroid -lEGL

include $(BUILD_SHARED_LIBRARY)

LOCAL_PATH:= $(call my-dir)

include $(CLEAR_VARS)
LOCAL_SRC_FILES:= hello.c
LOCAL_MODULE := xiongsh
include $(BUILD_EXECUTABLE)

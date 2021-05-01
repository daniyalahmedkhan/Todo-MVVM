#include <jni.h>

JNIEXPORT jstring JNICALL
Java_com_daniyal_todo_1mvvm_utilities_Constants_getBaseUAT(JNIEnv *env, jobject thiz) {
    return (*env)->NewStringUTF(env,"aHR0cHM6Ly82MDg3ZGRkYmE2ZjRhMzAwMTc0MjUxNDMubW9ja2FwaS5pby9hcGkv");
}
package com.bjtu1111.supersoundrecorder;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class PreferenceUtils {
	private static final String SHARED_PREFF_LOGIN = "islogin";// �ж��Ƿ��¼
	private static final String SHARED_PREFF_NAME = "loginname";// �ٶȵ�¼�û���
	private static final String SHARED_PREFF_TOKEN = "token";// ���ڱ���tokenֵ

	private static SharedPreferences mSharedPreferences;
	private static Editor mEditor;

	public static void init(Context context) {
		mSharedPreferences = context.getSharedPreferences("group1024",
				Context.MODE_PRIVATE);
		mEditor = mSharedPreferences.edit();
	}

	// �ӱ��ػ�ȡ��½״̬
	public static boolean getLoginStatus() {
		return mSharedPreferences.getBoolean(SHARED_PREFF_LOGIN, false);
	}

	// ���ñ��ص�½״̬
	public static void setLoginStatus(boolean status) {
		mEditor.putBoolean(SHARED_PREFF_LOGIN, status);
		mEditor.commit();
	}

	// �ӱ��ػ�ȡtoken
	public static String getToken() {
		return mSharedPreferences.getString(SHARED_PREFF_TOKEN, "");
	}

	// ���ñ���token
	public static void setToken(String token) {
		mEditor.putString(SHARED_PREFF_TOKEN, token);
		mEditor.commit();
	}

	// �ӱ��ػ�ȡ��½�û���
	public static String getLoginName() {
		return mSharedPreferences.getString(SHARED_PREFF_NAME, "");
	}

	// ���ñ��ص�½�û���
	public static void setLoginName(String name) {
		mEditor.putString(SHARED_PREFF_NAME, name);
		mEditor.commit();
	}

	// ������д洢������
	public static void clearData() {
		setLoginName("");
		setLoginStatus(false);
		setToken("");
	}
}
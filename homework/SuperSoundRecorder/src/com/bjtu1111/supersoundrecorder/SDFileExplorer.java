package com.bjtu1111.supersoundrecorder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.pcs.BaiduPCSActionInfo.PCSCommonFileInfo;
import com.baidu.pcs.BaiduPCSActionInfo.PCSFileInfoResponse;
import com.baidu.pcs.BaiduPCSActionInfo.PCSListInfoResponse;
import com.baidu.pcs.BaiduPCSActionInfo.PCSSimplefiedResponse;
import com.baidu.pcs.BaiduPCSClient;
import com.baidu.pcs.BaiduPCSStatusListener;

public class SDFileExplorer extends Activity implements OnClickListener {
	ListView listView;
	TextView textView;
	File currentParent;
	String filePath;
	File fileAudioList;
	File[] currentFiles;
	MediaPlayer mPlayer = new MediaPlayer();
	private boolean mIsLogin;
	private final static String BAIDU_ROOT_PATH = "/apps/pcstest_oauth/";
	private BaiduPCSClient mBaiduClient;
	private SimpleAdapter simpleAdapter;
	private ArrayAdapter<String> mCloudAdapter;
	private Button mBtnLocal;
	private Button mBtnCloud;
	private ListView mCloudListView;
	private List<PCSCommonFileInfo> mBaiduFileList;// 云端数据列表

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sdfileexplorer);
		mBaiduClient = new BaiduPCSClient();
		mIsLogin = PreferenceUtils.getLoginStatus();
		if (mIsLogin) {
			mBaiduClient.setAccessToken(PreferenceUtils.getToken());
		}
		listView = (ListView) findViewById(R.id.list);
		textView = (TextView) findViewById(R.id.path);
		mBtnLocal = (Button) findViewById(R.id.localbtn);
		mBtnCloud = (Button) findViewById(R.id.cloudbtn);
		mBtnCloud.setOnClickListener(this);
		mBtnLocal.setOnClickListener(this);
		mCloudListView = (ListView) findViewById(R.id.cloudlistview);
		mCloudListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View v,
					final int position, long arg3) {
				String[] menu = { "从云端删除", "下载到本地" };

				new AlertDialog.Builder(SDFileExplorer.this).setTitle("操作")
						.setItems(menu, new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								if (which == 0) {
									// 删除云端
									deleteBaiduFile(position);
								} else if (which == 1) {
									// 下载云端数据到本地
									downloadBaiduFile(position);
								}
							}
						}).show();
			}
		});
		File root = new File("/mnt/sdcard/sound_recorder");
		if (root.exists()) {
			currentParent = root;
			currentFiles = root.listFiles();
			inflateListView(currentFiles);
		}
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				int menuLength;
				if (mIsLogin) {
					menuLength = 3;
				} else {
					menuLength = 2;
				}
				String[] menu = new String[menuLength];
				menu[0] = "播放";
				menu[1] = "删除";
				if (mIsLogin) {
					menu[2] = "上传到百度云";
				}
				final File file = currentFiles[position];
				DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (which == 2) {
							if (!mIsLogin) {
								Toast.makeText(SDFileExplorer.this, "请先登录",
										Toast.LENGTH_SHORT).show();
							}
							new Thread() {
								public void run() {
									final PCSFileInfoResponse fileResponse = mBaiduClient.uploadFile(
											file.getAbsolutePath(),
											BAIDU_ROOT_PATH + file.getName(),
											new BaiduPCSStatusListener() {

												@Override
												public void onProgress(
														long progress,
														long total) {
													runOnUiThread(new Runnable() {
														public void run() {
														}
													});
												}
											});
									runOnUiThread(new Runnable() {
										public void run() {

											String toastMsg;
											if (fileResponse.status.errorCode == 0) {
												toastMsg = "传输成功";
											} else {
												toastMsg = fileResponse.status.message
														.toString();
											}
											Toast.makeText(SDFileExplorer.this,
													toastMsg,
													Toast.LENGTH_SHORT).show();

										}
									});
								};
							}.start();
						} else if (which == 1) {
							file.delete();
							File root = new File("/mnt/sdcard/sound_recorder");
							if (root.exists()) {
								currentParent = root;
								currentFiles = root.listFiles();
								inflateListView(currentFiles);
								simpleAdapter.notifyDataSetChanged();
							}
						} else if (which == 0) {
							try {
								mPlayer.setDataSource(file.getAbsolutePath());
							} catch (IllegalArgumentException e) {
								e.printStackTrace();
							} catch (IllegalStateException e) {
								e.printStackTrace();
							} catch (IOException e) {
								e.printStackTrace();
							}
							try {
								mPlayer.prepare();
							} catch (IllegalStateException e) {
								e.printStackTrace();
							} catch (IOException e) {
								e.printStackTrace();
							}
							mPlayer.start();
							new AlertDialog.Builder(SDFileExplorer.this)
									.setTitle("正在播放")
									.setMessage(file.getName() + " 正在播放...")
									.setPositiveButton(
											"停止",
											new DialogInterface.OnClickListener() {

												public void onClick(
														DialogInterface dialog,
														int which) {
													mPlayer.stop();
													mPlayer.release();
												}

											}).show();
						}
					}
				};

				new AlertDialog.Builder(SDFileExplorer.this).setTitle("操作")
						.setItems(menu, listener).show();

				// // TODO Auto-generated method stub
				// if (currentFiles[position].isFile()) {
				// MediaPlayer mediaPlayer = new MediaPlayer();
				// try {
				// mediaPlayer.setDataSource(currentFiles[position]
				// .getAbsolutePath());
				// mediaPlayer.prepare();
				// mediaPlayer.start();
				// } catch (Exception e) {
				// e.printStackTrace();
				// }
				// Recorder mRecorder = new Recorder(SDFileExplorer.this);
				// mRecorder.startPlayback(mRecorder.playProgress());
				//
				// }
				// String abcString=currentFiles[position].getName();
				//
				//
				//
				// //
				// filePath = "/mnt/sdcard/sound_recorder";
				//
				// fileAudioList = new File(filePath + "/" + abcString);

				// openFile(fileAudioList);
				//

				//
				//
				// File[] tmp = currentFiles[position].listFiles();
				//
				//
				// if (tmp == null || tmp.length == 0)
				// {
				// Toast.makeText(SDFileExplorer.this, "当前路径不可访问或该路径下没有文件",
				// Toast.LENGTH_SHORT).show();
				// }
				// else{
				// currentParent = currentFiles[position];
				// currentFiles = tmp;
				// inflateListView(currentFiles);
				// }
			}
		});
	}

	private void setCloudAdapter() {
		new Thread() {
			@Override
			public void run() {
				PCSListInfoResponse response = mBaiduClient.list(
						BAIDU_ROOT_PATH, "time", "desc");
				mBaiduFileList = response.list;
				final String[] itemStrings = new String[mBaiduFileList.size()];
				for (int i = 0; i < mBaiduFileList.size(); i++) {
					itemStrings[i] = mBaiduFileList.get(i).path.substring(20);
				}
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						mCloudAdapter = new ArrayAdapter<String>(
								SDFileExplorer.this,
								android.R.layout.simple_list_item_1,
								itemStrings);
						mCloudListView.setAdapter(mCloudAdapter);
						mCloudAdapter.notifyDataSetChanged();
					}
				});
			}
		}.start();

	}

	private void inflateListView(File[] files) {
		List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < files.length; i++) {
			Map<String, Object> listItem = new HashMap<String, Object>();
			if (files[i].isDirectory()) {
				listItem.put("icon", R.drawable.folder);
			} else {
				listItem.put("icon", R.drawable.file);
			}
			listItem.put("fileName", files[i].getName());
			listItems.add(listItem);
		}
		simpleAdapter = new SimpleAdapter(this, listItems, R.layout.line,
				new String[] { "icon", "fileName" }, new int[] { R.id.icon,
						R.id.file_name });
		listView.setAdapter(simpleAdapter);
		try {
			textView.setText("当前路径为：" + currentParent.getCanonicalPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.cloudbtn:
			mCloudListView.setVisibility(View.VISIBLE);
			listView.setVisibility(View.GONE);
			setCloudAdapter();
			break;
		case R.id.localbtn:
			mCloudListView.setVisibility(View.GONE);
			listView.setVisibility(View.VISIBLE);
			File root = new File("/mnt/sdcard/sound_recorder");
			if (root.exists()) {
				currentParent = root;
				currentFiles = root.listFiles();
				inflateListView(currentFiles);
			}
			break;
		}
	}

	// 删除百度云端的文件
	private void deleteBaiduFile(final int cloudFileIndex) {
		new Thread() {
			public void run() {
				final PCSSimplefiedResponse response = mBaiduClient
						.deleteFile(mBaiduFileList.get(cloudFileIndex).path);
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						String toastMsg;
						if (response.errorCode == 0) {
							toastMsg = "删除成功";
							setCloudAdapter();// 删除成功刷新listview
						} else {
							toastMsg = response.message;
						}
						Toast.makeText(SDFileExplorer.this, toastMsg,
								Toast.LENGTH_SHORT).show();

					}
				});
			};
		}.start();

	}

	// 下载班底云端文件
	private void downloadBaiduFile(final int cloudFileIndex) {
		new Thread() {
			public void run() {
				final PCSSimplefiedResponse response = mBaiduClient
						.downloadFile(
								mBaiduFileList.get(cloudFileIndex).path,

								"/mnt/sdcard/sound_recorder/"
										+ mBaiduFileList.get(cloudFileIndex).path
												.substring(20));
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						String toastMsg;
						if (response.errorCode == 0) {
							toastMsg = "下载成功";
							setCloudAdapter();// 下载成功刷新listview
						} else {
							toastMsg = response.message;
						}
						Toast.makeText(SDFileExplorer.this, toastMsg,
								Toast.LENGTH_SHORT).show();

					}
				});
			};
		}.start();
	}
}

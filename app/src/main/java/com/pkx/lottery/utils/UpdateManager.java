package com.pkx.lottery.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ProgressBar;

import com.pkx.lottery.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class UpdateManager {
	ArrayList<String> getCode = new ArrayList<String>();
	// 下载中...
	private static final int DOWNLOAD = 1;
	AlertDialog updateYesOrNo;
	private static final String apkName = "LOTT" + System.currentTimeMillis();

	// 下载完成
	private static final int DOWNLOAD_FINISH = 2;

	public String getUrlStr() {
		return urlStr;
	}

	public int getServiceVersionCode() {
		return serviceVersionCode;
	}

	String urlStr = null;
	int serviceVersionCode = 0;

	// 保存解析的XML信息
	// HashMap<String , String> mHashMap;
	// 下载保存路径
	private String mSavePath;
	// 记录进度条数量
	private int progress;
	// 是否取消更新
	private boolean cancelUpdate = false;
	// 上下文对象
	private Context mContext;
	// 进度条
	private ProgressBar mProgressBar;
	// 更新进度条的对话框
	private Dialog mDownloadDialog;

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
				// 下载中。。。
				case DOWNLOAD:
					// 更新进度条
					System.out.println(progress);
					mProgressBar.setProgress(progress);
					break;
				// 下载完成
				case DOWNLOAD_FINISH:
					// 安装文件
					installApk();
					break;
			}
		};
	};

	public UpdateManager(Context context, String apkUrl) {
		super();
		this.mContext = context;
		this.urlStr = apkUrl;
	}

	/**
	 * 检测软件更新
	 *
	 */
	public void checkUpdate() {
		// if (isUpdate()) {
		// 显示提示对话框
		showNoticeDialog();
		// } else {
		// Log.e("pkxh", "不更新");
		// // Toast.makeText(mContext, "不更新", Toast.LENGTH_SHORT).show();
		// }

	}

	private void showNoticeDialog() {
		// TODO Auto-generated method stub
		// 构造对话框
		if (updateYesOrNo == null) {
			updateYesOrNo = new AlertDialog.Builder(mContext).create();
			updateYesOrNo.setCanceledOnTouchOutside(false);
			updateYesOrNo.show();
			updateYesOrNo.getWindow().setContentView(
					R.layout.update_yesorno_dialog);
		}
		if (!updateYesOrNo.isShowing()) {
			updateYesOrNo.show();
		}
		View okButton = updateYesOrNo.findViewById(R.id.ok);
		okButton.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				updateYesOrNo.dismiss();
				// 显示下载对话框
				showDownloadDialog();
				return false;
			}
		});
		View cancelButton = updateYesOrNo.findViewById(R.id.cancel);
		cancelButton.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				updateYesOrNo.dismiss();
				return false;
			}
		});
		// ----------------------------------------------
		// AlertDialog.Builder builder = new Builder(mContext);
		// builder.setTitle("联网更新应用程序");
		// builder.setMessage("新版本增强了程序的稳定性.");
		// // 更新
		// builder.setPositiveButton("立刻更新", new OnClickListener() {
		//
		// @Override
		// public void onClick(DialogInterface dialog, int which) {
		// // TODO Auto-generated method stub
		// dialog.dismiss();
		// // 显示下载对话框
		// showDownloadDialog();
		// }
		// });
		// // 稍后更新
		// builder.setNegativeButton("暂不更新", new OnClickListener() {
		//
		// @Override
		// public void onClick(DialogInterface dialog, int which) {
		// // TODO Auto-generated method stub
		// dialog.dismiss();
		// }
		// });
		// Dialog noticeDialog = builder.create();
		// noticeDialog.show();
	}

	private void showDownloadDialog() {
		// 构造软件下载对话框
		final AlertDialog load = new AlertDialog.Builder(mContext).create();
		load.setCanceledOnTouchOutside(false);
		load.setCancelable(false);
		load.show();
		mDownloadDialog = load;
		load.getWindow().setContentView(R.layout.update_dialog);
		mProgressBar = (ProgressBar) load.findViewById(R.id.update_progress);
		View cancelButton = load.findViewById(R.id.cancel);
		cancelButton.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				load.dismiss();
				// 设置取消状态
				cancelUpdate = true;
				return false;
			}
		});
		downloadApk();
		// ---------------------------------------
		//
		// AlertDialog.Builder builder = new Builder(mContext);
		// builder.setTitle("68彩票更新");
		// // 给下载对话框增加进度条
		// final LayoutInflater inflater = LayoutInflater.from(mContext);
		// View view = inflater.inflate(R.layout.softupdate_progress, null);
		// mProgressBar = (ProgressBar) view.findViewById(R.id.update_progress);
		// builder.setView(view);
		// builder.setNegativeButton("取消", new OnClickListener() {
		//
		// @Override
		// public void onClick(DialogInterface dialog, int which) {
		// // TODO Auto-generated method stub
		// dialog.dismiss();
		// // 设置取消状态
		// cancelUpdate = true;
		// }
		// });
		// mDownloadDialog = builder.create();
		// mDownloadDialog.show();
		// downloadApk();
	}

	/**
	 * 下载APK文件
	 */
	private void downloadApk() {
		// TODO Auto-generated method stub
		// 启动新线程下载软件
		new DownloadApkThread().start();
	}

	/**
	 * 下载文件线程
	 *
	 * @author Administrator
	 *
	 */
	private class DownloadApkThread extends Thread {
		@Override
		public void run() {
			try {
				// 判断SD卡是否存在，并且是否具有读写权限
				if (Environment.getExternalStorageState().equals(
						Environment.MEDIA_MOUNTED)) {
					// 获取SDCard的路径
					String sdpath = Environment.getExternalStorageDirectory()
							+ "/";
					mSavePath = sdpath + "download";
					URL url = new URL(urlStr);
					// 创建连接
					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
					conn.connect();
					// 获取文件大小
					int length = conn.getContentLength();
					// 创建输入流
					InputStream is = conn.getInputStream();

					File file = new File(mSavePath);
					// 如果文件不存在，新建目录
					if (!file.exists()) {
						file.mkdir();
					}
					File apkFile = new File(mSavePath, apkName);
					FileOutputStream fos = new FileOutputStream(apkFile);
					int count = 0;
					// 缓存
					byte buf[] = new byte[1024];
					// 写入到文件中
					do {
						int numread = is.read(buf);
						count += numread;
						// 计算进度条的位置
						progress = (int) (((float) count / length) * 100);
						// 更新进度
						mHandler.sendEmptyMessage(DOWNLOAD);
						if (numread <= 0) {
							// 下载完成
							mHandler.sendEmptyMessage(DOWNLOAD_FINISH);
							break;
						}
						// 写入文件
						fos.write(buf, 0, numread);
					} while (!cancelUpdate);// 点击取消就停止下载
					fos.close();
					is.close();
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			// 取消下载对话框显示
			mDownloadDialog.dismiss();
		}
	}

	/**
	 * 安装APK文件
	 */
	private void installApk() {
		File apkfile = new File(mSavePath, apkName);
		if (!apkfile.exists()) {
			return;
		}
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setDataAndType(Uri.parse("file://" + apkfile.toString()),
				"application/vnd.android.package-archive");
		mContext.startActivity(i);
	}
}

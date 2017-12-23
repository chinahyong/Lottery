package com.pkx.lottery.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;
import com.pkx.lottery.Constant;
import com.pkx.lottery.R;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;

public class Net {
	public static final Gson gson;
	private static int times = 0;
	public static AsyncHttpClient client;
	private static String ur;
	private static boolean show;
	private static ProgressDialog dialog;
	private static RequestParams pa;
	static {
		GsonBuilder builder = new GsonBuilder();
		builder.excludeFieldsWithoutExposeAnnotation();
		// client = new AsyncHttpClient();
		// client.setTimeout(3*10000);
		// builder.setDateFormat("yyyy-MM-ddHH:mm:ss ZZ");
		builder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		gson = builder.create();
	}
	private static int dialogCount = 0;

	// private static Handler mHandler;
	private static Activity acti;
	private static int mAction = 0;
	public JsonHttpResponseHandler interface1 = new JsonHttpResponseHandler() {
		public void onFailure(int statusCode, Header[] headers,
							  byte[] responseBody, Throwable error) {
		};

		public void onStart() {
		};

		public void onSuccess(int statusCode, Header[] headers,
							  JSONObject response) {
		};
	};

	public static ResponseHandlerInterface interf = new ResponseHandlerInterface() {

		@Override
		public void setUseSynchronousMode(boolean arg0) {
			Log.e("pkx", "ResponseHandlerInterface");
			// if(Constant.DEBUG_FLAG){
			// Toast.makeText(acti, "setUseSynchronousMode",
			// Toast.LENGTH_SHORT).show();
			// }

		}

		@Override
		public void setRequestURI(URI arg0) {
			// if(Constant.DEBUG_FLAG){
			// Toast.makeText(acti, "setRequestURI", Toast.LENGTH_SHORT).show();
			// }
		}

		@Override
		public void setRequestHeaders(Header[] arg0) {
			// if(Constant.DEBUG_FLAG){
			// Toast.makeText(acti, "setRequestHeaders",
			// Toast.LENGTH_SHORT).show();
			// }
		}

		@Override
		public void sendSuccessMessage(int arg0, Header[] arg1, byte[] arg2) {
			// if(Constant.DEBUG_FLAG){
			// Toast.makeText(acti, "sendSuccessMessage",
			// Toast.LENGTH_SHORT).show();
			// }
		}

		@Override
		public void sendStartMessage() {
			// if(Constant.DEBUG_FLAG){
			// Toast.makeText(acti, "sendStartMessage",
			// Toast.LENGTH_SHORT).show();
			// }
		}

		@Override
		public void sendRetryMessage() {
			// if(Constant.DEBUG_FLAG){
			// Toast.makeText(acti, "sendRetryMessage",
			// Toast.LENGTH_SHORT).show();
			// }
		}

		@Override
		public void sendResponseMessage(HttpResponse arg0) throws IOException {
			// try {
			// Thread.sleep(5000);
			// } catch (InterruptedException e) {
			// e.printStackTrace();
			// }
			closeProgress();
			// String postRst = EntityUtils.toString(arg0.getEntity());
			// Log.e("pkxh", "短信验证：" + postRst);
			// JSONObject jo;
			// Log.e("pkx", "sendResponseMessage" + postRst);
			Log.e("pkx", "NET---POST4");
			org.json.JSONObject ojo = null;
			String json = EntityUtils.toString(arg0.getEntity());
			Log.e("pkx", "NET  json lenth:" + json.length() + "--" + "json:"
					+ json);
			try {
				ojo = new JSONObject(json);
				Log.e("pkx", "NET JSON:" + ojo.toString());
				Message msg = new Message();
				Log.e("pkx", "status" + ojo.getString("status"));
				if ("1".equals(ojo.getString("status"))) {
					msg.what = Constant.POST_SUCCESS_STATUS_ONE;
					msg.obj = ojo;
				} else if ("911".equals(ojo.getString("status"))) {
					Log.e("pkx", "911");
				} else {
					msg.what = Constant.POST_SUCCESS_STATUS_ZERO;
					msg.obj = ojo;
					Log.e("pkx", "NET--------POST_SUCCESS_STATUS_ZERO");
					// msg.obj = ojo.getString("errorTopic");
				}
				msg.arg1 = mAction;
				handler.sendMessage(msg);
			} catch (JSONException e) {
				Log.e("pkx", "net exception---");
				try {
					if ("ok".equals(ojo.getString("mess"))) {
						Log.e("pkx", "net exception---mess=ok");
						Message m = new Message();
						m.obj = ojo;
						m.arg1 = mAction;
						handler.sendMessage(m);
					}
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
			}

		}

		@Override
		public void sendProgressMessage(int arg0, int arg1) {
			// if(Constant.DEBUG_FLAG){
			// Toast.makeText(acti, "sendProgressMessage",
			// Toast.LENGTH_SHORT).show();
			// }
		}

		@Override
		public void sendFinishMessage() {
			// if(Constant.DEBUG_FLAG){
			// Toast.makeText(acti, "sendFinishMessage",
			// Toast.LENGTH_SHORT).show();
			// }
		}

		@Override
		public void sendFailureMessage(int arg0, Header[] arg1, byte[] arg2,
									   Throwable arg3) {
			closeProgress();
			Toast.makeText(acti, "获取失败", Toast.LENGTH_SHORT).show();
			// if(times<3){
			// times++;
			// if (show) {
			// showProgress(acti);
			// }
			// if (!checkNet(acti)) {
			// closeProgress();
			// showNetSetting(acti);
			// return;
			// }
			// new Thread(new Runnable() {
			//
			// @Override
			// public void run() {
			// client.post(acti, ur, pa, interf);
			// }
			// }).start();
			// }
			Log.e("pkx", "NET---POST3" + arg3.getMessage());
			// Message msg = new Message();
			// msg.what = Constant.POST_FAIL;
			// msg.obj = arg0;
			// msg.arg1 = mAction;
			// mHandler.sendMessage(msg);

		}

		@Override
		public URI getRequestURI() {
			// if(Constant.DEBUG_FLAG){
			// Toast.makeText(acti, "getRequestURI", Toast.LENGTH_SHORT).show();
			// }
			return null;
		}

		@Override
		public Header[] getRequestHeaders() {
			// if(Constant.DEBUG_FLAG){
			// Toast.makeText(acti, "getRequestHeaders",
			// Toast.LENGTH_SHORT).show();
			// }
			return null;
		}
	};

	public static void delete(final Activity ctx, String url,
							  RequestParams params) {
		client.delete(ctx, url, null, params, interf);
	}

	public static <T> void post(Boolean showDialog, final Activity ctx,
								final String url, final RequestParams params,
								final Handler handler, int action) {
		Log.e("pkxh", "NET---POST--act：" + String.valueOf(action));
		mAction = action;
		acti = ctx;
		pa = params;
		ur = url;
		show = showDialog;
		if (showDialog) {
			showProgress(ctx);
		}
		if (!checkNet(ctx)) {
			closeProgress();
			showNetSetting(ctx);
			return;
		}
		client = new AsyncHttpClient();
		client.setTimeout(3 * 10000);
		new Thread(new Runnable() {

			@Override
			public void run() {
				client.post(ctx, url, params, new ResponseHandlerInterface() {

					@Override
					public void setUseSynchronousMode(boolean arg0) {
						Log.e("pkx",
								"ResponseHandlerInterface  回调：setUseSynchronousMode");
						// if(Constant.DEBUG_FLAG){
						// Toast.makeText(acti, "setUseSynchronousMode",
						// Toast.LENGTH_SHORT).show();
						// }

					}

					@Override
					public void setRequestURI(URI arg0) {
						Log.e("pkx",
								"ResponseHandlerInterface  回调：setRequestURI");
						// if(Constant.DEBUG_FLAG){
						// Toast.makeText(acti, "setRequestURI",
						// Toast.LENGTH_SHORT).show();
						// }
					}

					@Override
					public void setRequestHeaders(Header[] arg0) {
						Log.e("pkx",
								"ResponseHandlerInterface  回调：setRequestHeaders");
						// if(Constant.DEBUG_FLAG){
						// Toast.makeText(acti, "setRequestHeaders",
						// Toast.LENGTH_SHORT).show();
						// }
					}

					@Override
					public void sendSuccessMessage(int arg0, Header[] arg1,
												   byte[] arg2) {
						Log.e("pkx",
								"ResponseHandlerInterface  回调：sendSuccessMessage");
						// if(Constant.DEBUG_FLAG){
						// Toast.makeText(acti, "sendSuccessMessage",
						// Toast.LENGTH_SHORT).show();
						// }
					}

					@Override
					public void sendStartMessage() {
						Log.e("pkx",
								"ResponseHandlerInterface  回调：sendStartMessage");
						// if(Constant.DEBUG_FLAG){
						// Toast.makeText(acti, "sendStartMessage",
						// Toast.LENGTH_SHORT).show();
						// }
					}

					@Override
					public void sendRetryMessage() {
						Log.e("pkx",
								"ResponseHandlerInterface  回调：sendRetryMessage");
						// if(Constant.DEBUG_FLAG){
						// Toast.makeText(acti, "sendRetryMessage",
						// Toast.LENGTH_SHORT).show();
						// }
					}

					@Override
					public void sendResponseMessage(HttpResponse arg0)
							throws IOException {
						// try {
						// Thread.sleep(5000);
						// } catch (InterruptedException e) {
						// e.printStackTrace();
						// }
						closeProgress();
						// String postRst =
						// EntityUtils.toString(arg0.getEntity());
						// Log.e("pkxh", "短信验证：" + postRst);
						// JSONObject jo;
						// Log.e("pkx", "sendResponseMessage" + postRst);
						Log.e("pkx", "NET---POST4");
						org.json.JSONObject ojo = null;
						String json = EntityUtils.toString(arg0.getEntity());
						Log.e("pkx", "NET  json lenth:" + json.length() + "--"
								+ "json:" + json);
						try {
							ojo = new JSONObject(json);
							Log.e("pkx", "NET JSON:" + ojo.toString());
							Message msg = new Message();
							Log.e("pkx", "status" + ojo.getString("status"));
							if ("1".equals(ojo.getString("status"))) {
								msg.what = Constant.POST_SUCCESS_STATUS_ONE;
								msg.obj = ojo;
							} else {
								msg.what = Constant.POST_SUCCESS_STATUS_ZERO;
								msg.obj = ojo;
								Log.e("pkx",
										"NET--------POST_SUCCESS_STATUS_ZERO");
								// msg.obj = ojo.getString("errorTopic");
							}
							msg.arg1 = mAction;
							handler.sendMessage(msg);
						} catch (JSONException e) {
							Log.e("pkx", "net exception---");
							try {
								if ("ok".equals(ojo.getString("mess"))) {
									Log.e("pkx", "net exception---mess=ok");
									Message m = new Message();
									m.obj = ojo;
									m.arg1 = mAction;
									handler.sendMessage(m);
								}
							} catch (JSONException e1) {
								e1.printStackTrace();
							}
							e.printStackTrace();
						}

					}

					@Override
					public void sendProgressMessage(int arg0, int arg1) {
						Log.e("pkx",
								"ResponseHandlerInterface  回调：sendProgressMessage");
						// if(Constant.DEBUG_FLAG){
						// Toast.makeText(acti, "sendProgressMessage",
						// Toast.LENGTH_SHORT).show();
						// }
					}

					@Override
					public void sendFinishMessage() {
						Log.e("pkx",
								"ResponseHandlerInterface  回调：sendFinishMessage");
						// if(Constant.DEBUG_FLAG){
						// Toast.makeText(acti, "sendFinishMessage",
						// Toast.LENGTH_SHORT).show();
						// }
					}

					@Override
					public void sendFailureMessage(int arg0, Header[] arg1,
												   byte[] arg2, Throwable arg3) {
						closeProgress();
						Toast.makeText(acti, "获取失败", Toast.LENGTH_SHORT).show();
						// if(times<3){
						// times++;
						// if (show) {
						// showProgress(acti);
						// }
						// if (!checkNet(acti)) {
						// closeProgress();
						// showNetSetting(acti);
						// return;
						// }
						// new Thread(new Runnable() {
						//
						// @Override
						// public void run() {
						// client.post(acti, ur, pa, interf);
						// }
						// }).start();
						// }
						Log.e("pkx", "NET---POST3" + arg3.getMessage());
						// Message msg = new Message();
						// msg.what = Constant.POST_FAIL;
						// msg.obj = arg0;
						// msg.arg1 = mAction;
						// mHandler.sendMessage(msg);

					}

					@Override
					public URI getRequestURI() {
						Log.e("pkx",
								"ResponseHandlerInterface  回调：getRequestURI");
						// if(Constant.DEBUG_FLAG){
						// Toast.makeText(acti, "getRequestURI",
						// Toast.LENGTH_SHORT).show();
						// }
						return null;
					}

					@Override
					public Header[] getRequestHeaders() {
						Log.e("pkx",
								"ResponseHandlerInterface  回调：getRequestHeaders");
						// if(Constant.DEBUG_FLAG){
						// Toast.makeText(acti, "getRequestHeaders",
						// Toast.LENGTH_SHORT).show();
						// }
						return null;
					}
				});
			}
		}).start();

		Log.e("pkx", "NET---POST2");
		// mHandler = handler;
	}

	private static Handler handler = new Handler(Looper.getMainLooper());

	// public static boolean isShowCheck;

	public static void showNetSetting(final Activity activity) {
		handler.post(new Runnable() {

			public void run() {
				// new AlertDialog.Builder(activity)
				// .setTitle("网络异常")
				// .setMessage(
				// "进入网络选择界面")
				// .setPositiveButton("确定",
				// new DialogInterface.OnClickListener() {
				// public void onClick(DialogInterface arg0,
				// int arg1) {
				// System.gc();
				// activity.startActivityForResult(
				// new Intent(
				// android.provider.Settings.ACTION_WIRELESS_SETTINGS),
				// 0);
				// isShowCheck = false;
				// }
				// })
				// .setNegativeButton("取消",
				// new DialogInterface.OnClickListener() {
				//
				// public void onClick(DialogInterface arg0,
				// int arg1) {
				// isShowCheck = false;
				// }
				//
				// }).setCancelable(false).show();
				alert(activity);
			}
		});
	}

	private static synchronized void closeProgress() {
		dialogCount--;
		if (dialogCount > 0) {
			return;
		}
		handler.post(new Runnable() {
			public synchronized void run() {
				if (dialog != null && dialog.isShowing()) {
					try {
						dialog.dismiss();
					} catch (IllegalArgumentException e) {
						System.out.println(e.getMessage());
					}

				}
			}
		});
	}

	public static boolean checkNet(Context acitivity) {

		ConnectivityManager manager = (ConnectivityManager) acti
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = manager.getActiveNetworkInfo();
		// try {
		// ConnectivityManager manager = (ConnectivityManager) acitivity
		// .getApplicationContext().getSystemService(
		// Context.CONNECTIVITY_SERVICE);
		// if (manager == null) {
		// return false;
		// }
		// NetworkInfo networkinfo = manager.getActiveNetworkInfo();
		// if (networkinfo == null || !networkinfo.isAvailable()) {
		// return false;
		// } else {
		// return true;
		// }
		// } catch (Exception e) {
		// e.printStackTrace();
		// return false;
		// }
		if (info != null && info.isConnected()) {
			return true;
		} else {
			return false;
		}
	}

	private synchronized static void showProgress(final Context activity) {
		dialogCount++;
		if (dialogCount > 1) {
			return;
		}
		handler.post(new Runnable() {
			public synchronized void run() {
				dialog = new ProgressDialog(activity, R.style.dialog);
				dialog.setCancelable(true);
				dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				dialog.setIndeterminate(true);
				dialog.setCanceledOnTouchOutside(false);
				if (dialog != null && !dialog.isShowing()) {
					try {
						dialog.show();
						dialog.setContentView(R.layout.net_dialog);
					} catch (Exception e) {
					}

				}
			}
		});
	};

	public static void alert(final Activity act) {
		final AlertDialog alert = new AlertDialog.Builder(act).create();
		alert.show();
		alert.getWindow().setContentView(R.layout.exit_dialog);

		TextView title = (TextView) alert.findViewById(R.id.title);
		title.setText("当前网络未连接，点击确定进入网络设置界面，确定？");
		View okButton = alert.findViewById(R.id.ok);
		okButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				System.gc();
				act.startActivityForResult(new Intent(
						android.provider.Settings.ACTION_SETTINGS), 0);
				alert.dismiss();
			}
		});

		View cancelButton = alert.findViewById(R.id.cancel);
		cancelButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				alert.dismiss();
			}
		});
	}
}

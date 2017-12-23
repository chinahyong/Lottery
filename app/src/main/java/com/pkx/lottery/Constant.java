package com.pkx.lottery;

import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.pkx.lottery.dto.basketball.BasDayMatchs;
import com.pkx.lottery.headerlist.DayMatchs;

import java.util.ArrayList;

public class Constant {
	// Release:101.37.17.112:18081 debug:  118.192.9.173
	public static boolean DEBUG_FLAG = true;
	public static String log;
	public static boolean VERSION_NO_FOOTBALLorBASTKETBALL = false;
	// public static String SID;
	public static String SHARE = "share";
	private static final String RELEASE_HOST = "http://101.37.17.112:";
	private static final String DEBUG_HOST = "http://118.192.9.173:";
	private static final String HOST = BuildConfig.DEBUG ? RELEASE_HOST : RELEASE_HOST;
	//http://118.192.9.173:1004";
	public static final String POST_URL_LOG = RELEASE_HOST + "18081";
	//"http://118.192.9.173:18080"
	public static String PAY_URL = RELEASE_HOST + "18081";
	//18080
	public static String RULES_URL = RELEASE_HOST+"18081/article.php?nid=121&Htype=wap";
	//8088
	public static String POST_URL = RELEASE_HOST+"18081";

	//	public static String RULES_URL = "http://www.hc88.cc/article.php?nid=121&Htype=wap";
//	 public static String POST_URL="http://api.hc88.cc";
//	public static String PAY_URL = "http://www.hc88.cc";
//	public static String RULES_URL = "http://test.hc88.cc/article.php?nid=121&Htype=wap";
//	public static String POST_URL="http://api.test.hc88.cc";
//	public static String PAY_URL = "http://test.hc88.cc";
	public static void alertWarning(Context ctx, String warning) {
		// if (alert == null) {
		final AlertDialog alert = new AlertDialog.Builder(ctx).create();
		// }
		alert.show();
		alert.getWindow().setContentView(R.layout.warning_dialog);
		TextView title = (TextView) alert.findViewById(R.id.title);
		title.setText(warning);
		View okButton = alert.findViewById(R.id.ok);
		okButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				alert.dismiss();
			}
		});
	}

	public static String getVersionName(Context ctx) {

		try {
			PackageManager pm = ctx.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(),
					PackageManager.GET_ACTIVITIES);

			if (pi != null) {
				return pi.versionName == null ? "null" : pi.versionName;
			} else {
				return "v_info_null";
			}
		} catch (NameNotFoundException e) {
			return "v_exception";
		}
	}

	// public static String uid;
	// public static String userKEY;
	// public static String deviceKEY;sdfgsdfg
	// public static String LOGIN_STRING="立即登录";
	public static int CORPBUY_LOTT_TYPE;
	public static int CHONGQING_PLAY_TYPE = 1;
	public static int BASKETBALL_PLAY_TYPE = 0;
	public static int HAPPY_PLAY_TYPE = 0;
	public static final int PAGE_CHANGED = 1;
	public static final int HOME_LIST_ITEM = 2;
	public static final int SHAKE_MESSAGE = 3;
	public static final int DOUBLE_FOLLOW = 4;
	public static final int DELETE_LIST_ITEM = 5;
	public static final int AD_PAGE_CHANGED = 6;
	public static final int POST_SUCCESS_STATUS_ONE = 7;
	public static final int POST_FAIL = 8;
	public static final int CLEAR_BET = 9;
	public static final int TICK_TOCK = 10;
	public static final int NET_ACTION_LOGIN = 11;
	public static final int NET_ACTION_REGIST = 12;
	public static final int FOOTBALL_BET_FRESH = 13;
	public static final int FOOTBALL_BET_MSG = 14;
	public static final int NOTICE_ITEM_CLICK = 15;
	public static final int COPER_ITEM_CLICK = 16;
	public static final int FOOTBALL_TYPE_CHANGED = 17;
	public static final int POST_SUCCESS_STATUS_ZERO = 18;
	public static final int NET_ACTION_USERINFO = 19;
	public static final int NET_ACTION_USERACCOUNT = 20;
	public static final int NET_ACTION_EDITPWD = 21;
	public static final int NET_ACTION_GETPERIO = 22;
	public static final int NET_ACTION_LOTHIS_WEF = 23;
	public static final int NET_ACTION_LOTHIS_RWEF = 24;
	public static final int NET_ACTION_LOTHIS = 25;
	public static final int NET_ACTION_LOTHIS_POINTS = 26;
	public static final int NET_ACTION_LOTHIS_MIX = 27;
	public static final int NET_ACTION_LOTHIS_GOALS = 28;
	public static final int NET_ACTION_LOTHIS_HALF = 29;
	public static final int NET_ACTION_CHORMBET = 30;
	public static final int NET_ACTION_DIMENTIONBET = 31;
	public static final int NET_ACTION_SENCENBET = 32;
	public static final int FAST_LOGIN = 33;
	public static final int NET_ACTION_FOOTBALLMIX = 34;
	public static final int CHECK_UPDATE = 35;
	public static final int NET_ACTION_NOTICE_FRESH = 36;
	public static final int NET_ACTION_CORPBUY = 37;
	public static final int NET_ACTION_CORPBUY_INFO = 38;
	public static final int NET_ACTION_SECRET_SECURE = 39;
	public static final int NET_ACTION_REFRESH = 40;
	public static final int NET_ACTION_LOADMORE = 41;
	public static final int NET_ACTION_BUYRECORD = 42;
	public static final int NET_ACTION_NOTICE_LOAD = 43;
	public static final int NET_ACTION_CHECKUPDATE = 44;
	public static final int WHEEL_SELECTED = 44;
	public static final int NET_ACTION_LOG_SEND = 45;
	public static final int REPAY_REQUEST = 46;
	public static final int ADS_PAGE_CHANGED = 47;
	public static int FOOTBALL_TYPE;
	public static boolean TICK_TOCK_FLAG = true;
	public static boolean TICK_TOCK_CHECK_FLAG = true;
	public static boolean isHandlySelect;
	public static boolean isHandlyEdit;
	public static boolean FAST_ANIMATION_FINISH;
	public static ArrayList<ArrayList<ArrayList<Integer>>> MATCH_STATUS;
	public static ArrayList<ArrayList<ArrayList<Integer>>> BASKET_MATCH_STATUS;
	public static ArrayList<DayMatchs> MIX_DAYMATCHESS;
	public static ArrayList<BasDayMatchs> BASEKET_MIX_DAYMATCHESS;
	public static ArrayList<ArrayList<String>> MATCH_INFOS;
	public static ArrayList<ArrayList<String>> BASKET_MATCH_INFOS;
	public static ArrayList<ArrayList<ArrayList<Integer>>> MATCH_BET_STRS = new ArrayList<ArrayList<ArrayList<Integer>>>();
	public static String[] FOOTBALL_BET_STRS = {"胜", "平", "负", "让胜", "让平",
			"让负", "0球", "1球", "2球", "3球", "4球", "5球", "6球", "7+球", "胜胜", "胜平",
			"胜负", "平胜", "平平", "平负", "负胜", "负平", "负负", "1:0", "2:0", "2:1",
			"3:0", "3:1", "3:2", "4:0", "4:1", "4:2", "5:0", "5:1", "5:2",
			"胜其他", "0:0", "1:1", "2:2", "3:3", "平其他", "0:1", "0:2", "1:2",
			"0:3", "1:3", "2:3", "0:4", "1:4", "2:4", "0:5", "1:5", "2:5",
			"负其他"};
	public static String[] BASKETBALL_BET_STRS = {"胜", "负"};
	public static String[] BASKETBALL_BS_BET_STRS = {"大", "小"};
	public static String[] FOOTBALL_BET_STRS_1 = {"3", "1", "0", "3", "1",
			"0", "0球", "1球", "2球", "3球", "4球", "5球", "6球", "7球", "胜胜", "胜平",
			"胜负", "平胜", "平平", "平负", "负胜", "负平", "负负", "1:0", "2:0", "2:1",
			"3:0", "3:1", "3:2", "4:0", "4:1", "4:2", "5:0", "5:1", "5:2",
			"胜其他", "0:0", "1:1", "2:2", "3:3", "平其他", "0:1", "0:2", "1:2",
			"0:3", "1:3", "2:3", "0:4", "1:4", "2:4", "0:5", "1:5", "2:5",
			"负其他"};

	public static String getMixBasString() {

		String betstr = "";
		// ArrayList<ArrayList<Integer>> selectMatchs=new
		// ArrayList<ArrayList<Integer>>();
		for (ArrayList<ArrayList<Integer>> daymatches : BASKET_MATCH_STATUS) {
			for (ArrayList<Integer> match : daymatches) {
				if (match.get(0) == 0 && match.get(1) == 1) {
					betstr += String.valueOf(match.get(5)) + "#0"
							+ String.valueOf(BASKETBALL_PLAY_TYPE) + "#1;";
				} else if (match.get(0) == 1 && match.get(1) == 0) {
					betstr += String.valueOf(match.get(5)) + "#0"
							+ String.valueOf(BASKETBALL_PLAY_TYPE) + "#0;";
				} else if (match.get(0) == 1 && match.get(1) == 1) {
					betstr += String.valueOf(match.get(5)) + "#0"
							+ String.valueOf(BASKETBALL_PLAY_TYPE) + "#0,1;";
				}

			}
		}

		return betstr;

	}

	public static String getMixBetString() {
		String betstr = "";
		// ArrayList<ArrayList<Integer>> selectMatchs=new
		// ArrayList<ArrayList<Integer>>();
		for (ArrayList<ArrayList<Integer>> daymatches : MATCH_STATUS) {
			for (ArrayList<Integer> match : daymatches) {
				String matchStr = "";
				if (match.get(64) == 1) {
					boolean wefSelected = false;// 胜平负 05
					boolean handicWefSelected = false;// 让球胜平负 01
					boolean goalsSelected = false;// 进球数 02
					boolean halfSelected = false;// 半全场 03
					boolean pointsSelected = false;// 全场比分 04
					String wefStr = "";
					String handicWefStr = "";
					String goalsStr = "";
					String halfStr = "";
					String pointsStr = "";
					for (int i = 0; i < 3; i++) {
						if (match.get(i) == 1) {
							wefStr = String.valueOf(match.get(62)) + "#05#";
							wefSelected = true;
							break;
						}
					}
					if (wefSelected) {
						for (int i = 0; i < 3; i++) {
							if (match.get(i) == 1) {
								wefStr += FOOTBALL_BET_STRS_1[i] + ",";
							}
						}
						matchStr += wefStr.substring(0, wefStr.length() - 1)
								+ ";";
					}

					for (int i = 3; i < 6; i++) {
						if (match.get(i) == 1) {
							handicWefStr = String.valueOf(match.get(62))
									+ "#01#";
							handicWefSelected = true;
							break;
						}
					}
					if (handicWefSelected) {
						for (int i = 3; i < 6; i++) {
							if (match.get(i) == 1) {
								handicWefStr += FOOTBALL_BET_STRS_1[i] + ",";
							}
						}
						matchStr += handicWefStr.substring(0,
								handicWefStr.length() - 1)
								+ ";";
					}
					for (int i = 6; i < 14; i++) {
						if (match.get(i) == 1) {
							goalsStr = String.valueOf(match.get(62)) + "#02#";
							goalsSelected = true;
							break;
						}
					}
					if (goalsSelected) {
						for (int i = 6; i < 14; i++) {
							if (match.get(i) == 1) {
								goalsStr += FOOTBALL_BET_STRS_1[i] + ",";
							}
						}
						matchStr += goalsStr
								.substring(0, goalsStr.length() - 1) + ";";
					}
					for (int i = 14; i < 23; i++) {
						if (match.get(i) == 1) {
							halfStr = String.valueOf(match.get(62)) + "#03#";
							halfSelected = true;
							break;
						}
					}
					if (halfSelected) {
						for (int i = 14; i < 23; i++) {
							if (match.get(i) == 1) {
								halfStr += FOOTBALL_BET_STRS_1[i] + ",";
							}
						}
						matchStr += halfStr.substring(0, halfStr.length() - 1)
								+ ";";
					}
					for (int i = 23; i < 54; i++) {
						if (match.get(i) == 1) {
							pointsStr = String.valueOf(match.get(62)) + "#04#";
							pointsSelected = true;
							break;
						}
					}
					if (pointsSelected) {
						for (int i = 23; i < 54; i++) {
							if (match.get(i) == 1) {
								pointsStr += FOOTBALL_BET_STRS_1[i] + ",";
							}
						}
						matchStr += pointsStr.substring(0,
								pointsStr.length() - 1) + ";";
					}

				}
				betstr += matchStr;
			}
		}

		return betstr;
	}
}

package com.pkx.lottery.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.pkx.lottery.Constant;
import com.pkx.lottery.R;
import com.pkx.lottery.bean.FootBallBetItem;
import com.pkx.lottery.dto.basketball.BasDayMatchs;
import com.pkx.lottery.dto.basketball.MatchBasketBallWFdto;
import com.pkx.lottery.headerlist.DayMatchs;
import com.pkx.lottery.headerlist.Match;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RandomBallsUtils {
	public static void alertFootballTypeDialog(Activity ctx,
			final Handler handler) {

		final AlertDialog alert = new AlertDialog.Builder(ctx).create();
		int whiteText = Color.parseColor("#ffffff");
		alert.show();
		alert.getWindow().setContentView(R.layout.football_type_dialog);
		TextView type_weft, type_rweft, type_pointst, type_goalst, type_halft, type_mixt;
		type_weft = (TextView) alert.findViewById(R.id.type_weft);
		type_rweft = (TextView) alert.findViewById(R.id.type_rweft);
		type_pointst = (TextView) alert.findViewById(R.id.type_pointst);
		type_goalst = (TextView) alert.findViewById(R.id.type_goalst);
		type_halft = (TextView) alert.findViewById(R.id.type_halft);
		type_mixt = (TextView) alert.findViewById(R.id.type_mixt);
		View type_wef = alert.findViewById(R.id.type_wef);
		View type_rwef = alert.findViewById(R.id.type_rwef);
		View type_points = alert.findViewById(R.id.type_points);
		View type_goals = alert.findViewById(R.id.type_goals);
		View type_half = alert.findViewById(R.id.type_half);
		View type_mix = alert.findViewById(R.id.type_mix);
		View bottomView=alert.findViewById(R.id.bottomView);
		bottomView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				alert.dismiss();
			}
		});
		switch (Constant.FOOTBALL_TYPE) {
		case 1:
			type_weft.setTextColor(whiteText);
			type_wef.setBackgroundResource(R.drawable.bp);
			break;
		case 2:
			type_rweft.setTextColor(whiteText);
			type_rwef.setBackgroundResource(R.drawable.bp);
			break;
		case 3:
			type_pointst.setTextColor(whiteText);
			type_points.setBackgroundResource(R.drawable.bp);
			break;
		case 4:
			type_goalst.setTextColor(whiteText);
			type_goals.setBackgroundResource(R.drawable.bp);
			break;
		case 5:
			type_halft.setTextColor(whiteText);
			type_half.setBackgroundResource(R.drawable.bp);
			break;
		case 6:
			type_mixt.setTextColor(whiteText);
			type_mix.setBackgroundResource(R.drawable.bp);
			break;
		}
		type_wef.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Message msg = new Message();
				msg.what = Constant.FOOTBALL_TYPE_CHANGED;
				msg.arg1 = 1;
				Constant.FOOTBALL_TYPE = 1;
				handler.sendMessage(msg);
				alert.dismiss();
			}
		});
		type_rwef.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Message msg = new Message();
				msg.what = Constant.FOOTBALL_TYPE_CHANGED;
				msg.arg1 = 2;
				Constant.FOOTBALL_TYPE = 2;
				handler.sendMessage(msg);
				alert.dismiss();
			}
		});
		type_points.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Message msg = new Message();
				msg.what = Constant.FOOTBALL_TYPE_CHANGED;
				msg.arg1 = 3;
				Constant.FOOTBALL_TYPE = 3;
				handler.sendMessage(msg);
				alert.dismiss();
			}
		});
		type_goals.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Message msg = new Message();
				msg.what = Constant.FOOTBALL_TYPE_CHANGED;
				msg.arg1 = 4;
				Constant.FOOTBALL_TYPE = 4;
				handler.sendMessage(msg);
				alert.dismiss();
			}
		});
		type_half.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Message msg = new Message();
				msg.what = Constant.FOOTBALL_TYPE_CHANGED;
				msg.arg1 = 5;
				Constant.FOOTBALL_TYPE = 5;
				handler.sendMessage(msg);
				alert.dismiss();
			}
		});
		type_mix.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Message msg = new Message();
				msg.what = Constant.FOOTBALL_TYPE_CHANGED;
				msg.arg1 = 6;
				Constant.FOOTBALL_TYPE = 6;
				handler.sendMessage(msg);
				alert.dismiss();
			}
		});
	}

	public static ArrayList<Integer> getRandomBalls(int ballNumber,
			int totalBalls) {
		Random random = new Random();
		ArrayList<Integer> returnBalls = new ArrayList<Integer>();
		while (returnBalls.size() < ballNumber) {
			int r = random.nextInt(totalBalls);
			if (returnBalls.size() == 0) {
				returnBalls.add(r);
			} else {
				boolean hasBalls = false;
				for (int i : returnBalls) {
					if (i == r) {
						hasBalls = true;
					}
				}
				if (!hasBalls) {
					returnBalls.add(r);
				}
			}
		}
		return returnBalls;
	}

	public static int getBetsNumber(int redBalls, int blueBalls) {
		return getBallBets(6, redBalls) * blueBalls;
	}

	public static ArrayList<Integer> sort(ArrayList<Integer> arr) {
		int[] strs = new int[arr.size()];
		for (int i = 0; i < arr.size(); i++) {
			strs[i] = arr.get(i);
		}
		Arrays.sort(strs);
		arr.clear();
		for (int i = 0; i < strs.length; i++) {
			arr.add(strs[i]);
		}
		return arr;
	}

	public static int getBallBets(int selectBalls, int totalBalls) {
		return (int) (recursion(totalBalls) / recursion(selectBalls) / recursion((totalBalls - selectBalls)));
	}

	public static long recursion(int i) {
		if (i < 0) // <0退出
			return -1;
		else if (i == 0) // 0的阶乘=1
			return 1L;
		else
			// 0继续递归
			return 1L * i * recursion(i - 1);
	}

	public static ArrayList<FootBallBetItem> changStatusToFootBet() {
		ArrayList<FootBallBetItem> bets = new ArrayList<FootBallBetItem>();
		ArrayList<ArrayList<Integer>> selectInfo = new ArrayList<ArrayList<Integer>>();
		ArrayList<Match> selectMatchs = new ArrayList<Match>();
		for (ArrayList<ArrayList<Integer>> ins : Constant.MATCH_STATUS) {
			for (ArrayList<Integer> in : ins) {
				if (in.get(64) == 1) {
					selectInfo.add(in);
				}
			}
		}
		for (DayMatchs dsy : Constant.MIX_DAYMATCHESS) {
			for (Match m : dsy.getMatchs()) {
				selectMatchs.add(m);
			}
		}
		FootBallBetItem bet;
		for (int i = 0; i < selectInfo.size(); i++) {
			bet = new FootBallBetItem();
			Log.e("pkx", "randomball-id:"+selectMatchs.get(i).getId());
			bet.setId(Integer.valueOf(selectMatchs.get(i).getId()));
			bet.setHandic(selectMatchs.get(i).getLetBall());
			ArrayList<Integer> selects = new ArrayList<Integer>();
			for (int j = 0; j < 54; j++) {
				if (selectInfo.get(i).get(j) == 1) {
					selects.add(j);
				}
				;
			}
			bet.setSelects(selects);
			bets.add(bet);
		}
		return bets;
	}
	public static ArrayList<FootBallBetItem> changStatusToBasketBet() {
		ArrayList<FootBallBetItem> bets = new ArrayList<FootBallBetItem>();
		ArrayList<ArrayList<Integer>> selectInfo = new ArrayList<ArrayList<Integer>>();
		ArrayList<MatchBasketBallWFdto> selectMatchs = new ArrayList<MatchBasketBallWFdto>();
		for (ArrayList<ArrayList<Integer>> ins : Constant.BASKET_MATCH_STATUS) {
			for (ArrayList<Integer> in : ins) {
				if (in.get(0) == 1||in.get(1) == 1) {
					selectInfo.add(in);
				}
			}
		}
		for (BasDayMatchs dsy : Constant.BASEKET_MIX_DAYMATCHESS) {
			for (MatchBasketBallWFdto m : dsy.getMatchs()) {
				selectMatchs.add(m);
			}
		}
		FootBallBetItem bet;
		for (int i = 0; i < selectInfo.size(); i++) {
			bet = new FootBallBetItem();
			Log.e("pkx", "randomball-id:"+selectMatchs.get(i).getMatch_id());
			bet.setId(Integer.valueOf(selectMatchs.get(i).getMatch_id()));
			bet.setHandic(selectMatchs.get(i).getLetBall());
			ArrayList<Integer> selects = new ArrayList<Integer>();
			for (int j = 0; j < 2; j++) {
				if (selectInfo.get(i).get(j) == 1) {
					selects.add(j);
				}
				;
			}
			bet.setSelects(selects);
			bets.add(bet);
		}
		return bets;
	}

	public static int getBetNumberFromBets(ArrayList<FootBallBetItem> bets,
			int chuan, int dan) {
		int betall = 0;
		if (dan == 0) {
			List<int[]> setList = MySetUtil.set(chuan, bets.size());
			for (int[] a : setList) {
				int betdouble = 1;
				for (int i = 0; i < chuan; i++) {
					betdouble *= bets.get(a[i] - 1).getSelects().size();
				}
				betall += betdouble;
			}

		} else {
			ArrayList<Integer> danFlags = new ArrayList<Integer>();
			ArrayList<FootBallBetItem> copyBets1 = new ArrayList<FootBallBetItem>();
			ArrayList<FootBallBetItem> restBets = new ArrayList<FootBallBetItem>();
			ArrayList<Integer> removeIndex = new ArrayList<Integer>();
			for (FootBallBetItem bet : bets) {
				FootBallBetItem newbet = new FootBallBetItem();
				newbet.setHandic(bet.getHandic());
				newbet.setId(bet.getId());
				newbet.setPlayType(bet.getPlayType());
				newbet.setSelects(bet.getSelects());
				copyBets1.add(newbet);
			}
			for (ArrayList<ArrayList<Integer>> ins : Constant.MATCH_STATUS) {
				for (ArrayList<Integer> in : ins) {
					if (in.get(64) == 1) {
						if (in.get(63) == 1) {
							danFlags.add(1);
						} else {
							danFlags.add(0);
						}
					}
				}
			}
			int doubleBets = 1;
			for (int i = 0; i < danFlags.size(); i++) {
				if (danFlags.get(i) == 1) {
					doubleBets *= copyBets1.get(i).getSelects().size();
					removeIndex.add(i);
				}
			}
			for (int i = 0; i < copyBets1.size(); i++) {
				boolean isremove = false;
				for (int ind : removeIndex) {
					if (ind == i) {
						isremove = true;
					}
				}
				if (!isremove) {
					FootBallBetItem newbet = new FootBallBetItem();
					newbet.setHandic(copyBets1.get(i).getHandic());
					newbet.setId(copyBets1.get(i).getId());
					newbet.setPlayType(copyBets1.get(i).getPlayType());
					newbet.setSelects(copyBets1.get(i).getSelects());
					restBets.add(newbet);
				}
			}
			return doubleBets * getBetNumberFromBets(restBets, chuan - dan, 0);

		}

		return betall;
	}
	public static int getBasketBetNumberFromBets(ArrayList<FootBallBetItem> bets,
			int chuan, int dan) {
		int betall = 0;
		if (dan == 0) {
			List<int[]> setList = MySetUtil.set(chuan, bets.size());
			for (int[] a : setList) {
				int betdouble = 1;
				for (int i = 0; i < chuan; i++) {
					betdouble *= bets.get(a[i] - 1).getSelects().size();
				}
				betall += betdouble;
			}
			
		} else {
			ArrayList<Integer> danFlags = new ArrayList<Integer>();
			ArrayList<FootBallBetItem> copyBets1 = new ArrayList<FootBallBetItem>();
			ArrayList<FootBallBetItem> restBets = new ArrayList<FootBallBetItem>();
			ArrayList<Integer> removeIndex = new ArrayList<Integer>();
			for (FootBallBetItem bet : bets) {
				FootBallBetItem newbet = new FootBallBetItem();
				newbet.setHandic(bet.getHandic());
				newbet.setId(bet.getId());
				newbet.setPlayType(bet.getPlayType());
				newbet.setSelects(bet.getSelects());
				copyBets1.add(newbet);
			}
			for (ArrayList<ArrayList<Integer>> ins : Constant.BASKET_MATCH_STATUS) {
				for (ArrayList<Integer> in : ins) {
					if (in.get(0) == 1||in.get(1) == 1) {
						if (in.get(3) == 1) {
							danFlags.add(1);
						} else {
							danFlags.add(0);
						}
					}
				}
			}
			int doubleBets = 1;
			for (int i = 0; i < danFlags.size(); i++) {
				if (danFlags.get(i) == 1) {
					doubleBets *= copyBets1.get(i).getSelects().size();
					removeIndex.add(i);
				}
			}
			for (int i = 0; i < copyBets1.size(); i++) {
				boolean isremove = false;
				for (int ind : removeIndex) {
					if (ind == i) {
						isremove = true;
					}
				}
				if (!isremove) {
					FootBallBetItem newbet = new FootBallBetItem();
					newbet.setHandic(copyBets1.get(i).getHandic());
					newbet.setId(copyBets1.get(i).getId());
					newbet.setPlayType(copyBets1.get(i).getPlayType());
					newbet.setSelects(copyBets1.get(i).getSelects());
					restBets.add(newbet);
				}
			}
			return doubleBets * getBasketBetNumberFromBets(restBets, chuan - dan, 0);
			
		}
		
		return betall;
	}

	public static int getBetNumberFromBets1(ArrayList<FootBallBetItem> bets,
			int chuan) {
		int seles = 0;
		for (FootBallBetItem b : bets) {
			seles += b.getSelects().size();
		}
		int betall = getBallBets(chuan, seles);
		switch (chuan) {
		case 2:
			for (FootBallBetItem bet : bets) {
				for (int i : bet.getSelects()) {
					if (i > 1)
						betall -= getBallBets(2, i);
				}
			}
			break;
		case 3:
			for (FootBallBetItem bet : bets) {
				for (int i : bet.getSelects()) {
					if (i == 2) {
						betall -= seles - 2;
					} else if (i > 2) {
						betall -= getBallBets(3, i);

					}

				}
			}
			break;

		}

		return betall;
	}
}

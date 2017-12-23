package com.pkx.lottery.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.pkx.lottery.R;
import com.pkx.lottery.bean.FastBet;
import com.pkx.lottery.bean.FastNewBet;

import java.util.ArrayList;

public class MyUtils {
	public static FastNewBet changeFastBetsToFastNewBet(ArrayList<FastBet> bets){
		ArrayList<Integer> ints=new ArrayList<Integer>();
		for(FastBet b:bets){
			ints.add(b.getBetNumber());
		}
		ints=RandomBallsUtils.sort(ints);
		FastNewBet newbet=new FastNewBet();
		newbet.setCurrentPage(bets.get(0).getCurrentPageId());
		newbet.setBalls(ints);
		return newbet;
	}
	public void showDialog(Context cnts,int doubles,int follows) {
//		// ChinesePickerDialog dialog = new ChinesePickerDialog(this);
//		ChinesePickerDialog dialog = new ChinesePickerDialog(cnts, doubles, follows);
//		dialog.setOnChooseSetListener(new OnChooseSetListener() {
//			public void OnChooseSet(AlertDialog dialog, String week, String end) {
//				int[] nums=new int[2];
//				nums[0]=Integer.valueOf(week);
//				nums[1]=Integer.valueOf(end);
//			}
//			
//		});
//		dialog.show();
	}
	public void showExitAlert(Context ctx){ 
        final AlertDialog alert = new AlertDialog.Builder(ctx).create(); 
        alert.show(); 

//        alert.getWindow().setLayout(DecorationApplication.cpc.changeImageX(657), DecorationApplication.cpc.changeImageX(311)); 
      
        alert.getWindow().setContentView(R.layout.exit_dialog); 
        
        TextView title = (TextView)alert.findViewById(R.id.title);
        title.setText("离开页面后将清除原页面上的投注信息，确定离开？");
        View okButton = alert.findViewById(R.id.ok);
        okButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				DecorationApplication d = (DecorationApplication)SettingActivity.this.getApplication();
//				Toast.makeText(ctx, "清理完毕", Toast.LENGTH_SHORT).show();
//		        ctx.finish();
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

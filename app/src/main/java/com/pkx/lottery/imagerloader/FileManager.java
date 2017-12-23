package com.pkx.lottery.imagerloader;


public class FileManager {

	public static String getSaveFilePath() {
		if (CommonUtil.hasSDCard()) {
			return CommonUtil.getRootFilePath() + "hc88/files/";
		} else {
			return CommonUtil.getRootFilePath() + "hc88/files";
		}
	}
}

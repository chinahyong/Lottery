package com.pkx.lottery.dto.lott;

import com.pkx.lottery.utils.RandomBallsUtils;

import java.io.Serializable;
import java.util.ArrayList;

public class HappyTenLott implements Serializable {
	private static final long serialVersionUID = 1L;
	private int playType;
	private int betType;
	private ArrayList<Integer> redBalls;
	private ArrayList<Integer> redBalls1;
	private ArrayList<Integer> redBalls2;
	private ArrayList<Integer> danBalls;
	private ArrayList<Integer> tuoBalls;

	private String getPlayTypeString() {
		String str = "";
		switch (playType) {
		case 0:
			str = "任选1:";
			break;
		case 1:
			str = "任选2:";
			break;
		case 2:
			str = "任选3:";
			break;
		case 3:
			str = "任选4:";
			break;
		case 4:
			str = "任选5:";
			break;
		case 5:
			str = "选2连组:";
			break;
		case 6:
			str = "选3-胆拖:";
			break;
		case 7:
			str = "选2连直:";
			break;
		case 8:
			str = "选3前组:";
			break;
		case 9:
			str = "选3前直:";
			break;
		}
		return str;
	}

	public void setPlayType(int playType) {
		if (playType < 5) {
			this.betType = 0;
		} else {
			this.betType = 1;
		}
		this.playType = playType;
	}

	public ArrayList<Integer> getRedBalls1() {
		return redBalls1;
	}

	public void setRedBalls1(ArrayList<Integer> redBalls1) {
		this.redBalls1 = redBalls1;
	}

	public ArrayList<Integer> getRedBalls2() {
		return redBalls2;
	}

	public void setRedBalls2(ArrayList<Integer> redBalls2) {
		this.redBalls2 = redBalls2;
	}

	public void setRedBalls(ArrayList<Integer> redBalls) {
		this.redBalls = redBalls;
	}

	public void setDanBalls(ArrayList<Integer> danBalls) {
		this.danBalls = danBalls;
	}

	public void setTuoBalls(ArrayList<Integer> tuoBalls) {
		this.tuoBalls = tuoBalls;
	}

	public int getPlayType() {
		return playType;
	}

	public int getBetType() {
		return betType;
	}

	public ArrayList<Integer> getRedBalls() {
		return redBalls;
	}

	public ArrayList<Integer> getDanBalls() {
		return danBalls;
	}

	public ArrayList<Integer> getTuoBalls() {
		return tuoBalls;
	}

	public int getPrice() {
		int result = 0;
		switch (playType) {
		case 0:
			if (redBalls.size() > 0) {
				result = redBalls.size() * 2;
			} else {
				result = 0;
			}
			break;
		case 1:
			if (redBalls.size() > 1) {
				result = RandomBallsUtils.getBallBets(2, redBalls.size()) * 2;
			} else {
				result = 0;
			}
			break;
		case 2:
			if (redBalls.size() > 2) {
				result = RandomBallsUtils.getBallBets(3, redBalls.size()) * 2;
			} else {
				result = 0;
			}
			break;
		case 3:
			if (redBalls.size() > 3) {
				result = RandomBallsUtils.getBallBets(4, redBalls.size()) * 2;
			} else {
				result = 0;
			}
			break;
		case 4:
			if (redBalls.size() > 4) {
				result = RandomBallsUtils.getBallBets(5, redBalls.size()) * 2;
			} else {
				result = 0;
			}
			break;
		case 5:
			if (redBalls.size() > 1) {
				result = RandomBallsUtils.getBallBets(2, redBalls.size()) * 2;
			} else {
				result = 0;
			}
			break;
		case 6:
			if (danBalls.size() > 0 && tuoBalls.size() > 0) {
				result = RandomBallsUtils.getBallBets((3 - danBalls.size()),
						tuoBalls.size()) * 2;
			} else {
				result = 0;
			}
			break;
		case 7:
			if (redBalls.size() > 0 && redBalls1.size() > 0) {
				result = redBalls.size() * redBalls1.size() * 2;
			} else {
				result = 0;
			}
			break;
		case 8:
			if (redBalls.size() > 2) {
				result = RandomBallsUtils.getBallBets(3, redBalls.size()) * 2;
			} else {
				result = 0;
			}
			break;
		case 9:
			if (redBalls.size() > 0 && redBalls1.size() > 0
					&& redBalls2.size() > 0) {
				result = redBalls.size() * redBalls1.size() * redBalls2.size()
						* 2;
			} else {
				result = 0;
			}
			break;
		}
		return result;
	}

	public CharSequence getBetInfo(int betDouble) {
		String str = "";
		String balls = "";
		switch (playType) {
		case 0:
			if (redBalls != null && redBalls.size() == 1) {// 单式
				for (int i : redBalls) {
					if (i > 9) {
						balls += String.valueOf(i) + ",";
					} else {
						balls += "0" + String.valueOf(i) + ",";
					}
				}
				balls = balls.substring(0, balls.length() - 1);
				str = "0@@0@@2@@1@@" + String.valueOf(betDouble) + "@@" + balls;
			} else {
				for (int i : redBalls) {
					if (i > 9) {
						balls += String.valueOf(i) + ",";
					} else {
						balls += "0" + String.valueOf(i) + ",";
					}
				}
				balls = balls.substring(0, balls.length() - 1);
				str = "1@@0@@" + String.valueOf(redBalls.size() * 2) + "@@"
						+ String.valueOf(redBalls.size()) + "@@"
						+ String.valueOf(betDouble) + "@@" + balls;
			}
			break;
		case 1:
			if (redBalls != null && redBalls.size() == 2) {// 单式
				for (int i : redBalls) {
					if (i > 9) {
						balls += String.valueOf(i) + ",";
					} else {
						balls += "0" + String.valueOf(i) + ",";
					}
				}
				balls = balls.substring(0, balls.length() - 1);
				str = "0@@1@@2@@1@@" + String.valueOf(betDouble) + "@@" + balls;
			} else {
				for (int i : redBalls) {
					if (i > 9) {
						balls += String.valueOf(i) + ",";
					} else {
						balls += "0" + String.valueOf(i) + ",";
					}
				}
				balls = balls.substring(0, balls.length() - 1);
				str = "1@@1@@"
						+ String.valueOf(RandomBallsUtils.getBallBets(2,
								redBalls.size()) * 2)
						+ "@@"
						+ String.valueOf(RandomBallsUtils.getBallBets(2,
								redBalls.size())) + "@@"
						+ String.valueOf(betDouble) + "@@" + balls;
			}

			break;
		case 2:
			if (redBalls != null && redBalls.size() == 3) {// 单式
				for (int i : redBalls) {
					if (i > 9) {
						balls += String.valueOf(i) + ",";
					} else {
						balls += "0" + String.valueOf(i) + ",";
					}
				}
				balls = balls.substring(0, balls.length() - 1);
				str = "0@@2@@2@@1@@" + String.valueOf(betDouble) + "@@" + balls;
			} else {
				for (int i : redBalls) {
					if (i > 9) {
						balls += String.valueOf(i) + ",";
					} else {
						balls += "0" + String.valueOf(i) + ",";
					}
				}
				balls = balls.substring(0, balls.length() - 1);
				str = "1@@2@@"
						+ String.valueOf(RandomBallsUtils.getBallBets(3,
								redBalls.size()) * 2)
						+ "@@"
						+ String.valueOf(RandomBallsUtils.getBallBets(3,
								redBalls.size())) + "@@"
						+ String.valueOf(betDouble) + "@@" + balls;
			}
			break;
		case 3:

			if (redBalls != null && redBalls.size() == 4) {// 单式
				for (int i : redBalls) {
					if (i > 9) {
						balls += String.valueOf(i) + ",";
					} else {
						balls += "0" + String.valueOf(i) + ",";
					}
				}
				balls = balls.substring(0, balls.length() - 1);
				str = "0@@3@@2@@1@@" + String.valueOf(betDouble) + "@@" + balls;
			} else {
				for (int i : redBalls) {
					if (i > 9) {
						balls += String.valueOf(i) + ",";
					} else {
						balls += "0" + String.valueOf(i) + ",";
					}
				}
				balls = balls.substring(0, balls.length() - 1);
				str = "1@@3@@"
						+ String.valueOf(RandomBallsUtils.getBallBets(4,
								redBalls.size()) * 2)
						+ "@@"
						+ String.valueOf(RandomBallsUtils.getBallBets(4,
								redBalls.size())) + "@@"
						+ String.valueOf(betDouble) + "@@" + balls;
			}
			break;
		case 4:

			if (redBalls != null && redBalls.size() == 5) {// 单式
				for (int i : redBalls) {
					if (i > 9) {
						balls += String.valueOf(i) + ",";
					} else {
						balls += "0" + String.valueOf(i) + ",";
					}
				}
				balls = balls.substring(0, balls.length() - 1);
				str = "0@@4@@2@@1@@" + String.valueOf(betDouble) + "@@" + balls;
			} else {
				for (int i : redBalls) {
					if (i > 9) {
						balls += String.valueOf(i) + ",";
					} else {
						balls += "0" + String.valueOf(i) + ",";
					}
				}
				balls = balls.substring(0, balls.length() - 1);
				str = "1@@4@@"
						+ String.valueOf(RandomBallsUtils.getBallBets(5,
								redBalls.size()) * 2)
						+ "@@"
						+ String.valueOf(RandomBallsUtils.getBallBets(5,
								redBalls.size())) + "@@"
						+ String.valueOf(betDouble) + "@@" + balls;
			}
			break;
		case 5:
			if (redBalls != null && redBalls.size() == 2) {// 单式
				for (int i : redBalls) {
					if (i > 9) {
						balls += String.valueOf(i) + ",";
					} else {
						balls += "0" + String.valueOf(i) + ",";
					}
				}
				balls = balls.substring(0, balls.length() - 1);
				str = "0@@5@@2@@1@@" + String.valueOf(betDouble) + "@@" + balls;
			} else {
				for (int i : redBalls) {
					if (i > 9) {
						balls += String.valueOf(i) + ",";
					} else {
						balls += "0" + String.valueOf(i) + ",";
					}
				}
				balls = balls.substring(0, balls.length() - 1);
				str = "1@@5@@"
						+ String.valueOf(RandomBallsUtils.getBallBets(2,
								redBalls.size()) * 2)
						+ "@@"
						+ String.valueOf(RandomBallsUtils.getBallBets(2,
								redBalls.size())) + "@@"
						+ String.valueOf(betDouble) + "@@" + balls;
			}
			break;
		case 6:// 选三胆拖
			for (int dan : danBalls) {
				if (dan > 9) {
					balls += String.valueOf(dan) + ",";
				} else {
					balls += "0" + String.valueOf(dan) + ",";
				}
			}
			balls = balls.substring(0, balls.length() - 1);
			balls += "|";
			for (int dan : tuoBalls) {
				if (dan > 9) {
					balls += String.valueOf(dan) + ",";
				} else {
					balls += "0" + String.valueOf(dan) + ",";
				}
			}
			balls = balls.substring(0, balls.length() - 1);
			str = "2@@2@@"
					+ String.valueOf(RandomBallsUtils.getBallBets(
							3 - danBalls.size(), tuoBalls.size()) * 2)
					+ "@@"
					+ String.valueOf(RandomBallsUtils.getBallBets(
							3 - danBalls.size(), tuoBalls.size())) + "@@"
					+ String.valueOf(betDouble) + "@@" + balls;

			break;
		case 7:// 选2连直
			if (redBalls.size() == 1 && redBalls1.size() == 1) {// 单式
				str = "0@@6@@2@@1@@" + String.valueOf(betDouble) + "@@"
						+ String.valueOf(redBalls.get(0)) + "|"
						+ String.valueOf(redBalls1.get(0));
			} else {
				for (int dan : redBalls) {
					if (dan > 9) {
						balls += String.valueOf(dan) + ",";
					} else {
						balls += "0" + String.valueOf(dan) + ",";
					}
				}
				balls = balls.substring(0, balls.length() - 1);
				balls += "|";
				for (int dan : redBalls1) {
					if (dan > 9) {
						balls += String.valueOf(dan) + ",";
					} else {
						balls += "0" + String.valueOf(dan) + ",";
					}
				}
				balls = balls.substring(0, balls.length() - 1);
				str = "1@@6@@"
						+ String.valueOf(redBalls.size() * redBalls1.size() * 2)
						+ "@@"
						+ String.valueOf(redBalls.size() * redBalls1.size())
						+ "@@" + String.valueOf(betDouble) + "@@" + balls;
			}

			break;
		case 8:
			if (redBalls != null && redBalls.size() == 3) {// 单式
				for (int i : redBalls) {
					if (i > 9) {
						balls += String.valueOf(i) + ",";
					} else {
						balls += "0" + String.valueOf(i) + ",";
					}
				}
				balls = balls.substring(0, balls.length() - 1);
				str = "0@@8@@2@@1@@" + String.valueOf(betDouble) + "@@" + balls;
			} else {
				for (int i : redBalls) {
					if (i > 9) {
						balls += String.valueOf(i) + ",";
					} else {
						balls += "0" + String.valueOf(i) + ",";
					}
				}
				balls = balls.substring(0, balls.length() - 1);
				str = "1@@8@@"
						+ String.valueOf(RandomBallsUtils.getBallBets(3,
								redBalls.size()) * 2)
						+ "@@"
						+ String.valueOf(RandomBallsUtils.getBallBets(3,
								redBalls.size())) + "@@"
						+ String.valueOf(betDouble) + "@@" + balls;
			}
			break;
		case 9:
			if (redBalls.size() == 1 && redBalls1.size() == 1
					&& redBalls2.size() == 1) {// 单式
				str = "0@@7@@2@@1@@" + String.valueOf(betDouble) + "@@"
						+ String.valueOf(redBalls.get(0)) + "|"
						+ String.valueOf(redBalls1.get(0)) + "|"
						+ String.valueOf(redBalls2.get(0));
			} else {
				for (int dan : redBalls) {
					if (dan > 9) {
						balls += String.valueOf(dan) + ",";
					} else {
						balls += "0" + String.valueOf(dan) + ",";
					}
				}
				balls = balls.substring(0, balls.length() - 1);
				balls += "|";
				for (int dan : redBalls1) {
					if (dan > 9) {
						balls += String.valueOf(dan) + ",";
					} else {
						balls += "0" + String.valueOf(dan) + ",";
					}
				}
				balls = balls.substring(0, balls.length() - 1);
				balls += "|";
				for (int dan : redBalls2) {
					if (dan > 9) {
						balls += String.valueOf(dan) + ",";
					} else {
						balls += "0" + String.valueOf(dan) + ",";
					}
				}
				balls = balls.substring(0, balls.length() - 1);
				str = "1@@7@@"
						+ String.valueOf(redBalls.size() * redBalls1.size()
								* redBalls2.size() * 2)
						+ "@@"
						+ String.valueOf(redBalls.size() * redBalls1.size()
								* redBalls2.size()) + "@@"
						+ String.valueOf(betDouble) + "@@" + balls;
			}
			break;

		}
		return str;
	}

	public CharSequence getBetStr() {
		// if(playType){
		//
		// }
		if (betType == 0) {
			String betStr = "";
			redBalls = RandomBallsUtils.sort(redBalls);
			for (int i : redBalls) {
				if (i > 9) {
					betStr += i + " ";
				} else {
					betStr += "0" + i + " ";
				}
			}
			betStr.substring(0, betStr.length() - 1);
			if (betStr.length() > 29) {
				return betStr.substring(0, 29) + "..";
			}
			return getPlayTypeString() + betStr;
		} else {
			if (playType == 6) {

				String betStr = "";
				danBalls = RandomBallsUtils.sort(danBalls);
				for (int i : danBalls) {
					if (i > 9) {
						betStr += i + " ";
					} else {
						betStr += "0" + i + " ";
					}
				}
				betStr = "(" + betStr.substring(0, betStr.length() - 1) + ")";
				tuoBalls = RandomBallsUtils.sort(tuoBalls);
				for (int i : tuoBalls) {
					if (i > 9) {
						betStr += i + " ";
					} else {
						betStr += "0" + i + " ";
					}
				}
				betStr = betStr.substring(0, betStr.length() - 1);
				if (betStr.length() > 29) {
					return betStr.substring(0, 29) + "..";
				}
				return getPlayTypeString() + betStr;
			} else {
				String betStr = "";
				String typeStr = "";
				switch (playType) {
				case 8:
				case 5:
					if (playType == 5) {
						typeStr = "选2连组:";
					} else {
						typeStr = "选3前组:";
					}
					redBalls = RandomBallsUtils.sort(redBalls);
					for (int i : redBalls) {
						if (i > 9) {
							betStr += i + " ";
						} else {
							betStr += "0" + i + " ";
						}
					}
					betStr.substring(0, betStr.length() - 1);
					if (betStr.length() > 29) {
						betStr = betStr.substring(0, 29) + "..";
					}
					break;
				case 7:
					typeStr = "选2连直:";
					String first = "";
					String second = "";
					for (int i : redBalls) {
						if (i > 9) {
							first += i + " ";
						} else {
							first += "0" + i + " ";
						}
					}
					for (int i : redBalls1) {
						if (i > 9) {
							second += i + " ";
						} else {
							second += "0" + i + " ";
						}
					}
					betStr = first + ";" + second;

					break;
				case 9:
					typeStr = "选3前直:";
					String first1 = "";
					String second1 = "";
					String thrid = "";
					for (int i : redBalls) {
						if (i > 9) {
							first1 += i + " ";
						} else {
							first1 += "0" + i + " ";
						}
					}
					for (int i : redBalls1) {
						if (i > 9) {
							second1 += i + " ";
						} else {
							second1 += "0" + i + " ";
						}
					}
					for (int i : redBalls2) {
						if (i > 9) {
							thrid += i + " ";
						} else {
							thrid += "0" + i + " ";
						}
					}
					betStr = first1 + ";" + second1 + ";" + thrid;
					break;
				}
				return typeStr + betStr;
			}
		}

	}
}

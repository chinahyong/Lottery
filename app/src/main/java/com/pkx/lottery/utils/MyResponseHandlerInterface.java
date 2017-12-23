package com.pkx.lottery.utils;

import org.apache.http.Header;

import com.loopj.android.http.ResponseHandlerInterface;

public interface MyResponseHandlerInterface extends ResponseHandlerInterface {
	public abstract void sendSuccessMessage(int i, Header aheader[],
											byte abyte0[]);

	public abstract void sendFailureMessage(int i, Header aheader[],
											byte abyte0[], Throwable throwable);
}

package com.example.toan.api.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.text.TextUtils;
import com.example.toan.api.base.LoggingInterceptor;
import com.example.toan.interfaces.HttpCallback;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public abstract class BaseOkHttp implements Callback {

	private static OkHttpClient okHttpClient = null;

	public static OkHttpClient getOkHttpClient() {
		if(okHttpClient == null) {
			okHttpClient = new OkHttpClient
					.Builder()
					.readTimeout(30, TimeUnit.SECONDS)
					.writeTimeout(30, TimeUnit.SECONDS)
					.connectTimeout(30, TimeUnit.SECONDS).addInterceptor(new LoggingInterceptor())
					.build();
		}

		return okHttpClient;
	}

	private boolean wantDialogCancelable = true;
	private boolean wantShowDialog = true;
	private Context context;
	private ProgressDialog progress;
	private String title = "";
	private String message = "Message...";
	private HttpCallback httpCallback;

	protected BaseOkHttp() {

	}

	public void start() {
		if(this.context != null) {
			progress = new ProgressDialog(this.context);
			progress.setCancelable(wantDialogCancelable);
			progress.setMessage(message);

			if(null != title && TextUtils.isEmpty(title)) {
				progress.setTitle(title);
			}
		}

		if(progress != null && wantShowDialog) {
			progress.show();
		}

		if(httpCallback != null) {
			httpCallback.onStart();
		}
	}

	@Override
	public void onFailure(Request request, IOException e) {
		dissmisDialog();
		if(httpCallback != null) {
			httpCallback.onFailure(request, e);
		}
	}

	@Override
	public void onResponse(Response response) throws IOException {
		dissmisDialog();

		if(response.isSuccessful()) {
			if(httpCallback != null) {
				String s = response.body().string();
				httpCallback.onSuccess(s);
			}
		}
		else {
			onFailure(null, null);
		}
	}

	private void dissmisDialog() {
		if(progress != null && progress.isShowing()) {
			try {
				if(context instanceof Activity) {
					if(((Activity)context).isFinishing()) {
						return;
					}
				}
				progress.dismiss();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

	public boolean isWantDialogCancelable() {return wantDialogCancelable;}

	public void setWantDialogCancelable(boolean wantDialogCancelable) {
		this.wantDialogCancelable = wantDialogCancelable;
	}

	public boolean isWantShowDialog() { return wantShowDialog; }

	public void setHttpCallback(HttpCallback httpCallback) {
		this.httpCallback = httpCallback;
	}

	public void setWantShowDialog(boolean wantShowDialog) {
		this.wantShowDialog = wantShowDialog;
	}

	public Context getContext() { return context; }

	public void setContext(Context context) {
		this.context = context;
	}

	public ProgressDialog getProgress() { return progress; }

	public void setProgress(ProgressDialog progress) {
		this.progress = progress;
	}

	public String getTitle() { return title; }

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessage() { return message; }

	public void setMessage(String message) {
		this.message = message;
	}

	public static class Builder {
		private BaseOkHttp baseOkHttp = new BaseOkHttp(){};

		/**
		 * co muon show dialog khong?
		 * @param wantShowDialog dieu kien
		 * @return return this
		 */
		public Builder setWantShowDialog(boolean wantShowDialog) {
			baseOkHttp.wantShowDialog = wantShowDialog;
			return this;
		}

		/**
		 * cai dat delegate
		 * @param httpCallback delegate
		 * @return return this
		 */
		public Builder setHttpCallback(HttpCallback httpCallback) {
			this.baseOkHttp.httpCallback = httpCallback;
			return this;
		}

		public Builder setContext(Context ctx) {
			baseOkHttp.context = ctx;
			return Builder.this;
		}

		public Builder setWantDialogCancelable(boolean wantDialogCancelable) {
			baseOkHttp.wantDialogCancelable = wantDialogCancelable;
			return Builder.this;
		}

		/**
		 * set title
		 * @param title title
		 * @return return this
		 */
		public Builder setTitle(String title) {
			baseOkHttp.title = title;
			return Builder.this;
		}

		/**
		 * set message
		 * @param message message
		 * @return return this
		 */
		public Builder setMessage(String message) {
			baseOkHttp.message = message;
			return Builder.this;
		}

		public BaseOkHttp build() {
			baseOkHttp.start();
			return baseOkHttp;
		}
	}
}
package library;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

public abstract class ActivityAsync extends AsyncTask<String, String, String> {

	private ProgressDialog pDialog;
	private Activity activity;
	
	public ActivityAsync(Activity activity) {
		super();
		this.activity = activity;
	}

	

	protected void onPreExecute() {
		super.onPreExecute();

		pDialog = new ProgressDialog(activity);
		pDialog.setMessage("иетп");
		pDialog.setIndeterminate(false);
		pDialog.setCancelable(true);
		pDialog.show();
	}
	
	protected void onPostExecute(String file_url) {
		// dismiss the dialog once done
		pDialog.dismiss();
	}

	public ProgressDialog getpDialog() {
		return pDialog;
	}

	public void setpDialog(ProgressDialog pDialog) {
		this.pDialog = pDialog;
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}
}

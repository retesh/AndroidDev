package codepath.apps.simpletodo;

import android.R.layout;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class EditActivity extends Activity {

	private EditText etEditItem;
	private long rowId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit);
		etEditItem = (EditText) findViewById(R.id.etEditItem);
		String itemText = getIntent().getStringExtra("itemText");
		rowId = getIntent().getLongExtra("rowId", 2);
		etEditItem.setText(itemText);
		etEditItem.setSelection(etEditItem.getText().length());

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit, menu);
		return true;
	}
	
	public void saveItem(View v) {
		Intent data = new Intent();
		data.putExtra("name", etEditItem.getText().toString());
		data.putExtra("rowId", rowId);
		setResult(RESULT_OK, data);
		finish();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}

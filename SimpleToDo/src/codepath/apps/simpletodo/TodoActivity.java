package codepath.apps.simpletodo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import android.R.layout;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemLongClickListener;

public class TodoActivity extends Activity {

	private ArrayList<String> todoItems;
	private ArrayAdapter<String> todoAdapter;
	private ListView lvItems;
	private EditText etNewItem;
	private final int REQUEST_CODE = 20;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo);
		etNewItem = (EditText) findViewById(R.id.etNewItem);
		lvItems = (ListView) findViewById(R.id.lvItems);
		readItems();
		populateArrayItems();
		todoAdapter = new ArrayAdapter<String>(getBaseContext(),
				android.R.layout.simple_list_item_1, todoItems);
		lvItems.setAdapter(todoAdapter);	
		setupListViewListener();
		
	}

	private void setupListViewListener() {
		lvItems.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id) {
				todoItems.remove(pos);
				todoAdapter.notifyDataSetChanged();
				writeItems();
				return true;
			}
		});
		
		lvItems.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> adapter, View item, int pos, long id) {
				Intent i = new Intent(TodoActivity.this, EditActivity.class);
				i.putExtra("itemText", ((TextView) item).getText());
				i.putExtra("rowId", id);
				i.putExtra("mode", 2);
				startActivityForResult(i, REQUEST_CODE); // brings up the second activity
				
			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
			String name = data.getExtras().getString("name");
			long rowId = data.getExtras().getLong("rowId");
			todoItems.set((int) rowId, name);
			todoAdapter.notifyDataSetChanged();
			writeItems();
		}
		
	}
	
	public void onAddedItem(View v) {
		String itemText = etNewItem.getText().toString();
		todoAdapter.add(itemText);
		etNewItem.setText("");
		writeItems();
		
	}
	
	private void readItems() {
		File filesDir = getFilesDir();
		File todoFile = new File(filesDir, "todo.txt");
		try {
			todoItems = new ArrayList<String>(FileUtils.readLines(todoFile));
			
		} catch (IOException e) {
			todoItems = new ArrayList<String>();
		}
		
	}
	
	private void writeItems() {
		File filesDir = getFilesDir();
		File todoFile = new File(filesDir, "todo.txt");
		try {
			FileUtils.writeLines(todoFile, todoItems);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void populateArrayItems() {
		todoItems = new ArrayList<String>();
		todoItems.add("Item 1");
		todoItems.add("Item 2");
		todoItems.add("Item 3");
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.todo, menu);
		return true;
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

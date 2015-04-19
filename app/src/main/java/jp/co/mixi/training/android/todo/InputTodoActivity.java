package jp.co.mixi.training.android.todo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import jp.co.mixi.training.android.todo.entity.TodoEntity;


public class InputTodoActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_todo);
        View submitButton = findViewById(R.id.submit);
        final EditText todoText = (EditText) findViewById(R.id.input_todo);
        Intent received = getIntent();
        final long entityId;
        long paramEntryId = 0;
        if (received != null) {
            String extra = received.getStringExtra("todo");
            if (extra != null) {
                TodoEntity entity = TodoEntity.fromJson(extra);
                todoText.setText(entity.getTitle());
                paramEntryId = entity.getId();
            }
        }
        entityId = paramEntryId;

        submitButton.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                TodoEntity entity = new TodoEntity();
                                                entity.setTitle(todoText.getText().toString());
                                                entity.setId(entityId);
                                                Activity activity = InputTodoActivity.this;
                                                TodoSaveService.startActionSave(activity, entity);
                                                activity.finish();
                                            }
                                        }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

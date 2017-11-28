package com.kkontagion.sugarbaby;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.kkontagion.sugarbaby.adapters.AutocompleteAdapter;
import com.kkontagion.sugarbaby.adapters.FoodAdapter;
import com.kkontagion.sugarbaby.objects.Food;
import com.kkontagion.sugarbaby.objects.FoodType;
import com.kkontagion.sugarbaby.objects.Meal;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MealCreatorActivity extends AppCompatActivity {
    public static final String TAG = "MealCreatorActivity";
    public static final int CREATE_MEAL = 69;

    RecyclerView rv;
    TextView tvTime, tvTotal;
    ImageButton btEdit;
    Button btSubmit;
    AutoCompleteTextView tvComplete;

    FoodAdapter foodAdapter;
    AutocompleteAdapter completeAdapter;

    private double cals = 0, carbs = 0;
    private Calendar time;
    private ArrayList<Food> autoarray;
    private static SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy hh:mmaa");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_creator);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        time = Calendar.getInstance();

        tvTime = findViewById(R.id.tv_time);
        tvTotal = findViewById(R.id.tv_total);
        tvComplete = findViewById(R.id.tv_auto);
        btEdit = findViewById(R.id.bt_edit);
        btSubmit = findViewById(R.id.bt_submit);
        rv = findViewById(R.id.rv);

        foodAdapter = new FoodAdapter(this);
        rv.setAdapter(foodAdapter);
        initAuto();
        completeAdapter = new AutocompleteAdapter(this, R.layout.item_autocomplete, autoarray);
        tvComplete.setAdapter(completeAdapter);
        tvComplete.setThreshold(1);

        tvTime.setText(df.format(time.getTime()));
        tvTotal.setText(getString(R.string.food_meal_totals, cals, carbs));

        setupActions();
    }

    private void initAuto() {
        autoarray = new ArrayList<>();
        autoarray.add(new Food(FoodType.VEGETABLE, "Steamed Carrots with Garlic-Ginger Butter", 69, 10.3));
        autoarray.add(new Food(FoodType.BURGERS, "Black bean burger", 182, 15.6));
        autoarray.add(new Food(FoodType.SWEETS, "Frozen yoghurt", 100, 18.6));
    }

    private void setupActions() {
        tvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO datepicker dialog, followed by time picker dialog.
                // On complete (both selected), update tvTime.setText(result) using the same format as `df` above.
            }
        });

        btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO dialog to confirm carbs and cals and food type
            }
        });

        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO generate meal and send to main activity.
                Meal meal = new Meal(time);
                meal.setFood(foodAdapter.getItems());
                Intent i = new Intent();
                i.putExtra("meal", meal);
                setResult(RESULT_OK, new Intent().putExtra(TAG, meal));
                finish();
            }
        });
    }
}
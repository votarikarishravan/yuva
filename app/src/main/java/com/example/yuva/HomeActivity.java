package com.example.yuva;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import org.json.JSONObject;

public class HomeActivity extends AppCompatActivity implements PaymentResultListener {
    private static final String TAG ="PAYMENT_ERROR" ;
    private DrawerLayout drawerLayout;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Checkout.preload(getApplicationContext());

        auth = FirebaseAuth.getInstance();
        drawerLayout = findViewById(R.id.drawer_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_nav_menu);

        findViewById(R.id.blood_doner_card_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,BloodDonersActivity.class));
            }
        });

        findViewById(R.id.edit_profile_card_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,EditProfileActivity.class));
            }
        });

        findViewById(R.id.donate_money_card_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPayment();
            }
        });

        findViewById(R.id.publish_card_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,PublishActivity.class));
            }
        });

        findViewById(R.id.events_card_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,EventsActivity.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.item1:
                auth.signOut();
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(HomeActivity.this,"payment successful",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(HomeActivity.this,"payment failed",Toast.LENGTH_SHORT).show();
    }

    public void startPayment() {

        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_ZQLlJqhaN2UbVs");
        checkout.setImage(R.drawable.logo);

        final Activity activity = this;

        try {
            JSONObject options = new JSONObject();

            options.put("name", "Yuva");
            options.put("description", "give your hand,we all together make happiest society");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
           // options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
           options.put("amount", "100");//pass amount in currency subunits
            //options.put("prefill.email", "gaurav.kumar@example.com");
            //options.put("prefill.contact","9988776655");
            checkout.open(activity, options);
        } catch(Exception e) {
            Log.e(TAG, "Error in starting Razorpay Checkout", e);
        }
    }
}
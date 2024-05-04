package fpoly.duanmau.ph40749;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import fpoly.duanmau.ph40749.R;

public class WelComeActivity extends AppCompatActivity {
    private EditText editTextAccount;
    private Button buttonLogin;
    private static final String CORRECT_ACCOUNT = "ph40749"; // Replace with the correct account

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wel_come);
//        Thread bamgio=new Thread(){
//            public void run()
//            {
//                try {
//                    sleep(3000);
//                } catch (Exception e) {
//                }
//                finally
//                {
//                    Intent intent1 = new Intent(WelComeActivity.this, MainActivity.class);
//                    startActivity(intent1);
//                }
//            }
//        };
//        bamgio.start();

        editTextAccount = findViewById(R.id.editTextAccount);
        buttonLogin = findViewById(R.id.buttonLogin);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredAccount = editTextAccount.getText().toString().trim();
                if (enteredAccount.equals(CORRECT_ACCOUNT)) {
                    // Correct account, move to main activity
                    Intent intent = new Intent(WelComeActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // Incorrect account, show error message
                    Toast.makeText(WelComeActivity.this, "Vui lòng nhập đúng mã SV", Toast.LENGTH_SHORT).show();
                }
            }
        });





    }
//    //sau khi chuyển sang màn hình chính, kết thúc màn hình chào
//    protected void onPause(){
//        super.onPause();
//        finish();
//    }
}
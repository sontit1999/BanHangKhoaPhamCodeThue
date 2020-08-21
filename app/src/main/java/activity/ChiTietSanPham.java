package activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.doantrasua.R;
import com.squareup.picasso.Picasso;

import model.Giohang;
import model.Sanpham;

public class ChiTietSanPham extends AppCompatActivity {
    Sanpham sanpham;
    ImageView ivSanpham;
    EditText edtSoluong;
    Button btnDatmua;
    TextView txtTensp,txtGiasp,txtMotasp;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san_pham);
        anhxa();
        Intent intent = getIntent();
        if(intent!=null && intent.getSerializableExtra("sanpham")!=null){

            sanpham = (Sanpham) intent.getSerializableExtra("sanpham");
        //    Toast.makeText(this, "Mo ta: " + sanpham.getMotasanpham(), Toast.LENGTH_SHORT).show();

        }
        if(sanpham!=null){

            txtTensp.setText(sanpham.getTensanpham());
            txtGiasp.setText(sanpham.getGiasanpham() + " VND");
            txtMotasp.setText(sanpham.getMotasanpham());
            Picasso.get().load(sanpham.getHinhanhsanpham()).into(ivSanpham);
        }
        event();
    }

    private void event() {
        btnDatmua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.addGioHang(new Giohang(sanpham.getID(), sanpham.getTensanpham(), sanpham.getGiasanpham(),sanpham.getHinhanhsanpham(), Integer.parseInt(edtSoluong.getText().toString())));
                Toast.makeText(ChiTietSanPham.this, "Thêm vào giỏ hàng@!!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void anhxa() {
        ivSanpham = (ImageView) findViewById(R.id.imageviewchitietsanpham);
        txtTensp = (TextView) findViewById(R.id.textviewtenchitietsanpham);
        txtGiasp = (TextView) findViewById(R.id.textviewgiachitietsanpham);
        txtMotasp = (TextView) findViewById(R.id.textviewmotachitietsanpham);;
        btnDatmua = (Button ) findViewById(R.id.buttondatmua);
        edtSoluong = (EditText) findViewById(R.id.edtSoluong);
    }
}

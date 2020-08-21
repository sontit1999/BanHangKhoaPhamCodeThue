package activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.doantrasua.R;

import java.text.DecimalFormat;

import adapter.GioHangAdapter;
import ultil.CheckConnection;

public class Giohang extends AppCompatActivity {

    ListView lvgiohang;
    TextView txtthongbao;
    static TextView txttongtien;
    Button btnthanhtoan, btntieptucmua;
    Toolbar toolbargiohang;
    GioHangAdapter gioHangAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giohang);

        anhxa();
       // ActionToolbar();
        CheckData();  //phai con hang thi moi do du lieu ra neu khong se hien thong bao
        EventUltil();
        Tinhtongtien();
        CatchOnItemListView();
        EventButton();
    }

    public static void Tinhtongtien() {
        txttongtien.setText(MainActivity.getTongTienGiohang() + " VND");
    }

    private void EventButton() {
        btntieptucmua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });
        btnthanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.manggiohang.size() > 0){  //phai co hang moi thanh toan duoc
                    Intent intent = new Intent(getApplicationContext(),ThongTinKhachHang.class);
                    startActivity(intent);
                }else {
                    CheckConnection.ShowToast_Short(getApplicationContext(),"Không có sản phẩm để thanh toán");
                }
            }
        });
    }

    private void CatchOnItemListView() {
        lvgiohang.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Giohang.this);
                builder.setTitle("Xác nhận xóa sản phẩm");
                builder.setMessage("Bạn có chắc muốn xóa sản phẩm này?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(MainActivity.manggiohang.size() <= 0){
                            txtthongbao.setVisibility(View.VISIBLE);
                        }else {
                            MainActivity.manggiohang.remove(position);
                            gioHangAdapter.notifyDataSetChanged();
                            EventUltil();
                            if(MainActivity.manggiohang.size() <= 0){
                                txtthongbao.setVisibility(View.VISIBLE);
                            }else {
                                txtthongbao.setVisibility(View.INVISIBLE);
                                gioHangAdapter.notifyDataSetChanged();
                                EventUltil();
                            }
                        }
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        gioHangAdapter.notifyDataSetChanged();
                        EventUltil();
                    }
                });
                builder.show();
                return true;
            }
        });
    }

    public static void EventUltil() {
        long tongtien = 0;
        for (int i = 0; i < MainActivity.manggiohang.size(); i++) {
            tongtien += MainActivity.manggiohang.get(i).getGiasp();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txttongtien.setText(decimalFormat.format(tongtien) + " vnd");


    }

    private void CheckData() {
        if (MainActivity.manggiohang.size() <= 0) {
            gioHangAdapter.notifyDataSetChanged();
            txtthongbao.setVisibility(View.VISIBLE);
            lvgiohang.setVisibility(View.INVISIBLE);
        } else {
            gioHangAdapter.notifyDataSetChanged();
            txtthongbao.setVisibility(View.INVISIBLE);
            lvgiohang.setVisibility(View.VISIBLE);
        }
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbargiohang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbargiohang.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void anhxa() {
        lvgiohang = findViewById(R.id.listviewgiohang);
        txtthongbao = findViewById(R.id.textviewthongbao);
        txttongtien = findViewById(R.id.textviewtongtien);
        btnthanhtoan = findViewById(R.id.buttonthanhtoangiohang);
        btntieptucmua = findViewById(R.id.buttontieptucmuahang);
        toolbargiohang = findViewById(R.id.toolbargiohang);
        gioHangAdapter = new GioHangAdapter(Giohang.this, MainActivity.manggiohang);
        lvgiohang.setAdapter(gioHangAdapter);
    }
}

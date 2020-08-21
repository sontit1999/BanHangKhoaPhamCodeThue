package activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.doantrasua.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import adapter.SanphamAdapter;
import model.Sanpham;
import ultil.Server;

public class DSSPActivity extends AppCompatActivity {
    String link = Server.DuongDangetTrasua ;

    ArrayList<Sanpham> mangsanpham;
    SanphamAdapter sanphamAdapter;
    RecyclerView rvSanPham ;
    TextView txtLoaisp;
    int idloaisp = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d_s_s_p);
        Intent intent = getIntent();
        if(intent!=null){
            idloaisp = intent.getIntExtra("id",1);
        }
        if(idloaisp ==1){
            link = Server.DuongDangetTrasua;

        }else if(idloaisp == 2){
            link = Server.DuongDangetCafe;
        }
        anhxa();
        if(idloaisp ==1){
            txtLoaisp.setText("Trà sữa");

        }else if(idloaisp == 2){
            txtLoaisp.setText("Cà Phê");
        }
        GetDuLieuSPMoiNhat();
    }

    private void anhxa() {
        mangsanpham = new ArrayList<>();
        sanphamAdapter = new SanphamAdapter(this,mangsanpham);

        txtLoaisp = (TextView) findViewById(R.id.tvLoaisp);
        rvSanPham = (RecyclerView) findViewById(R.id.rvSanPhamx);
        rvSanPham.setHasFixedSize(true);
        rvSanPham.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        rvSanPham.setAdapter(sanphamAdapter);


    }

    private void GetDuLieuSPMoiNhat() {

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(link,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (response != null) {
                            int ID = 0;
                            String Tensanpham = "";
                            Integer Giasanpham = 0;
                            String Hinhanhsanpham = "";
                            String Motasanpham = "";
                            int IDsanpham = 0;
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject jsonObject = response.getJSONObject(i);
                                    ID = jsonObject.getInt("id");
                                    Tensanpham = jsonObject.getString("tensp");
                                    Giasanpham = jsonObject.getInt("giasp");
                                    Hinhanhsanpham = jsonObject.getString("hinhanhsp");
                                    Motasanpham = jsonObject.getString("motasp");
                                    IDsanpham = jsonObject.getInt("idsanpham");
                                    mangsanpham.add(new Sanpham(ID, Tensanpham, Giasanpham, Hinhanhsanpham, Motasanpham, IDsanpham));

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            sanphamAdapter.notifyDataSetChanged();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("sondz",error.getMessage().toString());
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }
}
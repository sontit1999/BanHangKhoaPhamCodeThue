package activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.doantrasua.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import adapter.CafeAdapter;
import adapter.SanphamAdapter;
import model.Sanpham;
import ultil.CheckConnection;
import ultil.Server;

public class CafeActivity extends AppCompatActivity {
    Toolbar toolbarcafe;
    ListView lvcafe;
    CafeAdapter cafeAdapter;
    ArrayList<Sanpham> mangcafe;
    int idcafe = 0;
    int page = 1;
    RecyclerView rvSanPham;
    View footerView;
    boolean isLoading = false;
    mHandler mHandler;
    boolean limitdata = false;
    private int Idspcafe;
    private String Tencafe;
    private int Giacafe;
    private String Hinhanhcafe;
    private String Motacafe;
    SanphamAdapter spAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cafe);


        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
            anhxa();
            GetIdloaisanpham();
          //  ActionToolbar();
            GetData(page);
          //  LoadMoreData();
        }else {
            CheckConnection.ShowToast_Short(getApplicationContext(),"Kiểm tra lại kết nối");
            finish();
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menugiohang:
                Intent intent = new Intent(getApplicationContext(), activity.Giohang.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void anhxa() {
        rvSanPham = (RecyclerView)  findViewById(R.id.rvSanpham);
        toolbarcafe = findViewById(R.id.toolbarcafe);
        lvcafe = findViewById(R.id.listviewcafe);
        mangcafe = new ArrayList<>();
        cafeAdapter = new CafeAdapter(getApplicationContext(),mangcafe);
        spAdapter = new SanphamAdapter(getApplicationContext(), mangcafe);
       // lvcafe.setAdapter(cafeAdapter);
        rvSanPham.setAdapter(spAdapter);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerView = inflater.inflate(R.layout.progressbar,null);
        mHandler = new mHandler();
    }

    private void GetIdloaisanpham() {
         idcafe = getIntent().getIntExtra("idloaisanpham", -1);
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbarcafe);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarcafe.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void GetData(int Page) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String duongdan = Server.Duongdangetsanpham+String.valueOf(Page);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongdan,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        int id = 0;
                        String Tencafe = "";
                        int Giacafe = 0;
                        String Hinhanhcafe = "";
                        String Motacafe = "";
                        int Idspcafe = 0;
                        if(response != null && response.length() != 2){  //khac []
                            lvcafe.removeFooterView(footerView);
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                for (int i = 0; i < response.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    id = jsonObject.getInt("id");
                                    Tencafe = jsonObject.getString("tensp");
                                    Giacafe = jsonObject.getInt("giasp");
                                    Hinhanhcafe = jsonObject.getString("hinhanhsp");
                                    Motacafe = jsonObject.getString("motasp");
                                    Idspcafe = jsonObject.getInt("idsanpham");
                                    Log.d("sontit",Tencafe);
                                    mangcafe.add(new Sanpham(id,Tencafe,Giacafe,Hinhanhcafe,Motacafe,Idspcafe));

                                    cafeAdapter.notifyDataSetChanged();
                                }
                                spAdapter.notifyDataSetChanged();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }else{
                            limitdata = true;
                            lvcafe.removeFooterView(footerView);
                            CheckConnection.ShowToast_Short(getApplicationContext(),"Đã hết dữ liệu");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError { //day du lieu len
                HashMap<String,String > param = new HashMap<String, String>();
                param.put("idsanpham",String.valueOf(idcafe)); //key de giong trong file getsanpham.php
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void LoadMoreData() {
        lvcafe.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),ChiTietSanPham.class);
                intent.putExtra("thongtinsanpham",mangcafe.get(position));
                startActivity(intent);
            }
        });
        lvcafe.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount != 0
                        && isLoading == false &&limitdata == false){
                    isLoading = true;
                    ThreadData threadData = new ThreadData();
                    threadData.start();
                }
            }
        });
    }
    public class mHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 0:
                    lvcafe.addFooterView(footerView);
                    break;
                case 1:
                    GetData(++page);
                    isLoading = false;
                    break;
            }
            super.handleMessage(msg);
        }
    }

    public class ThreadData extends  Thread{
        @Override
        public void run() {
            mHandler.sendEmptyMessage(0);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message = mHandler.obtainMessage(1);
            mHandler.sendMessage(message);
            super.run();
        }
    }
}

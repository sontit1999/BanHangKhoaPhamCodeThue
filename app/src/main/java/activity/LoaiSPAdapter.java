package activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doantrasua.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import callback.MenuCallback;
import model.Loaisp;

public class LoaiSPAdapter extends RecyclerView.Adapter<LoaiSPAdapter.MyviewHodel> {
    Context context;
    ArrayList<Loaisp> list = new ArrayList<>();

    public void setCallback(MenuCallback callback) {
        this.callback = callback;
    }

    MenuCallback callback;

    public LoaiSPAdapter() {
    }

    @NonNull
    @Override
    public MyviewHodel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View heroView = inflater.inflate(R.layout.dong_listview_loaisp, parent, false);

        return  new MyviewHodel(heroView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHodel holder, int position) {
        final Loaisp loaisp = list.get(position);
        holder.binData(loaisp);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(callback!=null){
                    callback.onItemClick(loaisp);
                }
            }
        });
    }

    public LoaiSPAdapter(Context context) {
        this.context = context;
    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(ArrayList<Loaisp> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public class  MyviewHodel extends RecyclerView.ViewHolder{
        ImageView ivLoaisp;
        TextView tvLoaisp;
        public MyviewHodel(@NonNull View itemView) {
            super(itemView);
            ivLoaisp = (ImageView) itemView.findViewById(R.id.imageviewloaisp);
            tvLoaisp = (TextView) itemView.findViewById(R.id.textviewloaisp);
        }
        public void binData(Loaisp lsp){
            Picasso.get().load(lsp.getHinhanhloaisp())
                    .placeholder(android.R.drawable.ic_menu_camera)
                    .error(R.drawable.cancel)
                    .into(ivLoaisp);
            tvLoaisp.setText(lsp.Tenloaisp);
        }
    }
}

package com.abdu.teha.mycompanytesting03.User;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import com.abdu.teha.mycompanytesting03.R;
/**
 * Created by TEha on 4/24/2018.
 */

public class RecentProductAdapter extends ArrayAdapter {

    private Context context;
    public RecentProductAdapter(Context context, int resource) {
        super(context, resource);
        this.context = context;
    }

    private static class DataHandler{
        TextView recentPname, recentCname;
    }

    private List list = new ArrayList();

    @Override
    public void add(Object object) {
        super.add(object);
        list.add(object);
    }
    @Override
    public int getCount() {
        return this.list.size();
    }
    @Override
    public Object getItem(int position) {
        return this.list.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {
        View row;
        row = convertView;
        final DataHandler handler;
        if (convertView == null) {
            /* yb2a na 3mlt Inflater 3l4an L Views elli h3mlha gdeda tm4i 3la nfs L Style bta3 L parent elli h3rf L Style bta3o mn L Inflater*/
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.custom_recent_product_layout, parent, false);
            handler = new DataHandler();
            handler.recentPname = (TextView) row.findViewById(R.id.recentName);
            handler.recentCname = (TextView) row.findViewById(R.id.recentCompany);
            row.setTag(handler);
        }
        else {
            handler = (DataHandler) row.getTag();
        }
        /*
        w b3d ma b2a m3aya L Tag elli hwa L View y3ni --> h7ot feh L Data ell na 3ayzha 2w 23ml feh elli na 3ayzo
         */
        final RecentProductData recentProductData;
        recentProductData = (RecentProductData) this.getItem(position);
        handler.recentPname.setText(recentProductData.getRecentPname());
        handler.recentCname.setText(recentProductData.getRecentCname());
        handler.recentPname.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProductInfo.class);
                intent.putExtra("product_id", Integer.toString(recentProductData.getRecentProd_id()));
                intent.putExtra("company_id", Integer.toString(recentProductData.getRecentCom_id()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        handler.recentCname.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CompanyInfo.class);
                intent.putExtra("company_id", Integer.toString(recentProductData.getRecentCom_id()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        return row;
        /*
        w 5li balk --> kol L Functions elli fi l Class da bttnfz Automatic kol elli btb3to hwa L 7aga bta3t L Constructor bs
         */
    }

}

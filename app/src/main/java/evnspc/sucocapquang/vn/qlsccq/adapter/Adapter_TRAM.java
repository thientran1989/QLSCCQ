package evnspc.sucocapquang.vn.qlsccq.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.thtsoftlib.function.Thtcovert;

import java.util.ArrayList;
import java.util.List;

import evnspc.sucocapquang.vn.qlsccq.R;
import evnspc.sucocapquang.vn.qlsccq.object.Obj_TRAM;


public class Adapter_TRAM extends
        RecyclerView.Adapter<Adapter_TRAM.ContactViewHolder> {
    private List<Obj_TRAM> contactList;
    private List<Obj_TRAM> all_contactList;
    Context mcon;

    public Adapter_TRAM(Context mcon,List<Obj_TRAM> all_contactList) {
        this.mcon=mcon;
        this.all_contactList = all_contactList;
        this.contactList = new ArrayList<Obj_TRAM>(this.all_contactList);
    }
    public void get_search(String key){
        contactList.clear();
        if (key.length()>0){
            for (Obj_TRAM oMBA : all_contactList){
                if (oMBA.getTEN_TRAM().contains(key)){
                    contactList.add(oMBA);
                }
            }
        }else{
            contactList = new ArrayList<Obj_TRAM>(this.all_contactList);
        }
        notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    @Override
    public void onBindViewHolder(ContactViewHolder contactViewHolder, int i) {
        final Obj_TRAM ci = contactList.get(i);

        contactViewHolder.tv_matram.setText(ci.getMA_TRAM());
        contactViewHolder.tv_tentram.setText(ci.getTEN_TRAM());
        contactViewHolder.tv_loaitram.setText(ci.getLOAI_TRAM_label());


        contactViewHolder.setClickListener(new ContactViewHolder.ClickListener() {
            public void onClick(View v, int pos, boolean isLongClick) {
                if (isLongClick) {
                    Toast.makeText(mcon, "longclick " + ci.getTEN_TRAM(), Toast.LENGTH_LONG).show();
                } else {
                    String TT = "thong tin ";
                    TT=TT+"Tại trụ :"+ci.getTEN_TRAM()+"\n";
//                    TT=TT+"MSTS :"+ci.getMSTS()+"\n";
//                    TT=TT+"Thuộc kho :"+ci.getKho()+"\n";
//                    TT=TT+"Nhà sx :"+ci.getNha_sanxuat()+"\n";
//                    TT=TT+"Công suất :"+ci.getCong_suat()+"\n";
//                    TT=TT+"Nấc vận hành :"+ci.getNac_vanhanh()+"\n";
                    showBasicLongContent(TT);
                }
            }
        });
    }
    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.cardview_row_tram, viewGroup, false);
        return new ContactViewHolder(itemView);
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{
        public TextView tv_matram;
        public TextView tv_tentram;
        public TextView tv_loaitram;

        private ClickListener clickListener;

        public ContactViewHolder(View v) {
            super(v);

           tv_matram = (TextView) v
                    .findViewById(R.id.tv_matram_cardview_tram);
           tv_tentram = (TextView) v
                    .findViewById(R.id.tv_tentram_cardview_tram);
            tv_loaitram = (TextView) v
                    .findViewById(R.id.tv_loaitram_cardview_tram);


            v.setOnClickListener(this);
        }
        /* Interface for handling clicks - both normal and long ones. */
        public interface ClickListener {
            public void onClick(View v, int position, boolean isLongClick);

        }

        /* Setter for listener. */
        public void setClickListener(ClickListener clickListener) {
            this.clickListener = clickListener;
        }

        @Override
        public void onClick(View v) {
            // If not long clicked, pass last variable as false.
            clickListener.onClick(v, getPosition(), false);
        }
        @Override
        public boolean onLongClick(View v) {

            // If long clicked, passed last variable as true.
            clickListener.onClick(v, getPosition(), true);
            return true;
        }


    }
    private void showBasicLongContent(String thongtin) {
        new MaterialDialog.Builder(mcon)
                .title(R.string.thong_tin_may)
                .content(thongtin)
                    .positiveText(R.string.dong)
                .negativeText(R.string.sua)
                .show();
    }
}

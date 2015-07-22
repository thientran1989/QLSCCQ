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
import evnspc.sucocapquang.vn.qlsccq.object.Obj_TUYEN;


public class Adapter_TUYEN extends
        RecyclerView.Adapter<Adapter_TUYEN.ContactViewHolder> {
    private List<Obj_TUYEN> contactList;
    private List<Obj_TUYEN> all_contactList;
    Context mcon;

    public Adapter_TUYEN(Context mcon,List<Obj_TUYEN> all_contactList) {
        this.mcon=mcon;
        this.all_contactList = all_contactList;
        this.contactList = new ArrayList<Obj_TUYEN>(this.all_contactList);
    }
    public void get_search(String key){
        contactList.clear();
        if (key.length()>0){
            for (Obj_TUYEN oMBA : all_contactList){
                if (oMBA.getTEN_TUYEN().contains(key)){
                    contactList.add(oMBA);
                }
            }
        }else{
            contactList = new ArrayList<Obj_TUYEN>(this.all_contactList);
        }
        notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    @Override
    public void onBindViewHolder(ContactViewHolder contactViewHolder, int i) {
        final Obj_TUYEN ci = contactList.get(i);
        contactViewHolder.tv_tentuyen.setText(ci.getTEN_TUYEN());
        contactViewHolder.tv_loaicap.setText(ci.getLOAI_CAP_label());
        contactViewHolder.tv_chieudai.setText(ci.getCHIEU_DAI());
        contactViewHolder.tv_ttsoi.setText(ci.getTT_SOI());

        contactViewHolder.setClickListener(new ContactViewHolder.ClickListener() {
            public void onClick(View v, int pos, boolean isLongClick) {
                if (isLongClick) {
                    Toast.makeText(mcon, "longclick " + ci.getTEN_TUYEN(), Toast.LENGTH_LONG).show();
                } else {
                    String TT = "thong tin ";
                    TT=TT+ci.getTEN_TUYEN()+"\n";
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
                inflate(R.layout.cardview_row_tuyen, viewGroup, false);
        return new ContactViewHolder(itemView);
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{
        public TextView tv_tentuyen;
        public TextView tv_loaicap;
        public TextView tv_chieudai;
        public TextView tv_ttsoi;

        private ClickListener clickListener;

        public ContactViewHolder(View v) {
            super(v);
           tv_tentuyen = (TextView) v
                    .findViewById(R.id.tv_tentuyen_cardview_tuyen);
            tv_loaicap = (TextView) v
                    .findViewById(R.id.tv_loaicap_cardview_tuyen);
           tv_chieudai = (TextView) v
                    .findViewById(R.id.tv_chieudai_cardview_tuyen);
            tv_ttsoi = (TextView) v
                    .findViewById(R.id.tv_ttsoi_cardview_tuyen);


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

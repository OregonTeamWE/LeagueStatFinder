package com.example.searchlol.adapter;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.searchlol.R;
import com.example.searchlol.TFTFragment;
import com.example.searchlol.dataclass.TFTData.MatchDto;
import com.example.searchlol.dataclass.TFTData.ParticipantDto;
import com.example.searchlol.dataclass.TFTData.units;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TFTHistoryAdapter extends RecyclerView.Adapter<TFTHistoryAdapter.TFTHistoryViewHolder> {
    private ArrayList<MatchDto> mMatchInfo;
    private String mypuuid;
    private final String TAG= TFTHistoryAdapter.class.getSimpleName();
    private ArrayList<String> specialChampList= new ArrayList<String>();
    private OnNoteListener mOnNoteListener;

    public TFTHistoryAdapter(ArrayList<MatchDto> dto,OnNoteListener onNoteListener){
        this.mMatchInfo = dto;
        this.mOnNoteListener  = onNoteListener;
        specialChampList.add("tft3_nautilus");
        specialChampList.add("tft3_riven");
        specialChampList.add("tft3_janna");
        specialChampList.add("tft3_vayne");
        specialChampList.add("tft3_zed");
        specialChampList.add("tft3_viktor");
        specialChampList.add("tft2_brand");
        specialChampList.add("tft2_varus");
        specialChampList.add("tft2_zed");
        specialChampList.add("tft3_gnar");
        specialChampList.add("tft3_teemo");
        specialChampList.add("tft3_cassiopeia");
        specialChampList.add("tft3_urgot");
        specialChampList.add("tft3_kogmaw");
        specialChampList.add("tft3_illaoi");
        specialChampList.add("tft3_nocturne");
        specialChampList.add("tft3_bard");
    }

    public void updateMatchinfo(ArrayList<MatchDto> info, String puuid) {
        mMatchInfo = info;
        mypuuid = puuid;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TFTHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.matchtft_detail, parent, false);
        return new TFTHistoryViewHolder(itemView, mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TFTHistoryViewHolder holder, int position) {
        holder.bind(mMatchInfo.get(position));
    }

    @Override
    public int getItemCount() {
        if (mMatchInfo != null) {
            return mMatchInfo.size();
        } else {
            return 0;
        }
    }

    public interface OnNoteListener{
        void onNoteClick(int position);
    }

    class TFTHistoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView placement;
        private TextView lastTime;
        private TextView dateTime;
        private TextView mLevel;
        private ImageView companionIcon;
        private ImageView item1;
        private ImageView item2;
        private ImageView item3;
        private ImageView item4;
        private ImageView item5;
        private ImageView item6;
        private ImageView item7;
        private ImageView item8;
        private ImageView item9;
        private CardView linearLayout;
        OnNoteListener onNoteListener;

        public TFTHistoryViewHolder(View itemView, OnNoteListener onNoteListener) {
            super(itemView);
            this.onNoteListener = onNoteListener;

            placement = itemView.findViewById(R.id.tft_placement);
            lastTime = itemView.findViewById(R.id.tft_lasttime);
            companionIcon = itemView.findViewById(R.id.ivtft_match_champ);
            dateTime = itemView.findViewById(R.id.tft_datetime);
            mLevel = itemView.findViewById(R.id.mytftlevel);

            item1 = itemView.findViewById(R.id.iv_tftchamp_1);
            item2 = itemView.findViewById(R.id.iv_tftchamp_2);
            item3 = itemView.findViewById(R.id.iv_tftchamp_3);
            item4 = itemView.findViewById(R.id.iv_tftchamp_4);
            item5 = itemView.findViewById(R.id.iv_tftchamp_5);
            item6 = itemView.findViewById(R.id.iv_tftchamp_6);
            item7 = itemView.findViewById(R.id.iv_tftchamp_7);
            item8 = itemView.findViewById(R.id.iv_tftchamp_8);
            item9 = itemView.findViewById(R.id.iv_tftchamp_9);

            linearLayout = itemView.findViewById(R.id.matchtft_layout);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onNoteListener.onNoteClick(getAdapterPosition());
        }

        public void bind(MatchDto info) {
            lastTime.setText(convertTimeLength(info.info.game_length));
            dateTime.setText(receiveYearlyDate(info.info.game_datetime));
            ParticipantDto mypart = new ParticipantDto();
            String companionId="";
            TFTFragment myComp= new TFTFragment();

            String mypic="";
            for(ParticipantDto each : info.info.participants){
                if(String.valueOf(each.puuid).equals(mypuuid)){
                    mypart=each;
                    companionId= each.companion.content_ID;
                    mypic=myComp.getCompanionUrl(companionId);
                    break;
                }
            }

            placement.setText("Placement: " + String.valueOf(mypart.placement));
            mLevel.setText(String.valueOf(mypart.level));
            Glide.with(companionIcon.getContext()).load(mypic).into(companionIcon);
            long versiondate=1584499963000L;
            long gamedate= info.info.game_datetime;

            ArrayList<String> mylist=new ArrayList<String>();

            for(units unit : mypart.units){
                mylist.add(String.valueOf(unit.character_id).toLowerCase());
            }
            int listSize=mylist.size();

            if(0<listSize) {
                String thischampname= mylist.get(0);
                String champIconUrl = buildUrlPath(thischampname,gamedate>versiondate);
                if(champIconUrl==null) champIconUrl= buildUniquePath(thischampname,gamedate>versiondate);
                Glide.with(item1.getContext()).load(champIconUrl).into(item1);
                Log.d("TAG", "icon URL: " + champIconUrl);
            }

            if(1<listSize) {
                String thischampname= mylist.get(1);
                String champIconUrl2 = buildUrlPath(thischampname,gamedate>versiondate);
                if(champIconUrl2==null) champIconUrl2= buildUniquePath(thischampname,gamedate>versiondate);
                Glide.with(item2.getContext()).load(champIconUrl2).into(item2);
                Log.d("TAG", "icon URL: " + champIconUrl2);
            }


            if(2<listSize) {
                String thischampname= mylist.get(2);
                String champIconUrl3 = buildUrlPath(thischampname,gamedate>versiondate);
                if(champIconUrl3==null) champIconUrl3= buildUniquePath(thischampname,gamedate>versiondate);
                Glide.with(item3.getContext()).load(champIconUrl3).into(item3);
                Log.d("TAG", "icon URL: " + champIconUrl3);
            }

            if(3<listSize){
                String thischampname= mylist.get(3);
                String champIconUrl4 = buildUrlPath(thischampname,gamedate>versiondate);
                if(champIconUrl4==null) champIconUrl4= buildUniquePath(thischampname,gamedate>versiondate);
                Glide.with(item4.getContext()).load(champIconUrl4).into(item4);
            }

            if(4<listSize) {
                String thischampname= mylist.get(4);
                String champIconUrl5 = buildUrlPath(thischampname,gamedate>versiondate);
                if(champIconUrl5==null) champIconUrl5= buildUniquePath(thischampname,gamedate>versiondate);
                Glide.with(item5.getContext()).load(champIconUrl5).into(item5);
                Log.d("TAG", "icon URL: " + champIconUrl5);
            }

            if(5<listSize) {
                String thischampname= mylist.get(5);
                String champIconUrl6 = buildUrlPath(thischampname,gamedate>versiondate);
                if(champIconUrl6==null) champIconUrl6= buildUniquePath(thischampname,gamedate>versiondate);
                Glide.with(item6.getContext()).load(champIconUrl6).into(item6);
                Log.d("TAG", "icon URL: " + champIconUrl6);
            }

            if(6<listSize) {
                String thischampname= mylist.get(6);
                String champIconUrl7 = buildUrlPath(thischampname,gamedate>versiondate);
                if(champIconUrl7==null) champIconUrl7= buildUniquePath(thischampname,gamedate>versiondate);
                Glide.with(item7.getContext()).load(champIconUrl7).into(item7);
                Log.d("TAG", "icon URL: " + champIconUrl7);
            }

            if(7<listSize) {
                String thischampname= mylist.get(7);
                String champIconUrl8 = buildUrlPath(thischampname,gamedate>versiondate);
                if(champIconUrl8==null) champIconUrl8= buildUniquePath(thischampname,gamedate>versiondate);
                Glide.with(item8.getContext()).load(champIconUrl8).into(item8);
                Log.d("TAG", "icon URL: " + champIconUrl8);
            }

            if(8<listSize) {
                String thischampname= mylist.get(8);
                String champIconUrl9 = buildUrlPath(thischampname,gamedate>versiondate);
                if(champIconUrl9==null) champIconUrl9= buildUniquePath(thischampname,gamedate>versiondate);
                Glide.with(item9.getContext()).load(champIconUrl9).into(item9);
                Log.d("TAG", "icon URL: " + champIconUrl9);
            }

            if (mypart.placement ==1) {
                Log.d("TAG","This is the proof");
                linearLayout.setCardBackgroundColor(Color.argb(255, 245, 215, 66));//245, 215, 66
            } else if(mypart.placement ==2){
                linearLayout.setCardBackgroundColor(Color.argb(255, 192, 192, 192));//184, 182, 173
            } else if(mypart.placement ==3){
                linearLayout.setCardBackgroundColor(Color.argb(255, 171, 98, 31));//171, 98, 31
            } else{
                linearLayout.setCardBackgroundColor(Color.argb(100, 0, 0, 0));//171, 98, 31
            }

        }

    }

    public String receiveYearlyDate(long theDate){
        Date date = new Date(theDate);
        String myDate;
        Format format = new SimpleDateFormat("MM/dd/yyyy");//yyyy MM dd HH:mm:ss
        myDate= format.format(date);
        return "Date: "+ myDate;
    }

    public String convertTimeLength(float myTime){
        String finalTime= String.valueOf(myTime/60);

        finalTime=finalTime.substring(0,2)+":"+ finalTime.substring(3,5);
        return "Duration: "+finalTime;
    }

    public String buildUrlPath(String thischampname,boolean version){
        String champIconUrl="";
        if(version==true) {
            if(specialChampList.contains(thischampname)){
                champIconUrl = "https://raw.communitydragon.org/pbe/plugins/rcp-be-lol-game-data/global/default/assets/characters/" +
                        thischampname + "/hud/"+ thischampname + "_square.tft_set3_act2.png";
            }else {
                champIconUrl = "https://raw.communitydragon.org/pbe/plugins/rcp-be-lol-game-data/global/default/assets/characters/" +
                        thischampname + "/hud/" + thischampname + "_square.tft_set3.png";
            }

            if(thischampname.equals("tft3_xerath")){
                champIconUrl = "https://raw.communitydragon.org/pbe/plugins/rcp-be-lol-game-data/global/default/assets/characters/" +
                        thischampname + "/hud/" + thischampname + "_square.tft3_set3_xerath.png";
            }
            if(thischampname.equals("tft3_chogath")){
                champIconUrl = "https://raw.communitydragon.org/pbe/plugins/rcp-be-lol-game-data/global/default/assets/characters/" +
                        thischampname + "/hud/" + "tft_greenterror_square.tft_set3.png";
            }

        }else if(version==false){
            if(specialChampList.contains(thischampname)){
                champIconUrl = "https://raw.communitydragon.org/pbe/plugins/rcp-be-lol-game-data/global/default/assets/characters/" +
                        thischampname + "/hud/"+ thischampname + "_square_0.tft_set2.png";
            }else {
                champIconUrl = "https://raw.communitydragon.org/pbe/plugins/rcp-be-lol-game-data/global/default/assets/characters/" +
                        thischampname + "/hud/" + thischampname + "_square.tft_set2.png";
            }
        }

        return champIconUrl;
    }

    public String buildUniquePath(String thischampname,boolean version){
        String champIconUrl="";
        if (version == true) {
            champIconUrl = "https://raw.communitydragon.org/pbe/plugins/rcp-be-lol-game-data/global/default/assets/characters/" +
                    thischampname + "/hud/" + thischampname + "_square_0.tft_set3.png";
        } else if (version == false) {
            champIconUrl = "https://raw.communitydragon.org/pbe/plugins/rcp-be-lol-game-data/global/default/assets/characters/" +
                    thischampname + "/hud/" + thischampname + "_square_0.tft_set2.png";
        }
        return champIconUrl;
    }



}

